package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.UserFile;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/userFile")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String addNewFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile multipartFile, Model model){

            int userId = this.userService.getUserIdFromUserName(authentication.getName());
            if (multipartFile.getSize() > 5242880){
                model.addAttribute("successfulResult", Boolean.FALSE);
                model.addAttribute("successfulResultInfo", "The file is very big");
                return "result";
            }
                UserFile userFile = fileService.createNewUserFile(multipartFile, userId);
            if (userFile.getFileName().isEmpty()) {
                model.addAttribute("successfulResult", Boolean.FALSE);
                model.addAttribute("successfulResultInfo", "File name can't be empty");
                return "result";
            }
            if (this.fileService.gerUserFileByFileName(userFile.getFileName(), userId) != null) {
                model.addAttribute("successfulResult", Boolean.FALSE);
                model.addAttribute("successfulResultInfo", "You can't upload the same file name");
            } else {
                this.fileService.addUserFile(userFile);
                model.addAttribute("successfulResult", Boolean.TRUE);
            }
        return "result";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(Authentication authentication, @PathVariable Integer fileId, Model model) {
        Integer userId = this.userService.getUserIdFromUserName(authentication.getName());
        Boolean updateResult = fileService.deleteFile(userId, fileId);
        model.addAttribute("successfulResult", updateResult);
        return "result";
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> getFileById(@PathVariable("fileId") Integer fileId, Authentication authentication) {
        Integer userId = this.userService.getUserIdFromUserName(authentication.getName());
        UserFile file = fileService.getFileById(fileId);
        if(file.getUserId().equals(userId)) {
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(file.getFileData());
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
