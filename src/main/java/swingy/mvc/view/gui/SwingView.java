package swingy.mvc.view.gui;

import swingy.db.DataBase;
import swingy.mvc.controller.Controller;
import swingy.mvc.model.heroBuilder.DirectorHero;
import swingy.mvc.model.heroBuilder.HeroBuilder;
import swingy.mvc.model.heroBuilder.HeroProduct;
import swingy.mvc.model.mapBuilder.Coordinates;
import swingy.mvc.model.mapBuilder.MapGame;
import swingy.mvc.view.IView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SwingView extends JFrame implements IView {

    static int selected;
    static HeroBuilder hero;
    static DataBase db;
    static List<String> names;
    static HeroProduct heroP;
    static List<HeroProduct> heroes;
    Controller controller;
    DirectorHero directorHero;
    private DisplayMap displayMap;

    public SwingView(Controller controller) {
        this.controller = controller;
        db = DataBase.getDb();
        directorHero = new DirectorHero();
        hero = null;
        heroP = null;
        displayMap = new DisplayMap(this);

    }

    @Override
    public void displayStartViewAndGetHero() throws Exception {

        names = db.getNamesFromDB();
        DisplayStartViewAndGetHero displayStartViewAndGetHero = new DisplayStartViewAndGetHero(this);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        displayStartViewAndGetHero.setLocation(dim.width / 2 - displayStartViewAndGetHero.getSize().width / 2, dim.height / 2 - displayStartViewAndGetHero.getSize().height / 2);
        displayStartViewAndGetHero.setVisible(true);
        displayStartViewAndGetHero.setListeners();
    }

    @Override
    public void displayCreatePlayerView() {

        DisplayCreatePlayerView displayCreatePlayerView = new DisplayCreatePlayerView(this);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        displayCreatePlayerView.setLocation(dim.width / 2 - displayCreatePlayerView.getSize().width / 2, dim.height / 2 - displayCreatePlayerView.getSize().height / 2);
        displayCreatePlayerView.setVisible(true);
        displayCreatePlayerView.setListeners();
    }

    @Override
    public void displayHeroNamePrompt(String type) {

        DisplayHeroNamePrompt displayHeroNamePrompt = new DisplayHeroNamePrompt(this);
        directorHero.construct(type);
        hero = directorHero.getHeroBuilder();

        displayHeroNamePrompt.jTextArea1.setText(hero.infoHero());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        displayHeroNamePrompt.setLocation(dim.width / 2 - displayHeroNamePrompt.getSize().width / 2, dim.height / 2 - displayHeroNamePrompt.getSize().height / 2);
        displayHeroNamePrompt.setVisible(true);
        displayHeroNamePrompt.setListeners();
    }

    @Override
    public void displayExistingHero() throws Exception {

        if (names.size() == 0) {
            JOptionPane.showMessageDialog(this, "There is no saved Heroes. Create Him", "Notice to you", JOptionPane.ERROR_MESSAGE);
            displayCreatePlayerView();
        } else {
            heroes = swingHeroesFromDatabase();
            DisplayExistingHero displayExistingHero = new DisplayExistingHero(this);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            displayExistingHero.setLocation(dim.width / 2 - displayExistingHero.getSize().width / 2, dim.height / 2 - displayExistingHero.getSize().height / 2);
            displayExistingHero.setVisible(true);
            displayExistingHero.setListeners((ArrayList<HeroProduct>) swingHeroesFromDatabase());
        }
    }

    @Override
    public void displayMap(MapGame map) {
        displayMap.displayPlayView();

    }

    @Override
    public boolean displayQuitDialogue(String message) {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, message, "Choose below", dialogButton);

        return dialogResult == 0;
    }

    @Override
    public void displayBattleReport(String report) {
        displayMap.reportText.setText(report);
    }

    @Override
    public void displayOptions() {

    }

    @Override
    public void clearScreen() {
        //setVisible(false);
        //dispose();
    }

    @Override
    public void displayForcedFightMsg() {
        String msg = "Busted!!! Fight";
        JOptionPane.showMessageDialog(this, msg);
    }

    @Override
    public void displayGameOver() throws Exception {
        String status = controller.heroWon ? "WON!" : "DIED!";
        Object[] options1 = {"Start New Game ?", "Quit"};
        int dialogResult = JOptionPane.showOptionDialog(this, "You " + status, "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);
        if (dialogResult == JOptionPane.YES_OPTION) {
            controller.saveHero();
            if (controller.countLife == 0) {
                controller.countLife = 1;
            }
            controller.heroWon = false;
            controller.hero.setHp(controller.randClass.nextInt(300));
            controller.hero.setCoordinates(new Coordinates(controller.map.getSize() / 2, controller.map.getSize() / 2));
            controller.initGame();
            controller.displayGame();
        } else {
            controller.saveHero();
            displayHeroWonTheGame2();
        }
    }

    @Override
    public void displayHeroWonTheGame() {
        JOptionPane.showMessageDialog(this, "Sorry You have No Life ! Goodbye !!!", "Notice to you", JOptionPane.INFORMATION_MESSAGE);
        System.exit(1);
    }

    @Override
    public void displayHeroWonTheGame2() {
        JOptionPane.showMessageDialog(this, "Goodbye !!!", "Notice to you", JOptionPane.INFORMATION_MESSAGE);
        System.exit(1);
    }

    void swingEnterYourName() {
        DisplayEnterYourName displayEnterYourName = new DisplayEnterYourName(this);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        displayEnterYourName.setLocation(dim.width / 2 - displayEnterYourName.getSize().width / 2, dim.height / 2 - displayEnterYourName.getSize().height / 2);
        displayEnterYourName.setVisible(true);
        displayEnterYourName.setListeners();
    }

    private List<HeroProduct> swingHeroesFromDatabase() throws Exception {
        ArrayList<HeroProduct> details = new ArrayList<>();
        for (String name : names) {
            details.add(db.getHeroFromDB(name));
        }
        return details;
    }

    void quitDialogue() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Save current Game?", "Exiting Game", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                db.updateHero(controller.hero);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.setVisible(false);
            this.dispose();
        } else {
            this.setVisible(false);
            this.dispose();
        }
    }

}

