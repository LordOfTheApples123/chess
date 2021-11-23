package vsu.csf.halilov.GUI;

import vsu.csf.halilov.Game.Chess;
import vsu.csf.halilov.Pieces.Piece;
import vsu.csf.halilov.Pieces.Square;
import vsu.csf.halilov.enums.EnumPiece;
import vsu.csf.halilov.enums.GameState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static javax.swing.SwingUtilities.isLeftMouseButton;

public class FrameMain extends JFrame{
    private static BoardPanel boardPanel = new BoardPanel();
    private static Chess game = new Chess();
    private static InfoPanel infoPanel = new InfoPanel();


    public FrameMain(){
        this.setTitle("Chess");
        this.setSize(600, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        final JMenuBar menu = new JMenuBar();
        populateMenuBar(menu);
        this.setJMenuBar(menu);
        this.add(boardPanel, BorderLayout.CENTER);
        this.add(infoPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }


    private void populateMenuBar(JMenuBar menu) {
        menu.add(createFileMenu());
    }

    private JMenu createFileMenu() {
        final JMenu gameMenu = new JMenu("Game");

        final JMenuItem newGame = new JMenuItem("Start new game");
        newGame.addActionListener(e -> startNewGame());
        gameMenu.add(newGame);
        return gameMenu;
    }

    private void startNewGame() {
        game = new Chess();
        boardPanel.getStartBoard();
        infoPanel.update(new ChessState());
    }

    public static class BoardPanel extends JPanel {
        private static TilePanel[][] boardTiles = initializeStartingBoard();


        public BoardPanel() {
            super(new GridLayout(8, 8));
            for(int row = 7; row >=0; row--){
                for(int col = 0; col < 8; col++){
                    add(boardTiles[row][col]);
                }

            }
            Dimension BOARD_DIMENSION = new Dimension(600, 600);
            setPreferredSize(BOARD_DIMENSION);
            validate();
        }

        public TilePanel getTile(int row, int col){
            return boardTiles[row][col];
        }

        private static TilePanel[][] initializeStartingBoard() {
            Color brown = new Color(181, 136, 99);
            Color pale = new Color(240, 217, 181);
            TilePanel[][] board = new TilePanel[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    board[i][j] = new TilePanel(i, j, EnumPiece.NONE, (i + j) % 2 == 0 ? brown : pale);
                }
            }
            board[0][0].setPiece(EnumPiece.WHITE_ROOK);
            board[0][1].setPiece(EnumPiece.WHITE_KNIGHT);
            board[0][2].setPiece(EnumPiece.WHITE_BISHOP);
            board[0][3].setPiece(EnumPiece.WHITE_QUEEN);
            board[0][4].setPiece(EnumPiece.WHITE_KING);
            board[0][5].setPiece(EnumPiece.WHITE_BISHOP);
            board[0][6].setPiece(EnumPiece.WHITE_KNIGHT);
            board[0][7].setPiece(EnumPiece.WHITE_ROOK);
            for (int i = 0; i < 8; i++) {
                board[1][i].setPiece(EnumPiece.WHITE_PAWN);
            }

            //black placement
            board[7][0].setPiece(EnumPiece.BLACK_ROOK);
            board[7][1].setPiece(EnumPiece.BLACK_KNIGHT);
            board[7][2].setPiece(EnumPiece.BLACK_BISHOP);
            board[7][3].setPiece(EnumPiece.BLACK_QUEEN);
            board[7][4].setPiece(EnumPiece.BLACK_KING);
            board[7][5].setPiece(EnumPiece.BLACK_BISHOP);
            board[7][6].setPiece(EnumPiece.BLACK_KNIGHT);
            board[7][7].setPiece(EnumPiece.BLACK_ROOK);
            for (int i = 0; i < 8; i++) {
                board[6][i].setPiece(EnumPiece.BLACK_PAWN);
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    board[i][j].drawPieceByName();
                }
            }
            return board;
        }

        public void getStartBoard() {
            removeAll();
            boardTiles = initializeStartingBoard();
            for(int row = 7; row >=0; row--){
                for(int col = 0; col < 8; col++){
                    add(boardTiles[row][col]);
                }
                validate();
                repaint();
            }

        }

        private static class TilePanel extends JPanel {
            private final int row;
            private final int col;
            private Color color;
            private EnumPiece piece;

            public TilePanel(int row, int col, EnumPiece piece, Color color) {
                super(new GridBagLayout());
                this.color = color;
                this.row = row;
                this.col = col;
                this.piece = piece;
                setBackground(color);
                setSize(75, 75);
                addMouseListener(new MouseListener() {



                    private static Square source = null;
                    private static EnumPiece movedEnumPiece = null;
                    private static ChessState chessState = new ChessState();
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(isLeftMouseButton(e)){
                            if(source == null){

                                movedEnumPiece = boardTiles[row][col].getPiece();
                                Piece movedPiece = EnumPiece.enumToPiece(movedEnumPiece);
                                if(movedPiece != null && movedPiece.getColor() == game.getCurrPlayer()) {
                                    source = new Square(row, col, movedPiece);
                                    Set<Square> moves = game.getPiecePossibleMoves(source);

                                        for (Square square : moves) {
                                            TilePanel curr = boardTiles[square.getRow()][square.getCol()];
                                            curr.lightUp();
                                            curr.repaintTile();
                                        }



                                }
                            } else{
                                Set<Square> moves = game.getPiecePossibleMoves(source);

                                for (Square square : moves) {
                                    TilePanel curr = boardTiles[square.getRow()][square.getCol()];
                                    curr.lightDown();
                                    curr.repaintTile();
                                }

                                Piece targetPiece = EnumPiece.enumToPiece(boardTiles[row][col].getPiece());
                                Square target = new Square(row, col, targetPiece);
                                if(game.combinedMoveChecking(source, target)){
                                    game.move(source, target);
                                    boardTiles[source.getRow()][source.getCol()].setPiece(EnumPiece.NONE);
                                    boardTiles[target.getRow()][target.getCol()].setPiece(movedEnumPiece);
                                    boardTiles[source.getRow()][source.getCol()].repaintTile();
                                    boardTiles[target.getRow()][target.getCol()].repaintTile();
                                    game.updateChessState(chessState);
                                    infoPanel.update(chessState);
                                }

//                                boardPanel.drawBoard(boardTiles);
                                moves.clear();
                                source = null;
                                target = null;
                                movedEnumPiece = null;
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

            private void repaintTile() {
                removeAll();
                drawTile();
            }

            private void lightUp() {
                Color lightBrown = new Color(170,162,58);
                Color lightPale = new Color(205,210,106);
                color = ((row + col) % 2 == 0 ? lightBrown : lightPale);
            }

            private void lightDown(){
                Color brown = new Color(181, 136, 99);
                Color pale = new Color(240, 217, 181);
                color = ((row + col) % 2 == 0 ? brown : pale);
            }

            public void drawTile() {
                setBackground(color);
                drawPieceByName();
                validate();
                repaint();
            }

            private void redraw() {
                removeAll();
                drawPieceByName();
            }

            private void drawPieceByName(){
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

        private void drawBoard() {

            for(TilePanel[] tilePanels: boardTiles){
                for(TilePanel tilePanel: tilePanels){
                    tilePanel.removeAll();
                    tilePanel.drawTile();
                    repaint();
                }
                validate();
                repaint();
            }
        }
    }


}
