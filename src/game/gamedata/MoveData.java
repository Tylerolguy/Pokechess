package game.gamedata;

import java.io.IOException;

import game.view.JSONImporter;

public class MoveData {

  public final String moveName;
  public final String damageType;
  public final boolean targetEnemy;
  public final boolean targetAlly;
  public final boolean targetSelf;
  public final int baseDamage;
  public final int range;
  public final String tag;
  public final int cost;
  private JSONImporter jsonImporter ;

  public MoveData(String moveName, String damageType, boolean targetEnemy, int baseDamage, int range, int cost, String tag) {
    this.moveName = moveName;
    this.damageType = damageType;
    this.targetEnemy = targetEnemy;
    this.baseDamage = baseDamage;
    this.range = range;
    this.cost = cost;
    this.tag = tag;
    this.targetAlly = false;
    this.targetSelf = false;
  
  }

    public MoveData(String moveName, String damageType, boolean targetEnemy, int baseDamage, int range, int cost) {
    this.moveName = moveName;
    this.damageType = damageType;
    this.targetEnemy = targetEnemy;
    this.baseDamage = baseDamage;
    this.range = range;
    this.cost = cost;
    this.tag = null;
    this.targetAlly = false;
    this.targetSelf = false;
  }


  public MoveData(String moveName) throws IOException {
    this.moveName = moveName;
    this.jsonImporter = new JSONImporter(moveName);

    String[] strings = this.jsonImporter.getMoveStrings();
    this.damageType = strings[0];

    if (strings.length > 1) {
      this.tag = strings[1];
    }
    else {
      this.tag = "";
    }

    boolean[] bools = this.jsonImporter.getTargetMove();
    this.targetEnemy = bools[0];
    this.targetAlly = bools[1];
    this.targetSelf = bools[2];


    int[] ints = this.jsonImporter.getMoveInts();




    this.baseDamage = ints[0];
    this.range = ints[1];
    this.cost = ints[2];
    



  }





  
}
