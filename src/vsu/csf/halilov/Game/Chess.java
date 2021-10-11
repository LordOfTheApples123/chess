package vsu.csf.halilov.Game;

import vsu.csf.halilov.CLI.ConsoleInterface;
import vsu.csf.halilov.Pieces.*;
import vsu.csf.halilov.enums.GameState;
import vsu.csf.halilov.enums.PColor;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Chess {
    private final Map<PColor, Square> kings;
    private PColor checked;
    private GameState gamestate;
    private final Square[][] board;
    private final int BOARD_SIZE = 8;
    private final String HELP = "Enter coordinates in the next order: a3 b6, \nwhere a3 - is starting square and b6 is target square";
    private PColor currPlayer;

    public Chess() {
        this.board = createBoard();
        this.gamestate = GameState.NOT_PlAYING;
        this.checked = null;
        kings = new HashMap<>();
    }

    public void game() {
        this.gamestate = GameState.PLAYING;
        this.currPlayer = PColor.WHITE;
        System.out.println(HELP);
        while(gamestate != GameState.MATE){
            String input = ConsoleInterface.inputHandling();
            Square startingSquareInput = ConsoleInterface.getStartingSquareFromString(input);
            Square targetSquareInput = ConsoleInterface.getTargetSquareFromString(input);

            //getting actual squares out of coords
            Square startingSquare = this.board[startingSquareInput.getRow()][startingSquareInput.getCol()];
            Square targetSquare = this.board[targetSquareInput.getRow()][targetSquareInput.getCol()];

            while(!combinedMoveChecking(startingSquare, targetSquare)) {
                input = ConsoleInterface.inputHandling();
                startingSquareInput = ConsoleInterface.getStartingSquareFromString(input);
                targetSquareInput = ConsoleInterface.getTargetSquareFromString(input);

                //getting actual squares out of coords
                startingSquare = this.board[startingSquareInput.getRow()][startingSquareInput.getCol()];
                targetSquare = this.board[targetSquareInput.getRow()][targetSquareInput.getCol()];
            }

            move(startingSquare, targetSquare);
        }
    }

    private void end(){
        this.gamestate = GameState.MATE;
        System.out.println(currPlayer.toString() + " won");
    }



    private void move(Square startingSquare, Square targetSquare) {

        if(startingSquare.getPiece().getPieceId().equals("K")){
            kings.replace(currPlayer, targetSquare);
        }

        Piece.move(this.board, startingSquare, targetSquare);
        if(isMate()){
            end();
            return;
        }
        if(isCheck()){
            this.checked = currPlayer.getOpos();
        } else this.checked = null;

    }

    private boolean combinedMoveChecking(Square startingSquare, Square targetSquare){
        return isMoveCorrect(startingSquare, targetSquare) &&
                isMoveLegal(startingSquare, targetSquare) &&
                isMovePossible(startingSquare, targetSquare) &&
                Piece.isSquareInBounds(startingSquare) &&
                Piece.isSquareInBounds(targetSquare);
    }

    private boolean isMoveCorrect(Square startingSquare, Square targetSquare){
        if(startingSquare.getPiece() == null){
            return false;
        }
        if(startingSquare.getPieceColor() != currPlayer){
            return false;
        }
        return targetSquare.getPieceColor() != currPlayer;

    }

    private boolean isMovePossible(Square startingSquare, Square targetSquare){
        return startingSquare.getPiece().isMovePossible(this.board, startingSquare, targetSquare);
    }

    private boolean isMoveLegal(Square startingSquare, Square targetSquare){
        Square[][] testBoard = this.cloneBoard();
        testBoard[targetSquare.getRow()][targetSquare.getCol()].setPiece(startingSquare.getPiece());
        testBoard[startingSquare.getRow()][startingSquare.getCol()].setPiece(null);
        return King.getCheckCount(testBoard, this.kings.get(currPlayer)) == 0;
    }

    public boolean isCheck(){
        PColor inDangerPlayer = currPlayer.getOpos();
        Square kingSquare = kings.get(inDangerPlayer);
        return King.getCheckCount(this.board, kingSquare) > 0;
    }

    public boolean isMate(){
        PColor inDangerPlayer = currPlayer.getOpos();
        Square kingSquare = this.kings.get(inDangerPlayer);
        if(King.canKingMove(this.board, kingSquare)){
            return false;
        }
        if(King.isKnightCheck(this.board, kingSquare)){
            return true;
        }
        if(King.getCheckCount(this.board, kingSquare)>1){
            return true;
        }
        return canPiecesBlockCheck();
    }

    private boolean canPiecesBlockCheck(){
        PColor inDangerPlayer = currPlayer.getOpos();
        Square kingSquare = kings.get(inDangerPlayer);
        Square checkingPieceSquare = King.getCheckingPieceForBlock(this.board, kingSquare);
        if(checkingPieceSquare == null){
            System.out.println("checkingPiece is null");
            System.exit(20);
        }
        Set<Square> defenders = getDefendersSetOfTheColor(inDangerPlayer);
        for(Square lineSquare: Square.getTrajectory(board, kingSquare, checkingPieceSquare)){
            for(Square defender: defenders){
                if(defender.getPiece().isMovePossible(board, defender, lineSquare)){
                    return true;
                }
            }
        }
        return false;
    }

    public Set<Square> getDefendersSetOfTheColor(PColor color){
        Set<Square> res = new HashSet<>();
        for(Square[] squareArray: this.board){
            for(Square sq: squareArray){
                if(sq.getPiece() != null) {
                    if (sq.getPieceColor() == color && !sq.getPiece().getPieceId().equals("K")) {
                        res.add(sq);
                    }
                }
            }
        }
        return res;
    }

    private void PlacePiecesOnDefaultSpots(Square[][] board) {
        PColor white = PColor.WHITE;
        PColor black = PColor.BLACK;
        //white placement
        board[0][0].setPiece(new Rook(white));
        board[0][1].setPiece(new Knight(white));
        board[0][2].setPiece(new Bishop(white));
        board[0][3].setPiece(new Queen(white));
        board[0][4].setPiece(new King(white));
        this.kings.put(PColor.WHITE, this.board[0][4]);
        board[0][5].setPiece(new Bishop(white));
        board[0][6].setPiece(new Knight(white));
        board[0][7].setPiece(new Rook(white));
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[1][i].setPiece(new Pawn(white));
        }

        //black placement
        board[7][0].setPiece(new Rook(black));
        board[7][1].setPiece(new Knight(black));
        board[7][2].setPiece(new Bishop(black));
        board[7][3].setPiece(new King(black));
        this.kings.put(PColor.BLACK, board[7][3]);
        board[7][4].setPiece(new Queen(black));
        board[7][5].setPiece(new Bishop(black));
        board[7][6].setPiece(new Knight(black));
        board[7][7].setPiece(new Rook(black));
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[6][i].setPiece(new Pawn(black));
        }

        //blank sq placement

        for(int i = 2; i < BOARD_SIZE-2; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                board[i][j].setPiece(null);
            }
        }
    }

    private Square[][] createBoard() {
        Color brown = new Color(181, 136, 99);
        Color pale = new Color(240, 217, 181);
        Square[][] board = new Square[BOARD_SIZE][BOARD_SIZE];
        //(i+j)%2 == 0 ==> color.BLACK
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ((i + j) % 2 == 0) {
                    board[i][j] = new Square(i, j, brown);
                } else board[i][j] = new Square(i, j, pale);
            }
        }
        PlacePiecesOnDefaultSpots(board);
        return board;
    }

    private Square[][] cloneBoard(){
        Square[][] res = new Square[this.board.length][this.board.length];
        for(int i = 0; i < this.board.length; i++){
            for(int j = 0; j < this.board.length; j++){
                res[i][j] = new Square(i, j, this.board[i][j].getPiece());
            }
        }
        return res;
    }
}
