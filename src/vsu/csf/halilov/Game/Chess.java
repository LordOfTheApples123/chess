package vsu.csf.halilov.Game;

import vsu.csf.halilov.Pieces.*;
import vsu.csf.halilov.enums.GameState;
import vsu.csf.halilov.enums.PColor;

import java.awt.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Chess {
    private Map<PColor, Square> kings;
    private PColor checked;
    private GameState gamestate;
    private final Square[][] board;
    private final int BOARD_SIZE = 8;

    public Chess() {
        this.board = createBoard();
        this.gamestate = GameState.NOT_STARTED;
        this.checked = null;
    }

    public void start() {
        this.gamestate = GameState.PLAYING;
        //move()
    }

    public void wrongMoveHandling(){
        System.out.println("Неправильный ход, повторите попытку: ");
        //TODO input
    }

    public void move(PColor currPlayer, Square startingSquare, Square targetSquare) {
        while(!combinedMoveChecking(currPlayer, startingSquare, targetSquare)){
            wrongMoveHandling();
        }

        Piece.move(this.board, startingSquare, targetSquare);
        if(isMate(currPlayer.getOpos())){
            //TODO end(PColor winnerColor);
            //return;
        }
        if(isCheck(currPlayer.getOpos())){
            this.checked = currPlayer.getOpos();
        } else this.checked = null;

        nextMove(currPlayer.getOpos());
    }

    public void nextMove(PColor currPlayer){
        /*
        TODO input;
        move(currPlayer, startingSquare, targetSquare);
        */
    }

    private boolean combinedMoveChecking(PColor currPlayer, Square startingSquare, Square targetSquare){
        return isMoveCorrect(currPlayer, startingSquare, targetSquare) &&
                isMoveLegal(currPlayer, startingSquare, targetSquare) &&
                isMovePossible(startingSquare, targetSquare) &&
                Piece.isSquareInBounds(startingSquare) &&
                Piece.isSquareInBounds(targetSquare);
    }

    private boolean isMoveCorrect(PColor currPlayer, Square startingSquare, Square targetSquare){
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

    private boolean isMoveLegal(PColor currPlayer, Square startingSquare, Square targetSquare){
        Square[][] testBoard = this.cloneBoard();
        testBoard[targetSquare.getRow()][targetSquare.getCol()].setPiece(startingSquare.getPiece());
        testBoard[startingSquare.getRow()][startingSquare.getCol()].setPiece(null);
        return King.getCheckCount(testBoard, this.kings.get(currPlayer)) == 0;
    }

    public boolean isCheck(PColor inDangerPlayer){
        Square kingSquare = kings.get(inDangerPlayer);
        return King.getCheckCount(this.board, kingSquare) > 0;
    }

    public boolean isMate(PColor inDangerPlayer){
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
        return canPiecesBlockCheck(inDangerPlayer);
    }

    private boolean canPiecesBlockCheck(PColor inDangerPlayer){
        Square kingSquare = kings.get(inDangerPlayer);
        Square checkingPieceSquare = King.getCheckingPieceForBlock(this.board, kingSquare);
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
        PColor white = PColor.WHITE;
        PColor black = PColor.BLACK;
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
