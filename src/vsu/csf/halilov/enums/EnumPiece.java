package vsu.csf.halilov.enums;

import vsu.csf.halilov.Pieces.*;

public enum EnumPiece {
    WHITE_PAWN,
    WHITE_ROOK,
    WHITE_KNIGHT,
    WHITE_BISHOP,
    WHITE_QUEEN,
    WHITE_KING,
    BLACK_PAWN,
    BLACK_ROOK,
    BLACK_KNIGHT,
    BLACK_BISHOP,
    BLACK_QUEEN,
    BLACK_KING,
    NONE;

    public static Piece enumToPiece(EnumPiece enumPiece){
        return switch (enumPiece) {
            case WHITE_PAWN -> new Pawn(PColor.WHITE);
            case BLACK_PAWN -> new Pawn(PColor.BLACK);
            case WHITE_KNIGHT -> new Knight(PColor.WHITE);
            case BLACK_KNIGHT -> new Knight(PColor.BLACK);
            case WHITE_BISHOP -> new Bishop(PColor.WHITE);
            case BLACK_BISHOP -> new Bishop(PColor.BLACK);
            case WHITE_ROOK -> new Rook(PColor.WHITE);
            case BLACK_ROOK -> new Rook(PColor.BLACK);
            case WHITE_QUEEN -> new Queen(PColor.WHITE);
            case BLACK_QUEEN -> new Queen(PColor.BLACK);
            case WHITE_KING -> new King(PColor.WHITE);
            case BLACK_KING -> new King(PColor.BLACK);
            default -> null;
        };
    }

    public static EnumPiece pieceToEnum(Piece piece){
        if(piece == null){
            return NONE;
        }
        if(piece.getColor() == PColor.WHITE && piece.getPieceId().equals("P")){
            return WHITE_PAWN;
        }
        if(piece.getColor() == PColor.WHITE && piece.getPieceId().equals("N")){
            return WHITE_KNIGHT;
        }
        if(piece.getColor() == PColor.WHITE && piece.getPieceId().equals("B")){
            return WHITE_BISHOP;
        }
        if(piece.getColor() == PColor.WHITE && piece.getPieceId().equals("Q")){
            return WHITE_QUEEN;
        }
        if(piece.getColor() == PColor.WHITE && piece.getPieceId().equals("K")){
            return WHITE_KING;
        }
        if(piece.getColor() == PColor.WHITE && piece.getPieceId().equals("R")){
            return WHITE_ROOK;
        }
        if(piece.getColor() == PColor.BLACK && piece.getPieceId().equals("P")){
            return BLACK_PAWN;
        }
        if(piece.getColor() == PColor.BLACK && piece.getPieceId().equals("N")){
            return BLACK_KNIGHT;
        }
        if(piece.getColor() == PColor.BLACK && piece.getPieceId().equals("B")){
            return BLACK_BISHOP;
        }
        if(piece.getColor() == PColor.BLACK && piece.getPieceId().equals("Q")){
            return BLACK_QUEEN;
        }
        if(piece.getColor() == PColor.BLACK && piece.getPieceId().equals("K")){
            return BLACK_KING;
        }
        if(piece.getColor() == PColor.BLACK && piece.getPieceId().equals("R")){
            return BLACK_ROOK;
        }
        return null;
    }
}
