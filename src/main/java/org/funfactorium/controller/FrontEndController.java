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

    @Autowired
    private FunFactService funFactService;

    @Autowired
    private TopicService topicService;

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

    //TODO
    @PostMapping(path = "/login")
    public String login() {
        return "redirect:/";
    }

    //TODO
    @GetMapping(path = "/logout")
    public String logout() {
        return "redirect:/";
    }


}
