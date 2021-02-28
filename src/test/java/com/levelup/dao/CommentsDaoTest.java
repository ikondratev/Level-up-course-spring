package com.levelup.dao;

import com.levelup.model.Answer;
import com.levelup.model.Comment;
import com.levelup.model.User;
import com.levelup.tests.TestConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CommentsDaoTest {
    @Autowired
    private EntityManager manager;

    @Autowired
    private CommentsDao commentsDao;

    private Date date = new Date();
    private Date dateBefore = new Date(date.getTime() - 100000000);
    private Answer testAnswer;

    @Before
    public void setUp() throws Exception {
        manager.getTransaction().begin();
        testAnswer = new Answer("testBodyAnswer");
        User author = new User("testLoginUser", "testPasswordUser", false);
        manager.persist(testAnswer);
        manager.persist(author);
        Comment firstComment = new Comment("FirstTestBodyComment");
        Comment secondComment = new Comment("SecondTestBodyComment");
        firstComment.setAuthor(author);
        firstComment.setAnswer(testAnswer);
        firstComment.setCreated(date);
        secondComment.setAnswer(testAnswer);
        manager.persist(firstComment);
        manager.persist(secondComment);
        manager.getTransaction().commit();
    }

    @Test
    public void findAll() {
        assertEquals(2, commentsDao.findAll().size());
    }

    @Test
    public void findByAuthor() {
        List<Comment> foundCommentsList = commentsDao.findByAuthor("testLoginUser");
        assertEquals(1, foundCommentsList.size());
        assertEquals("FirstTestBodyComment", foundCommentsList.get(0).getBody());
    }

    @Test
    public void findByAnswerId() {
        List<Comment> foundCommentsList = commentsDao.findByAnswerId(testAnswer);
        assertEquals(2, foundCommentsList.size());
    }

    @Test
    public void findByCreatedBefore() {
        List<Comment> foundCommentsList = commentsDao.findByCreatedBefore(date);
        assertEquals(1, foundCommentsList.size());
        assertEquals("FirstTestBodyComment", foundCommentsList.get(0).getBody());

        List<Comment> emptyList = commentsDao.findByCreatedBefore(dateBefore);
        assertEquals(0, emptyList.size());
    }
}