package vsu.csf.halilov.GUI;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    private static final Dimension INFO_PANEL_DIMENSION = new Dimension(600, 200);

    public InfoPanel(){
        super(new BorderLayout());

        setPreferredSize(INFO_PANEL_DIMENSION);
        JLabel stat = new JLabel("Ход White", SwingConstants.CENTER);

        stat.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
        add(stat, BorderLayout.CENTER);
    }

    public void update(ChessState chessState){
        removeAll();
        JLabel stat = new JLabel(chessState.getStat(), SwingConstants.CENTER);
        stat.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
        add(stat, BorderLayout.CENTER);
        validate();
        repaint();
    }
}
