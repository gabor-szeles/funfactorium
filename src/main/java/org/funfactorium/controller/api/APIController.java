package org.funfactorium.controller.api;

import org.funfactorium.funfacts.FunFactService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class APIController {

    @Autowired
    FunFactService funFactService;

    @PostMapping(value = "/api/filter", consumes = "application/json")
    public ResponseEntity renderFilteredIndex(@RequestBody String id) {
        long topicId = Long.parseLong(id.split("=")[1]);
        List<Map<String, String>> params = funFactService.searchByTopic(topicId);
        return ResponseEntity.ok(params);
    }
}
