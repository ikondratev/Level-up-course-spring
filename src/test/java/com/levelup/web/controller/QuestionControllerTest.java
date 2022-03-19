package com.levelup.web.controller;

import com.levelup.web.model.Answer;
import com.levelup.web.model.Comment;
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
import java.util.Collections;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestWebConfiguration.class)
@AutoConfigureMockMvc
public class QuestionControllerTest {
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
    public void showQuestion() throws Exception {
        Question testQuestion = new Question("Question: 1", "testBodyQuestion");
        Mockito.when(questionService.findById(1L)).thenReturn(testQuestion);


        mvc.perform(get("/question/{questionId}", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Question: 1"))
                .andExpect(model().attribute("question", testQuestion))
                .andExpect(model().attribute("answers", Collections.emptyList()));

        Mockito.verify(questionService, Mockito.atLeast(1))
                .findById(1L);
    }

    @Test
    public void showQuestionLoggedAsAdmin() throws Exception {
        Question testQuestion = new Question("Question: 1", "testBodyQuestion");
        Mockito.when(questionService.findById(1L)).thenReturn(testQuestion);


        mvc.perform(get("/question/{questionId}", 1L)
                .with(user("admin")
                        .roles("ADMIN"))
                )
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Question: 1"))
                .andExpect(model().attribute("question", testQuestion))
                .andExpect(model().attribute("answers", Collections.emptyList()));

        Mockito.verify(questionService, Mockito.atLeast(1))
                .findById(1L);
    }

    @Test
    public void addQuestionLoggedAsAdmin() throws Exception {
        Question question = new Question("Test title question", "Test body question");
        Mockito.when(questionService.save("Test title question", "Test body question"))
                .thenReturn(question);

        mvc.perform(post("/question/add").with(user("admin").roles("ADMIN"))
                .param("title", "Test title question")
                .param("body", "Test body question")
                .with(csrf())
        )
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Test title question"));

        Mockito.verify(questionService, Mockito.atLeast(1))
                .save("Test title question", "Test body question");
    }
}