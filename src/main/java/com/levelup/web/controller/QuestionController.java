package com.levelup.web.controller;

import com.levelup.web.model.Answer;
import com.levelup.web.model.Comment;
import com.levelup.web.model.Question;
import com.levelup.web.service.AnswerService;
import com.levelup.web.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("user-session")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{questionId}")
    public String showQuestion(
            Model model,
            @SessionAttribute("user-session") UserSession userSession,
            @PathVariable String questionId
    ) {
        boolean isLogged = userSession.isAdmin();
        Question question = questionService.findById(Long.parseLong(questionId));

        if (question != null) {
            model.addAttribute("title", "Question: " + questionId);
            model.addAttribute("question", question);
            model.addAttribute("answers",  question.getListOfAnswer());
            model.addAttribute("isLogged", isLogged);
            return "questionShow";
        } else {
            model.addAttribute("error", "Queston " + questionId + " is not found");
            return "questionError";
        }
    }

    @PostMapping("/question/{questionId}/add_answer")
    public String addAnswer(
            Model model,
            @RequestParam String answerBody,
            @SessionAttribute("user-session") UserSession userSession,
            @PathVariable String questionId
    ) {

        List<Answer> answersList;
        boolean isLogged = userSession.isAdmin();
        Question question = questionService.findById(Long.parseLong(questionId));
        Answer newAnswer = answerService.createAnswer(answerBody, question);

        if (question != null && newAnswer != null) {
            answersList = question.getListOfAnswer();
            answersList.add(newAnswer);
            question.setListOfAnswer(answersList);
            questionService.update(question);
            model.addAttribute("title", "Question: " + questionId);
            model.addAttribute("question", question);
            model.addAttribute("answers",  question.getListOfAnswer());
            model.addAttribute("isLogged", isLogged);
            return "questionShow";
        } else {
            model.addAttribute("error", "Answer for " + questionId + " is not created");
            return "questionError";
        }
    }

    @PostMapping("/question/{questionId}/{answerId}/add_comment")
    public String addComment(
            Model model,
            @RequestParam String bodyComment,
            @SessionAttribute("user-session") UserSession userSession,
            @PathVariable String questionId,
            @PathVariable String answerId
    ) {
        boolean isLogged = userSession.isAdmin();
        Question question = questionService.findById(Long.parseLong(questionId));
        Comment comment = commentService.addComment(answerId, bodyComment);

        if (comment != null) {
            model.addAttribute("title", "Question: " + questionId);
            model.addAttribute("question", question);
            model.addAttribute("answers",  question.getListOfAnswer());
            model.addAttribute("isLogged", isLogged);
            return "questionShow";
        } else {
            model.addAttribute("error", "comment for answer: " + answerId + " is not created");
            return "questionError";
        }
    }
}
