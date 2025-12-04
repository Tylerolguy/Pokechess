package game.view;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.gamedata.PokemonData;


public class AbilityModel {

  public void draw(Graphics g, PokemonData pokemon) {
    String location = "src/game/assets/icons/";
    String pokemonIconLocation = location + pokemon.name + "Icon.png" ;
    BufferedImage currentIcon;


    try {
      currentIcon = ImageIO.read(new File(location + "iconHolder.png"));
      g.drawImage(currentIcon, 150, 500, 500, 100, null);

      currentIcon = ImageIO.read(new File(pokemonIconLocation));
      g.drawImage(currentIcon, 175, 520, 50, 50, null);

      currentIcon = ImageIO.read(new File(location + "bootIcon.png"));
      g.drawImage(currentIcon, 255, 520, 50, 50, null);
      g.setFont(new Font("Arial", Font.BOLD, 24));
      g.drawString("M", 315, 538);
      g.drawString("2", 319, 566);



    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
        
    
        

  }
  
}
