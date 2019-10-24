package com.javabycode.springmvc.controller;

import com.javabycode.springmvc.model.AccessToken;
import com.javabycode.springmvc.model.Account;
import com.javabycode.springmvc.model.Profile;
import com.javabycode.springmvc.service.AccessTokenService;
import com.javabycode.springmvc.service.ProfileService;
import com.javabycode.springmvc.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/editProfile")
public class EditProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AccessTokenService accessTokenService;

    @GetMapping()
    public String getEditPage(HttpServletResponse response, HttpServletRequest request, Model model) {
        String accessTokenValue = securityService.checkAccessToken(request);
        if (accessTokenValue == null) {
            response.setStatus(401);
            return "redirect:/signin";
        }
        AccessToken accessToken = accessTokenService.getAccessTokenByValue(accessTokenValue);
        if (accessToken == null) {
            response.setStatus(422);
            return "redirect:/signin";
        }

        Boolean isAccessTokenLive = accessTokenService.checkAccessTokenTTL(accessToken);
        if (!isAccessTokenLive) {
            response.setStatus(422);
            accessTokenService.remove(accessToken);
            return "redirect:/signin";
        }
        Account currentAccount = accessToken.getAccount();
        model.addAttribute("name", currentAccount.getName());
        model.addAttribute("lastname", currentAccount.getLastname());
        model.addAttribute("phone", currentAccount.getPhone());
        model.addAttribute("email", currentAccount.getEmail());
        Profile profile = profileService.getProfileByAccountId(currentAccount);
        if (profile == null) return "editPage";
        model.addAttribute("photoSrc", profile.getPhoto());
        String softSkills = profile.getSkills().getSoftskills();
        String hardSkills = profile.getSkills().getHardskills();
        String position = profile.getSkills().getPosition();
        List<String> hardSkillsList = new ArrayList<>(Arrays.asList(hardSkills.split(",")));
        model.addAttribute("softSkills", softSkills);
        model.addAttribute("hardSkills", hardSkillsList);
        model.addAttribute("position", position);
        return "editPage";
    }
}