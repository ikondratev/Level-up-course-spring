package com.levelup.web.controller;

import com.levelup.web.model.Question;
import com.levelup.web.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
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
            Model model
    ) {
        String title = "Holla!";


        List<Question> questionsList = questionService.findAll();
        model.addAttribute("title", title);
        model.addAttribute("questions", questionsList);

        return "index";
    }
}
