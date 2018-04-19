package org.funfactorium.controller;

import org.funfactorium.funfacts.FunFact;
import org.funfactorium.funfacts.FunFactService;
import org.funfactorium.funfacts.topics.Topic;
import org.funfactorium.funfacts.topics.TopicService;
import org.funfactorium.user.User;
import org.funfactorium.user.UserRegistrationDto;
import org.funfactorium.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Locale;


@Controller
public class FrontEndController {
    @Autowired
    private UserService userService;

    private final FunFactService funFactService;

    private final TopicService topicService;

    @Autowired
    public FrontEndController(FunFactService funFactService, TopicService topicService) {
        this.funFactService = funFactService;
        this.topicService = topicService;
    }

    @GetMapping(path = "/")
    public String renderIndex(Authentication aut, Principal principal, Model model) {
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

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                              BindingResult result){

        User existingName = userService.findByUserName(userDto.getUserName());
        User existingEmail = userService.findByEmail(userDto.getEmail());
        if (existingName != null || existingEmail != null){
            result.rejectValue("Existing user", null, "There is already an account " +
                                                                "registered with that username or email");
        }

        if (result.hasErrors()){
            return result.getAllErrors().toString();
        }

        userService.register(userDto);
        return "redirect:/";
    }

    @GetMapping("/add-funfact")
    public String renderAddFunfactPage(Principal principal, Model model) {
        String userName = principal.getName();
        List<Topic> allTopics = topicService.allTopics();
        model.addAttribute("username", userName);
        model.addAttribute("topics", allTopics);
        return "add_funfact";
    }


}
