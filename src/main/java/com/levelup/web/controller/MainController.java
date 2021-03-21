package com.levelup.web.controller;

import com.levelup.web.model.Question;
import com.levelup.web.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(
            Model model,
            Authentication authentication
    ) {
        String title;
        boolean isAdmin = false;
        if (authentication == null) {
            title = "Hello anonymous!";
        } else {
            title = "Hello " + authentication.getName() + "!";
            isAdmin = authentication.getAuthorities().contains(
                    new SimpleGrantedAuthority("ADMIN")
            );
        }

        List<Question> questionsList = questionService.findAll();
        model.addAttribute("title", title);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isLogged", authentication != null);
        model.addAttribute("questions", questionsList);

        return "index";
    }
}
