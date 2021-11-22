package vsu.csf.halilov.GUI;

import vsu.csf.halilov.enums.EnumPiece;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private final TilePanel[][] boardTiles;


    public BoardPanel() {
        super(new GridLayout(8, 8));
        this.boardTiles = initializeStartingBoard();
        for(TilePanel[] tilePanels: boardTiles){
            for(TilePanel tilePanel: tilePanels){
                add(tilePanel);
            }
        }
        Dimension BOARD_DIMENSION = new Dimension(600, 600);
        setPreferredSize(BOARD_DIMENSION);
        validate();
    }

    public TilePanel getTile(int row, int col){
        return boardTiles[row][col];
    }

    private TilePanel[][] initializeStartingBoard() {
        Color brown = new Color(181, 136, 99);
        Color pale = new Color(240, 217, 181);
        TilePanel[][] board = new TilePanel[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new TilePanel(i, j, EnumPiece.NONE, (i + j) % 2 == 0 ? brown : pale);
            }
        }
        board[7][0].setPiece(EnumPiece.WHITE_ROOK);
        board[7][1].setPiece(EnumPiece.WHITE_KNIGHT);
        board[7][2].setPiece(EnumPiece.WHITE_BISHOP);
        board[7][3].setPiece(EnumPiece.WHITE_QUEEN);
        board[7][4].setPiece(EnumPiece.WHITE_KING);
        board[7][5].setPiece(EnumPiece.WHITE_BISHOP);
        board[7][6].setPiece(EnumPiece.WHITE_KNIGHT);
        board[7][7].setPiece(EnumPiece.WHITE_ROOK);
        for (int i = 0; i < 8; i++) {
            board[6][i].setPiece(EnumPiece.WHITE_PAWN);
        }

        //black placement
        board[0][0].setPiece(EnumPiece.BLACK_ROOK);
        board[0][1].setPiece(EnumPiece.BLACK_KNIGHT);
        board[0][2].setPiece(EnumPiece.BLACK_BISHOP);
        board[0][3].setPiece(EnumPiece.BLACK_QUEEN);
        board[0][4].setPiece(EnumPiece.BLACK_KING);
        board[0][5].setPiece(EnumPiece.BLACK_BISHOP);
        board[0][6].setPiece(EnumPiece.BLACK_KNIGHT);
        board[0][7].setPiece(EnumPiece.BLACK_ROOK);
        for (int i = 0; i < 8; i++) {
            board[1][i].setPiece(EnumPiece.BLACK_PAWN);
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j].assignPieceByName();
            }
        }
        return board;
    }
}
