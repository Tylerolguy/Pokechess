package game.engine;
import javax.swing.*;

import game.scenes.BattleScene;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;






public class GameEngine implements Runnable {
    private JFrame frame;
    private BattleScene currentScene;

    public GameEngine(JFrame frame) {
        this.frame = frame;
        this.currentScene = new BattleScene();

    //   CharacterModel player = characters.get(0);

    //     switch (e.getKeyCode()) {
    //         case KeyEvent.VK_UP:    player.move(0, -GRID_SIZE); break;
    //         case KeyEvent.VK_DOWN:  player.move(0, GRID_SIZE); break;
    //         case KeyEvent.VK_LEFT:  player.move(-GRID_SIZE, 0); break;
    //         case KeyEvent.VK_RIGHT: player.move(GRID_SIZE, 0); break;
    //         case KeyEvent.VK_SPACE: player.nextFrame(); break;
    //     }

    //     repaint();
        
    
}

    public void start() {
        new Thread(this).start();
        // Input handling
    }

    public void run() {
        while (true) {
            repaint();
           try { Thread.sleep(60); } catch (Exception ignored) {}
        }
    }





    
    public void repaint() {
        this.currentScene.paint(frame.getGraphics());
        
    }

    public void input (KeyEvent e) {
        this.currentScene.input(e);
    }



}