package vsu.csf.halilov.CLI;

import vsu.csf.halilov.Pieces.Piece;
import vsu.csf.halilov.Pieces.Square;
import vsu.csf.halilov.enums.PColor;
import vsu.csf.halilov.utils.ChessUtils;

import java.util.Scanner;

public class ConsoleInterface {

    public static String input(PColor color){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your next move,  "+ color.toString() + ": ");
        return in.nextLine();
    }

    public static String inputHandling(PColor color){
        String input = input(color);
        while(!isInputCorrect(input.toCharArray())){
            System.out.println("Incorrect input try again: ");
            input = input(color);
        }

        while(!isSquareInputCorrect(input)){
            System.out.println("Input out of bounds try again: ");
            input = input(color);
        }

        return input;
    }

    public static boolean isSquareInputCorrect(String input){
        String id = input.substring(3, 5);
        int row = ChessUtils.idToCol(id);

        int col = ChessUtils.idToRow(id);

        if(!Piece.isCoordInBounds(row, col)){
            return false;
        }

        id = input.substring(0, 2);
        row = ChessUtils.idToCol(id);

        col = ChessUtils.idToRow(id);

        return Piece.isCoordInBounds(row, col);
    }

    public static Square getTargetSquareFromString(String input) {
        String id = input.substring(3, 5);
        int row = ChessUtils.idToCol(id);

        int col = ChessUtils.idToRow(id);
        return new Square(row, col);
    }

    public static boolean isInputCorrect(char[] input){
        if(input.length <5){
            return false;
        }
        if(!Character.isLetter(input[0])){
            return false;
        }

        if(!Character.isLetter(input[3])){
            return false;
        }

        if(!Character.isDigit(input[1])){
            return false;
        }

        return Character.isDigit(input[4]);

    }

    public static Square getStartingSquareFromString(String input){
        String id = input.substring(0, 2);
        int row = ChessUtils.idToCol(id);

        int col = ChessUtils.idToRow(id);
        return new Square(row, col);
    }
}
