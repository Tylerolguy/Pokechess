package game.gamedata;

import game.view.CharacterModel;
import java.awt.*;

public class PokemonData {
  public String name;
  private int hp;
  private int maxHP;
  private int speedStat;
  public int currentSpeed;
  public int x;
  public int y;
  public String trainer;
  private CharacterModel model;

  public PokemonData(String name, int hp, int x, int y, int speedStat, String trainer, CharacterModel model) {
    this.name = name;
    this.hp = hp;
    this.maxHP = hp;
    this.x = x;
    this.y = y;
    this.speedStat = speedStat;
    this.currentSpeed = 0;
    this.trainer = trainer;
    this.model = model;
  }

  public void move(int x, int y) {

        this.x += x;
        this.y += y;
        this.model.move(x, y);

  }

  public void drawPokemon(Graphics g) {
    this.model.draw(g);
    this.model.drawHealthBar(g, this.x, this.y, this.hp, this.maxHP);
  }

  public void updateSpeed() {
    this.currentSpeed += this.speedStat;
  }

  public boolean canAct() {
    return this.currentSpeed >= 100;
  }

  public String toString() {
    return this.name + ":" + this.currentSpeed;
  }

  public void takeDamage(int damage) {
    this.hp -= damage;
  }


}
