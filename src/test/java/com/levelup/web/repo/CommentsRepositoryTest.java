package com.levelup.web.repo;

import com.levelup.web.model.Answer;
import com.levelup.web.model.Comment;
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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@Transactional
public class CommentsRepositoryTest {
    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private AnswersRepository answersRepository;

    @Autowired
    private UsersRepository usersRepository;

    private Date date = new Date();
    private Date dateBefore = new Date(date.getTime() - 100000000);
    private Answer testAnswer;

    @Before
    public void setUp() throws Exception {
        testAnswer = new Answer("testBodyAnswer");
        User author = new User("testLogin@user.com", "testPasswordUser", UserRoles.USER);
        usersRepository.save(author);
        answersRepository.save(testAnswer);
        Comment firstComment = new Comment("FirstTestBodyComment");
        Comment secondComment = new Comment("SecondTestBodyComment");
        firstComment.setAuthor(author);
        firstComment.setAnswer(testAnswer);
        firstComment.setCreated(date);
        secondComment.setAnswer(testAnswer);
        commentsRepository.save(firstComment);
        commentsRepository.save(secondComment);
    }

    @Test
    public void findByAuthor() {
        List<Comment> foundCommentsList = commentsRepository.findByAuthorLogin("testLogin@user.com");
        assertEquals(1, foundCommentsList.size());
        assertEquals("FirstTestBodyComment", foundCommentsList.get(0).getBody());
    }

    @Test
    public void findByAnswerId() {
        List<Comment> foundCommentsList = commentsRepository.findByAnswerId(testAnswer.getId());
        assertEquals(2, foundCommentsList.size());
    }

    @Test
    public void findByCreatedBefore() {
        List<Comment> foundCommentsList = commentsRepository.findByCreatedIsLessThanEqual(date);
        assertEquals(1, foundCommentsList.size());
        assertEquals("FirstTestBodyComment", foundCommentsList.get(0).getBody());

        List<Comment> emptyList = commentsRepository.findByCreatedIsLessThanEqual(dateBefore);
        assertEquals(0, emptyList.size());
    }
}