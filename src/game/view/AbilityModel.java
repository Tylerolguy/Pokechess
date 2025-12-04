package game.view;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class AbilityModel {
  private String pokemonName;
  private int hp;
  private int maxHP;
  private int movementPoints;
  private final String location = "src/game/assets/icons/";

  public AbilityModel(String name, int hp, int maxHP) {
    this.pokemonName = name;
    this.hp = hp;
    this.maxHP = maxHP;
  }

  //Draws the main ability panel
  public void draw(Graphics g) {

    BufferedImage currentIcon;


    try {
      //draws the box that abilites appear in
      currentIcon = ImageIO.read(new File(location + "iconHolder.png"));
      g.drawImage(currentIcon, 100, 500, 605, 150, null);

      //draws the pokemon icon in the box
      currentIcon = ImageIO.read(new File(location + this.pokemonName + "Icon.png"));
      g.drawImage(currentIcon, 132, 523, 50, 50, null);

      //the highlight around the selected ability (update the if statemen) (maybe make it a function)
      if (true){
        //ability rectanges
        g.setColor(Color.PINK);
        g.fillRect(192, 517, 163, 60);
        g.fillRect(355, 517, 163, 60);
        g.fillRect(518, 517, 163, 60);

        g.setColor(Color.BLACK);
        g.drawRect(192, 517, 163, 60);
        g.drawRect(355, 517, 163, 60);
        g.drawRect(518, 517, 163, 60);

        //stat rectangles
        g.drawRect(395, 580, 40, 18);
        g.drawRect(395, 600, 40, 18);
        g.drawRect(355, 580, 40, 18);
        g.drawRect(355, 600, 40, 18);
        g.drawRect(315, 580, 40, 18);
        g.drawRect(315, 600, 40, 18);
        //stat placeholder image
        g.drawImage(currentIcon, 318, 582, 15, 15,  null);


        //auto box, movebox, endTurn box
        g.drawRect(132, 580, 60, 38);
        g.drawRect(192, 580, 60, 38);
        g.drawRect(252, 580, 60, 38);

        g.setFont(new Font("Arial", Font.BOLD, 11));

        g.drawString("Move: " + this.movementPoints, 134, 592);
        g.drawString("Button: M", 134, 611);

        g.drawString("Auto: " + this.movementPoints, 194, 592);
        g.drawString("Button: X", 194, 611);

        g.drawString("End Turn", 254, 592);
        g.drawString("Space", 254, 611);
        



      }
      

      //draws the abilites, change the if statement to be a for each ability that the pokemon has
      if (true){
        this.drawAbility(g, location + this.pokemonName + "Icon.png", "Wood Hammer", "Q", 207, 520);
        this.drawAbility(g, location + this.pokemonName + "Icon.png", "Shadow Punch", "E", 370, 520);
        this.drawAbility(g, location + this.pokemonName + "Icon.png", "Cut", "R", 533, 520);
      }


      //Draws the health and mana bar, might be able to convert to one method if throw in colors, and location
      this.drawHealth(g, this.hp, this.maxHP);
      this.drawMana(g, 0, 10);






    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
        
    
        

  }

  public void updateMovementPoints(int newPointValue) {
    this.movementPoints = newPointValue;
  }


  private void drawAbility(Graphics g, String fileLocation, String nameOfMove, String buttonToActivate, int x, int y) throws IOException {
      g.setColor(Color.BLACK);

      BufferedImage abilityIcon = ImageIO.read(new File(fileLocation));
      g.drawImage(abilityIcon, x, y, 50, 50, null);
      g.setFont(new Font("Arial", Font.BOLD, 12));
      g.drawString(nameOfMove, x + 55, y + 13);

      g.setFont(new Font("Arial", Font.BOLD, 12));
      g.drawString(buttonToActivate, x - 12, y + 30);

  }

  private void drawHealth(Graphics g, int hp, int maxHP) {
      int length = 240;
      int height = 18;
      int x = 441;
      int y = 580;

      // Calculate the width of the filled portion based on HP
      int filledWidth = (int) ((hp/ (float) maxHP) * length);

      // Draw background (empty bar)
      g.setColor(Color.RED);
      g.fillRect(x, y, length, height);

      // Draw foreground (filled bar)
      g.setColor(Color.GREEN);
      g.fillRect(x, y, filledWidth, height);

      // Optional: draw borders
      g.setColor(Color.BLACK);
      g.drawRect(x, y, length, height);

    g.setFont(new Font("Arial", Font.BOLD, 12));
      g.drawString("  10/10 ", x + (int)(length / 2) - 21, y + (int)(height / 2) + 5);

  }


  private void drawMana(Graphics g, int hp, int maxHP) {
      int length = 240;
      int height = 18;
      int x = 441;
      int y = 600;

      // Calculate the width of the filled portion based on HP
      int filledWidth = (int) ((hp/ (float) maxHP) * length);

      // Draw background (empty bar)
      g.setColor(Color.LIGHT_GRAY);
      g.fillRect(x, y, length, height);

      // Draw foreground (filled bar)
      g.setColor(Color.CYAN);
      g.fillRect(x, y, filledWidth, height);

      // Optional: draw borders
      g.setColor(Color.BLACK);
      g.drawRect(x, y, length, height);

      g.setFont(new Font("Arial", Font.BOLD, 12));
      g.drawString("  10/10 ", x + (int)(length / 2) - 21, y + (int)(height / 2) + 5);

  }
  
}
