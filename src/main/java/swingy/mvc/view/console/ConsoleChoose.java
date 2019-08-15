package swingy.mvc.view.console;

import swingy.mvc.model.HeroBuilder.Director;
import swingy.utils.Colors;
import swingy.utils.FileMethods;

import java.util.List;

public class ConsoleChoose {

    private Director director;
    private List<String> names;

    public ConsoleChoose() {
        this.director = new Director();
    }

    public void getHero() {
        System.out.println(Colors.ANSI_GREEN + "Welcome to the game" + Colors.ANSI_RESET);
        System.out.println(Colors.ANSI_WHITE + "1) Create a new hero.\n2) Choose from existing hero.\n0) Exit" + Colors.ANSI_RESET);
        int selected;

        selected = FileMethods.rightInt();

        while (selected < 0 || selected > 2) {
            System.out.println(Colors.ANSI_RED + "Enter correct number from 0-2" + Colors.ANSI_RESET);
            selected = FileMethods.rightInt();
        }

        switch (selected) {
            case 1:
                heroCreator();
                break;
            case 2:
                existingHero();
                break;
            case 0:
                System.exit(0);
        }

    }

    public void heroCreator() {

        int value;

        while (true) {
            System.out.println("Select a Type of Hero\n" + Colors.ANSI_GREEN + "1) Warrior\n2) Hunter\n3) Thief\n0) Cancel" + Colors.ANSI_RESET);

            if ((value = FileMethods.rightInt()) == 0)
                break;
            if ((value > 0 && value < 4)) {
                switch (value) {
                    case 1:
                        this.createHero("warrior");
                        break;
                    case 2:
                        this.createHero("hunter");
                        break;
                    case 3:
                        this.createHero("thief");
                        break;
                }
            }
        }
    }

    public void createHero(String type) {

        String nameOfTheHero;

        System.out.println(Colors.ANSI_WHITE + "Enter your name: " + Colors.ANSI_RESET);
        nameOfTheHero = FileMethods.acceptingInput();
        director.construct(nameOfTheHero, type);

    }

    public void existingHero() {
    }
}
