package vsu.csf.halilov.Pieces;

import vsu.csf.halilov.enums.PColor;

import java.util.HashSet;
import java.util.Set;

public class King extends Piece {

    public King(PColor color) {
        super(color, "K");
    }

    public static Set<Square> KING_MOVES = Set.of(
            new Square(0, 1),
            new Square(0, -1),
            new Square(1, 1),
            new Square(1, -1),
            new Square(1, 0),
            new Square(-1, 1),
            new Square(-1, -1),
            new Square(-1, 0)
    );

    public static Set<Square> getKingMoves(Square[][] board, Square piecePos) {
        Set<Square> res = new HashSet<>();
        for (Square moves : KING_MOVES) {
            int row = piecePos.getRow()+ moves.getRow();
            int col = piecePos.getCol() + moves.getCol();
            if (isCoordInBounds(row, col) && board[row][col].getPieceColor() != piecePos.getPieceColor()  ) {
                res.add(board[row][col]);
            }
        }
        return res;
    }

    @Override
    public boolean isMovePossible(Square[][] board, Square piecePos, Square pos) {
        Set<Square> moves = getKingMoves(board, piecePos);
        return moves.contains(pos);
    }

    public static int getCheckCount(Square[][] board, Square kingSquare) {
        int checkCount = 0;
        for (Square square : Rook.getLineMoves(board, kingSquare)) {

            if (square.getPieceColor() == kingSquare.getPieceColor().getOpos() && square.getPieceId().equals("R") || square.getPieceId().equals("Q")) {
                checkCount++;
            }
        }
        Set<Square> diag = Bishop.getDiagMoves(board, kingSquare);
        for (Square square : diag) {

            if (square.getPieceColor() == kingSquare.getPieceColor().getOpos() && square.getPieceId().equals("B") || square.getPieceId().equals("Q")) {
                checkCount++;
            }
        }
        for (Square square : Knight.getKnightMoves(board, kingSquare)) {
            if (square.getPieceColor() == kingSquare.getPieceColor().getOpos() && square.getPieceId().equals("N")) {
                checkCount++;
            }
        }
        if (isPawnCheck(board, kingSquare, kingSquare.getPieceColor()) != null) {
            checkCount++;
        }
        return checkCount;
    }

    public static boolean isKnightCheck(Square[][] board, Square kingSquare) {
        for (Square square : Knight.getKnightMoves(board, kingSquare)) {
            if (square.getPieceId().equals("N")) {
                return true;
            }
        }
        return false;
    }

    public static boolean canKingMove(Square[][] board, Square kingSquare) {
        for (Square square : getKingMoves(board, kingSquare)) {
            Piece tmp = board[square.row][square.col].getPiece();
            board[square.row][square.col].setPiece(kingSquare.getPiece());
            if (getCheckCount(board, square) == 0) {
                return true;
            }
            board[square.row][square.col].setPiece(tmp);
        }
        return false;
    }

    public static Square getCheckingPieceForBlock(Square[][] board, Square kingSquare) {
        for (Square square : Rook.getLineMoves(board, kingSquare)) {
            if (square.getPieceId().equals("R") || square.getPieceId().equals("Q")) {
                return square;
            }
        }
        for (Square square : Bishop.getDiagMoves(board, kingSquare)) {
            if (square.getPieceId().equals("B") || square.getPieceId().equals("Q")) {
                return square;
            }
        }
        for (Square square : Knight.getKnightMoves(board, kingSquare)) {
            if (square.getPieceId().equals("N")) {
                return square;
            }
        }
        if (isPawnCheck(board, kingSquare, kingSquare.getPieceColor()) != null) {
            return isPawnCheck(board, kingSquare, kingSquare.getPieceColor());
        }
        return null;
    }

    public static Square isPawnCheck(Square[][] board, Square piecePos, PColor color) {
        int pieceRow = piecePos.row;
        int pieceCol = piecePos.col;
        int delta = color == PColor.WHITE ? 1 : -1;




        if (isCoordInBounds(pieceRow + delta, pieceRow + 1)) {
            Square pos1 = board[pieceRow + delta][pieceCol + 1];
            if (pos1.getPieceId().equals("P") && pos1.getPieceColor() == color.getOpos()) {
                return pos1;
            }
        }

        if (isCoordInBounds(pieceRow + delta, pieceRow - 1)) {

            Square pos = board[pieceRow + delta][pieceCol - 1];
            if (pos.getPieceId().equals("P") && pos.getPieceColor() != color) {
                return pos;
            }
        }

        return null;
    }

    @Override
    public Set<Square> getPossibleMoves(Square[][] board, Square pos) {
        return getKingMoves(board, pos);
    }
}
