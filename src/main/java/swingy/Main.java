package swingy;

import swingy.mvc.controller.Controller;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                System.out.println("Usage: java -jar swingy.jar [gui/console]");
                System.exit(1);
            }
            Controller c = new Controller();
            c.startGame(args[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
