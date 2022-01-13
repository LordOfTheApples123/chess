package vsu.csf.halilov.Pieces;

import vsu.csf.halilov.enums.PColor;

import java.util.HashSet;
import java.util.Set;

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



    private boolean isFirstMove(Square[][] board, Square piecePos, Square pos) {
        int pieceRow = piecePos.row;
        int pieceCol = piecePos.col;
        int row = pos.row;
        int col = pos.col;
        if(row == pieceRow + 2 * delta && col == pieceCol && firstMove){

            return board[row][col].getPiece() == null;
        } else return false;
    }

    public boolean isCapture(Square[][] board, Square piecePos, Square pos){
        int pieceRow = piecePos.row;
        int pieceCol = piecePos.col;
        int row = pos.row;
        int col = pos.col;

        return row == pieceRow + delta && Math.abs(pos.col - pieceCol) == 1 && board[row][col].getPieceColor() == piecePos.getPieceColor().getOpos();
    }

    @Override
    public Set<Square> getPossibleMoves(Square[][] board, Square pos) {
        Set<Square> set = new HashSet<>();
        int row = pos.row;
        int col = pos.col;
        if(isCoordInBounds(row + delta, col) && isMovePossible(board, pos, board[row+delta][col])){
            set.add(board[row+delta][col]);
        }
        if(isCoordInBounds(row + 2* delta, col) && isMovePossible(board, pos, board[row+2*delta][col])){
            set.add(board[row+2* delta][col]);
        }
        if(isCoordInBounds(row + delta, col - 1) && isMovePossible(board, pos, board[row+delta][col - 1])){
            set.add(board[row+delta][col - 1]);
        }
        if(isCoordInBounds(row + delta, col + 1) && isMovePossible(board, pos, board[row+delta][col + 1])){
            set.add(board[row+delta][col + 1]);
        }
        return set;
    }

    //TODO pawn promotion
    //TODO pawn first move
    //TODO en passant capture
}
