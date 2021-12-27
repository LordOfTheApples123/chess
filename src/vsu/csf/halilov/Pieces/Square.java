package vsu.csf.halilov.Pieces;

import vsu.csf.halilov.enums.PColor;
import vsu.csf.halilov.utils.ChessUtils;

import java.awt.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Square {
    int row;
    int col;
    Piece piece;
    Color squareColor;

    public Square(String pos) {
        this.row = ChessUtils.idToRow(pos);
        this.col = ChessUtils.idToCol(pos);
    }

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Square(int row, int col, Piece piece) {
        this.row = row;
        this.col = col;
        this.piece = piece;
    }

    public String getPieceId(){
        if(piece == null){
            return "Null";
        }
        return piece.getPieceId();
    }

    public Square(int row, int col, Color squareColor) {
        this.row = row;
        this.col = col;
        this.squareColor = squareColor;
    }

    public Square(int row, int col, Piece piece, Color squareColor) {
        this.row = row;
        this.col = col;
        this.piece = piece;
        this.squareColor = squareColor;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public void remove() {
        this.piece = null;
    }

    public PColor getPieceColor() {
        if(piece == null){
            return null;
        }
        return piece.getColor();
    }

    public String getId() {
        if(piece == null){
            return " ";
        }
        return piece.getPieceId();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public static Set<Square> getTrajectory(Square[][] board, Square startingSquare, Square targetSquare){
        Set<Square> res = new HashSet<>();

        int lowerRow = startingSquare.row;
        int higherRow = targetSquare.row;

        int lowerCol = startingSquare.col;
        int higherCol = targetSquare.col;

        if(Bishop.isDiag(startingSquare, targetSquare)){
            if(lowerRow>higherRow){
                lowerCol = targetSquare.col;
                lowerRow = targetSquare.row;

                higherCol = startingSquare.col;
                higherRow = startingSquare.row;
            }
            int delta = higherCol > lowerCol ? 1 : -1;

            for(int i = lowerRow; i < higherRow; i++){
                for(int j = lowerCol; j!=higherCol; j+=delta) {
                    res.add(board[i][j]);
                }
            }
        }

        if(lowerRow == higherRow){
            if(lowerCol>higherCol){
                lowerCol = targetSquare.col;
                higherCol = startingSquare.col;
            }

            for(int i = lowerCol; i < higherCol; i++){
                res.add(board[lowerRow][i]);
            }
        }

        if(lowerCol == higherCol){
            if(lowerRow>higherRow){
                lowerRow = targetSquare.row;
                higherRow = startingSquare.row;
            }

            for(int i = lowerRow; i < higherRow; i++){
                res.add(board[i][lowerRow]);
            }
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return row == square.row && col == square.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, piece);
    }

    @Override
    public String toString() {
        String res = "";
        switch(col){
            case 0: res +="a";
                break;
            case 1: res +="b";
                break;
            case 2: res +="c";
                break;
            case 3: res +="d";
                break;
            case 4: res +="e";
                break;
            case 5: res +="f";
                break;
            case 6: res +="g";
                break;
            case 7: res +="h";
                break;
        }
        int resCol = row+1;
        res += resCol;
        return res;
    }
}
