package game.view;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.gamedata.PokemonData;


public class AbilityModel {
  private String name;
  private int hp;
  private int maxHP;

  public AbilityModel(String name, int hp, int maxHP) {
    this.name = name;
    this.hp = hp;
    this.maxHP = maxHP;
  }

  public void draw(Graphics g) {
    String location = "src/game/assets/icons/";
    String pokemonIconLocation = location + this.name + "Icon.png" ;
    BufferedImage currentIcon;


    try {
      currentIcon = ImageIO.read(new File(location + "iconHolder.png"));
      g.drawImage(currentIcon, 125, 500, 550, 150, null);

      currentIcon = ImageIO.read(new File(pokemonIconLocation));
      g.drawImage(currentIcon, 150, 520, 50, 50, null);

      currentIcon = ImageIO.read(new File(location + "bootIcon.png"));
      g.drawImage(currentIcon, 230, 520, 50, 50, null);
      g.setFont(new Font("Arial", Font.BOLD, 24));
      g.drawString("M", 290, 538);
      g.drawString("2", 294, 566);

      //place holder for move 1
      //currentIcon = ImageIO.read(new File(location + "bootIcon.png"));
      g.drawImage(currentIcon, 330, 520, 50, 50, null);
      g.setFont(new Font("Arial", Font.BOLD, 12));
      g.drawString("A", 387, 534);
      g.drawString("Shadow Punch", 387, 560);


      //place holder for move 1
      //currentIcon = ImageIO.read(new File(location + "bootIcon.png"));
      g.drawImage(currentIcon, 485, 520, 50, 50, null);
      g.setFont(new Font("Arial", Font.BOLD, 12));
      g.drawString("A", 542, 534);
      g.drawString("Shadow Punch", 542, 560);


      // Calculate the width of the filled portion based on HP
      int filledWidth = (int) ((hp/ (float) maxHP) * 240);

      // Draw background (empty bar)
      g.setColor(Color.RED);
      g.fillRect(150, 580, 240, 20);

      // Draw foreground (filled bar)
      g.setColor(Color.GREEN);
      g.fillRect(150, 580, filledWidth, 20);

      // Optional: draw borders
      g.setColor(Color.BLACK);
      g.drawRect(150, 580, 240, 20);

      g.drawString("  5/10 ", 255, 595);



    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
        
    
        

  }
  
}
