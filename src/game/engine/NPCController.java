package game.engine;

import game.gamedata.MoveData;
import game.gamedata.PokemonData;

public class NPCController {
  private MoveCalculator moveCalculator = new MoveCalculator();

  public PokemonData[][] takeTurn(PokemonData[][] map, PokemonData currentPokemon) {
    MoveData auto = new MoveData("Auto", "Physical", true, 1, currentPokemon.getAutoRange(),0, "Auto");


    PokemonData enemy = this.getEnemy(map, currentPokemon);

    MoveData qMove = currentPokemon.getQSpecial();
    MoveData eMove = currentPokemon.getESpecial();
    MoveData rMove = currentPokemon.getRSpecial();

    int minRange = this.getMinRange(currentPokemon.getAuto(), qMove, eMove, rMove);
    double distanceToEnemy = Math.hypot(currentPokemon.x - enemy.x, currentPokemon.y - enemy.y);


    if (enemy != null) {
      if (currentPokemon.canAuto() && moveCalculator.validAutoTarget(auto, currentPokemon, enemy)) {
        moveCalculator.doAuto(auto, currentPokemon, enemy);  
      }
      else if (this.canDoSpecial(qMove, currentPokemon, enemy)) {
          moveCalculator.doMove(qMove, currentPokemon, enemy);
      }
      else if (this.canDoSpecial(eMove, currentPokemon, enemy)) {
          moveCalculator.doMove(eMove, currentPokemon, enemy);

      }
      else if (this.canDoSpecial(rMove, currentPokemon, enemy)) {
          moveCalculator.doMove(rMove, currentPokemon, enemy);
      }
      else if (minRange < distanceToEnemy && currentPokemon.canMove()) {
        int targetX = enemy.x;
        int targetY = enemy.y;
        this.npcMove(targetX, targetY, currentPokemon, map);
      }
    }

    
    return map;


  }

  private int getMinRange(MoveData auto, MoveData qMove, MoveData eMove, MoveData rMove) {
    int minRange = auto.range;

    if (qMove != null && qMove.range < minRange) {
      minRange = qMove.range;
    }
    if (eMove != null && eMove.range < minRange) {
      minRange = eMove.range;
    }
    if (rMove != null && rMove.range < minRange) {
      minRange = rMove.range;
    }
  




    return minRange;

  }

  public boolean shouldEndTurn(PokemonData[][] map, PokemonData currentPokemon) {
    PokemonData enemy = this.getEnemy(map, currentPokemon);
    MoveData qMove = currentPokemon.getQSpecial();
    MoveData eMove = currentPokemon.getESpecial();
    MoveData rMove = currentPokemon.getRSpecial();
    int minRange = this.getMinRange(currentPokemon.getAuto(), qMove, eMove, rMove);
    double distanceToEnemy = Math.hypot(currentPokemon.x - enemy.x, currentPokemon.y - enemy.y);
    boolean canAutoEnemy = moveCalculator.validAutoTarget(currentPokemon.getAuto(), currentPokemon, enemy);

    if (currentPokemon.canAct() && canAutoEnemy) {
      return false;

    }
    else if (this.canDoSpecial(qMove, currentPokemon, enemy) || this.canDoSpecial(rMove, currentPokemon, enemy)
    || this.canDoSpecial(eMove, currentPokemon, enemy)) {
      return false;
    }
    else if (minRange < distanceToEnemy && currentPokemon.canMove()) {
      return false;
    }
    else {
      return true;
    }
  }


  private PokemonData getEnemy(PokemonData[][] map, PokemonData currentPokemon) {

    PokemonData enemy = null;

    for (int i = 0; i < 16; i++) {
      for (int j = 0; j < 10; j++) {
        enemy = map[i][j];
        if(enemy != null && enemy.trainer == "player") {
          return enemy;
        }
      }
    }

    return null;


  }

  private boolean canDoSpecial(MoveData move, PokemonData user, PokemonData enemy) {
    return move != null && user.canUseSpecial(move.cost)
      && moveCalculator.validSpecialTarget(move, user, enemy);
  }


  private void npcMove(int targetX, int targetY, PokemonData currentPokemon, PokemonData[][] map) {
    int currentX = currentPokemon.x;
    int currentY = currentPokemon.y;

    // Directions: 8 neighbors (diagonal included)
    int[][] dirs = {
        { 1, 0}, {-1, 0}, {0, 1}, {0, -1},
        { 1, 1}, { 1,-1}, {-1, 1}, {-1,-1}
    };

    int bestX = currentX;
    int bestY = currentY;
    double bestScore = Double.MAX_VALUE;

    for (int[] d : dirs) {
        int nx = currentX + d[0];
        int ny = currentY + d[1];

        // Bounds check
        if (nx < 0 || nx >= map.length || ny < 0 || ny >= map[0].length) 
            continue;

        // Occupied
        if (map[nx][ny] != null) 
            continue;

        // Distance heuristic (Greedy Best First)
        double dist = Math.hypot(targetX - nx, targetY - ny);

        if (dist < bestScore) {
            bestScore = dist;
            bestX = nx;
            bestY = ny;
        }
    }

    // No valid moves → stay still
    if (bestX == currentX && bestY == currentY)
        return;

    // Move NPC
    map[bestX][bestY] = currentPokemon;
    map[currentX][currentY] = null;
    currentPokemon.move(bestX - currentX, bestY - currentY);
}






  


  // private boolean shouldMove(PokemonData currentPokemon, boolean canAutoEnemy) {
    




  // }

  





  
}
