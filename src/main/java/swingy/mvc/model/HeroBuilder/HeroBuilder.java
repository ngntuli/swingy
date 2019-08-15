package swingy.mvc.model.HeroBuilder;

/* "Interface Builder" */
public interface HeroBuilder {

    Hero createsHero();

    Hero getHero();

    String infoHero();

    void setHeroStatsWithDefaultValues();

    void setNameOfHero(String string);

    void artifactsWeapon();

    void artifactsArmor();

    void artifactsHelm();
}
