package game.scenes;

import game.engine.Scene;
import game.gamedata.PokemonData;
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
  private SelectorModel selectorModel;
  private int frameNumber = 0;


  private enum selectionState {
    MOVING("Moving"),
    AUTO("Auto"),
    SPECIAL("Special"),
    WAITING("Waiting");

    private final String name;

    selectionState(String name) {
      this.name = name;
    }

    public String getState() {
      return this.name;
    }
  }
  private selectionState state = selectionState.WAITING;


    public BattleScene() {
      setPreferredSize(new Dimension(807, 630));
      this.listOfPokemons = new ArrayList<PokemonData>();
      this.indexOfPokemonToAct = new ArrayList<Integer>();
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
        this.currentPokemon.drawAbilities(g, state.getState());

        if (this.frameNumber < 32) {


          this.selectorModel.draw(g);
        }

        if (this.state == selectionState.AUTO) {
            this.selectorModel.drawAutoSelector(g, map[this.selectorModel.selectorX][this.selectorModel.selectorY]);
          }
      }
      

      for (PokemonData p: this.listOfPokemons) {
        p.drawPokemon(g);
      }



    }

    public void update() {
      frameNumber = (frameNumber + 1) % 64;

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
        if (state == selectionState.WAITING) {
          switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE: this.endTurn(); break;
                case KeyEvent.VK_M: state = selectionState.MOVING; break;
                case KeyEvent.VK_X: state = selectionState.AUTO; break;
          }}
        else if (state == selectionState.MOVING) {
          switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE: state = selectionState.WAITING; break;
            case KeyEvent.VK_M: state = selectionState.WAITING; break;
            case KeyEvent.VK_X: state = selectionState.AUTO; break;
            case KeyEvent.VK_W: this.movePokemon(0, -1); break; //UP
            case KeyEvent.VK_S: this.movePokemon(0, 1); break; //DOWN
            case KeyEvent.VK_A: this.movePokemon(-1, 0); break; //LEFT
            case KeyEvent.VK_D: this.movePokemon(1, 0); break; //RIGHT
          }
        }
        else if (state == selectionState.AUTO) {
          switch (e.getKeyCode()) {
            case KeyEvent.VK_M: state = selectionState.MOVING; break;
            case KeyEvent.VK_X: state = selectionState.WAITING; break;
            case KeyEvent.VK_SPACE: this.doAuto(this.selectorModel.selectorX, this.selectorModel.selectorY); break;
            case KeyEvent.VK_W: this.moveSelector(0, -1); break; //UP
            case KeyEvent.VK_S: this.moveSelector(0, 1); break; //DOWN
            case KeyEvent.VK_A: this.moveSelector(-1, 0); break; //LEFT
            case KeyEvent.VK_D: this.moveSelector(1, 0); break; //RIGHT

        }}
     
      }
      
    }
    

    public void endTurn() {
      this.playersTurn = false;
      this.currentPokemon.endTurn();

    }

    public void pokemonTurn(PokemonData pokemon) {

      this.indexOfPokemonToAct.add(this.listOfPokemons.indexOf(pokemon));


    }

    private void takeTurns() {
      if (!playersTurn) {
        
        int currentIndex = indexOfPokemonToAct.get(0);

        this.currentPokemon = this.listOfPokemons.get(currentIndex);
        this.currentPokemon.currentSpeed -= 100;

        this.selectorModel.addSelectedPokemonLocation(this.currentPokemon.x, this.currentPokemon.y);
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
    state = selectionState.WAITING;
    if (!this.movePokemon(1, 0)) {
      this.movePokemon(0, 1);
    }
    this.currentPokemon.endTurn();


  }

  private boolean movePokemon(int x, int y) {
    int oldX = this.currentPokemon.x;
    int oldY = this.currentPokemon.y;
    int newX = oldX + x;
    int newY = oldY + y;

    //checks if the new location is in bound
    if (!(newX < 0 || newX > 15 || newY < 0 || newY > 9) && map[newX][newY] == null 
        && this.currentPokemon.canMove()) {
        map[oldX][oldY] = null;
        this.currentPokemon.move(x, y);
        map[oldX + x][oldY + y] = this.currentPokemon;
        this.selectorModel.moveSelectedPokemonLocation(x, y);
        return true;
    }
    else {
      return false;
    }



  }

  private boolean moveSelector(int x, int y) {
    int oldX = this.selectorModel.selectorX;
    int oldY = this.selectorModel.selectorY;
    int newX = oldX + x;
    int newY = oldY + y;
    

    //checks if the new location is in bound
    if (!(newX < 0 || newX > 15 || newY < 0 || newY > 9) && this.validRange(newX, newY)) {
        //map[oldX][oldY] = null;
        this.selectorModel.moveSelector(x, y);
        //map[oldX + x][oldY + y] = this.currentPokemon;
        return true;
    }
    else {
      return false;
    }
    
  }


  private boolean validRange(int selectorX, int selectorY) {
    return selectorX <= this.currentPokemon.x + 1 
    && selectorX >= this.currentPokemon.x - 1
    && selectorY <= this.currentPokemon.y + 1
    && selectorY >= this.currentPokemon.y - 1;


  }

  private void doAuto(int x, int y) {
    this.currentPokemon.doAuto(x, y, map[x][y]);


  }

  

}



