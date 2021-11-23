package vsu.csf.halilov.Pieces;

import vsu.csf.halilov.enums.PColor;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {
    public Rook(PColor color) {
        super(color, "R");
    }

    public static Set<Square> getLineMoves(Square[][] board, Square square) {
        PColor color = square.getPieceColor();
        int row = square.row + 1;
        int col = square.col;

        Set<Square> res = new HashSet<>();
        while (isCoordInBounds(row, col) && board[row][col].getPieceColor() != color) {
            res.add(board[row][col]);
            if (board[row][col].piece != null) {
                break;
            }
            row++;
        }

        row = square.row - 1;

        while ( isCoordInBounds(row, col) && board[row][col].getPieceColor() != color) {
            res.add(board[row][col]);
            if (board[row][col].piece != null) {
                break;
            }
            row--;
        }

        row = square.row;
        col = square.col + 1;

        while (isCoordInBounds(row, col) && board[row][col].getPieceColor() != color) {
            res.add(board[row][col]);
            if (board[row][col].piece != null) {
                break;
            }
            col++;
        }

        col = square.col - 1;

        while (isCoordInBounds(row, col) && board[row][col].getPieceColor() != color) {
            res.add(board[row][col]);
            if (board[row][col].piece != null) {
                break;
            }
            col--;
        }

        return res;
    }

    @Override
    public boolean isMovePossible(Square[][] board, Square piecePos, Square pos) {
        return getLineMoves(board, piecePos).contains(pos);
    }


}
