package swingy.mvc.model.HeroBuilder;

/* "ConcreteBuilder" */
public class Warrior implements HeroBuilder {

    private Hero heroWarrior = createsHero();

    @Override
    public Hero createsHero() {
        return new Hero();
    }

    @Override
    public Hero getHero() {
        return this.heroWarrior;
    }

    @Override
    public String infoHero() {
        Hero s = heroWarrior;
        return (s.getName() + "," + s.getType() + "," + s.getLevel() + "," + s.getXp() + "" + s.getAttack() + "," + s.getDefense() + "," +
                s.getHp());
    }

    @Override
    public void setHeroStatsWithDefaultValues() {
        heroWarrior.setType("warrior");
        heroWarrior.setLevel(1);
        heroWarrior.setXp(0);
        heroWarrior.setAttack(8);
        heroWarrior.setDefense(13);
        heroWarrior.setHp(140);
    }

    @Override
    public void setNameOfHero(String string) {
        heroWarrior.setName(string);
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
