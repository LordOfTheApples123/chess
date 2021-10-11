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
            Square curr = board[piecePos.row + moves.row][piecePos.col + moves.col];
            if (isSquareInBounds(curr)) {
                res.add(curr);
            }
        }
        return res;
    }

    @Override
    public boolean isMovePossible(Square[][] board, Square piecePos, Square pos) {
        return getKingMoves(board, piecePos).contains(pos);
    }

    public static int getCheckCount(Square[][] board, Square kingSquare){
        int checkCount = 0;
        for(Square square: Rook.getLineMoves(board, kingSquare)){
            if(square.piece.pieceId.equals("R") || square.piece.pieceId.equals("Q")){
                checkCount++;
            }
        }
        for(Square square: Bishop.getDiagMoves(board, kingSquare)){
            if(square.piece.pieceId.equals("B") || square.piece.pieceId.equals("Q")){
                checkCount++;
            }
        }
        for(Square square: Knight.getKnightMoves(board, kingSquare)){
            if(square.piece.pieceId.equals("N")){
                checkCount++;
            }
        }
        if(board[kingSquare.row+1][kingSquare.col-1].piece.pieceId.equals("P") || board[kingSquare.row+1][kingSquare.col+1].piece.pieceId.equals("P")){
            checkCount++;
        }
        return checkCount;
    }

    public static boolean isKnightCheck(Square[][] board, Square kingSquare){
        for(Square square: Knight.getKnightMoves(board, kingSquare)){
            if(square.piece.pieceId.equals("N")){
                return true;
            }
        }
        return false;
    }

    public static boolean canKingMove(Square[][] board, Square kingSquare){
        for(Square square: getKingMoves(board, kingSquare)){
            if(getCheckCount(board, kingSquare) == 0){
                return true;
            }
        }
        return false;
    }

    public static Square getCheckingPieceForBlock(Square[][] board, Square kingSquare){
        for(Square square: Rook.getLineMoves(board, kingSquare)){
            if(square.piece.pieceId.equals("R") || square.piece.pieceId.equals("Q")){
                return square;
            }
        }
        for(Square square: Bishop.getDiagMoves(board, kingSquare)){
            if(square.piece.pieceId.equals("B") || square.piece.pieceId.equals("Q")){
                return square;
            }
        }
        for(Square square: Knight.getKnightMoves(board, kingSquare)){
            if(square.piece.pieceId.equals("N")){
                return square;
            }
        }
        if(board[kingSquare.row+1][kingSquare.col-1].piece.pieceId.equals("P")){
            return board[kingSquare.row+1][kingSquare.col-1];
        }
        return null;
    }
}
