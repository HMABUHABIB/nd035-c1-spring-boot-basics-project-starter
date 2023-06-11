package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UserNote;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void addNote(UserNote userNote) {
        noteMapper.addNote(userNote);
    }

    public List<UserNote> getNoteListByUserId(Integer userId) {
        return noteMapper.getNotes(userId);
    }

    public Boolean deleteNote(Integer userId, Integer noteId) {
        if (noteMapper.getNote(noteId) != null &&
                noteMapper.getNote(noteId).getUserId().equals(userId)) {
            noteMapper.deleteNote(noteId);
            return true;
        } else {
            return false;
        }
    }

    public void updateNote(UserNote userNote) {
        noteMapper.updateNote(userNote);
    }
}
