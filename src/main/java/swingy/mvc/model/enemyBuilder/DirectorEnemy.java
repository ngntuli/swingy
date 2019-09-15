package swingy.mvc.model.enemyBuilder;

import lombok.Getter;

public class DirectorEnemy {

    @Getter
    private EnemyProduct enemyBuilder;
    private String[] enemies = {"Rat", "Monster", "Dragon", "Alien"};


    public void construct() {
        int rand = (int) (Math.random() * 4);

        if (enemies[rand].equals(enemies[0])) {
            enemyBuilder = new EnemyProduct();
            enemyBuilder.setEnemyBuilderStatsByDefaultsValue(enemies[rand]);
        } else if (enemies[rand].equals(enemies[1])) {
            enemyBuilder = new EnemyProduct();
            enemyBuilder.setEnemyBuilderStatsByDefaultsValue(enemies[rand]);
        } else if (enemies[rand].equals(enemies[2])) {
            enemyBuilder = new EnemyProduct();
            enemyBuilder.setEnemyBuilderStatsByDefaultsValue(enemies[rand]);
        } else if (enemies[rand].equals(enemies[3])) {
            enemyBuilder = new EnemyProduct();
            enemyBuilder.setEnemyBuilderStatsByDefaultsValue(enemies[rand]);
        }
    }
}
