package game.engine;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import game.gamedata.PokemonData;

public abstract class Scene extends JPanel{

    public int gameOver;

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      
    };

    public abstract void paint(Graphics g);

    public abstract void input(KeyEvent e);

    public abstract void update();

    public abstract PokemonData[] getPlayersPokemon();

    public abstract PokemonData[] getNPCPokemon();



}
