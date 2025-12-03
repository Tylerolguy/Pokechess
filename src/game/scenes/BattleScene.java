package game.scenes;
import javax.swing.*;

import game.engine.Scene;
import game.view.CharacterModel;
import game.view.JSONImporter;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;




public class BattleScene extends Scene{
  private BufferedImage background;
  private ArrayList<CharacterModel> characters = new ArrayList<>();
  private JSONImporter jsonImporter;
  private static final int GRID_SIZE = 32;


    public BattleScene() {
      jsonImporter = new JSONImporter();
      setDoubleBuffered(false);
      setPreferredSize(new Dimension(807, 630));
      



      try {
        // Load background
        background = ImageIO.read(new File("src/game/assets/applewoods.png"));

        // Load characters from JSON
        characters.add(new CharacterModel(150, 150, jsonImporter.loadFromJSON("src/game/assets/pokemonImages/0001.png", "src/game/assets/pokemonImages/0001.json")));
        characters.add(new CharacterModel(200, 200, jsonImporter.loadFromJSON("src/game/assets/pokemonImages/0004.png", "src/game/assets/pokemonImages/0004.json")));
        //characters.add(new CharacterModel(300, 300, jsonImporter.loadFromJSON("src/game/assets/pokemonImages/0007.png", "src/game/assets/pokemonImages/0007.json")));

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
        g.drawImage(c.getFrame(), 100, 100,  null);
      }

    }


    public void input(KeyEvent e) {
      switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE: this.characters.get(0).nextFrame(); break;
        }

    }


  

}



