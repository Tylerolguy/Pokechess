package game.view;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;


public class CharacterModel{
        private int x, y;
        private List<BufferedImage> frames;
        private int currentFrame = 0;
        private final int GRID_SIZE = 32;


        public CharacterModel(int x, int y, List<BufferedImage> frames) {
            this.x = x * GRID_SIZE + 150;
            this.y = y * GRID_SIZE + 150;
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
            this.x += x * GRID_SIZE;
            this.y += y * GRID_SIZE;


        }

        public BufferedImage getFrame() {
            return this.frames.get(currentFrame);
        }


        public void drawHealthBar(Graphics g, int x, int y, int currentHp, int maxHp) {
            int barWidth = GRID_SIZE - 4;         // width matches your grid
            int barHeight = 4;               // fixed height
            int padding = 2; // optional padding above sprite

            x = x * GRID_SIZE + 150;       
            y = y * GRID_SIZE + 150;          

            // Calculate the width of the filled portion based on HP
            int filledWidth = (int) ((currentHp / (float) maxHp) * barWidth);

            // Draw background (empty bar)
            g.setColor(Color.RED);
            g.fillRect(x - 2, y - barHeight - padding, barWidth, barHeight);

            // Draw foreground (filled bar)
            g.setColor(Color.GREEN);
            g.fillRect(x - 2, y - barHeight - padding, filledWidth, barHeight);

            // Optional: draw borders
            g.setColor(Color.BLACK);
            g.drawRect(x - 2, y - barHeight - padding, barWidth, barHeight);
        }
    }




