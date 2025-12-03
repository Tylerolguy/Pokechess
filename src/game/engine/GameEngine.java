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
    private Scene currentScene;

    public GameEngine(JFrame frame) {
        this.frame = frame;
        this.currentScene = new BattleScene();

    
    }

    public void start() {
        new Thread(this).start();
        
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