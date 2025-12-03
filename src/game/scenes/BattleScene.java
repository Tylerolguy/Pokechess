package game.scenes;

import game.engine.Scene;
import game.gamedata.PokemonData;
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


    public BattleScene() {
      jsonImporter = new JSONImporter();
      setDoubleBuffered(false);
      setPreferredSize(new Dimension(807, 630));
      this.listOfPokemons = new ArrayList<PokemonData>();
      this.indexOfPokemonToAct = new ArrayList<Integer>();
      
      playersTurn = false;
      



      try {
        // Load background
        background = ImageIO.read(new File("src/game/assets/applewoods.png"));

        // Load characters from JSON
        characters.add(new CharacterModel(150, 150, jsonImporter.loadFromJSON("src/game/assets/pokemonImages/0405.png", "src/game/assets/pokemonImages/0405.json")));
        characters.add(new CharacterModel(150 + GRID_SIZE, 150, jsonImporter.loadFromJSON("src/game/assets/pokemonImages/0004.png", "src/game/assets/pokemonImages/0004.json")));
        // characters.add(new CharacterModel(300, 300, jsonImporter.loadFromJSON("src/game/assets/pokemonImages/0007.png", "src/game/assets/pokemonImages/0007.json")));
        


      } catch (Exception e) {
        e.printStackTrace();
      }
      for (int i = 0; i < 5; i++) {
        this.characters.get(0).nextFrame();
      }

      PokemonData luxray = new PokemonData("Luxray", 10, 150, 150, 20, "player", characters.get(0)); 
      PokemonData charmander = new PokemonData("Charmander", 10, 150 + GRID_SIZE, 150, 20, "npc", characters.get(1)); 
      this.listOfPokemons.add( luxray);
      this.listOfPokemons.add(charmander);

      this.currentPokemon = luxray;
    
    
    }

      
      public void paint(Graphics g) {
        this.paintComponent(g);
      }


    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      g.drawImage(background, 0, 0, 807, 630, null);

      //currentPokemon.drawPokemon(g);

      for (CharacterModel c : this.characters) {
        c.draw(g);
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
              case KeyEvent.VK_W: this.currentPokemon.move(0, -GRID_SIZE); break; //UP
              case KeyEvent.VK_S: this.currentPokemon.move(0, GRID_SIZE); break; //DOWN
              case KeyEvent.VK_A: this.currentPokemon.move(-GRID_SIZE, 0); break; //LEFT
              case KeyEvent.VK_D: this.currentPokemon.move(GRID_SIZE, 0); break; //RIGHT

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
    this.currentPokemon.move(GRID_SIZE, 0);


  }



  

}



