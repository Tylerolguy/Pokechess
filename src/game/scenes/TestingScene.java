package game.scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;

import game.engine.Scene;
import game.gamedata.MoveData;
import game.gamedata.PokemonData;

public class TestingScene extends Scene{
  PokemonData[] pokemons;
  PokemonData[] npcpokemons;


  public TestingScene() {
    MoveData ember;
    MoveData withdraw;
    MoveData poisonPowder;


    PokemonData charmander;
    PokemonData bulbasaur;
    PokemonData squirtle;
    PokemonData rattata;
    PokemonData spearow;
    PokemonData pikachu;
    //charmander = new PokemonData("Charmander", 10, 0, 0, 25, "player", 4, new MoveData[]{ember});
    try {
      charmander = new PokemonData("Charmander", 0, 5, null, 4, "player");
      bulbasaur = new PokemonData("Bulbasaur", 0, 6, null, 1, "player");
      squirtle = new PokemonData("Squirtle", 0, 7, null, 7, "player");

      rattata = new PokemonData("Rattata", 14, 7, null, 19, "npc");
      spearow = new PokemonData("Spearow", 14, 6, null, 21, "npc");
      pikachu = new PokemonData("Pikachu", 14, 5, null, 25, "npc");

      ember = new MoveData("Ember");
      withdraw = new MoveData("Tackle");
      poisonPowder = new MoveData("Tackle");

    } catch (IOException e) {
      ember = new MoveData("Ember", "Special", true, 3, 2, 4);
      withdraw = new MoveData("Withdraw", "Status", false, 3, 0, 3);
      poisonPowder = new MoveData("Poison Powder", "Status", true, 0, 2, 5);
      charmander = new PokemonData("Charmander", 10, 0, 0, 25, "player", 4, new MoveData[]{ember});
      bulbasaur = new PokemonData("Bulbasaur", 10, 0, 2, 25, "player", 1, new MoveData[]{poisonPowder});
      squirtle = new PokemonData("Squirtle", 15, 0, 1, 25, "player", 7, new MoveData[]{withdraw});
      rattata = new PokemonData("Charmander", 10, 0, 0, 25, "player", 4, new MoveData[]{ember});
      spearow = new PokemonData("Charmander", 10, 0, 0, 25, "player", 4, new MoveData[]{ember});
      pikachu = new PokemonData("Charmander", 10, 0, 0, 25, "player", 4, new MoveData[]{ember});
      e.printStackTrace();
    }//new PokemonData("Charmander", 10, 0, 0, 25, "player", "0004", new MoveData[]{ember});

    pokemons = new PokemonData[]{charmander, squirtle, bulbasaur};
    npcpokemons = new PokemonData[]{rattata, spearow, pikachu};
  }


  public PokemonData[] getPlayersPokemon() {
    return pokemons;
  }

  public PokemonData[] getNPCPokemon() {
    return npcpokemons;
  }


  @Override
  public void paint(Graphics g) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'paint'");
  }

  @Override
  public void input(KeyEvent e) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'input'");
  }

  @Override
  public void update() {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'update'");
  }
  
}
