package swingy.mvc.model.heroBuilder;

import lombok.Getter;
import lombok.Setter;
import swingy.mvc.model.artifactsBuilder.Artifacts;
import swingy.mvc.model.mapBuilder.Coordinates;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;


/* "Product" */
public class HeroProduct {
    @Getter
    @Setter
    public ArrayList<Artifacts> artifacts;
    @Getter
    @Setter
    @Pattern(regexp = "^[0-9A-Za-z]+", message = "Enter only letters and digits")
    @Size(min = 3, max = 12, message = "Length of name must be 3-12 symbols")
    private String name;
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private int level;
    @Getter
    @Setter
    private int xp;
    @Getter
    @Setter
    private int attack;
    @Getter
    @Setter
    private int defense;
    @Getter
    private int hp;
    @Getter
    @Setter
    private Coordinates prevCoordinates;
    @Getter
    @Setter
    private Coordinates coordinates;

    public HeroProduct() {
        prevCoordinates = new Coordinates(0, 0);
        coordinates = new Coordinates(0, 0);
        artifacts = new ArrayList<>();
    }

    public String getInfo() {
        return ("[" + type + " ] [Level: " + level + " ] [Exp: " + xp
                + " ] [Attack: " + attack + " ] [Defense: " + defense + " ] [Hit points: " + hp + " ]");
    }

    public void move(int x, int y) {
        prevCoordinates = new Coordinates(coordinates.getX(), coordinates.getY());
        coordinates = new Coordinates(coordinates.getX() + x, coordinates.getY() + y);
    }

    public int getXPNeeded() {
        double xP;
        xP = level * 1000 + Math.pow(((double) level) - 1, 2) * 450;
        return (int) xP;
    }

    public void setHp(int hitP) {
        hp = Math.max(hitP, 0);
    }
}
