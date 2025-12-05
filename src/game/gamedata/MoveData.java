package game.gamedata;

public class MoveData {

  public final String moveName;
  public final String damageType;
  public final boolean targetEnemy;
  public final int baseDamage;
  public final int range;
  public final String tag;
  public final int cost;

  public MoveData(String moveName, String damageType, boolean targetEnemy, int baseDamage, int range, int cost, String tag) {
    this.moveName = moveName;
    this.damageType = damageType;
    this.targetEnemy = targetEnemy;
    this.baseDamage = baseDamage;
    this.range = range;
    this.cost = cost;
    this.tag = tag;
  
  }

    public MoveData(String moveName, String damageType, boolean targetEnemy, int baseDamage, int range, int cost) {
    this.moveName = moveName;
    this.damageType = damageType;
    this.targetEnemy = targetEnemy;
    this.baseDamage = baseDamage;
    this.range = range;
    this.cost = cost;
    this.tag = null;
  }





  
}
