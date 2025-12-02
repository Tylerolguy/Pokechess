package game.scenes;
import javax.swing.*;

import game.view.CharacterModel;
import game.view.JSONImporter;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;




public class BattleScene extends JPanel implements Runnable {
  private BufferedImage background;
  private ArrayList<CharacterModel> characters = new ArrayList<>();
  private JSONImporter jsonImporter;
  private static final int GRID_SIZE = 32;

    public BattleScene() {
      try {
        // Load background
        background = ImageIO.read(new File("src/assets/applewoods.png"));

        // Load characters from JSON
        //characters.add(new CharacterModel(100, 100, jsonImporter.loadFromJSON("src/assets/pokemonImages/0007.png", "src/assets/pokemonImages/0007.json")));
        //characters.add(new Character(200, 200, loadFromJSON("src/assets/pokemonImages/0004.png", "src/assets/pokemonImages/0004.json")));
        //characters.add(new Character(300, 300, loadFromJSON("src/assets/pokemonImages/0007.png", "src/assets/pokemonImages/0007.json")));

      } catch (Exception e) {
        e.printStackTrace();
      }

    

  // Input handling
  this.addKeyListener(new KeyAdapter() {

    @Override
    public void keyPressed(KeyEvent e) {

      CharacterModel player = characters.get(0);

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:    player.move(0, -GRID_SIZE); break;
            case KeyEvent.VK_DOWN:  player.move(0, GRID_SIZE); break;
            case KeyEvent.VK_LEFT:  player.move(-GRID_SIZE, 0); break;
            case KeyEvent.VK_RIGHT: player.move(GRID_SIZE, 0); break;
            case KeyEvent.VK_SPACE: player.nextFrame(); break;
        }

        repaint();
        }
    });}


    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

      for (CharacterModel c : characters) {
        c.draw(g);
      }

    }
    @Override
    public void run() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

}



