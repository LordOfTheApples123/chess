package vsu.csf.halilov.enums;

public enum PColor {
    WHITE,
    BLACK;

    public PColor getOpos() {
        if (this == WHITE) {
            return BLACK;
        }
        if (this == BLACK) {
            return WHITE;
        } else return null;
    }
}
