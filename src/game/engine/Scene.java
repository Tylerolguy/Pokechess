package game.engine;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public abstract class Scene extends JPanel{

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      
    };

    public abstract void paint(Graphics g);

    public abstract void input(KeyEvent e);

    


}
