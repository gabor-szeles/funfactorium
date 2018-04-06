package org.funfactorium.funfacts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.funfactorium.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FunFactService {

    private final FunFactRepository funFactRepository;

    @Autowired
    public FunFactService(FunFactRepository funFactRepository) {
        this.funFactRepository = funFactRepository;
    }

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

    public Map<String, Object> getRandomFact() {
        long minId = 1L;
        long maxId = funFactRepository.findMaxId();
        long randomId = minId + (long) (Math.random() * (maxId - minId));
        FunFact randomFact = funFactRepository.findById(randomId);
        return Utils.buildJsonFromObject(randomFact);
    }
}
