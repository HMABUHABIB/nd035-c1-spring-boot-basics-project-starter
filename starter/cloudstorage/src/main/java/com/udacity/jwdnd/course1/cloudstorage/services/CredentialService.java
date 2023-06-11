package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UserCredential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public void addCredential(UserCredential userCredential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        userCredential.setKey(encodedKey);
        userCredential.setPassword(getEncryptedData(userCredential.getPassword(), encodedKey));
        credentialMapper.addCredential(userCredential);
    }

    public List<UserCredential> getUserCredentialListByUserId(Integer userId){
        List<UserCredential> userCredentialList = credentialMapper.getCredentials(userId);
        for (UserCredential userCredential : userCredentialList) {
            userCredential.setDecryptedPassword(getDecryptedData(userCredential.getPassword(), userCredential.getKey()));
        }
        return userCredentialList;
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
        String encryptedPassword = getEncryptedData(userCredential.getPassword(), userCredential.getKey());
        userCredential.setPassword(encryptedPassword);
        credentialMapper.updateCredential(userCredential);
    }

    private String getEncryptedData(String data, String encodedKey) {
        return encryptionService.encryptValue(data, encodedKey);
    }

    private String getDecryptedData(String data, String encodedKey) {
        return encryptionService.decryptValue(data, encodedKey);
    }

}
