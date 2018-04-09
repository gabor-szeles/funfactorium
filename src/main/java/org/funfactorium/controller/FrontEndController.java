package org.funfactorium.controller;

import org.funfactorium.funfacts.FunFact;
import org.funfactorium.funfacts.FunFactService;
import org.funfactorium.funfacts.topics.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String renderIndex(Model model) {
        List<FunFact> allFactList = funFactService.allFunFacts();
        model.addAttribute("funfacts", allFactList);
        model.addAttribute("topics", topicService.allTopics());
        return "index";
    }

    @GetMapping(path = "/api-docs")
    public String renderApiDocumentation() {
        return "api_documentation";
    }

    //TODO
    @PostMapping(path = "/register")
    public String register() {
        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String renderLoginPage() {
        return "login_page";
    }



}
