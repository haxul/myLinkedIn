package com.javabycode.springmvc.controller;

import com.javabycode.springmvc.helpers.StringRandom;
import com.javabycode.springmvc.model.AccessToken;
import com.javabycode.springmvc.model.Account;
import com.javabycode.springmvc.model.AuthForm;
import com.javabycode.springmvc.service.AccessTokenService;
import com.javabycode.springmvc.service.AccountService;
import com.javabycode.springmvc.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.NoSuchAlgorithmException;


@Controller("/signin")
public class SigninController {

    @Autowired
    private AccessTokenService service;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SecurityService securityService;

    @GetMapping
    public String getPage() {
        return "signin";
    }

    @PostMapping
    public String handleAuth(@ModelAttribute("authForm") AuthForm form, Model model) throws NoSuchAlgorithmException {
        String email = form.getEmail();
        Boolean isCorrectEmail = accountService.checkCorrectnessOfEmail(email);
        String password = form.getPassword();
        if (password.equals("") || !isCorrectEmail) {
            sendErrorToView(model, "fill out fields correctly");
            return "signin";
        }
        Account account = accountService.findByEmail(email);
        if (account == null) {
            sendErrorToView(model, "The User is not found");
            return "signin";
        }

        String correctedHashedPassword = account.getPassword();
        String currentHashedPassword = securityService.generateHashPassword(password + SecurityService.SALT);

        if (!correctedHashedPassword.equals(currentHashedPassword)) {
            sendErrorToView(model, "The User is not found");
            return "signin";
        }
        String tokenValue = StringRandom.generateRandomString(60);
        AccessToken token = new AccessToken(AccessTokenService.TTL, account, tokenValue);
        service.save(token);
        return "userProfile";
    }

    private void sendErrorToView(Model model, String message) {
        model.addAttribute("error", message);
    }
}