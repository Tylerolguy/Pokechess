package game.scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;

import game.engine.Scene;
import game.gamedata.PokemonData;

public class TestingScene extends Scene{
  PokemonData[] pokemons;
  PokemonData[] npcpokemons;

  public int gameOver = 0;


  public TestingScene() {


    PokemonData charmander;
    PokemonData bulbasaur;
    PokemonData squirtle;
    PokemonData rattata;
    PokemonData spearow;
    PokemonData pikachu;
    //charmander = new PokemonData("Charmander", 10, 0, 0, 25, "player", 4, new MoveData[]{ember});
    try {
      // ember = new MoveData("Ember");
      // withdraw = new MoveData("Tackle");
      // poisonPowder = new MoveData("Tackle");
      charmander = new PokemonData("Charmander", 0, 5, new String[]{"ember"}, 4, "player");
      bulbasaur = new PokemonData("Bulbasaur", 0, 6, new String[]{"ember"}, 1, "player");
      squirtle = new PokemonData("Squirtle", 0, 7, new String[]{"ember"}, 7, "player");

      rattata = new PokemonData("Rattata", 14, 7, new String[]{"ember"}, 19, "npc");
      spearow = new PokemonData("Spearow", 14, 6, new String[]{"ember"}, 21, "npc");
      pikachu = new PokemonData("Pikachu", 14, 5, new String[]{"ember"}, 25, "npc");

      pokemons = new PokemonData[]{charmander, squirtle, bulbasaur};
      npcpokemons = new PokemonData[]{rattata, spearow, pikachu};

    } catch (IOException e) {
      pokemons = null;
      npcpokemons = null;
      e.printStackTrace();
    }//new PokemonData("Charmander", 10, 0, 0, 25, "player", "0004", new MoveData[]{ember});


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
