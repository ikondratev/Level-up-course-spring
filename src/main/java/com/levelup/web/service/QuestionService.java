package com.levelup.web.service;

import com.levelup.web.dao.QuestionsDao;
import com.levelup.web.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionsDao questionsDao;

    public List<Question> findAll() {
        return questionsDao.findAll();
    }

    public void save(String title, String body) {
        Question question = new Question(title, body);
        questionsDao.save(question);
    }
}
