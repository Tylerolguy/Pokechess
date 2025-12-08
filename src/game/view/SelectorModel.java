package game.view;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.gamedata.MoveData;
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

  public void drawSingleEnemySelector(Graphics g, PokemonData pokemon) {
    BufferedImage currentImage;
    try {
      //there is no target in this square
      if (pokemon == null) {
        currentImage = ImageIO.read(new File(location + "selectedYellow.png")); 
        
      }
      //invalid target
      else if (pokemon.trainer == "player"){
        currentImage = ImageIO.read(new File(location + "selectedRed.png"));
      }
      //valid target
      else if (pokemon.trainer == "npc") {
        currentImage = ImageIO.read(new File(location + "selectedGreen.png"));
      }
      //cant use the move due to mana or something
      else {
        currentImage = ImageIO.read(new File(location + "selectedGreen.png"));
      }

      g.drawImage(currentImage, this.selectorX * GRID_SIZE + 150, this.selectorY  * GRID_SIZE + 150, GRID_SIZE, GRID_SIZE, null);
      
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
   
  }

  public void drawRangeMapTargetEnemy(Graphics g, PokemonData[][] map, MoveData move, boolean canUseMove) {
    int moveRange = move.range * GRID_SIZE;
    for (int i = this.selectedPokemonX - moveRange; i <= this.selectedPokemonX + moveRange; i += GRID_SIZE) {
      for (int j = this.selectedPokemonY - moveRange; j <= this.selectedPokemonY + moveRange; j += GRID_SIZE) {
        
      
      
      //draws whats in bound
      if(!(i < 150 || i > 15 * GRID_SIZE + 150 || j < 150 || j > 9 * GRID_SIZE + 150)) {
        if ((i == this.selectedPokemonX && j == this.selectedPokemonY && move.targetSelf)) {
          g.setColor(new Color(51, 204, 51, 150)); //green
        }
        else if (i == this.selectedPokemonX && j == this.selectedPokemonY) {
            g.setColor(new Color(255, 51, 51, 150)); //red
          }
        else {
          if (map[(i - 150) / GRID_SIZE][(j - 150) /GRID_SIZE] == null) {
            g.setColor(new Color(0, 176, 240, 100)); //blue
          }
          else if (((map[(i - 150) / GRID_SIZE][(j - 150) /GRID_SIZE].trainer == "npc") && move.targetEnemy) ||
                  ((map[(i - 150) / GRID_SIZE][(j - 150) /GRID_SIZE].trainer == "player") && move.targetAlly)) {
            g.setColor(new Color(51, 204, 51, 150)); //green
          } 
          else if (((map[(i - 150) / GRID_SIZE][(j - 150) /GRID_SIZE].trainer == "npc") && !move.targetEnemy) ||
                  ((map[(i - 150) / GRID_SIZE][(j - 150) /GRID_SIZE].trainer == "player") && !move.targetAlly)){
                  g.setColor(new Color(255, 51, 51, 150)); //red
        }}
        if (!canUseMove) {
          g.setColor(new Color(100, 100, 150, 100));
        }


        
        g.fillRect(i, j, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(i, j, GRID_SIZE, GRID_SIZE);
        }
      }
    }




    // no move that targets enemy can have range 0
    // if (moveRange == 0) {
    //   g.fillRect(this.selectedPokemonY, this.selectedPokemonY, GRID_SIZE, GRID_SIZE);
    // }


  }


  public void addSelectedPokemonLocation(int x, int y) {
    selectedPokemonX = x * GRID_SIZE + 150;
    selectedPokemonY = y * GRID_SIZE + 150;
    this.setSelector(x, y);


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

  public void setSelector(int x, int y) {
    this.selectorX = x;
    this.selectorY = y;
  }

  public void drawWalkingRange(Graphics g, boolean shift, PokemonData[][] map, boolean canMove) {
    if (shift) {
      if (this.inBoundRelativeToPokemon(map, -1, -1) && this.emptySpot(map, -1, -1)) {
        g.setColor(new Color(51, 204, 51, 150)); //green
        g.fillRect(this.selectedPokemonX - GRID_SIZE, selectedPokemonY - GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(this.selectedPokemonX - GRID_SIZE, selectedPokemonY - GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.drawString("W", this.selectedPokemonX - GRID_SIZE + 10 , selectedPokemonY - GRID_SIZE + 13);
      }
      else if (this.inBoundRelativeToPokemon(map, -1, -1)){
        g.setColor(new Color(255, 51, 51, 150)); //red
        g.fillRect(this.selectedPokemonX - GRID_SIZE, selectedPokemonY - GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(this.selectedPokemonX - GRID_SIZE, selectedPokemonY - GRID_SIZE, GRID_SIZE, GRID_SIZE);
      }

      if (this.inBoundRelativeToPokemon(map, -1, 1) && this.emptySpot(map, -1, 1)) {
        g.setColor(new Color(51, 204, 51, 150)); //green
        g.fillRect(this.selectedPokemonX - GRID_SIZE, selectedPokemonY + GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(this.selectedPokemonX - GRID_SIZE, selectedPokemonY + GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.drawString("A", this.selectedPokemonX - GRID_SIZE + 10 , selectedPokemonY + GRID_SIZE + 13);
      }
      else if (this.inBoundRelativeToPokemon(map, -1, 1)){
        g.setColor(new Color(255, 51, 51, 150)); //red
        g.fillRect(this.selectedPokemonX - GRID_SIZE, selectedPokemonY + GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(this.selectedPokemonX - GRID_SIZE, selectedPokemonY + GRID_SIZE, GRID_SIZE, GRID_SIZE);
      }

      if (this.inBoundRelativeToPokemon(map, 1, 1) && this.emptySpot(map, 1, 1)) {
        g.setColor(new Color(51, 204, 51, 150)); //green
        g.fillRect(this.selectedPokemonX + GRID_SIZE, selectedPokemonY + GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(this.selectedPokemonX + GRID_SIZE, selectedPokemonY + GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.drawString("S", this.selectedPokemonX + GRID_SIZE + 10 , selectedPokemonY + GRID_SIZE + 13);
      }
      else if (this.inBoundRelativeToPokemon(map, 1, 1)){
        g.setColor(new Color(255, 51, 51, 150)); //red
        g.fillRect(this.selectedPokemonX + GRID_SIZE, selectedPokemonY + GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(this.selectedPokemonX + GRID_SIZE, selectedPokemonY + GRID_SIZE, GRID_SIZE, GRID_SIZE);
      }

      if (this.inBoundRelativeToPokemon(map, 1, -1) && this.emptySpot(map, 1, -1)) {
        g.setColor(new Color(51, 204, 51, 150)); //green
        g.fillRect(this.selectedPokemonX + GRID_SIZE, selectedPokemonY - GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(this.selectedPokemonX + GRID_SIZE, selectedPokemonY - GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.drawString("D", this.selectedPokemonX + GRID_SIZE + 10 , selectedPokemonY - GRID_SIZE + 13);
      }
      else if (this.inBoundRelativeToPokemon(map, 1, -1)){
        g.setColor(new Color(255, 51, 51, 150)); //red
        g.fillRect(this.selectedPokemonX + GRID_SIZE, selectedPokemonY - GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(this.selectedPokemonX + GRID_SIZE, selectedPokemonY - GRID_SIZE, GRID_SIZE, GRID_SIZE);
      }



    }

    else {
      if (this.inBoundRelativeToPokemon(map, -1, 0) && this.emptySpot(map, -1,0)) {
        g.setColor(new Color(51, 204, 51, 150)); //green
        g.fillRect(this.selectedPokemonX - GRID_SIZE, selectedPokemonY, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(this.selectedPokemonX - GRID_SIZE, selectedPokemonY, GRID_SIZE, GRID_SIZE);
        g.drawString("A", this.selectedPokemonX - GRID_SIZE + 10 , selectedPokemonY + 13);
      }
      else if (this.inBoundRelativeToPokemon(map, -1, 0)){
        g.setColor(new Color(255, 51, 51, 150)); //red
        g.fillRect(this.selectedPokemonX - GRID_SIZE, selectedPokemonY, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(this.selectedPokemonX - GRID_SIZE, selectedPokemonY, GRID_SIZE, GRID_SIZE);
      }

      if (this.inBoundRelativeToPokemon(map, 0, 1) && this.emptySpot(map, 0, 1)) {
        g.setColor(new Color(51, 204, 51, 150)); //green
        g.fillRect(this.selectedPokemonX, selectedPokemonY + GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(this.selectedPokemonX, selectedPokemonY + GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.drawString("S", this.selectedPokemonX + 10 , selectedPokemonY + GRID_SIZE + 13);
      }
      else if (this.inBoundRelativeToPokemon(map, 0, 1)){
        g.setColor(new Color(255, 51, 51, 150)); //red
        g.fillRect(this.selectedPokemonX, selectedPokemonY + GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(this.selectedPokemonX, selectedPokemonY + GRID_SIZE, GRID_SIZE, GRID_SIZE);
      }

      if (this.inBoundRelativeToPokemon(map, 1, 0) && this.emptySpot(map, 1, 0)) {
        g.setColor(new Color(51, 204, 51, 150)); //green
        g.fillRect(this.selectedPokemonX + GRID_SIZE, selectedPokemonY, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(this.selectedPokemonX + GRID_SIZE, selectedPokemonY , GRID_SIZE, GRID_SIZE);
        g.drawString("D", this.selectedPokemonX + GRID_SIZE + 10 , selectedPokemonY + 13);
      }
      else if (this.inBoundRelativeToPokemon(map, 1, 0)){
        g.setColor(new Color(255, 51, 51, 150)); //red
        g.fillRect(this.selectedPokemonX + GRID_SIZE, selectedPokemonY, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(this.selectedPokemonX + GRID_SIZE, selectedPokemonY , GRID_SIZE, GRID_SIZE);
      }

      if (this.inBoundRelativeToPokemon(map, 0, -1) && this.emptySpot(map, 0, -1)) {
        g.setColor(new Color(51, 204, 51, 150)); //green
        g.fillRect(this.selectedPokemonX, selectedPokemonY - GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(this.selectedPokemonX, selectedPokemonY - GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.drawString("W", this.selectedPokemonX + 10 , selectedPokemonY - GRID_SIZE + 13);
      }
      else if (this.inBoundRelativeToPokemon(map, 0, -1)){
        g.setColor(new Color(255, 51, 51, 150)); //red
        g.fillRect(this.selectedPokemonX, selectedPokemonY - GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(this.selectedPokemonX, selectedPokemonY - GRID_SIZE, GRID_SIZE, GRID_SIZE);
      }

    }


  
  }


  public boolean emptySpot(PokemonData[][] map, int x, int y) {
    x = (this.selectedPokemonX - 150) / GRID_SIZE + x;
    y = (this.selectedPokemonY - 150) / GRID_SIZE +  y;
    if (!(x < 0 || x > 15 || y < 0 || y > 9)) {
      return map[x][y] == null;
    }
    else {
      return false;
    }
    

  }


  public boolean inBoundRelativeToPokemon(PokemonData[][] map, int x, int y) {
    x = (this.selectedPokemonX - 150) / GRID_SIZE + x;
    y = (this.selectedPokemonY - 150) / GRID_SIZE + y;
    return !(x < 0 || x > 15 || y < 0 || y > 9);

  }

}
