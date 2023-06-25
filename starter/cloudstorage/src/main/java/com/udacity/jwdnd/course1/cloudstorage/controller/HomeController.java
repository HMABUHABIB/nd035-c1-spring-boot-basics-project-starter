package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
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
    private final FileService fileService;

    public HomeController(NoteService noteService, UserService userService, CredentialService credentialService, FileService fileService) {
        this.noteService = noteService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }

    @GetMapping()
    public String homeView(Authentication authentication, Model model) {
        try {Integer userId = this.userService.getUserIdFromUserName(authentication.getName());
            model.addAttribute("logInUserName", "Logged in as: "+authentication.getName());
            model.addAttribute("noteList", this.noteService.getNoteListByUserId(userId));
            model.addAttribute("credentialList", this.credentialService.getUserCredentialListByUserId(userId));
            model.addAttribute("userFileList", this.fileService.getUserFileList(userId));
        } catch (Exception e) {
            return "login";
        }
        return "home";
    }
}
