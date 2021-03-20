package com.levelup.web.controller;

import com.levelup.web.model.Question;
import com.levelup.web.service.AnswerService;
import com.levelup.web.service.CommentService;
import com.levelup.web.service.QuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestWebConfiguration.class)
@AutoConfigureMockMvc
public class MainControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private AnswerService answerService;

    @Test
    public void testNullQuestions() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Hello anonymous"))
                .andExpect(model().attribute("questions", Collections.emptyList()))
                .andExpect(model().attribute("isAdmin", false))
                .andExpect(model().attribute("isLogged", false));
    }

    @Test
    public void testNullQuestionsLogedAsAdmin() throws Exception {
        UserSession userSession = new UserSession("admin", true);

        mvc.perform(get("/").sessionAttr("user-session", userSession))
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Hello admin"))
                .andExpect(model().attribute("questions", Collections.emptyList()))
                .andExpect(model().attribute("isAdmin", true))
                .andExpect(model().attribute("isLogged", true));
    }

    @Test
    public void testSeveralQuestionsNotLogged() throws Exception {
        List<Question> expectQuestionsList  = Arrays.asList(
                new Question("testQuestionTitle", "testQuestionBody")
        );

        Mockito.when(questionService.findAll()).thenReturn(expectQuestionsList);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("title", "Hello anonymous"))
                .andExpect(model().attribute("isAdmin", false))
                .andExpect(model().attribute("isLogged", false))
                .andExpect(model().attribute("questions", expectQuestionsList));
    }
}