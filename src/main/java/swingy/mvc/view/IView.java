package swingy.mvc.view;

import swingy.mvc.model.mapBuilder.MapGame;

public interface IView {

    void displayStartViewAndGetHero() throws Exception;

    void displayCreatePlayerView();

    void displayHeroNamePrompt(String type);

    void displayExistingHero() throws Exception;

    void displayMap(MapGame map);

    boolean displayQuitDialogue(String string);

    void displayBattleReport(String report);

    void displayOptions();

    void clearScreen();

    void displayForcedFightMsg();

    void displayGameOver() throws Exception;

    void displayHeroWonTheGame();

    void displayHeroWonTheGame2();
}
