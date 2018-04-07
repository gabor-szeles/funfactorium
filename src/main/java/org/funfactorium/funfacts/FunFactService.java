package org.funfactorium.funfacts;

import org.funfactorium.Utils;
import org.funfactorium.funfacts.topics.TopicNotFoundException;
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

    public long getRandomFunFactId() throws NullPointerException{
        long minId = 1L;
        long maxId = funFactRepository.findMaxId();
        return minId + (long) (Math.random() * (maxId - minId));
    }

    public Map<String, Object> getFunFact(long id) throws FunFactNotFoundException {
        FunFact targetFact = funFactRepository.findById(id);
        if(targetFact==null) {
            throw new FunFactNotFoundException();
        }
        return Utils.buildSingleFactJson(targetFact);
    }

    public List<Map<String, Object>> getFunFactByTopicName(String properTopicName) throws TopicNotFoundException {
        List<FunFact> allFactsForTopic = funFactRepository.findAllByTopicSet_name(properTopicName);
        if(allFactsForTopic.size()==0) {
            throw new TopicNotFoundException();
        }
        return  Utils.buildFactListJson(allFactsForTopic);
    }

    public List<Map<String, Object>> getFunFactByTopicId(long id) throws TopicNotFoundException {
        List<FunFact> allFactsForTopic = funFactRepository.findAllByTopicSet_id(id);
        if(allFactsForTopic.size()==0) {
            throw new TopicNotFoundException();
        }
        return  Utils.buildFactListJson(allFactsForTopic);
    }
}
