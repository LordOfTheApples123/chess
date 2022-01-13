package vsu.csf.halilov.Game;

import vsu.csf.halilov.CLI.ConsoleInterface;
import vsu.csf.halilov.GUI.ChessState;
import vsu.csf.halilov.Pieces.*;
import vsu.csf.halilov.enums.GameState;
import vsu.csf.halilov.enums.PColor;
import vsu.csf.halilov.utils.DebugUtils;

import java.awt.*;
import java.util.*;

import static vsu.csf.halilov.enums.PColor.to1LetterString;

public class Chess {
    private final Map<PColor, Square> kings = new HashMap<>();
    private PColor checked;
    private GameState gamestate;
    private final Square[][] board = new Square[BOARD_SIZE][BOARD_SIZE];
    private static final int BOARD_SIZE = 8;
    private final String HELP = "Enter coordinates in the next order: a3 b6, \nwhere a3 - is starting square and b6 is target square";
    private PColor currPlayer;

    public Chess() {
        initializeBoard(board);
        this.gamestate = GameState.PLAYING;
        this.checked = null;
        currPlayer = PColor.WHITE;
    }

    public Square getBoard(int x, int y) {
        return board[x][y];
    }
    public void setBoard(int x, int y, Square square) {
        board[x][y] = square;
    }

    public GameState getGamestate() {
        return gamestate;
    }

    public void setGamestate(GameState gamestate) {
        this.gamestate = gamestate;
    }

    public void setCurrPlayer(PColor currPlayer) {
        this.currPlayer = currPlayer;
    }

    public Chess init(){
        Chess chess= new Chess();
        chess.gamestate = GameState.PLAYING;
        chess.currPlayer = PColor.WHITE;
        return chess;
    }

    public void nextMove(Square[][] board, Square startingSquare, Square targetSquare){

    }

    public Set<Square> getPiecePossibleMoves(Square square){
        if(square.getPiece() == null){
            return new HashSet<>();
        }
        return square.getPiece().getPossibleMoves(board, square);
    }

    public void game() {
        this.gamestate = GameState.PLAYING;
        this.currPlayer = PColor.WHITE;
        System.out.println(HELP);
        while(gamestate != GameState.MATE){
            printBoard();
            String input = ConsoleInterface.inputHandling(currPlayer);
            Square startingSquareInput = ConsoleInterface.getStartingSquareFromString(input);
            Square targetSquareInput = ConsoleInterface.getTargetSquareFromString(input);

            //getting actual squares out of coords
            Square startingSquare = this.board[startingSquareInput.getRow()][startingSquareInput.getCol()];
            Square targetSquare = this.board[targetSquareInput.getRow()][targetSquareInput.getCol()];

            while(!combinedMoveChecking(startingSquare, targetSquare)) {
                System.out.println("Impossible move, try again: ");
                input = ConsoleInterface.inputHandling(currPlayer);
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



    public void move(Square startingSquare, Square targetSquare) {



        Piece.move(this.board, startingSquare, targetSquare);
        if(startingSquare.getPieceId().equals("K")){
            kings.replace(currPlayer, board[targetSquare.getRow()][targetSquare.getCol()]);
        }
        if(isCheck(currPlayer.getOpos()) && isMate()){
            end();
            return;
        }
        this.checked = isCheck(currPlayer.getOpos()) ? currPlayer.getOpos() : null;
        currPlayer = currPlayer.getOpos();

    }

    public char[] randomResponse(){
        Set<Square> res = new HashSet<>();
        Square startingSquare = new Square(0, 0);
        Square targetSquare = new Square(0, 0);
        for(Square[] squareArray: this.board){
            for(Square sq: squareArray){
                if(sq.getPiece() != null) {
                    if (sq.getPieceColor() == currPlayer && !sq.getPiece().getPieceId().equals("K")) {
                        if(!getPiecePossibleMoves(sq).isEmpty()){
                            startingSquare = sq;
                            for(Square target: getPiecePossibleMoves(sq)){
                                targetSquare = target;
                                break;
                            }
                        }
                    }
                }
            }
        }
        String resString = startingSquare.toString() + " " + targetSquare.toString();
        move(startingSquare, targetSquare);
        return resString.toCharArray();
    }

    public boolean combinedMoveChecking(Square startingSquare, Square targetSquare){
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
        int row = startingSquare.getRow();
        int col = startingSquare.getCol();
        Square king = this.kings.get(currPlayer);

        testBoard[targetSquare.getRow()][targetSquare.getCol()].setPiece(startingSquare.getPiece());
        testBoard[startingSquare.getRow()][startingSquare.getCol()].remove();
        if(testBoard[targetSquare.getRow()][targetSquare.getCol()].getPieceId().equals("K")){
            king = testBoard[targetSquare.getRow()][targetSquare.getCol()];
        }
        int checks = King.getCheckCount(testBoard, king);
        return checks == 0;
    }

    public boolean isCheck(PColor inDangerPlayer){
        Square kingSquare = kings.get(inDangerPlayer);
        return King.getCheckCount(this.board, kingSquare) > 0;
    }

    public boolean isMate(){
        PColor inDangerPlayer = currPlayer.getOpos();
        Square kingSquare = this.kings.get(inDangerPlayer);
        if(King.canKingMove(cloneBoard(), kingSquare)){
            return false;
        }
        if(King.isKnightCheck(this.board, kingSquare)){
            return true;
        }
        if(King.getCheckCount(this.board, kingSquare)>1){
            return true;
        }
        return !canPiecesBlockCheck();
    }

    private boolean canPiecesBlockCheck(){
        PColor inDangerPlayer = currPlayer.getOpos();
        Square kingSquare = kings.get(inDangerPlayer);
        Square checkingPieceSquare = King.getCheckingPieceForBlock(this.board, kingSquare);

        if(checkingPieceSquare == null){
            System.out.println("checkingPiece is null");
            System.exit(20);
        }
        Set<Square> trajectory = new HashSet<>();
        if(checkingPieceSquare.getPieceId().equals("n")){
            trajectory.add(checkingPieceSquare);
        } else{
            trajectory.addAll(Square.getTrajectory(board, kingSquare, checkingPieceSquare));
        }
        Set<Square> defenders = getDefendersSetOfTheColor(inDangerPlayer);
        for(Square lineSquare: trajectory){
            for(Square defender: defenders){
                if(combinedMoveChecking(defender, lineSquare)){
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
        board[7][3].setPiece(new Queen(black));
        this.kings.put(PColor.BLACK, board[7][4]);
        board[7][4].setPiece(new King(black));
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

    private void initializeBoard(Square[][] board) {
        Color brown = new Color(181, 136, 99);
        Color pale = new Color(240, 217, 181);
        //(i+j)%2 == 0 ==> color.BLACK
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ((i + j) % 2 == 0) {
                    board[i][j] = new Square(i, j, brown);
                } else board[i][j] = new Square(i, j, pale);
            }
        }
        PlacePiecesOnDefaultSpots(board);
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

    public void printBoard(){
        for(int i = BOARD_SIZE - 1; i >= 0; i--){
            for(int j = 0; j< BOARD_SIZE; j++){
                System.out.print(to1LetterString(board[i][j].getPieceColor()));
                System.out.print(" ");
                System.out.print("|");
            }
            System.out.println(i+1);
            for(int j = 0; j< BOARD_SIZE; j++){
                System.out.print(" ");
                System.out.print(board[i][j].getId());
                System.out.print("|");
            }
            System.out.println();
            System.out.println("________________________");
        }
        System.out.println("a  b  c  d  e  f  g  h");
    }

    public void test1(){
        this.gamestate = GameState.PLAYING;
        this.currPlayer = PColor.WHITE;
        moveForDebug("e2 e3");
        moveForDebug("e7 e6");
        moveForDebug("d1 f3");
        moveForDebug("e6 e5");
        moveForDebug("f1 c4");
        moveForDebug("a7 a6");
        moveForDebug("f3 f7");
        System.out.println(gamestate);

    }

    public void test2(){
        this.gamestate = GameState.PLAYING;
        this.currPlayer = PColor.WHITE;
        moveForDebug("e2 e3");
        moveForDebug("e7 e6");
        moveForDebug("e3 e4");
        moveForDebug("d8 f6");
        moveForDebug("a2 a3");
        moveForDebug("f8 c5");
        moveForDebug("a1 a2");
        moveForDebug("f6 f2");
        System.out.println(gamestate);

    }

    public void testFoolMate(){
        this.gamestate = GameState.PLAYING;
        this.currPlayer = PColor.WHITE;
        moveForDebug("g2 g4");
        moveForDebug("e7 e5");
        moveForDebug("f2 f3");
        moveForDebug("d8 h4");
        System.out.println(gamestate);
    }

    public void testKnightStaleMateAndQueenPin(){
        this.gamestate = GameState.PLAYING;
        this.currPlayer = PColor.WHITE;
        moveForDebug("d2 d4");
        moveForDebug("e7 e5");
        moveForDebug("d4 e5");
        moveForDebug("b8 c6");
        moveForDebug("g1 f3");
        moveForDebug("d8 e7");
        moveForDebug("c2 c4");
        moveForDebug("c6 e5");
        moveForDebug("b1 d2");
        moveForDebug("e5 d3");
        System.out.println(gamestate);
    }

    private void moveForDebug(String input){
        Square startingSquareInput = ConsoleInterface.getStartingSquareFromString(input);
        Square targetSquareInput = ConsoleInterface.getTargetSquareFromString(input);

        //getting actual squares out of coords
        Square startingSquare = this.board[startingSquareInput.getRow()][startingSquareInput.getCol()];
        Square targetSquare = this.board[targetSquareInput.getRow()][targetSquareInput.getCol()];

        move(startingSquare, targetSquare);
        printBoard();
        currPlayer = currPlayer.getOpos();
    }

    public void testDoubleCheckMate(){
        String fen = "2pkpn2/2p1p3/2r5/8/2bN4/8/4q3/3R3K";
        this.gamestate = GameState.PLAYING;
        this.currPlayer = PColor.WHITE;
        DebugUtils.fenToBoard(fen, board, kings);
        printBoard();
        moveForDebug("d4 e6");
        System.out.println(gamestate);


    }

    public PColor getCurrPlayer() {
        return currPlayer;
    }

    public void updateChessState(ChessState chessState) {
        chessState.setCurrCheck(checked);
        chessState.setCurrPlayer(currPlayer);
        chessState.setGameState(gamestate);
    }

    public Square[][] getBoard() {
        return board;
    }
}
