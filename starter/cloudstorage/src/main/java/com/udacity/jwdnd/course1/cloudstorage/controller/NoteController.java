package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.UserNote;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/userNote")
public class NoteController {

    private final NoteService noteService;

    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String postNewNote(Authentication authentication, @ModelAttribute UserNote userNote, Model model) {
        if (userNote.getNoteId() != null) {
            this.noteService.updateNote(userNote);
        }
        else {
            int userId = this.userService.getUserIdFromUserName(authentication.getName());
            userNote.setUserId(userId);
            if (this.noteService.checkIfSameNoteWithTitleAndDescription(userNote)) {
                model.addAttribute("successfulResult", Boolean.FALSE);
                model.addAttribute("successfulResultInfo", "The value for the new note is duplicate values");
                return "result";
            }
            this.noteService.addNote(userNote);
        }
        model.addAttribute("successfulResult", Boolean.TRUE);
        return "result";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(Authentication authentication, @PathVariable Integer noteId, Model model) {
        Integer userId = this.userService.getUserIdFromUserName(authentication.getName());
        Boolean updateResult = noteService.deleteNote(userId, noteId);
        model.addAttribute("successfulResult", updateResult);
        return "result";
    }
}
