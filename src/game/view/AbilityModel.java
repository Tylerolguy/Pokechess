package game.view;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;


public class AbilityModel {

  public void draw(Graphics g, String name) {
    String location = "src/game/assets/icons/";
    String pokemonIconLocation = location + name + "Icon.png" ;
    String iconHolderLocation = location + "iconHolder.png";
    BufferedImage pokemonIcon;


    try {
      pokemonIcon = ImageIO.read(new File(iconHolderLocation));
      g.drawImage(pokemonIcon, 150, 500, 500, 100, null);

      pokemonIcon = ImageIO.read(new File(pokemonIconLocation));
      g.drawImage(pokemonIcon, 175, 520, 50, 50, null);



    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
        
    
        

  }
  
}
