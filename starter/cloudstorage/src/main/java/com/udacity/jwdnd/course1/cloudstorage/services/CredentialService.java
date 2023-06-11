package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UserCredential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public void addCredential(UserCredential userCredential) {
        credentialMapper.addCredential(userCredential);
    }

    public List<UserCredential> getUserCredentialListByUserId(Integer userId){
        return credentialMapper.getCredentials(userId);
    }

    public Boolean deleteCredential(Integer userId, Integer credentialId) {
        if (credentialMapper.getCredential(credentialId) != null &&
                credentialMapper.getCredential(credentialId).getUserId().equals(userId)) {
            credentialMapper.deleteCredential(credentialId);
            return true;
        } else {
            return false;
        }
    }

    public void updateCredential(UserCredential userCredential) {
        credentialMapper.updateCredential(userCredential);
    }

}
