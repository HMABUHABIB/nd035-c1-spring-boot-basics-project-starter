package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UserFile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public void addUserFile(UserFile userFile) {
        fileMapper.addFile(userFile);
    }

    public UserFile gerUserFileByFileName(String fileName, Integer userId) {
        return fileMapper.getFileByFileName(fileName, userId);
    }

    public List<UserFile> getUserFileList (Integer userId) {
        return fileMapper.getFiles(userId);
    }

    public UserFile getFileById(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    public Boolean deleteFile(Integer userId, Integer fileId) {
        if (fileMapper.getFile(fileId) != null &&
                fileMapper.getFile(fileId).getUserId().equals(userId)) {
            fileMapper.deleteFile(fileId);
            return true;
        } else {
            return false;
        }
    }

    public UserFile createNewUserFile(MultipartFile multipartFile, Integer userId) {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        UserFile userFile = null;
        try {
            userFile = new UserFile(
                    null,
                    fileName,
                    multipartFile.getContentType(),
                    String.valueOf(multipartFile.getSize()),
                    userId,
                    multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userFile;
    }

}
