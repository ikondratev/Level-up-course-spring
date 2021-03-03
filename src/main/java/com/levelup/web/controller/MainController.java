package com.levelup.web.controller;

import com.levelup.web.model.Question;
import com.levelup.web.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("user-session")
public class MainController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(
            Model model,
            @ModelAttribute("user-session") UserSession userSession
    ) {
        String title;
        if  (userSession.getUserLogin() == null) {
            title = "Hello anonymous";
        } else {
            title = "Hello " + userSession.getUserLogin();
        }

        List<Question> questionsList = questionService.findAll();
        model.addAttribute("title", title);
        model.addAttribute("questions", questionsList);
        model.addAttribute("isAdmin", userSession.isAdmin());
        model.addAttribute("isLogged", userSession.getUserLogin() != null);

        return "index";
    }

    @PostMapping("/add")
    public String addQuestion(
            Model model,
            @RequestParam String title,
            @RequestParam String body
    ) {
        Question newQuestion = questionService.save(title, body);
        model.addAttribute("title", title);

        if (newQuestion != null) {
            return "questionAdded";
        } else {
            model.addAttribute("error", "question_not_created");
            return "questionError";
        }
    }

    @ModelAttribute("user-session")
    public UserSession createUserSession() {
        return new UserSession();
    }
}
