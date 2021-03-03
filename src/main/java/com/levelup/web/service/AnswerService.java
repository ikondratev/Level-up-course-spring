package com.levelup.web.service;

import com.levelup.web.dao.AnswersDao;
import com.levelup.web.dao.CommentsDao;
import com.levelup.web.model.Answer;
import com.levelup.web.model.Comment;
import com.levelup.web.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AnswerService {
    @Autowired
    private AnswersDao answersDao;

    @Autowired
    private CommentsDao commentsDao;

    public Answer createAnswer(String body, Question question) {
        Answer newAnswer = new Answer(body);
        Date now = new Date();

        newAnswer.setQuestion(question);
        newAnswer.setCreated(now);

        try {
            answersDao.save(newAnswer);
            return newAnswer;
        } catch (Exception e) {
            return null;
        }
    }
}
