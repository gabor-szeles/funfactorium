package org.funfactorium.funfacts;

import org.funfactorium.Utils;
import org.funfactorium.funfacts.topics.Topic;
import org.funfactorium.funfacts.topics.TopicNotFoundException;
import org.funfactorium.funfacts.topics.TopicService;
import org.funfactorium.user.User;
import org.funfactorium.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FunFactService {

    private final FunFactRepository funFactRepository;
    private final TopicService topicService;
    private final UserService userService;

    @Autowired
    public FunFactService(FunFactRepository funFactRepository, TopicService topicService, UserService userService) {
        this.funFactRepository = funFactRepository;
        this.topicService = topicService;
        this.userService = userService;
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
        List<FunFact> allFactsForTopic = funFactRepository.findByTopicSetId(id);
        if(allFactsForTopic.size()==0) {
            throw new TopicNotFoundException();
        }
        return  Utils.buildFactListJson(allFactsForTopic);
    }

    public boolean titleExists(String title) {
        return funFactRepository.existsByTitle(title);
    }

    public void addFunFact(FunFactDto funFactDto, String userName) {
        User author = userService.findByUserName(userName);
        FunFact newFact = new FunFact(funFactDto.getTitle(), funFactDto.getDescription(), author, 3);
        for (long topicId:funFactDto.getTopics()) {
            Topic topic = topicService.getByTopicId(topicId);
            newFact.getTopic().add(topic);
            topic.getFunFacts().add(newFact);
            newFact.getTopic().add(topic);
            topic.getFunFacts().add(newFact);
        }
        funFactRepository.save(newFact);
    }
}
