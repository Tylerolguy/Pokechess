package game.scenes;

import game.engine.Scene;
import game.gamedata.PokemonData;
import game.view.AbilityModel;
import game.view.SelectorModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.*;
import java.util.*;





public class BattleScene extends Scene{
  private BufferedImage background;
  private PokemonData currentPokemon;
  private ArrayList<PokemonData> listOfPokemons;
  private boolean playersTurn;
  private ArrayList<Integer> indexOfPokemonToAct;
  private PokemonData[][] map;
  private AbilityModel abilityModel;
  private SelectorModel selectorModel;
  private int frameNumber = 0;
  private boolean frameSwitch = true;


    public BattleScene() {
      setDoubleBuffered(false);
      setPreferredSize(new Dimension(807, 630));
      this.listOfPokemons = new ArrayList<PokemonData>();
      this.indexOfPokemonToAct = new ArrayList<Integer>();
      this.abilityModel = new AbilityModel();
      this.selectorModel = new SelectorModel();
      
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


      } catch (Exception e) {
        e.printStackTrace();
      }

      PokemonData mew = new PokemonData("Mew", 10, 0, 0, 20, "player", "0151"); 
      PokemonData charmander = new PokemonData("Charmander", 10, 1, 0, 20, "npc", "0004"); 
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

      if (this.currentPokemon != null) {
        this.abilityModel.draw(g, this.currentPokemon);

        if (frameSwitch) {
          this.selectorModel.draw(g, this.currentPokemon.x, this.currentPokemon.y);
        }
      }
      

      for (PokemonData p: this.listOfPokemons) {
        p.drawPokemon(g);
      }



    }

    public void update() {
      frameNumber = (frameNumber + 1) % 32;
      if (frameNumber == 0) frameSwitch = !frameSwitch;

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



