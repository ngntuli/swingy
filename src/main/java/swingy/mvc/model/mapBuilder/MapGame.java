package swingy.mvc.model.mapBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapGame {
    private int size;
    private int[][] grid;

    MapGame(int size) {
        this.setSize(size);
        this.setGrid(new int[size][size]);
    }
}
