package swingy.mvc.model.artifactsBuilder;

import lombok.Getter;


public class Artifacts {

    @Getter
    private String name;
    @Getter
    private int value;

    Artifacts(String name, int value) {
        this.name = name;
        this.value = value;
    }
}
