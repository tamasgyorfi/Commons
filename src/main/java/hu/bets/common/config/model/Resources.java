package hu.bets.common.config.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Resources {

    private List<Object> resources = new LinkedList<>();

    public Resources addResource(Object resource) {
        resources.add(resource);
        return this;
    }

    public List<Object> getResources() {
        return Collections.unmodifiableList(resources);
    }
}
