package vsu.csf.halilov.Pieces;

import vsu.csf.halilov.enums.PColor;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.abs;

public class Bishop extends Piece {
    public Bishop(PColor color) {
        super(color, "B");
    }

    public static boolean isDiag(Square pos1, Square pos2) {
        return abs(pos2.col - pos1.col) % (pos2.row - pos1.row) == 0;
    }


    public static Set<Square> getDiagMoves(Square[][] board, Square square) {
        Set<Square> res = new HashSet<>();
        int row = square.row + 1;
        int col = square.col + 1;
        while (isSquareAccessible(board, square, board[row][col])) {
            res.add(board[row][col]);
            if (board[row][col].piece != null) {
                break;
            }
            row++;
            col++;
        }
        row = square.row + 1;
        col = square.col - 1;

        while (isSquareAccessible(board, square, board[row][col])) {
            res.add(board[row][col]);
            if (board[row][col].piece != null) {
                break;
            }
            row++;
            col--;
        }

        row = square.row - 1;
        col = square.col + 1;

        while (isSquareAccessible(board, square, board[row][col])) {
            res.add(board[row][col]);
            if (board[row][col].piece != null) {
                break;
            }
            row--;
            col++;
        }

        row = square.row - 1;
        col = square.col - 1;

        while (isSquareAccessible(board, square, board[row][col])) {
            res.add(board[row][col]);
            if (board[row][col].piece != null) {
                break;
            }
            row--;
            col--;
        }

        return res;
    }

    @Override
    public boolean isMovePossible(Square[][] board, Square piecePos, Square pos) {
        if (!isDiag(piecePos, pos)) {
            return false;
        }
        return getDiagMoves(board, piecePos).contains(pos);
    }
}
