package com.levelup.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@SessionAttributes("user-session")
public class LoginController {
    @GetMapping("/login")
    public String showLoginForm() {

        return "loginForm";
    }

    @PostMapping("/login")
    public RedirectView authoriseAndRedirect(
            @RequestParam String login,
            @RequestParam String password
    ) {
        if (login.equals("admin") && password.equals("admin")) {
//            currentUser.setUserLogin(login);
//            currentUser.setAdmin(true);
            return new RedirectView("/");
        } else {
            return new RedirectView("/login");
        }

    }
}
