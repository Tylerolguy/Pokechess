package game.view;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.gamedata.PokemonData;

public class SelectorModel {
  private final String location = "src/game/assets/";
  private final int GRID_SIZE = 32;
  private int selectedPokemonX;
  private int selectedPokemonY;
  public int selectorX;
  public int selectorY;

  
  public void draw(Graphics g) {

    BufferedImage currentImage;

    try {
      currentImage = ImageIO.read(new File(location + "selectedPink.png"));
      g.drawImage(currentImage, this.selectedPokemonX, this.selectedPokemonY, GRID_SIZE, GRID_SIZE, null);


    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void drawAutoSelector(Graphics g, PokemonData pokemon) {
    BufferedImage currentImage;
    try {
      if (pokemon == null) {
        currentImage = ImageIO.read(new File(location + "selectedYellow.png")); 
        
      }
      else if (pokemon.trainer == "player"){
        currentImage = ImageIO.read(new File(location + "selectedGreen.png"));
      }
      else {//if (pokemon.trainer == "player"){
        currentImage = ImageIO.read(new File(location + "selectedRed.png"));
      }

      g.drawImage(currentImage, this.selectorX * GRID_SIZE + 150, this.selectorY  * GRID_SIZE + 150, GRID_SIZE, GRID_SIZE, null);
      
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
   
  }


  public void addSelectedPokemonLocation(int x, int y) {
    selectedPokemonX = x * GRID_SIZE + 150;
    selectedPokemonY = y * GRID_SIZE + 150;
    this.selectorX = x;
    this.selectorY = y;


  }


  public void moveSelectedPokemonLocation(int x, int y) {
    selectedPokemonX += x * GRID_SIZE;
    selectedPokemonY += y * GRID_SIZE;
    this.moveSelector(x, y);
  }

  public void moveSelector(int x, int y) {
    this.selectorX += x;
    this.selectorY += y;
  }

  
}
