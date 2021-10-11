package vsu.csf.halilov.utils;

public class ChessUtils {

    private ChessUtils() {
    }

    public static int idToRow(String pos) {
        String letter = pos.substring(0, 1);
        if (letter.equals("a")) {
            return 0;
        }
        if (letter.equals("b")) {
            return 1;
        }
        if (letter.equals("c")) {
            return 2;
        }
        if (letter.equals("d")) {
            return 3;
        }
        if (letter.equals("e")) {
            return 4;
        }
        if (letter.equals("f")) {
            return 5;
        }
        if (letter.equals("g")) {
            return 6;
        }
        if (letter.equals("h")) {
            return 7;
        }
        return -1;
    }

    public static int idToCol(String pos) {
        return Integer.parseInt(pos.substring(1, 2));
    }
}
