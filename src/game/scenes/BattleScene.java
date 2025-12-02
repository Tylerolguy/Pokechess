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




public class BattleScene extends JPanel{
  private BufferedImage background;
  private ArrayList<CharacterModel> characters = new ArrayList<>();
  private JSONImporter jsonImporter;
  private static final int GRID_SIZE = 32;


    public BattleScene() {
      jsonImporter = new JSONImporter();


      try {
        // Load background
        background = ImageIO.read(new File("src/game/assets/applewoods.png"));

        // Load characters from JSON
        characters.add(new CharacterModel(100, 100, jsonImporter.loadFromJSON("src/game/assets/pokemonImages/0007.png", "src/game/assets/pokemonImages/0007.json")));
        //characters.add(new Character(200, 200, loadFromJSON("src/assets/pokemonImages/0004.png", "src/assets/pokemonImages/0004.json")));
        //characters.add(new Character(300, 300, loadFromJSON("src/assets/pokemonImages/0007.png", "src/assets/pokemonImages/0007.json")));

      } catch (Exception e) {
        e.printStackTrace();
      }}

      
      public void paint(Graphics g) {
        this.paintComponent(g);
      }


    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      g.drawImage(background, 0, 0, 807, 630, null);

      for (CharacterModel c : characters) {
        c.draw(g);
      }

    }


    public void input(KeyEvent e) {
      System.out.println("herre");
      switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE: this.characters.get(0).nextFrame(); break;
        }

    }
  

}



