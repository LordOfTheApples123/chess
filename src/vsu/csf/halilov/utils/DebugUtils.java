package vsu.csf.halilov.utils;

import vsu.csf.halilov.Pieces.*;
import vsu.csf.halilov.enums.PColor;

import java.util.Map;

public class DebugUtils {

    public static Square[][] fenToBoard(String fen, Square[][] board, Map<PColor, Square> kings){
        kings.clear();
        String[] rows = fen.split(("/"));
        for(int i = 0; i < 8; i++){
            String[] pieces = rows[i].split("");
            for(int j = 0; j < pieces.length; j++){
                int index = 0;
                if(pieces[j].equals("p")){
                    board[i][index].setPiece(new Pawn(PColor.WHITE));
                    index++;
                }
                if(pieces[j].equals("n")){
                    board[i][index].setPiece(new Knight(PColor.WHITE));
                    index++;
                }
                if(pieces[j].equals("b")){
                    board[i][index].setPiece(new Bishop(PColor.WHITE));
                    index++;
                }
                if(pieces[j].equals("r")){
                    board[i][index].setPiece(new Rook(PColor.WHITE));
                    index++;
                }
                if(pieces[j].equals("q")){
                    board[i][index].setPiece(new Queen(PColor.WHITE));
                    index++;
                }
                if(pieces[j].equals("k")){
                    board[i][index].setPiece(new King(PColor.WHITE));
                    index++;
                    kings.put(board[i][index].getPieceColor(), board[i][index]);
                }


                if(pieces[j].equals("P")){
                    board[i][index] = new Square(i, index, new Pawn(PColor.WHITE));
                    index++;
                }
                if(pieces[j].equals("N")){
                    board[i][index] = new Square(i, index, new Knight(PColor.WHITE));
                    index++;
                }
                if(pieces[j].equals("B")){
                    board[i][index] = new Square(i, index, new Bishop(PColor.WHITE));
                    index++;
                }
                if(pieces[j].equals("R")){
                    board[i][index] = new Square(i, index, new Rook(PColor.WHITE));
                    index++;
                }
                if(pieces[j].equals("Q")){
                    board[i][index] = new Square(i, index, new Queen(PColor.WHITE));
                    index++;
                }

                if(Character.isDigit(pieces[j].toCharArray()[0])){
                    int count = Integer.parseInt(pieces[j]);
                    for(int k = 0; k < count; k++){
                        board[i][index] = new Square(i, index);
                    }
                }
            }
        }
        return board;
    }
}
