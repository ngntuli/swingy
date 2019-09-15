package swingy.mvc.controller;

import lombok.Getter;
import lombok.Setter;
import swingy.db.DataBase;
import swingy.mvc.model.artifactsBuilder.Artifacts;
import swingy.mvc.model.artifactsBuilder.DirectorArtifacts;
import swingy.mvc.model.enemyBuilder.DirectorEnemy;
import swingy.mvc.model.enemyBuilder.EnemyProduct;
import swingy.mvc.model.heroBuilder.HeroProduct;
import swingy.mvc.model.mapBuilder.Coordinates;
import swingy.mvc.model.mapBuilder.DirectorCoordinates;
import swingy.mvc.model.mapBuilder.DirectorMap;
import swingy.mvc.model.mapBuilder.MapGame;
import swingy.mvc.view.IView;
import swingy.mvc.view.console.ConsoleView;
import swingy.mvc.view.gui.SwingView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class Controller {
    private static DataBase db;
    private static ArrayList<EnemyProduct> enemies;
    private static DirectorArtifacts artifactsBuilder;
    private static Artifacts artifactsInput;
    private static IView currentDisplay;
    private static List<String> names;
    private static ConsoleView console;
    private static SwingView swing;
    private static String fightReport;
    private static DirectorEnemy directorEnemy;
    private static EnemyProduct enemy;
    private static boolean gameOn;
    public Random randClass;
    public boolean heroWon;
    public int countLife;
    public MapGame map;
    public HeroProduct hero;

    public Controller() {
        db = DataBase.getDb();
        directorEnemy = new DirectorEnemy();
        artifactsBuilder = new DirectorArtifacts();
        hero = null;
        map = null;
        console = null;
        randClass = new Random();
        enemies = new ArrayList<>();
        heroWon = false;
        gameOn = true;
        countLife = 2;
    }

    public void startGame(String arg) throws Exception {
        if (arg.matches("console")) {
            console = new ConsoleView(this);
            currentDisplay = console;
            currentDisplay.displayStartViewAndGetHero();
            initGame();
            playGame();


        } else if (arg.matches("gui")) {
            swing = new SwingView(this);
            currentDisplay = swing;
            currentDisplay.displayStartViewAndGetHero();

        } else {
            System.out.println("Check your spelling");
            System.exit(1);
        }

    }


    public void initGame() {
        createMap(hero.getLevel());
        createEnemies();
    }

    private void newFormOfMap() {
        createMap(hero.getLevel());
        int x, y;

        for (EnemyProduct enemy : enemies) {
            x = enemy.getCoordinatesEnemy().getX();
            y = enemy.getCoordinatesEnemy().getY();
            map.getGrid()[y][x] = 2;
        }

        x = hero.getCoordinates().getX();
        y = hero.getCoordinates().getY();
        map.getGrid()[y][x] = 1;

    }

    private void createEnemies() {
        enemies.clear();
        Random rand = new Random();

        Coordinates c;
        int numEnemiesRange = map.getSize();
        int numEnemies = rand.nextInt(numEnemiesRange) + 1;
        for (int i = 0; i < numEnemies; i++) {
            int x = rand.nextInt(map.getSize()), y = rand.nextInt(map.getSize());
            c = DirectorCoordinates.newCoordinates(x, y, map);
            directorEnemy.construct();
            directorEnemy.getEnemyBuilder().setCoordinatesEnemy(c);
            enemies.add(directorEnemy.getEnemyBuilder());
        }
    }

    private void createMap(int level) {
        map = DirectorMap.newMap(level);
    }

    public void saveHero() throws Exception {
        db.updateHero(hero);
    }

    public void receiveInputFromUser(int input) throws Exception {
        movePlayer(input);
        if (enemyEncountered()) {
            simulateFight(enemy);
        } else {
            checkPlayerWon();
            if (heroWon) {
                currentDisplay.displayGameOver();
            }
        }
    }

    private boolean enemyEncountered() {
        for (EnemyProduct e : enemies) {
            if (e.getCoordinatesEnemy().getX() == hero.getCoordinates().getX() &&
                    e.getCoordinatesEnemy().getY() == hero.getCoordinates().getY()) {
                enemy = e;
                return true;
            }
        }
        return false;
    }

    private void simulateFight(EnemyProduct enemy) throws Exception {

        Coordinates coordinatesE = enemy.getCoordinatesEnemy();
        currentDisplay.displayBattleReport(enemy.getInfo());
        if (currentDisplay.displayQuitDialogue(hero.getName() + " (" + hero.getHp() + "HP) VS " + enemy.getNameEnemy() + " (" + enemy.getHpEnemy() + "HP)")) {
            if (tryToKillEnemy())
                return;
            fighting(enemy);
        } else {
            if (forcedFight()) {
                hero.setCoordinates(new Coordinates(hero.getPrevCoordinates().getX(), hero.getPrevCoordinates().getY()));
                currentDisplay.displayBattleReport("\tYou Able To Escape !!!");
            } else {
                currentDisplay.displayForcedFightMsg();
                if (tryToKillEnemy())
                    return;
                fighting(enemy);
            }
        }

        if (!coordinatesE.equals(hero.getCoordinates()))
            return;
        tryToKillEnemy();
    }

    private boolean tryToKillEnemy() {
        String s = "";
        if (enemy.getHpEnemy() <= 0) {
            s = "won";
            int enemyP = (enemy.getAttackEnemy() + enemy.getDefenseEnemy());
            hero.setXp(hero.getXp() + (enemyP * 3));
            currentDisplay.displayBattleReport("You won the battle!!! and gained " + (enemyP * 3) + " XP !");
            enemies.remove(enemy);
            levelUp(enemy);
        } else {
            hero.setCoordinates(new Coordinates(hero.getPrevCoordinates().getX(), hero.getPrevCoordinates().getY()));
        }
        return !s.equals("");
    }

    private void fighting(EnemyProduct enemy) throws Exception {

        if (randClass.nextInt(9) == 3) {
            enemy.setHpEnemy(0);
            currentDisplay.displayBattleReport("\t!!!!!!! KO !!!!!!!");
            tryToKillEnemy();
        } else {
            hero.setHp(hero.getHp() - (enemy.getAttackEnemy() * 2) + hero.getDefense());

            if (tryToKillEnemy()) return;

            if (heroLife()) return;

            enemy.setHpEnemy(enemy.getHpEnemy() - hero.getAttack() + enemy.getDefenseEnemy());
            int raisedDamage = (enemy.getAttackEnemy() * 2) - hero.getDefense();

            currentDisplay.displayBattleReport("You received " + (hero.getAttack() - enemy.getDefenseEnemy())
                    + " damage points!\n" + (raisedDamage < 0 ? " You defended all " + enemy.getNameEnemy() + " attacks" : " You received " + raisedDamage) + " damage points.");
        }
    }

    private void movePlayer(int input) {
        newFormOfMap();
        switch (input) {
            case 1:
                if (hero.getCoordinates().getY() - 1 >= 0)
                    hero.move(0, -1);
                else heroWon = true;
                break;
            case 2:
                if (hero.getCoordinates().getX() - 1 >= 0)
                    hero.move(-1, 0);
                else heroWon = true;
                break;
            case 3:
                if (hero.getCoordinates().getX() + 1 < map.getSize())
                    hero.move(1, 0);
                else heroWon = true;
                break;
            case 4:
                if (hero.getCoordinates().getY() + 1 < map.getSize())
                    hero.move(0, 1);
                else heroWon = true;
                break;
        }
    }

    private void levelUp(EnemyProduct enemy) {
        if (hero.getXp() >= hero.getXPNeeded()) {
            currentDisplay.displayBattleReport("\tLevel up ! Skills increased !");
            hero.setHp(hero.getHp() + (4 * hero.getLevel()));
            hero.setAttack(hero.getAttack() + (hero.getLevel() * 2));
            hero.setDefense(hero.getDefense() + (hero.getLevel()));
            hero.setLevel(hero.getLevel() + 1);
            createMap(hero.getLevel());
            newFormOfMap();
            createEnemies();
        }
        enemyDroppedSomeUsefulStuff(enemy);
    }

    private void enemyDroppedSomeUsefulStuff(EnemyProduct enemy) {
        if (randClass.nextInt(3) == 2) {
            if (randClass.nextInt(2) == 0) {
                int up = randClass.nextInt(30) + 5;
                if (currentDisplay.displayQuitDialogue("Found Helm pick it up ?")) {
                    hero.setHp(hero.getHp() + up);
                    currentDisplay.displayBattleReport("\tYou picked up a helm which adds + " + up + " hp !");
                } else currentDisplay.displayBattleReport("\tYou Left a helm which adds + " + up + " hp !");
            } else
                pickUpArtifacts(enemy);
        }
    }

    private void pickUpArtifacts(EnemyProduct enemy) {
        String name = randClass.nextInt(2) == 0 ? "Weapon" : "Armor";
        int value = ((name.equals("Weapon") ? enemy.getAttackEnemy() + 33 : enemy.getDefenseEnemy())) + 27;

        if (currentDisplay.displayQuitDialogue("Found " + name + " artifact (" + value + ") pick it up ?")) {
            if (name.equals("Weapon")) hero.setAttack(value);
            else hero.setDefense(value);

            artifactsBuilder.construct(name, value);
            artifactsInput = artifactsBuilder.getArtifactsBuilder();
            hero.getArtifacts().add(artifactsInput);
            currentDisplay.displayBattleReport("\tPicked " + name + " !!!");
        }
    }

    private boolean heroLife() throws Exception {
        if (hero.getHp() <= 0) {
            if (currentDisplay.displayQuitDialogue("Died have " + countLife + " life left, Start on new map ?")) {
                hero.setCoordinates(new Coordinates(map.getSize() / 2, map.getSize() / 2));
                initGame();
                newFormOfMap();
                if (countLife > 0) {
                    countLife--;
                    hero.setHp(randClass.nextInt(1));
                    saveHero();
                } else {
                    saveHero();
                    currentDisplay.displayHeroWonTheGame();
                }
                return true;
            } else {
                saveHero();
                currentDisplay.displayHeroWonTheGame2();
            }
            return true;
        }
        return false;
    }

    private boolean forcedFight() {
        int roll = randClass.nextInt(2);
        return (roll == 1);
    }

    public void displayGame() {
        currentDisplay.clearScreen();
        newFormOfMap();
        currentDisplay.displayMap(map);
    }

    public void playGame() {
        newFormOfMap();
        while (gameOn) {
            displayGame();
        }
    }

    private void checkPlayerWon() {
        if (hero.getLevel() > 7)
            heroWon = true;
    }
}
