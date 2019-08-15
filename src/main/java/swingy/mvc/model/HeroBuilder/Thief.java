package swingy.mvc.model.HeroBuilder;

/* "ConcreteBuilder" */
public class Thief implements HeroBuilder {
    private Hero heroThief = createsHero();

    @Override
    public String infoHero() {
        Hero s = heroThief;
        return (s.getName() + "," + s.getType() + "," + s.getLevel() + "," + s.getXp() + "" + s.getAttack() + "," + s.getDefense() + "," +
                s.getHp());
    }

    @Override
    public Hero createsHero() {
        return new Hero();
    }

    @Override
    public Hero getHero() {
        return this.heroThief;
    }

    @Override
    public void setHeroStatsWithDefaultValues() {
        heroThief.setType("thief");
        heroThief.setLevel(1);
        heroThief.setXp(0);
        heroThief.setAttack(16);
        heroThief.setDefense(2);
        heroThief.setHp(60);
    }

    @Override
    public void setNameOfHero(String string) {
        heroThief.setName(string);
    }

    @Override
    public void artifactsWeapon() {

    }

    @Override
    public void artifactsArmor() {

    }

    @Override
    public void artifactsHelm() {

    }
}
