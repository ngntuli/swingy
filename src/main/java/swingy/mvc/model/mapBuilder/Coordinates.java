package swingy.mvc.model.mapBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coordinates {

    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.setX(x);
        this.setY(y);
    }
}
