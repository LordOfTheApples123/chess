package vsu.csf.halilov.Pieces;

import vsu.csf.halilov.enums.PColor;

import java.util.Set;

public class Queen extends Piece {
    public Queen(PColor color) {
        super(color, "Q");
    }

    public static Set<Square> getQueenMoves(Square[][] board, Square piecePos) {
        Set<Square> pieceSet = Bishop.getDiagMoves(board, piecePos);
        pieceSet.addAll(Rook.getLineMoves(board, piecePos));
        return pieceSet;
    }

    @Override
    public boolean isMovePossible(Square[][] board, Square piecePos, Square pos) {
        return getQueenMoves(board, piecePos).contains(pos);
    }
}
