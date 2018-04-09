package org.funfactorium.controller.api;

import org.funfactorium.Utils;
import org.funfactorium.funfacts.FunFactNotFoundException;
import org.funfactorium.funfacts.FunFactService;
import org.funfactorium.funfacts.topics.TopicNotFoundException;
import org.funfactorium.funfacts.topics.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class APIController {

    private final FunFactService funFactService;
    private final TopicService topicService;

    @Autowired
    public APIController(FunFactService funFactService, TopicService topicService) {
        this.funFactService = funFactService;
        this.topicService = topicService;
    }

    @PostMapping(value = "/api/filter", consumes = "application/json")
    public ResponseEntity renderFilteredIndex(@RequestBody String id) {
        long topicId = Long.parseLong(id.split("=")[1]);
        List<Map<String, String>> params = funFactService.searchByTopic(topicId);
        return ResponseEntity.ok(params);
    }

    @GetMapping(value = "/api/funfact")
    public ResponseEntity getRandomFunFact() {
        try {
            long randomId = funFactService.getRandomFunFactId();
            return ResponseEntity.ok(funFactService.getFunFact(randomId));
        } catch (NullPointerException e) {
            return new ResponseEntity(Utils.buildApiErrorMessage(e), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/api/funfact/{factId}")
    public ResponseEntity getFunFact(@PathVariable("factId") long id) {
        try {
            return ResponseEntity.ok(funFactService.getFunFact(id));
        } catch (FunFactNotFoundException e) {
            return new ResponseEntity(Utils.buildApiErrorMessage(e), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/api/funfact/topic")
    public ResponseEntity displayAllTopics() {
        return ResponseEntity.ok(topicService.getTopicMap());
    }

    @GetMapping(value = "/api/funfact/topic/{topicName}")
    public ResponseEntity getFunFactByTopic(@PathVariable("topicName") String topicName) {
        String properTopicName = StringUtils.capitalize(topicName.toLowerCase());
        try {
            return ResponseEntity.ok(funFactService.getFunFactByTopicName(properTopicName));
        } catch (TopicNotFoundException e) {
            return new ResponseEntity(Utils.buildApiErrorMessage(e), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/api/funfact/topic/id/{topicId}")
    public ResponseEntity getFunFactByTopicId(@PathVariable("topicId") long id) {
        try {
            return ResponseEntity.ok(funFactService.getFunFactByTopicId(id));
        } catch (TopicNotFoundException e) {
            return new ResponseEntity(Utils.buildApiErrorMessage(e), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/api/authenticate")
    public ResponseEntity authenticateUser() {
        System.out.println("Im running");
        return ResponseEntity.ok("ok");
    }
}
