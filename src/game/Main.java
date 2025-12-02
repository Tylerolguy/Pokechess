package game;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Main extends JPanel {

    
    private static final int GRID_SIZE = 32;

    public Main() {
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);

    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("PokeChess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new Main());
        frame.pack();
        frame.setVisible(true);
    }

}