package swingy.mvc.model.heroBuilder;

/* "ConcreteBuilder" */
public class ThiefConcrete extends HeroProduct implements HeroBuilder {

    @Override
    public String infoHero() {
        return getInfo();
    }

    @Override
    public String getNameI() {
        return getName();
    }

    @Override
    public void setNameI(String name) {
        setName(name);
    }

    @Override
    public String getTypeI() {
        return getType();
    }

    @Override
    public int getLevelI() {
        return getLevel();
    }

    @Override
    public int getXpI() {
        return getXp();
    }

    @Override
    public int getAttackI() {
        return getAttack();
    }

    @Override
    public int getDefenseI() {
        return getDefense();
    }

    @Override
    public int getHpI() {
        return getHp();
    }

    @Override
    public void setHeroStatsWithDefaultValues() {
        setType("thief");
        setLevel(1);
        setXp(0);
        setAttack(16);
        setDefense(2);
        setHp(260);
    }
}
