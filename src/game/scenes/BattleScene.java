package game.scenes;

import game.engine.Scene;
import game.gamedata.PokemonData;
import game.view.AbilityModel;
import game.view.CharacterModel;
import game.view.JSONImporter;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.*;
import java.util.*;





public class BattleScene extends Scene{
  private BufferedImage background;
  private ArrayList<CharacterModel> characters = new ArrayList<>();
  private JSONImporter jsonImporter;
  private PokemonData currentPokemon;
  private ArrayList<PokemonData> listOfPokemons;
  private static final int GRID_SIZE = 32;
  private boolean playersTurn;
  private ArrayList<Integer> indexOfPokemonToAct;
  private PokemonData[][] map;
  private AbilityModel abilityModel;


    public BattleScene() {
      jsonImporter = new JSONImporter();
      setDoubleBuffered(false);
      setPreferredSize(new Dimension(807, 630));
      this.listOfPokemons = new ArrayList<PokemonData>();
      this.indexOfPokemonToAct = new ArrayList<Integer>();
      this.abilityModel = new AbilityModel();
      
      playersTurn = false;

      map = new PokemonData[16][10];

      for (int i = 0; i < 15; i++) {
        for (int j = 0; j < 10; j++) {
          map[i][j] = null;

        }
      }


      
      



      try {
        // Load background
        background = ImageIO.read(new File("src/game/assets/applewoods.png"));

        // Load characters from JSON
        characters.add(new CharacterModel(150, 150, jsonImporter.loadFromJSON("src/game/assets/pokemonImages/0151.png", "src/game/assets/pokemonImages/0151.json")));
        characters.add(new CharacterModel(150 + GRID_SIZE, 150, jsonImporter.loadFromJSON("src/game/assets/pokemonImages/0004.png", "src/game/assets/pokemonImages/0004.json")));
        // characters.add(new CharacterModel(300, 300, jsonImporter.loadFromJSON("src/game/assets/pokemonImages/0007.png", "src/game/assets/pokemonImages/0007.json")));
        


      } catch (Exception e) {
        e.printStackTrace();
      }
      for (int i = 0; i < 44; i++) {
        this.characters.get(0).nextFrame();
      }

      PokemonData mew = new PokemonData("Mew", 10, 0, 0, 20, "player", characters.get(0)); 
      PokemonData charmander = new PokemonData("Charmander", 10, 1, 0, 20, "npc", characters.get(1)); 
      this.listOfPokemons.add( mew);
      this.listOfPokemons.add(charmander);

      this.map[0][0] = mew;
      this.map[1][0] = charmander;

    
    
    }

      
      public void paint(Graphics g) {
        this.paintComponent(g);
      }


    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      g.drawImage(background, 0, 0, 807, 630, null);


      for (PokemonData p: this.listOfPokemons) {
        p.drawPokemon(g);
      }

      if (this.currentPokemon != null) {
        this.abilityModel.draw(g, this.currentPokemon.name);
      }

    }

    public void update() {
      if (!this.playersTurn && this.indexOfPokemonToAct.isEmpty()) {
        for (PokemonData p : this.listOfPokemons) {
          p.updateSpeed();
        }

        this.listOfPokemons.stream()
          .filter(p -> p.canAct())
          .sorted((a, b) -> Integer.compare(b.currentSpeed, a.currentSpeed))
          .forEach(this::pokemonTurn);
        
        
      }

      if (!this.indexOfPokemonToAct.isEmpty()) {
        this.takeTurns();
      }


    }


    public void input(KeyEvent e) {
      if (playersTurn) {
        switch (e.getKeyCode()) {
              case KeyEvent.VK_SPACE: this.endTurn(); break;
              case KeyEvent.VK_W: this.movePokemon(0, -1); break; //UP
              case KeyEvent.VK_S: this.movePokemon(0, 1); break; //DOWN
              case KeyEvent.VK_A: this.movePokemon(-1, 0); break; //LEFT
              case KeyEvent.VK_D: this.movePokemon(1, 0); break; //RIGHT

          }
      }

    }

    public void endTurn() {
      this.playersTurn = false;

    }

    public void pokemonTurn(PokemonData pokemon) {

      this.indexOfPokemonToAct.add(this.listOfPokemons.indexOf(pokemon));


    }

    private void takeTurns() {
      if (!playersTurn) {
        
        int currentIndex = indexOfPokemonToAct.get(0);

        this.currentPokemon = this.listOfPokemons.get(currentIndex);
        this.currentPokemon.currentSpeed -= 100;
        System.out.println(this.currentPokemon.name);
        this.indexOfPokemonToAct.remove(0);

        if (this.currentPokemon.trainer.equals("player")) {
          this.playersTurn = true;
        }
        else {
          this.npcMove();
        }
      }


    }

  private void npcMove() {
    if (!this.movePokemon(1, 0)) {
      this.movePokemon(0, 1);
    }


  }

  private boolean movePokemon(int x, int y) {
    int oldX = this.currentPokemon.x;
    int oldY = this.currentPokemon.y;
    int newX = oldX + x;
    int newY = oldY + y;

    //checks if the new location is in bound
    if (!(newX < 0 || oldX + x > 15 || oldY + y < 0 || oldY + y > 9) && map[newX][newY] == null) {
        map[oldX][oldY] = null;
        this.currentPokemon.move(x, y);
        map[oldX + x][oldY + y] = this.currentPokemon;
        return true;
    }
    else {
      return false;
    }



  }
  

}



