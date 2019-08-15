package swingy.mvc.controller;

import swingy.mvc.view.console.ConsoleChoose;
import swingy.utils.Colors;

public class Controller {

    public void startGame(String arg) {

        if (arg.matches("console")) {
            ConsoleChoose obj = new ConsoleChoose();
            obj.getHero();
        } else if (arg.matches("gui")) {
            System.out.println("Displaying GUI");
            System.out.println("Under Construction!");
        } else System.out.println(Colors.ANSI_RED + "Check your spelling" + Colors.ANSI_RESET);
    }
}
