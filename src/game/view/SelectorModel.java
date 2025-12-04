package game.view;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SelectorModel {
  private final String location = "src/game/assets/";
  private final int GRID_SIZE = 32;
  
  public void draw(Graphics g, int x, int y) {

    BufferedImage currentImage;
    x = x * GRID_SIZE + 150;
    y = y * GRID_SIZE + 150;

    try {
      currentImage = ImageIO.read(new File(location + "selected.png"));
      g.drawImage(currentImage, x, y, GRID_SIZE, GRID_SIZE, null);


    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  
}
