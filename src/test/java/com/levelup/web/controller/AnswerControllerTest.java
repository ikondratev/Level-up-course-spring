package com.levelup.web.controller;

import com.levelup.web.model.Answer;
import com.levelup.web.model.Question;
import com.levelup.web.service.AnswerService;
import com.levelup.web.service.CommentService;
import com.levelup.web.service.QuestionService;
import com.levelup.web.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestWebConfiguration.class)
@AutoConfigureMockMvc
public class AnswerControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private AnswerService answerService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private UserService userService;


    @Test
    public void addAnswer() throws Exception {
        Question testQuestion = new Question("Question: 1", "testBodyQuestion");
        Mockito.when(questionService.findById(1L)).thenReturn(testQuestion);

        Answer testAnswer = new Answer("TestBodyAnswer");
        testAnswer.setQuestion(testQuestion);
        List<Answer> testAnswersList = new ArrayList<>();
        testAnswersList.add(testAnswer);
        testQuestion.setListOfAnswer(testAnswersList);
        Mockito.when(answerService.createAnswer("TestBodyAnswer", testQuestion)).thenReturn(testAnswer);

        mvc.perform(post("/question/{questionId}/add_answer", 1L)
                .with(user("admin").roles("ADMIN"))
                .param("answerBody", "TestBodyAnswer")
                .with(csrf())
        )
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Question: 1"))
                .andExpect(model().attribute("question", testQuestion))
                .andExpect(model().attribute("answers", testAnswersList));

        Mockito.verify(questionService, Mockito.atLeast(1))
                .findById(1L);

        Mockito.verify(answerService, Mockito.atLeast(1))
                .createAnswer("TestBodyAnswer", testQuestion);
    }
}