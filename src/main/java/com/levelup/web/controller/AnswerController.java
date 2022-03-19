package com.levelup.web.controller;

import com.levelup.web.model.Answer;
import com.levelup.web.model.Question;
import com.levelup.web.service.AnswerService;
import com.levelup.web.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @PostMapping("/question/{questionId}/add_answer")
    public String addAnswer(
            Model model,
            @RequestParam String answerBody,
            @PathVariable String questionId
    ) {

        List<Answer> answersList;
        Question question = questionService.findById(Long.parseLong(questionId));

        if (question != null) {
            Answer newAnswer = answerService.createAnswer(answerBody, question);
            answersList = question.getListOfAnswer();
            answersList.add(newAnswer);
            question.setListOfAnswer(answersList);
            questionService.update(question);
            model.addAttribute("title", "Question: " + questionId);
            model.addAttribute("question", question);
            model.addAttribute("answers",  question.getListOfAnswer());
            return "questionShow";
        } else {
            model.addAttribute("error", "Answer for " + questionId + " is not created");
            return "questionError";
        }
    }
}
