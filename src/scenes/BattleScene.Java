package src;




public class BattleScene {
  private BufferedImage background;
  private ArrayList<Character> characters = new ArrayList<>();

    public BattleScene(){
      try {
        // Load background
        background = ImageIO.read(new File("src/assets/applewoods.png"));

        // Load characters from JSON
        characters.add(new Character(100, 100, loadFromJSON("src/assets/pokemonImages/0007.png", "src/assets/pokemonImages/0007.json")));
        //characters.add(new Character(200, 200, loadFromJSON("src/assets/pokemonImages/0004.png", "src/assets/pokemonImages/0004.json")));
        //characters.add(new Character(300, 300, loadFromJSON("src/assets/pokemonImages/0007.png", "src/assets/pokemonImages/0007.json")));

      } catch (Exception e) {
        e.printStackTrace();
      }

      }

  // Input handling
  this.addKeyListener(new KeyAdapter() {

    @Override
    public void keyPressed(KeyEvent e) {

      Character player = characters.get(0);

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:    player.y -= GRID_SIZE; break;
            case KeyEvent.VK_DOWN:  player.y += GRID_SIZE; break;
            case KeyEvent.VK_LEFT:  player.x -= GRID_SIZE; break;
            case KeyEvent.VK_RIGHT: player.x += GRID_SIZE; break;
            case KeyEvent.VK_SPACE: player.nextFrame(); break;
        }

        repaint();
        }
    });


    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

      for (Character c : characters) {
        c.draw(g);
      }

    }

}



