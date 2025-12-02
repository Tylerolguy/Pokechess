package game.engine;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;






public class GameEngine implements Runnable  {

    public GameEngine() {

    new Thread(this).start();
    }

    public void run() {
        while (true) {
            repaint();
            try { Thread.sleep(16); } catch (Exception ignored) {}
        }
    }

    public void repaint() {

        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }



}