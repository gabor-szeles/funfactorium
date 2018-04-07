package org.funfactorium;

import org.funfactorium.controller.api.FunFactNotFoundException;
import org.funfactorium.funfacts.FunFact;
import org.funfactorium.funfacts.topics.Topic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    public static List<Map<String, String>> buildFilteredPage(List<FunFact> funFacts) {
        List<Map<String, String>> model = new ArrayList<>();
        for (FunFact funFact : funFacts) {
            Map<String, String> currentFunFacts = new HashMap<>();
            currentFunFacts.put("title", funFact.getTitle());
            currentFunFacts.put("author", funFact.getAuthor().getUserName());
            currentFunFacts.put("description", funFact.getDescription());
            model.add(currentFunFacts);
        }
        return model;
    }

    public static Map<String, Object> buildJsonFromObject(FunFact randomFact) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", randomFact.getId());
        result.put("title", randomFact.getTitle());
        result.put("author", randomFact.getAuthor().getUserName());
        result.put("topics", randomFact.getTopic().stream().map(Topic::getName).collect(Collectors.toList()));
        result.put("description", randomFact.getDescription());
        result.put("rating", randomFact.getRating());
        result.put("status", "FOUND");
        return result;
    }

    public static Map<String, String> buildApiErrorMessage(RuntimeException e) {
        Map<String, String> result = new HashMap<>();
        if(e instanceof FunFactNotFoundException) {
            result.put("status", "NOT FOUND");
            result.put("description", "No entry with this id was found in the database!");
        }
        return result;
    }
}
