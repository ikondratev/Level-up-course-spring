package com.levelup.web.service;

import com.levelup.web.repo.QuestionsRepository;
import com.levelup.web.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionsRepository questionsRepository;

    public List<Question> findAll() {
        return questionsRepository.findAll();
    }

    public Question save(String title, String body) {
        Date nowDate = new Date();
        Question question = new Question(title, body);
        question.setCreated(nowDate);

        return questionsRepository.save(question);
    }

    public Question update(Question question) {
        return questionsRepository.save(question);
    }

    public Question findById(Long id) {
        return questionsRepository.findById(id).get();
    }
}
