package swingy.mvc.view.console;

import swingy.db.DataBase;
import swingy.mvc.controller.Controller;
import swingy.mvc.model.heroBuilder.DirectorHero;
import swingy.mvc.model.heroBuilder.HeroBuilder;
import swingy.mvc.model.heroBuilder.HeroProduct;
import swingy.mvc.model.mapBuilder.Coordinates;
import swingy.mvc.model.mapBuilder.MapGame;
import swingy.mvc.view.IView;
import swingy.utils.FileMethods;

import java.util.List;

public class ConsoleView implements IView {

    private static DataBase db;
    private DirectorHero directorHero;
    private HeroBuilder hero;
    private HeroProduct heroP;
    private List<String> names;
    private Controller controller;


    public ConsoleView(Controller controller) {
        this.controller = controller;
        db = DataBase.getDb();
        directorHero = new DirectorHero();
        hero = null;
        heroP = null;
    }


    @Override
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void displayStartViewAndGetHero() throws Exception {
        names = db.getNamesFromDB();

        while (heroP == null) {
            clearScreen();

            System.out.println("Welcome to the game\n1) Create hero.\n2) Saved heroes.\n0) Exit");
            int selected;

            selected = FileMethods.rightInt(0, 2);

            switch (selected) {
                case 1:
                    displayCreatePlayerView();
                    break;
                case 2:
                    displayExistingHero();
                    break;
                case 0:
                    System.exit(0);
            }
        }
    }

    @Override
    public void displayCreatePlayerView() {

        int value;

        while (true) {
            clearScreen();
            System.out.println("Select a Type of Hero\n1) Warrior\n2) Hunter\n3) Thief\n0) GoBack To Main");

            value = FileMethods.rightInt(0, 3);
            if (value == 0)
                break;
            switch (value) {
                case 1:
                    displayHeroNamePrompt("warrior");
                    break;
                case 2:
                    displayHeroNamePrompt("hunter");
                    break;
                case 3:
                    displayHeroNamePrompt("thief");
                    break;
            }
        }
    }

    @Override
    public void displayHeroNamePrompt(String type) {

        String nameOfTheHero;
        String error = "";

        directorHero.construct(type);
        hero = directorHero.getHeroBuilder();
        if (displayQuitDialogue(hero.infoHero() + "\n\nCreate ?")) {
            while (error != null) {
                System.out.println("Enter your name: ");
                nameOfTheHero = FileMethods.acceptingInput();

                while (nameOfTheHero.equals(""))
                    nameOfTheHero = FileMethods.acceptingInput();

                error = directorHero.SetTheNameOfUser(hero, nameOfTheHero);

                try {
                    if (DataBase.getDb().heroExists(nameOfTheHero)) {
                        error = "Already created";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (error != null)
                    System.err.println(error);

                nameOfTheHero = "";
            }
            try {
                db.insertHero(hero);
                names.add(hero.getNameI());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void displayExistingHero() throws Exception {

        int index;
        int item;

        while (true) {
            clearScreen();
            index = 0;

            if (names.size() == 0) {
                System.out.println("There is no saved Heroes. Create Him");
                return;
            }

            System.out.println("\nSelect?\n0) cancel");
            for (String name : names)
                System.out.println(++index + ") " + name);

            if ((item = FileMethods.rightInt(0, index)) == 0) {
                break;
            } else if (item <= index) {
                System.out.println(db.getHeroFromDB(names.get(item - 1)).getInfo());
                System.out.println("\n1) Select   2) Delete   3) Cancel");
                int input = FileMethods.rightInt(1, 3);

                if (input == 1) {
                    heroP = db.getHeroFromDB(names.get(item - 1));
                    controller.hero = heroP;
                } else if (input == 2) {
                    db.deleteHero(names.get(item - 1));
                    names.remove(item - 1);
                }
                if (input == 1 || input == 3) {
                    break;
                }
            }
        }

    }

    @Override
    public void displayMap(MapGame map) {

        System.out.println(controller.hero.getInfo());
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                if (map.getGrid()[i][j] == 1)
                    System.out.print(" H  ");
                else if (map.getGrid()[i][j] == 2)
                    System.out.print(" E  ");
                else
                    System.out.print(" .  ");
            }
            System.out.println();
        }
        displayOptions();
        FileMethods.rightInt(0, 4);
        try {
            if (FileMethods.inpuInt == 0) {
                if (displayQuitDialogue("Save current Game?")) {
                    db.updateHero(controller.hero);
                    System.exit(0);
                } else System.exit(0);
            }
            controller.receiveInputFromUser(FileMethods.inpuInt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public boolean displayQuitDialogue(String string) {
        System.out.println(string + "\n1) Yes\n2) No");
        FileMethods.rightInt(1, 2);
        return FileMethods.inpuInt == 1;

    }

    @Override
    public void displayBattleReport(String report) {
        System.out.println(report);
    }

    @Override
    public void displayOptions() {
        System.out.println("\n0) Exit\n\n     1) North\n2) West     3) East\n     4) South\n");
    }

    @Override
    public void displayForcedFightMsg() {
        String msg = "Busted!!! Fight";
        System.out.println(msg);
    }

    @Override
    public void displayGameOver() throws Exception {
        String status = controller.heroWon ? "WON!" : "DIED!";
        if (displayQuitDialogue("You  " + status + " , start a new Game ?")) {
            controller.saveHero();
            if (controller.countLife == 0) {
                controller.countLife = 1;
            }
            controller.heroWon = false;
            controller.hero.setCoordinates(new Coordinates(controller.map.getSize() / 2, controller.map.getSize() / 2));
            controller.initGame();
            controller.playGame();
        } else {
            controller.saveHero();
            displayHeroWonTheGame2();
        }
    }

    @Override
    public void displayHeroWonTheGame() {
        System.out.println("\n\nSorry You have No Life ! Goodbye !!!\n");
        System.exit(1);

    }

    @Override
    public void displayHeroWonTheGame2() {
        System.out.println("\n\nGoodbye !!!\n");
        System.exit(1);

    }

}
