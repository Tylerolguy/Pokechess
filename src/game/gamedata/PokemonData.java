package game.gamedata;

import game.view.AbilityModel;
import game.view.CharacterModel;
import game.view.JSONImporter;

import java.awt.*;

public class PokemonData {

  // ---------------- Pokemon stats -----------------------
  public String name; //name
  
  private int hp; //current hp value
  private int maxHP; // hp at full health

  private int movementPoints = 2; // How much the pokemon can move this turn
  private int movementPointsTotal = 2; //how much can a pokemon move on their turn

  private int autoPoints = 1; // How much the pokemon can move this turn
  private int autoPointsTotal = 1; //how much can a pokemon move on their turn

  private int speedStat; // how often the pokemon gets to take a turn (100/5)
  public int currentSpeed = 0; // the current speed, when it gets to 100 it takes its turn

  // --------------- Map location ----------
  public int x;
  public int y;

  // --------------- Other Pokemon Information
  public String trainer;


  // ---------------- Visual components --------------
  private CharacterModel characterModel;
  private AbilityModel abilityModel;


  // ---------------- Other Components ------------
  private JSONImporter jsonImporter = new JSONImporter();;



  //Constructor
  public PokemonData(String name, int hp, int x, int y, int speedStat, String trainer, String id) {
    this.name = name;
    this.hp = hp;
    this.maxHP = hp;

    this.speedStat = speedStat;

    this.x = x;
    this.y = y;

    this.trainer = trainer;

    this.abilityModel = new AbilityModel(name, hp, maxHP);
    try {
      this.characterModel = new CharacterModel(this.x, this.y, this.jsonImporter.loadFromJSON(id));
    } catch (Exception e) {
      System.out.println("could not find file" + id);
      e.printStackTrace();
    }
  }


  // --------------------------------- Updaters -------------------------------------------

  //causes the pokemon to move, using one of their movement points and also has to update the
  //character models new location, and abilitie model display of movement points
  public void move(int x, int y) {
        this.movementPoints -= 1;
        this.x += x;
        this.y += y;
        this.characterModel.move(x, y);
        this.abilityModel.updatePoints(this.movementPoints, 0);

  }


  //updates the pokemons current speed.
  public void updateSpeed() {
    this.currentSpeed += this.speedStat;
  }


  //causes the pokemon to take damage, losing hp
  public void takeDamage(int damage) {
    this.hp -= damage;
  }


  //called when a pokemon's turn is over, updating any end of turn information
  public void endTurn() {
    this.movementPoints = this.movementPointsTotal;
    this.autoPoints = this.autoPointsTotal;
  }


  public void doAuto(int x, int y, PokemonData target) {
    if (this.autoPoints > 0 && target.trainer != this.trainer) {
      this.autoPoints -= 1;
      target.takeDamage(1);
    }
  }



  // --------------------------------------Getters--------------------------------
  // a helpful to string method
  public String toString() {
    return this.name;
  }

  //returns is the pokemon can still move this turn
  public boolean canMove() {
    return this.movementPoints > 0;
  }

  //returns if the pokemon's turn is up
  public boolean canAct() {
    return this.currentSpeed >= 100;
  }



  // ---------------------------- Visual draw methods and helpers ----------------------------------------

  //draws the pokemon at the current position with its health bar above it
  public void drawPokemon(Graphics g) {
    this.characterModel.draw(g);
    this.characterModel.drawHealthBar(g, this.hp, this.maxHP);
  }


  //draws the pokemon abilites, icon, and health at the bottom of the screen.
  public void drawAbilities(Graphics g, String mode) {
    this.abilityModel.updatePoints(this.movementPoints, this.autoPoints);
    this.abilityModel.draw(g, mode);

  }
}
