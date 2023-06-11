package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.UserCredential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/userCredential")
public class CredentialController {

    private final CredentialService credentialService;

    private final UserService userService;

    public CredentialController( CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String postNewCredential(Authentication authentication, @ModelAttribute UserCredential userCredential, Model model) {
        if (userCredential.getCredentialId() != null) {
            this.credentialService.updateCredential(userCredential);
        }
        else {
            int userId = this.userService.getUserIdFromUserName(authentication.getName());
            userCredential.setUserId(userId);
            this.credentialService.addCredential(userCredential);
        }
        model.addAttribute("successfulResult", Boolean.TRUE);
        return "result";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(Authentication authentication, @PathVariable Integer credentialId, Model model) {
        Integer userId = this.userService.getUserIdFromUserName(authentication.getName());
        Boolean updateResult = credentialService.deleteCredential(userId, credentialId);
        model.addAttribute("successfulResult", updateResult);
        return "result";
    }
}
