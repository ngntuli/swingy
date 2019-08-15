package swingy;

import swingy.mvc.controller.Controller;
import swingy.utils.Colors;
import swingy.utils.FileMethods;

public class Main {
    public static void main(String[] args) {
        try {
            FileMethods.createFile();

            if (args.length != 1) {
                System.out.println(Colors.ANSI_RED + "Usage: java -jar swingy.jar [gui/console]" + Colors.ANSI_RESET);
                System.exit(1);
            }

            Controller controller = new Controller();
            controller.startGame(args[0]);
            FileMethods.writeToFile();
            FileMethods.showHeroesInfo();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileMethods.closeFile();
        }
    }
}
