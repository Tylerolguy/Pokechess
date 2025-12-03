package game.engine;
import javax.swing.*;

import game.scenes.BattleScene;

import java.awt.event.*;







public class GameEngine implements Runnable {
    private JFrame frame;
    private Scene currentScene;

    public GameEngine(JFrame frame) {
        this.frame = frame;
        this.currentScene = new BattleScene();
        this.changeScene(currentScene);

    }

    public void start() {
        new Thread(this).start();
        
    }

    public void run() {
        while (true) {
            this.currentScene.update();  
            this.currentScene.repaint();

            try { Thread.sleep(16); } catch (Exception ignored) {}
        }
    }


    public void input (KeyEvent e) {
        this.currentScene.input(e);
    }

    private void changeScene(Scene newScene) {
        this.frame.remove(currentScene);
        this.currentScene = newScene;
        this.frame.add(currentScene);
        this.frame.pack();
    }



}