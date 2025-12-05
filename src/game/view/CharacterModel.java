package game.view;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;


public class CharacterModel{
        private int x, y;
        private List<BufferedImage> frames;
        public int currentFrame = 0;
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

        public void previousFrame() {
            if (frames != null && currentFrame != 0) {
                currentFrame = (currentFrame - 1);
            }
            else if (currentFrame == 0) {
                currentFrame = frames.size() - 1;
            }
        }

        public void draw(Graphics g, int hp, int maxHP, int mana, int maxMana) {

            
            if (frames != null && frames.get(currentFrame) != null) {
                g.drawImage(frames.get(currentFrame), x, y, null);
                this.drawHealthBar(g, hp, maxMana);
                this.drawManaBar(g, mana, maxMana);
                
            }
        }

        public void move(int x, int y) {
            this.x += x * GRID_SIZE;
            this.y += y * GRID_SIZE;


        }

        public BufferedImage getFrame() {
            return this.frames.get(currentFrame);
        }


        private void drawHealthBar(Graphics g, int currentHp, int maxHp) {
            int barWidth = GRID_SIZE - 4;         // width matches your grid
            int barHeight = 3;               // fixed height
            int padding = 2; // optional padding above sprite

            int nx = this.x;       
            int ny = this.y;          

            // Calculate the width of the filled portion based on HP
            int filledWidth = (int) ((currentHp / (float) maxHp) * barWidth);

            // Draw background (empty bar)
            g.setColor(Color.RED);
            g.fillRect(nx - 2, ny - barHeight - padding, barWidth, barHeight);

            // Draw foreground (filled bar)
            g.setColor(Color.GREEN);
            g.fillRect(nx - 2, ny - barHeight - padding, filledWidth, barHeight);

            // Optional: draw borders
            g.setColor(Color.BLACK);
            g.drawRect(nx - 2, ny - barHeight - padding, barWidth, barHeight);
        }

    private void drawManaBar(Graphics g, int mana, int maxMana) {
            int barWidth = GRID_SIZE - 4;         // width matches your grid
            int barHeight = 3;               // fixed height
            int padding = 2; // optional padding above sprite

            int nx = this.x;       
            int ny = this.y;          

            // Calculate the width of the filled portion based on HP
            int filledWidth = (int) ((mana / (float) maxMana) * barWidth);

            // Draw background (empty bar)
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(nx - 2, ny - barHeight - padding + barHeight, barWidth, barHeight);

            // Draw foreground (filled bar)
            g.setColor(Color.CYAN);
            g.fillRect(nx - 2, ny - barHeight - padding + barHeight, filledWidth, barHeight);

            // Optional: draw borders
            g.setColor(Color.BLACK);
            g.drawRect(nx - 2, ny - barHeight - padding + barHeight, barWidth, barHeight);

  }
    }




