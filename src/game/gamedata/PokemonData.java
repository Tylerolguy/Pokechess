package game.gamedata;

import game.view.AbilityModel;
import game.view.CharacterModel;
import game.view.JSONImporter;

import java.awt.*;
import java.io.IOException;

public class PokemonData {

  // ---------------- Pokemon stats -----------------------
  public String name; //name
  public int id;
  
  private int hp; //current hp value
  private int maxHP; // hp at full health
  private int attack = 1;
  private int specialAttack = 1;
  private int defense = 1;
  private int specialDefense = 1;
  private int autoRange = 2;
  private int currentMana = 0;
  private int manaRecovery = 1;
  private int maxMana = 10;

  private MoveData[] moves;

  private int movementPoints; // How much the pokemon can move this turn
  private int movementPointsTotal = 15; //how much can a pokemon move on their turn

  private int autoPoints = 1; // How much the pokemon can move this turn
  private int autoPointsTotal = 1; //how much can a pokemon move on their turn

  private int speedStat; // how often the pokemon gets to take a turn (100/5)
  private int currentSpeed = 0; // the current speed, when it gets to 100 it takes its turn

  // --------------- Map location ----------
  public int x;
  public int y;

  // --------------- Other Pokemon Information
  public String trainer;


  // ---------------- Visual components --------------
  private CharacterModel characterModel;
  private AbilityModel abilityModel;


  // ---------------- Other Components ------------
  private JSONImporter jsonImporter;

  public PokemonData(String name, int x, int y, String[] moveNames, int id, String trainer) throws IOException {


    this.jsonImporter = new JSONImporter(name);
    int[] stats = this.jsonImporter.getStats();


    this.name = name;
    this.x = x;
    this.y = y;

    this.hp = stats[0];
    this.maxHP = stats[0];
    this.attack = stats[1];
    this.defense = stats[2];
    this.specialAttack = stats[3];
    this.specialDefense = stats[4];
    this.speedStat = stats[5];
    this.manaRecovery = stats[6];
    this.maxMana = stats[7];
    this.autoRange = stats[8];
    this.movementPointsTotal = stats[9];
    this.movementPoints = this.movementPointsTotal;

    this.trainer = trainer;
    this.moves = new MoveData[moveNames.length];

    for (int i = 0 ; i < this.moves.length; i++) {
      this.moves[i] = new MoveData(moveNames[i]);
      
    }


    this.abilityModel = new AbilityModel(name);
    try {
      String s = String.valueOf(id);
      while(s.length() < 4) {
        s = "0" + s; 
      }
      this.characterModel = new CharacterModel(this.x, this.y, this.jsonImporter.loadFramesFromJSON(s));
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
  public void timePass() {
    this.currentSpeed += this.speedStat;
    this.currentMana += this.manaRecovery;
    if (this.currentMana > this.maxMana) {
      this.currentMana = this.maxMana;
    }
  }


  //causes the pokemon to take damage, losing hp
  public void takeDamage(int damage) {
    this.hp -= damage;
  }


  //called when a pokemon's turn is over, updating any end of turn information
  public void endTurn() {
    this.movementPoints = this.movementPointsTotal;
    this.autoPoints = this.autoPointsTotal;
    this.currentSpeed -= 100;
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

  public int getSpeed() {
    return this.currentSpeed;
  }

  public boolean canAuto() {
    return this.autoPoints > 0;
  }

  public int getAutoRange() {
    return this.autoRange;
  }

  public boolean canUseSpecial(int cost) {
    return this.currentMana >= cost;
  }

  //not every pokemon has a e, so have to check it exists first
  public MoveData getQSpecial() {
    if (this.moves.length > 0) {
      return this.moves[0];
    }
    else {
      return null;
    }
  }

  //not every pokemon has a e, so have to check it exists first
  public MoveData getESpecial() {
    if (this.moves.length > 1) {
      return this.moves[1];
    }
    else {
      return null;
    }
  }

  //not every pokemon has a r, so have to check it exists first
  public MoveData getRSpecial() {
    if (this.moves.length > 2) {
      return this.moves[2];
    }
    else {
      return null;
    }
  }


  public boolean isFainted() {
    return this.hp <= 0;
  }

  public MoveData getAuto() {
    return new MoveData("Auto", "Physical", true, 1, this.autoRange, 0, "Auto");
  }



  /////////////////////////// Battle Methods ////////////////////////////////////





  public void doAuto() {
      this.autoPoints -= 1;
    }

  public void doSpecial(int cost) {
    this.currentMana -= cost;
  }
  


  public int getDamage(String type, int baseDamage) {
    if (type == "Physical"){
      return baseDamage + this.attack;
    }
    else {
      return baseDamage + this.specialAttack;
    }
  }


  public void takeDamage(String type, int damage)
  {
    if (type == "Physical"){
      this.hp = this.hp - (damage - this.defense);
    }
    else {
      this.hp = this.hp - (damage - this.specialDefense);
    }
  }

  



  // ---------------------------- Visual draw methods and helpers ----------------------------------------

  //draws the pokemon at the current position with its health bar above it
  public void drawPokemon(Graphics g) {
    
    this.characterModel.draw(g, this.hp, this.maxHP, this.currentMana, this.maxMana);
  }


  //draws the pokemon abilites, icon, and health at the bottom of the screen.
  public void drawAbilities(Graphics g, String mode) {
    this.abilityModel.updatePoints(this.movementPoints, this.autoPoints);
    this.abilityModel.draw(g, mode, moves, this.hp, this.maxHP, this.currentMana, this.maxMana);

  }

  public void switchFrame() {
    this.characterModel.nextFrame();
  }
}
