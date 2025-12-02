package game;


import javax.swing.*;

import game.engine.GameEngine;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Main extends JPanel {

    
    private static GameEngine gameEngine;

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("PokeChess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Prevent content pane from stealing focus
        JPanel panel = new JPanel();
        panel.setFocusable(false);
        frame.setContentPane(panel);

        frame.setSize(800, 600);
        frame.setVisible(true);

        // Make sure frame can receive key input
        frame.setFocusable(true);
        frame.requestFocusInWindow();

        GameEngine engine = new GameEngine(frame);
        // Add KeyListener directly to the JFrame
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                engine.input(e);
            }
        });

        // Start Game Engine
        
        engine.start();
    }
    

}