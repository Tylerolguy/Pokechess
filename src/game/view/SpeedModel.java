package game.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;



public class SpeedModel {
  private final String location = "src/game/assets/icons/";


  public void drawBar(Graphics g) {

    g.setColor(Color.ORANGE);
    g.fillRect(200, 50, 400, 20);

    g.setColor(Color.BLACK);
    g.drawRect(200, 50, 400, 20);
    g.drawRect(199, 49, 402, 22);

    g.drawRect(200, 50, 100, 20);
    g.drawRect(200, 50, 200, 20);
    g.drawRect(200, 50, 300, 20);

    
  }


private java.util.Map<Integer, Integer> xUsed = new HashMap<>();

public void addPokemon(Graphics g, String name, int speed, boolean playersPokemon) {
  
    int result = playersPokemon ? 1 : 0;

    int baseX = 200 + speed * 4;
    int y = 25 + result * 50;

    int x = baseX;

    // Spread icons if collision occurs
    while (xUsed.containsKey(x)) {
        if(xUsed.get(x) == result) {

          x += 10; // Push right by 5 pixels
        }
        else if (x > 600) {
          x += 10;
        }
        else {break;}
        
    }

    xUsed.put(x, result);

    try {
        BufferedImage img = ImageIO.read(new File(location + name.toLowerCase() + "Icon.png"));
        if (speed < 100) {
            g.drawImage(img, x, y, 20, 20, null);
            g.drawRect(x, y, 20, 20);
            g.drawLine(x + 10, y + 20 * (1 - result), baseX + 10, 56 + result * 9);
            g.drawLine(x + 9, y + 20 * (1 - result), baseX + 9, 56 + result * 9);
        }
        else {
          g.drawImage(img, x, 50, 20, 20, null);
          g.drawRect(x, 50, 20, 20);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}



  public void endOfTurn() {
    xUsed.clear();

  }



  
}
