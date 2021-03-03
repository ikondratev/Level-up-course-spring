package com.levelup.web.service;

import com.levelup.web.dao.QuestionsDao;
import com.levelup.web.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionsDao questionsDao;

    public List<Question> findAll() {
        return questionsDao.findAll();
    }

    public Question save(String title, String body) {
        Date nowDate = new Date();
        Question question = new Question(title, body);
        question.setCreated(nowDate);

        try {
            questionsDao.save(question);
        } catch (Exception e) {
            return null;
        }

        return question;
    }

    public Question update(Question question) {

        try {
            questionsDao.update(question);
        } catch (Exception e) {
            return null;
        }

        return question;
    }

    public Question findById(Long id) {
        try {
            return questionsDao.findById(id);
        } catch (Exception e) {
            return null;
        }
    }
}
