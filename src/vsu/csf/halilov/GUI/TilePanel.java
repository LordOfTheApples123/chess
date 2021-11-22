package vsu.csf.halilov.GUI;

import vsu.csf.halilov.Pieces.Square;
import vsu.csf.halilov.enums.EnumPiece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.swing.SwingUtilities.isLeftMouseButton;

public class TilePanel extends JPanel {
    private final int row;
    private final int col;
    private EnumPiece piece;

    public TilePanel(int row, int col, EnumPiece piece, Color color) {
        super(new GridBagLayout());
        this.row = row;
        this.col = col;
        this.piece = piece;
        setBackground(color);
        setSize(75, 75);
        addMouseListener(new MouseListener() {



            private static Square source = null;
            private static Square target = null;
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isLeftMouseButton(e)){
                    if(source == null){
                        source = new Square(row, col, EnumPiece.enumToPiece(piece));
                    } else{
                        if(source.getPiece() == null){
                            source = null;
                            return;
                        }
                        target = new Square(row, col, EnumPiece.enumToPiece(piece));

                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void assignPieceByName(){
        if(piece == EnumPiece.NONE){
            return;
        }
        String path = "";
        try {
            switch (piece) {
                case WHITE_PAWN -> path = "PiecesPng/WhitePawn.png";
                case WHITE_BISHOP -> path = "PiecesPng/WhiteBishop.png";
                case WHITE_KNIGHT -> path = "PiecesPng/WhiteKnight.png";
                case WHITE_QUEEN -> path = "PiecesPng/WhiteQueen.png";
                case WHITE_KING -> path = "PiecesPng/WhiteKing.png";
                case WHITE_ROOK -> path = "PiecesPng/WhiteRook.png";
                case BLACK_PAWN -> path = "PiecesPng/BlackPawn.png";
                case BLACK_BISHOP -> path = "PiecesPng/BlackBishop.png";
                case BLACK_KNIGHT -> path = "PiecesPng/BlackKnight.png";
                case BLACK_QUEEN -> path = "PiecesPng/BlackQueen.png";
                case BLACK_KING -> path = "PiecesPng/BlackKing.png";
                case BLACK_ROOK -> path = "PiecesPng/BlackRook.png";
            }
            if(path.equals("")){
                System.out.println("свич сломался");
                return;
            }
            BufferedImage image = ImageIO.read(new File(path));
            add(new JLabel(new ImageIcon(image)));
        } catch(IOException e){
            e.printStackTrace();
        }

    }





    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public EnumPiece getPiece() {
        return piece;
    }

    public void setPiece(EnumPiece piece) {
        this.piece = piece;
    }
}