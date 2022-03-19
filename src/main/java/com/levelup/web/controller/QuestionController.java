package com.levelup.web.controller;

import com.levelup.web.controller.partial.AddQuestionForm;
import com.levelup.web.model.Comment;
import com.levelup.web.model.Question;
import com.levelup.web.service.AnswerService;
import com.levelup.web.service.CommentService;
import com.levelup.web.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/add")
    public String viewFormQuestion(
            Model model,
            @ModelAttribute("questionForm") AddQuestionForm questionForm,
            BindingResult bindingResult
    ) {
        return "addQuestion";
    }

    @PostMapping("/question/add")
    public String addQuestion(
            Model model,
            @Valid @ModelAttribute("questionForm") AddQuestionForm questionForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "addQuestion";
        }

        Question addedQuestion;

        try {
            addedQuestion = questionService.save(questionForm.getTitle(), questionForm.getBody());
        } catch (Throwable e) {
            bindingResult.addError(
                    new FieldError(
                            "form",
                            "title",
                            "body"
                    )
            );
            return "addQuestion";
        }

        model.addAttribute("title", addedQuestion.getTitle());
        return "questionAdded";
    }

    @GetMapping("/question/{questionId}")
    public String showQuestion(
            Model model,
            @PathVariable String questionId
    ) {
        Question question = questionService.findById(Long.parseLong(questionId));

        if (question != null) {
            model.addAttribute("title", "Question: " + questionId);
            model.addAttribute("question", question);
            model.addAttribute("answers", question.getListOfAnswer());
            return "questionShow";
        } else {
            model.addAttribute("error", "Queston " + questionId + " is not found");
            return "questionError";
        }
    }
}
