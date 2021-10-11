package vsu.csf.halilov.Pieces;

import vsu.csf.halilov.enums.PColor;

import java.util.Objects;
import java.util.Set;

public abstract class Piece {

    protected PColor color;
    protected String pieceId;

    public Piece(PColor color, String pieceId) {
        this.color = color;
        this.pieceId = pieceId;
    }

    public static boolean isCoordInBounds(int row, int col) {
        return row>=0 && row < 8 && col >=0 && col<8;
    }

    public PColor getColor() {
        return color;
    }

    public String getPieceId() {
        return pieceId;
    }

    protected static boolean isSquareAccessible(Square[][] board, Square piecePos, Square pos) {
        int row = pos.row;
        int col = pos.col;
        PColor color = piecePos.getPieceColor();
        if (row < 0 || row >= 8 || col < 0 || col >= 8) {
            return false;
        }
        if(board[row][col].piece == null){
            return true;
        }

        return board[row][col].getPieceColor() != color;
    }

    public static boolean isSquareInBounds(Square square) {
        int row = square.row;
        int col = square.col;

        return row >= 0 && col >= 0 && row < 8 && col < 8;
    }

    public static void move(Square[][] board, Square piecePos, Square pos) {
        board[pos.row][pos.col].setPiece(piecePos.getPiece());
        board[piecePos.row][piecePos.col].setPiece(null);
    }

    public boolean isMovePossible(Square[][] board, Square piecePos, Square pos) {
        return false;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return color == piece.color && Objects.equals(pieceId, piece.pieceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, pieceId);
    }
}
