package swingy.mvc.model.HeroBuilder;

import swingy.utils.FileMethods;

public class Director {

    private HeroBuilder heroBuilder;
    private String info;

    public void construct(String name, String type) {
        if (type.equals("warrior")) {
            this.heroBuilder = new Warrior();
            constructWithUserName(name);
            info = heroBuilder.infoHero();
            FileMethods.addHeroInfo(info);
        } else if (type.equals("hunter")) {
            this.heroBuilder = new Hunter();
            constructWithUserName(name);
            info = heroBuilder.infoHero();
            FileMethods.addHeroInfo(info);
        } else if (type.equals("thief")) {
            this.heroBuilder = new Thief();
            constructWithUserName(name);
            info = heroBuilder.infoHero();
            FileMethods.addHeroInfo(info);
        } else {
            System.exit(1);
        }
    }

    public void constructWithUserName(String user) {
        this.heroBuilder.setHeroStatsWithDefaultValues();
        this.heroBuilder.setNameOfHero(user);
    }

}
