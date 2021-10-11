package vsu.csf.halilov.Pieces;


import vsu.csf.halilov.enums.PColor;

import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {
    public Knight(PColor color) {
        super(color, "N");
    }

    private static final Set<Square> KNIGHT_POSSIBLE_MOVES = Set.of(
            new Square(2, 1),
            new Square(2, -1),
            new Square(-2, 1),
            new Square(-2, -1),
            new Square(1, 2),
            new Square(1, -2),
            new Square(-1, 2),
            new Square(-1, -2));

    @Override
    public boolean isMovePossible(Square[][] board, Square piecePos, Square pos) {
        for (Square moves : getKnightMoves(board, piecePos)) {
            if (moves.equals(pos)) {
                return true;
            }
        }
        return false;
    }

    public static Set<Square> getKnightMoves(Square[][] board, Square pos) {
        Set<Square> res = new HashSet<>();
        for (Square moves : KNIGHT_POSSIBLE_MOVES) {
            if (isCoordInBounds(pos.row + moves.row, pos.col + moves.col)) {
                res.add(board[pos.row + moves.row][pos.col + moves.col]);
            }
        }
        return res;
    }

}
