package org.funfactorium.funfacts;

import org.funfactorium.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FunFactService {

    @Autowired
    private FunFactRepository funFactRepository;

    public List<FunFact> allFunFacts() {
        return funFactRepository.findAll();
    }

    public List<Map<String,String>> searchByTopic(long id) {
        List<Map<String, String>> result;
        if(id==0) {
            result = Utils.buildFilteredPage(funFactRepository.findAll());
        } else {
            result = Utils.buildFilteredPage(funFactRepository.findByTopicSetId(id));
        }
        return result;
    }
}
