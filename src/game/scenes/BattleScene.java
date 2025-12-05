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


  //Image control stuff
  private BufferedImage background;
  private SelectorModel selectorModel = new SelectorModel();

  //Current Battle information
  private ArrayList<PokemonData> listOfPokemons = new ArrayList<PokemonData>(); //a list of all the pokemon on the field
  private PokemonData[][] map = new PokemonData[16][10]; //a 2d array of the locations of all the pokemon/objects on the field
  private boolean playersTurn = false; //a boolean if it is the current players turn
  private ArrayList<Integer> indexOfPokemonToAct = new ArrayList<Integer>(); //the list of pokemon who have speed over 100 in order
  private int frameNumber = 0; //how many "frames" have past since this battle has been loaded (not equivlant to amount of turns)

  //current selected pokemon Data
  private PokemonData currentPokemon; //the current pokemon whos turn it is

  private enum selectionState { //the current option that the player has selected, determines what their next inputs do
    MOVING("Moving"),
    AUTO("Auto"),
    QSPECIAL("QSpecial"),
    ESPECIAL("ESpecial"),
    RSPECIAL("RSpecial"),
    WAITING("Waiting");

    private final String name;

    selectionState(String name) {
      this.name = name;
    }

    public String getState() {
      return this.name;
    }
  }

  //sets the default state
  private selectionState state = selectionState.WAITING;


    public BattleScene() {
      setPreferredSize(new Dimension(807, 630));
    
      //sets the map to have no current pokemon (will start with some later)
      for (int i = 0; i < 15; i++) {
        for (int j = 0; j < 10; j++) {
          map[i][j] = null;

        }
      }

      //testing purposes
      PokemonData mew = new PokemonData("Mew", 10, 0, 0, 20, "player", "0151"); 
      PokemonData charmander = new PokemonData("Charmander", 10, 1, 0, 20, "npc", "0004"); 
      this.listOfPokemons.add( mew);
      this.listOfPokemons.add(charmander);
      this.map[0][0] = mew;
      this.map[1][0] = charmander;

      //loads the starting background based on what game engine gives it
      try {
        background = ImageIO.read(new File("src/game/assets/applewoods.png"));
      } catch (Exception e) {
        e.printStackTrace();
      }

    }

  
    ////////////////////////////////////////// Game Run Methods ////////////////////////////////////////


    //called by game engine to start running the game
    public void update() {
      //update the frame number
      frameNumber = (frameNumber + 1) % 64;

      //if its not the players turn, increase the speed of all the pokemon but only if no pokemon are not at 100 speed
      if (!this.playersTurn && this.indexOfPokemonToAct.isEmpty()) {
        for (PokemonData p : this.listOfPokemons) {
          p.updateSpeed();
        }

        // adds all pokemon that are above 100 speed to list of acting pokemon, sorted in order.
        this.listOfPokemons.stream()
          .filter(p -> p.canAct())
          .sorted((a, b) -> Integer.compare(b.currentSpeed, a.currentSpeed))
          .forEach(this::pokemonTurn);
      }

      //if pokemon have a turn, take their turn
      if (!this.indexOfPokemonToAct.isEmpty()) {
        this.takeTurns();
      }
    }





    //adds a index of a pokemon to list of turns (couldnt figure out the steam), 
    private void pokemonTurn(PokemonData pokemon) {
      this.indexOfPokemonToAct.add(this.listOfPokemons.indexOf(pokemon));
    }



    //this resolves a pokemons turn, setting the current pokemon to whos turn it is.
    //if the pokemon belongs to a trainer, it sets players turn to true, allowing them
    //to act or gives to npc if not. also makes sure to update visuals of the current pokemon
    private void takeTurns() {
      if (!playersTurn) {

        //sets the pokemon and reduces its speed, removes the pokemon from the list as well
        int currentIndex = indexOfPokemonToAct.get(0);
        this.currentPokemon = this.listOfPokemons.get(currentIndex);
        this.currentPokemon.currentSpeed -= 100;
        this.selectorModel.addSelectedPokemonLocation(this.currentPokemon.x, this.currentPokemon.y);
        this.indexOfPokemonToAct.remove(0);

        //determines who turn it is
        if (this.currentPokemon.trainer.equals("player")) {
          this.playersTurn = true;
        }
        else {
          this.npcMove();
        }
      }
  }


  //ends the current pokemons turn and triggers any end of turn effects for the current pokemon
  private void endTurn() {
    this.playersTurn = false;
    this.currentPokemon.endTurn();
    this.state = selectionState.WAITING;
  }


  //place holder method until the npc AI is made
  private void npcMove() {
    state = selectionState.WAITING;
    if (!this.movePokemon(1, 0)) {
      this.movePokemon(0, 1);
    }
    this.currentPokemon.endTurn();
  }






  ////////////////////////////////////////////// Input and callers //////////////////////////////////////////////////
  /// //handles all inputs.
  public void input(KeyEvent e) {

      //only allow input if its the players turn
      if (playersTurn) {
        //if no state is yet selected, determines what action the player wants to take
        if (state == selectionState.WAITING) {
          switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE: this.endTurn(); break;
                case KeyEvent.VK_M: state = selectionState.MOVING; break;
                case KeyEvent.VK_X: state = selectionState.AUTO; break;
                case KeyEvent.VK_Q: state = selectionState.QSPECIAL; break;
                case KeyEvent.VK_E: state = selectionState.ESPECIAL; break;
                case KeyEvent.VK_R: state = selectionState.RSPECIAL; break;
          }}


        //if the player wants to move
        else if (state == selectionState.MOVING) {
          switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE: this.endTurn(); break;
            case KeyEvent.VK_M: state = selectionState.WAITING; break;
            case KeyEvent.VK_X: state = selectionState.AUTO; break;
            case KeyEvent.VK_Q: state = selectionState.QSPECIAL; break;
            case KeyEvent.VK_E: state = selectionState.ESPECIAL; break;
            case KeyEvent.VK_R: state = selectionState.RSPECIAL; break;
            
            case KeyEvent.VK_W: this.movePokemon(0, -1); break; //UP
            case KeyEvent.VK_S: this.movePokemon(0, 1); break; //DOWN
            case KeyEvent.VK_A: this.movePokemon(-1, 0); break; //LEFT
            case KeyEvent.VK_D: this.movePokemon(1, 0); break; //RIGHT
          }
        }
        else if (state == selectionState.AUTO) {
          switch (e.getKeyCode()) {
            //add method select
            case KeyEvent.VK_SPACE: this.doAuto(this.selectorModel.selectorX, this.selectorModel.selectorY); break;
            case KeyEvent.VK_M: state = selectionState.MOVING; break;
            case KeyEvent.VK_X: state = selectionState.WAITING; break;
            case KeyEvent.VK_Q: state = selectionState.QSPECIAL; break;
            case KeyEvent.VK_E: state = selectionState.ESPECIAL; break;
            case KeyEvent.VK_R: state = selectionState.RSPECIAL; break;

            case KeyEvent.VK_W: this.moveSelector(0, -1); break; //UP
            case KeyEvent.VK_S: this.moveSelector(0, 1); break; //DOWN
            case KeyEvent.VK_A: this.moveSelector(-1, 0); break; //LEFT
            case KeyEvent.VK_D: this.moveSelector(1, 0); break; //RIGHT
        }}
        else if (state == selectionState.QSPECIAL) {
          switch (e.getKeyCode()) {
            //add method select
            case KeyEvent.VK_SPACE: this.doAuto(this.selectorModel.selectorX, this.selectorModel.selectorY); break;
            case KeyEvent.VK_M: state = selectionState.MOVING; break;
            case KeyEvent.VK_X: state = selectionState.AUTO; break;
            case KeyEvent.VK_Q: state = selectionState.WAITING; break;
            case KeyEvent.VK_E: state = selectionState.ESPECIAL; break;
            case KeyEvent.VK_R: state = selectionState.RSPECIAL; break;

            case KeyEvent.VK_W: this.moveSelector(0, -1); break; //UP
            case KeyEvent.VK_S: this.moveSelector(0, 1); break; //DOWN
            case KeyEvent.VK_A: this.moveSelector(-1, 0); break; //LEFT
            case KeyEvent.VK_D: this.moveSelector(1, 0); break; //RIGHT
        }}
        else if (state == selectionState.ESPECIAL) {
          switch (e.getKeyCode()) {
            //add method select
            case KeyEvent.VK_SPACE: this.doAuto(this.selectorModel.selectorX, this.selectorModel.selectorY); break;
            case KeyEvent.VK_M: state = selectionState.MOVING; break;
            case KeyEvent.VK_X: state = selectionState.AUTO; break;
            case KeyEvent.VK_Q: state = selectionState.QSPECIAL; break;
            case KeyEvent.VK_E: state = selectionState.WAITING; break;
            case KeyEvent.VK_R: state = selectionState.RSPECIAL; break;

            case KeyEvent.VK_W: this.moveSelector(0, -1); break; //UP
            case KeyEvent.VK_S: this.moveSelector(0, 1); break; //DOWN
            case KeyEvent.VK_A: this.moveSelector(-1, 0); break; //LEFT
            case KeyEvent.VK_D: this.moveSelector(1, 0); break; //RIGHT

        }}
        else if (state == selectionState.RSPECIAL) {
          switch (e.getKeyCode()) {
            //add method select
            case KeyEvent.VK_SPACE: this.doAuto(this.selectorModel.selectorX, this.selectorModel.selectorY); break;
            case KeyEvent.VK_M: state = selectionState.MOVING; break;
            case KeyEvent.VK_X: state = selectionState.AUTO; break;
            case KeyEvent.VK_Q: state = selectionState.QSPECIAL; break;
            case KeyEvent.VK_E: state = selectionState.ESPECIAL; break;
            case KeyEvent.VK_R: state = selectionState.WAITING; break;

            case KeyEvent.VK_W: this.moveSelector(0, -1); break; //UP
            case KeyEvent.VK_S: this.moveSelector(0, 1); break; //DOWN
            case KeyEvent.VK_A: this.moveSelector(-1, 0); break; //LEFT
            case KeyEvent.VK_D: this.moveSelector(1, 0); break; //RIGHT

        }}
     
      }
      
    }


  /////////////////////////////////////////// Map Managers  ////////////////////////////////////


    
  // moves a pokemon, updating any relavent info such as the selector and map
  //returns a boolean so that npc can know if they made a valid move.
  private boolean movePokemon(int x, int y) {
    int oldX = this.currentPokemon.x;
    int oldY = this.currentPokemon.y;
    int newX = oldX + x;
    int newY = oldY + y;

    //checks if the new location is in bound and that spot is empty
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



  //moves the selector icon when an attack is selected. must ensure that the selector is in range
  private void moveSelector(int x, int y) {
    int oldX = this.selectorModel.selectorX;
    int oldY = this.selectorModel.selectorY;
    int newX = oldX + x;
    int newY = oldY + y;
    
    //checks if the new location is in bound
    if (!(newX < 0 || newX > 15 || newY < 0 || newY > 9) && this.validRange(newX, newY, 1)) {
        this.selectorModel.moveSelector(x, y);
    }
  }

  //determines if the new location is valid based on the current pokemon location and the
  // range of the action
  private boolean validRange(int selectorX, int selectorY, int range) {
    return selectorX <= this.currentPokemon.x + range 
    && selectorX >= this.currentPokemon.x - range
    && selectorY <= this.currentPokemon.y + range
    && selectorY >= this.currentPokemon.y - range;
  }
    



  ////////////////////////////// Paint Components /////////////////////////////////////////////////


  //calls paint Component 
  public void paint(Graphics g) {
    this.paintComponent(g);
  }



//paints all the components needed, background, selectors, pokemon
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

    


    
    



    

    



  




  private void doAuto(int x, int y) {
    this.currentPokemon.doAuto(x, y, map[x][y]);


  }

  

}



