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
        if(row == pieceRow + delta && col == pieceCol){
            return board[row][col].getPiece() == null;
        }
        return isCapture(board, piecePos, pos);
    }

    public boolean isCapture(Square[][] board, Square piecePos, Square pos){
        int pieceRow = piecePos.row;
        int pieceCol = piecePos.col;
        int row = pos.row;
        int col = pos.col;

        return row == pieceRow + delta && Math.abs(pos.col - pieceCol) == 1 && board[row][col].getPieceColor() == piecePos.getPieceColor().getOpos();
    }





    //TODO pawn promotion
    //TODO pawn first move
    //TODO en passant capture
}
