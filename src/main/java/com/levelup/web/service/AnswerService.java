package com.levelup.web.service;

import com.levelup.web.repo.AnswersRepository;
import com.levelup.web.repo.CommentsRepository;
import com.levelup.web.model.Answer;
import com.levelup.web.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AnswerService {
    @Autowired
    private AnswersRepository answersRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    public Answer createAnswer(String body, Question question) {
        Answer newAnswer = new Answer(body);
        Date now = new Date();

        newAnswer.setQuestion(question);
        newAnswer.setCreated(now);

        try {
            answersRepository.save(newAnswer);
            return newAnswer;
        } catch (Exception e) {
            return null;
        }
    }
}
