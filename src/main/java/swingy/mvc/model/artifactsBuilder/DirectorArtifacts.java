package swingy.mvc.model.artifactsBuilder;

import lombok.Getter;

public class DirectorArtifacts {

    @Getter
    public Artifacts artifactsBuilder;

    public void construct(String name, int value) {
        artifactsBuilder = new Artifacts(name, value);
    }
}