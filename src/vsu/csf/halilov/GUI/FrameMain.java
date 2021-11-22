package vsu.csf.halilov.GUI;

import vsu.csf.halilov.Game.Chess;
import vsu.csf.halilov.Pieces.Square;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.swing.SwingUtilities.isLeftMouseButton;

public class FrameMain extends JFrame{
    private final BoardPanel boardPanel;
    private final Chess game;


    public FrameMain(){
        this.setTitle("Chess");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        final JMenuBar menu = new JMenuBar();
        populateMenuBar(menu);
        this.setJMenuBar(menu);
        boardPanel = new BoardPanel();
        this.add(boardPanel, BorderLayout.CENTER);
        this.setVisible(true);

        game = new Chess();
    }


    private void populateMenuBar(JMenuBar menu) {
        menu.add(createFileMenu());
    }

    private JMenu createFileMenu() {
        final JMenu gameMenu = new JMenu("Game");

        final JMenuItem newGame = new JMenuItem("Start new game");
        newGame.addActionListener(e -> System.out.println("da"));
        gameMenu.add(newGame);
        return gameMenu;
    }


}
