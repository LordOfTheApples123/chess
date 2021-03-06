package vsu.csf.halilov.CLI;

import vsu.csf.halilov.Pieces.Square;
import vsu.csf.halilov.utils.ChessUtils;

import java.util.Scanner;

public class ConsoleInterface {

    public static String input(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your next move: ");
        return in.nextLine();
    }

    public static void inputHandling(){
        String input = input();
        while(!isInputCorrect(input().toCharArray())){
            System.out.println("Incorrect input try again: ");
            input = input();
        }

        Square startingSquare =
    }

    private static void incorrectInputHandling() {
        System.out.println("Incorrect move. Try again");
        inputHandling();
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

        if(!Character.isDigit(input[2])){
            return false;
        }

        return Character.isDigit(input[4]);

    }

    public Square getStartingSquareFromString(String input){
        String id = input.substring(0, 2);
        int row = ChessUtils.idToRow(id);

        int col = ChessUtils.idToCol(id);
        return new Square(row, col);
    }
}
