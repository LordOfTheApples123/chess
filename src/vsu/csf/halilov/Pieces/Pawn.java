package vsu.csf.halilov.Pieces;

import vsu.csf.halilov.enums.PColor;

public class Pawn extends Piece {
    boolean firstMove = true;
    int delta;

    public Pawn(PColor color) {
        super(color, "P");
        this.delta = color == PColor.WHITE ? 1 : -1;
    }


    @Override
    public boolean isMovePossible(Square[][] board, Square piecePos, Square pos) {

        int pieceRow = piecePos.row;
        int pieceCol = piecePos.col;
        int row = pos.row;
        int col = pos.col;

        return board[row + delta][col].piece == null;
    }

    private boolean isCapture(Piece[][] board, Square piecePos, Square pos) {
        int pieceRow = piecePos.row;
        int pieceCol = piecePos.col;
        int row = pos.row;
        int col = pos.row;
        return row == pieceRow + 1 && Math.abs(pieceCol - col) == 1 && board[pos.row][pos.col].color != this.color;
    }

    //TODO pawn promotion
    //TODO pawn first move
    //TODO en passant capture
}
