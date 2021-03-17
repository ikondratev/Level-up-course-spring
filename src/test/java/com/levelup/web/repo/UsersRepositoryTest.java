package com.levelup.web.repo;

import com.levelup.web.model.User;
import com.levelup.web.model.UserStates;
import com.levelup.tests.TestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UsersRepositoryTest {
    @Autowired
    private EntityManager manager;

    @Autowired
    private UsersRepository usersRepository;

    private Date date = new Date();
    private Date dateBefore = new Date(date.getTime() - 100000000);

    @Before
    public void init() {
        User testUserFirst = new User("login@first","qwertyFirst",false);
        testUserFirst.setCreated(date);
        testUserFirst.setStatus(UserStates.ACTIVE);
        User testUserSecond = new User("login@second","qwertySecond",true);
        testUserSecond.setStatus(UserStates.BANNED);

        manager.getTransaction().begin();
        manager.persist(testUserFirst);
        manager.persist(testUserSecond);
        manager.getTransaction().commit();
    }

    @Test
    public void findByLogin() {
        assertNull(usersRepository.findByLogin("wrong@test@user@login"));
        User userFound = usersRepository.findByLogin("login@first").get(0);
        assertNotNull(userFound);
        assertEquals("login@first", userFound.getLogin());
    }

    @Test
    public void findAll() {
        assertEquals(usersRepository.findAll().size(), 2);
    }

    @Test
    public void findByLoginAndPassword() {
        assertNull(usersRepository.findByLoginAndPassword("wrongTest", "wrongPass"));
        User userFound = usersRepository.findByLoginAndPassword("login@first", "qwertyFirst").get(0);
        assertNotNull(userFound);
        assertEquals("login@first", userFound.getLogin());
    }

    @Test
    public void findByIsAdmin() {
        List<User> isAdminUsers = usersRepository.findByIsAdmin(true);
        assertEquals(isAdminUsers.size(), 1);
        assertEquals(isAdminUsers.get(0).getLogin(), "login@second");

        List<User> notAdminUsers = usersRepository.findByIsAdmin(false);
        assertEquals(notAdminUsers.size(), 1);
        assertEquals(notAdminUsers.get(0).getLogin(), "login@first");
    }

    @Test
    public void testFindByCreatedBefore() {
        List<User> nowCreatedUserList = usersRepository.findByCreatedIsLessThanEqual(date);
        assertEquals( "login@first", nowCreatedUserList.get(0).getLogin());
        assertEquals(1, nowCreatedUserList.size());

        List<User> beforeCreatedUserList = usersRepository.findByCreatedIsLessThanEqual(dateBefore);
        assertEquals(0, beforeCreatedUserList.size());
    }

    @Test
    public void findByStates() {
        List<User> activeList = usersRepository.findByStatus(UserStates.ACTIVE);
        assertEquals(1, activeList.size());
        assertEquals("login@first", activeList.get(0).getLogin());

        List<User> bannedList = usersRepository.findByStatus(UserStates.BANNED);
        assertEquals(1, bannedList.size());
        assertEquals("login@second", bannedList.get(0).getLogin());
    }
}