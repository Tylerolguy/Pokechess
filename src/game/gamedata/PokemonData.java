package game.gamedata;

import game.view.AbilityModel;
import game.view.CharacterModel;
import game.view.JSONImporter;

import java.awt.*;

public class PokemonData {
  private JSONImporter jsonImporter;
  public String name;
  private int hp;
  private int maxHP;
  private int speedStat;
  public int currentSpeed;
  public int x;
  public int y;
  public String trainer;
  private CharacterModel characterModel;
  private AbilityModel abilityModel;

  public PokemonData(String name, int hp, int x, int y, int speedStat, String trainer, String id) {
    this.jsonImporter = new JSONImporter();
    this.name = name;
    this.hp = hp;
    this.maxHP = hp;
    this.x = x;
    this.y = y;
    this.speedStat = speedStat;
    this.currentSpeed = 0;
    this.trainer = trainer;
    this.abilityModel = new AbilityModel(name, hp, maxHP);
    try {
      this.characterModel = new CharacterModel(this.x, this.y, this.jsonImporter.loadFromJSON(id));
    } catch (Exception e) {
      System.out.println("could not find file" + id);
      e.printStackTrace();
    }
  }

  public void move(int x, int y) {

        this.x += x;
        this.y += y;
        this.characterModel.move(x, y);

  }

  public void drawPokemon(Graphics g) {
    this.characterModel.draw(g);
    this.characterModel.drawHealthBar(g, this.x, this.y, this.hp, this.maxHP);
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


  public void drawAbilities(Graphics g) {
    this.abilityModel.draw(g);

  }


}
