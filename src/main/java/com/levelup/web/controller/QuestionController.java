package com.levelup.web.controller;

import com.levelup.web.model.Question;
import com.levelup.web.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model) {
        List<Question> questionsList = questionService.findAll();
        model.addAttribute("title", "Questions");
        model.addAttribute("questions", questionsList);

        return "index";
    }

    @PostMapping("/add")
    public String addQuestion(
            Model model,
            @RequestParam String title,
            @RequestParam String body
    ) {
        questionService.save(title, body);
        model.addAttribute("title", title);

        return "questionAdded";
    }

}
