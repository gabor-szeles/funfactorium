package org.funfactorium.funfacts.topics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> allTopics() {
        return topicRepository.findAll();
    }

    public Map<Long, String> getTopicMap() {
        Map<Long, String> result = new HashMap<>();
        List<Topic> allTopics = topicRepository.findAll();
        for (Topic topic:allTopics) {
            result.put(topic.getId(), topic.getName());
        }
        return result;
    }

    public Topic getByTopicId(long topicId) {
        return topicRepository.findOne(topicId);
    }
}
