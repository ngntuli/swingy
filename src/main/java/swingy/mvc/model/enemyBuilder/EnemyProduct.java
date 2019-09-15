package swingy.mvc.model.enemyBuilder;

import lombok.Getter;
import lombok.Setter;
import swingy.mvc.model.mapBuilder.Coordinates;

import java.util.Random;

@Getter
@Setter
public class EnemyProduct {

    private String nameEnemy;
    private String typeEnemy = "Enemy";
    private int levelEnemy;
    private int xpEnemy;
    private int attackEnemy;
    private int defenseEnemy;
    private int hpEnemy;
    private Coordinates coordinatesEnemy;

    EnemyProduct() {
        coordinatesEnemy = new Coordinates(0, 0);
    }

    public String getInfo() {
        return ("\n\t" + nameEnemy + "\n\n\n\tLevel: " + levelEnemy + "\n\n\n\tExp: " + xpEnemy
                + "\n\n\n\tAttack: " + attackEnemy + "\n\n\n\tDefense: " + defenseEnemy + "\n\n\n\tHit points: " + hpEnemy + "\n\n\n");
    }

    void setEnemyBuilderStatsByDefaultsValue(String nameEnemy) {
        Random rand = new Random();
        int c = rand.nextInt(16) + 1;
        setNameEnemy(nameEnemy);
        setLevelEnemy(rand.nextInt(6) + 1);
        setXpEnemy(0);
        setAttackEnemy(rand.nextInt(16 - c + 2) * 21);
        setDefenseEnemy(rand.nextInt(16 - c + 1) * 16);
        setHpEnemy(rand.nextInt(16 - c + 1) * 66);
    }
}
