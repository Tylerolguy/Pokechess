package src;


public class CharacterModel{
    int x, y;
        List<BufferedImage> frames;
        int currentFrame = 0;

        public Character(int x, int y, List<BufferedImage> frames) {
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
    }




