package org.funfactorium;

import org.funfactorium.funfacts.FunFact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
