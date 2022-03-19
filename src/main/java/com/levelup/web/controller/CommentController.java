package com.levelup.web.controller;

import com.levelup.web.model.Comment;
import com.levelup.web.model.Question;
import com.levelup.web.service.AnswerService;
import com.levelup.web.service.CommentService;
import com.levelup.web.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class CommentController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/question/{questionId}/{answerId}/add_comment")
    public String addComment(
            Model model,
            @RequestParam String bodyComment,
            @PathVariable String questionId,
            @PathVariable String answerId
    ) {
        Question question = questionService.findById(Long.parseLong(questionId));

        if (question != null) {
            Comment comment = commentService.addComment(answerId, bodyComment);
            model.addAttribute("title", "Question: " + questionId);
            model.addAttribute("question", question);
            model.addAttribute("answers", question.getListOfAnswer());
            return "questionShow";
        } else {
            model.addAttribute("error", "comment for answer: " + answerId + " is not created");
            return "questionError";
        }
    }
}
