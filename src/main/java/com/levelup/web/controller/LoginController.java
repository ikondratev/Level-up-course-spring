package com.levelup.web.controller;

import com.levelup.web.controller.partial.RegistrationForm;
import com.levelup.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {

        return "loginForm";
    }

    @GetMapping("/registration/user")
    public String getRegistration(
            Model model,
            @ModelAttribute("registrationForm") RegistrationForm registrationForm,
            BindingResult bindingResult
    ) {
        return "registration";
    }

    @PostMapping("/registration/user")
    public String showRegistrationForm(
            Model model,
            @ModelAttribute("registrationForm") RegistrationForm registrationForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        boolean isSave;

        try {
            isSave = userService.save(
                    registrationForm.getLogin(),
                    registrationForm.getPassword(),
                    registrationForm.getConfirmation()
            );
        } catch (Throwable e) {
            bindingResult.addError(
                    new FieldError(
                           "from",
                           "login",
                           "User is not created"
                    )
            );

            return "registration";
        }

        if (isSave) {
            return "redirect:/login";
        } else {
            return "registration";
        }
    }

}
