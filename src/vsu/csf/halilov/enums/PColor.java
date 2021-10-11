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

    public String toString(){
        if (this == WHITE) {
            return "White";
        }
        if (this == BLACK) {
            return "Black";
        } else return null;
    }
}
