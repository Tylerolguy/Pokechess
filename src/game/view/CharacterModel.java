package game.view;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;


public class CharacterModel{
        private int x, y;
        private List<BufferedImage> frames;
        private int currentFrame = 0;
        // private static final int GRID_SIZE = 32;

        public CharacterModel(int x, int y, List<BufferedImage> frames) {
            this.x = x;
            this.y = y;
            this.frames = frames;
            
        }

        public void nextFrame() {
            if (frames != null && frames.size() > 0) {
                currentFrame = (currentFrame + 1) % frames.size();
            }
        }

        public void draw(Graphics g) {
            if (frames != null && frames.get(currentFrame) != null) {
                g.drawImage(frames.get(currentFrame), x, y, null);
            }
        }

        public void move(int x, int y) {
            this.x += x;
            this.y += y;


        }

        public BufferedImage getFrame() {
            return this.frames.get(currentFrame);
        }
    }




