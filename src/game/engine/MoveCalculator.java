package game.engine;

import game.gamedata.MoveData;
import game.gamedata.PokemonData;

public class MoveCalculator {

  public void doAuto(MoveData move, PokemonData user, PokemonData target) {
    int damageDealt = user.getDamage(move.damageType, move.baseDamage);
    target.takeDamage(move.damageType, damageDealt);
    user.doAuto();


  }
  
  public void doMove(MoveData move, PokemonData user, PokemonData target) {
    int damageDealt = user.getDamage(move.damageType, move.baseDamage);
    target.takeDamage(move.damageType, damageDealt);
    user.doSpecial(move.cost);


  }


  public boolean validTarget(MoveData move, PokemonData user, PokemonData target) {
    if (user.canAuto() && target != null) {
      if ( move.targetEnemy && (user.trainer != target.trainer)){
        return true;
      }
      else {
        return false;
      }
    }
    else {
      return false;
    }
  }


  public boolean validSpecialTarget(MoveData move, PokemonData user, PokemonData target){
    if (user.canUseSpecial(move.cost) && target != null) {
      if ( move.targetEnemy && (user.trainer != target.trainer)){
        return true;
      }
      else {
        return false;
      }
    }
    else {
      return false;
    }
  }




}
