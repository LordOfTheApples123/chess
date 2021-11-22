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
}
