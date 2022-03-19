package com.levelup.web.service;

import com.levelup.web.model.UserRoles;
import com.levelup.web.repo.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DetailsService.class)
public class DetailsServiceTest {
    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder encoder;

    @Autowired
    private DetailsService service;

    @Test
    public void testAdmin() {
        Mockito.when(encoder.encode("admin")).thenReturn("encryptedAdmin");

        UserDetails expected = User.withUsername("admin")
                .password("encryptedAdmin")
                .roles("ADMIN", "USER")
                .build();

        UserDetails result = service.loadUserByUsername("admin");
        assertNotNull(result);
        assertEquals(expected.getUsername(), result.getUsername());
        assertEquals(expected.getAuthorities(), result.getAuthorities());
        assertEquals(expected.getPassword(), result.getPassword());

    }

    @Test
    public void testFoundUserFromRepo() {
        com.levelup.web.model.User user = new com.levelup.web.model.User(
                "login@test.user",
                "encryptedPassword",
                UserRoles.USER);

        Mockito.when(userService.findByLogin(user.getLogin())).thenReturn(user);

        UserDetails expected = User.withUsername(user.getLogin())
                .password(user.getPassPhrase())
                .roles("USER")
                .build();

        UserDetails result = service.loadUserByUsername(user.getLogin());
        assertNotNull(result);
        assertEquals(expected.getUsername(), result.getUsername());
        assertEquals(expected.getAuthorities(), result.getAuthorities());
        assertEquals(expected.getPassword(), result.getPassword());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testNonExistUser() {
        service.loadUserByUsername("test-not@found.user");
    }
}