package com.levelup.web.repo;

import com.levelup.web.model.Answer;
import com.levelup.web.model.Question;
import com.levelup.web.model.User;
import com.levelup.tests.TestConfiguration;
import com.levelup.web.model.UserRoles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@Transactional
public class AnswersRepositoryTest {

    @Autowired
    private AnswersRepository answersRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private UsersRepository usersRepository;

    private Date date = new Date();
    private Date dateBefore = new Date(date.getTime() - 100000000);

    @Before
    @Transactional
    public void setUp() {
        Question question = new Question("TestTitle", "TestBody");
        questionsRepository.save(question);
        User author = new User("test@ogin.com", "testPasswoerd", UserRoles.USER);
        usersRepository.save(author);
        Answer firstAnswer = new Answer("TestAnswerBodyFirst");
        Answer secondAnswer = new Answer("TestAnswerBodySecond");
        firstAnswer.setQuestion(question);
        firstAnswer.setAuthor(author);
        firstAnswer.setCreated(date);
        secondAnswer.setQuestion(question);
        answersRepository.save(firstAnswer);
        answersRepository.save(secondAnswer);
    }

    @Test
    public void findByAuthor() {
        assertEquals(0, answersRepository
                .findByAuthorLogin("wrongLogin")
                .size());
        assertEquals("TestAnswerBodyFirst", answersRepository
                .findByAuthorLogin("test@ogin.com")
                .get(0)
                .getBody());
    }

    @Test
    public void findByQuestion() {
        assertEquals(2, answersRepository
                .findByQuestionTitle("TestTitle")
                .size());
    }

    @Test
    public void findByCreatedBefore() {
        List<Answer> foundList = answersRepository.findByCreatedIsLessThanEqual(date);
        assertEquals(1, foundList.size());
        assertEquals("TestAnswerBodyFirst", foundList.get(0).getBody());

        List<Answer> emptyList = answersRepository.findByCreatedIsLessThanEqual(dateBefore);
        assertEquals(0, emptyList.size());
    }
}