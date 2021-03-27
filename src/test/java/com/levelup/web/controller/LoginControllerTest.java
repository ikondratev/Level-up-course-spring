package com.levelup.web.controller;

import com.levelup.web.service.AnswerService;
import com.levelup.web.service.CommentService;
import com.levelup.web.service.QuestionService;
import com.levelup.web.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.*;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestWebConfiguration.class)
@AutoConfigureMockMvc
public class LoginControllerTest {
    @MockBean
    private UserService userService;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private AnswerService answerService;

    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void showRegisterForm() throws Exception {
        mvc.perform(get("/user/registration")).andExpect(status().isOk());
    }
}