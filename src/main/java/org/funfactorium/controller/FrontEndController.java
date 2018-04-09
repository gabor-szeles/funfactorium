package org.funfactorium.controller;

import org.funfactorium.funfacts.FunFact;
import org.funfactorium.funfacts.FunFactService;
import org.funfactorium.funfacts.topics.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class FrontEndController {

    private final FunFactService funFactService;

    private final TopicService topicService;

    @Autowired
    public FrontEndController(FunFactService funFactService, TopicService topicService) {
        this.funFactService = funFactService;
        this.topicService = topicService;
    }

    @GetMapping(path = "/")
    public String renderIndex(Principal principal, Model model) {
        String userName = null;
        if (principal!=null) {
            userName = principal.getName();
        }
        List<FunFact> allFactList = funFactService.allFunFacts();
        model.addAttribute("funfacts", allFactList);
        model.addAttribute("username", userName);
        model.addAttribute("topics", topicService.allTopics());
        return "index";
    }

    @GetMapping(path = "/api-docs")
    public String renderApiDocumentation(Principal principal, Model model) {
        String userName = null;
        if (principal!=null) {
            userName = principal.getName();
        }
        model.addAttribute("username", userName);
        return "api_documentation";
    }

    //TODO
    @PostMapping(path = "/register")
    public String register() {
        return "redirect:/";
    }


}
