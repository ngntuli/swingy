package swingy.mvc.model.HeroBuilder;

/* "ConcreteBuilder" */
public class Hunter implements HeroBuilder {

    private Hero heroHunter = createsHero();

    @Override
    public Hero createsHero() {
        return new Hero();
    }

    @Override
    public Hero getHero() {
        return this.heroHunter;
    }

    @Override
    public String infoHero() {
        Hero s = heroHunter;
        return (s.getName() + "," + s.getType() + "," + s.getLevel() + "," + s.getXp() + "," + s.getAttack() + "," + s.getDefense() + "," +
                s.getHp());
    }

    @Override
    public void setHeroStatsWithDefaultValues() {
        heroHunter.setType("hunter");
        heroHunter.setLevel(1);
        heroHunter.setXp(0);
        heroHunter.setAttack(11);
        heroHunter.setDefense(3);
        heroHunter.setHp(100);
    }

    @Override
    public void setNameOfHero(String string) {
        heroHunter.setName(string);
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
