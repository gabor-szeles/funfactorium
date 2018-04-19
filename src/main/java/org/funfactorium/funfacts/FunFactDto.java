package org.funfactorium.funfacts;

import java.util.List;

public class FunFactDto {

    private String title;

    private String description;

    private List<Long> topics;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Long> getTopics() {
        return topics;
    }
}
