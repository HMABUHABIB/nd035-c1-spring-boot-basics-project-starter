package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final NoteService noteService;
    private final UserService userService;
    private final CredentialService credentialService;

    public HomeController(NoteService noteService, UserService userService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @GetMapping()
    public String homeView(Authentication authentication, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("logInUserName", "Logged in as: "+authentication.getName());
        model.addAttribute("noteList", this.noteService.getNoteListByUserId(userId));
        model.addAttribute("credentialList", this.credentialService.getUserCredentialListByUserId(userId));
        return "home";
    }
}
