package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.UserFile;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<UserFile> getFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    UserFile getFile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName} and userid = #{userId}")
    UserFile getFileByFileName(String fileName, Integer userId);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    void deleteFile(Integer fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize} , #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(UserFile userFile);

}
