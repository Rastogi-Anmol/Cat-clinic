package com.example.catclinic.services;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class keyGenerationManager {


    //standard SHA-256 code taken from
    //https://medium.com/@AlexanderObregon/what-is-sha-256-hashing-in-java-0d46dfb83888
    //generates a SHA-256 encoded password
    public static String generateHashedPassword(String password){

        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();

            for(byte b : encodedHash){
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateSalt()
    {
        return generateRandomKey(16);
    }

    public static String generateSessionKey()
    {
        return generateRandomKey(32);
    }



    private static String generateRandomKey(int numBytes) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[numBytes];
        secureRandom.nextBytes(key);  // Fills with secure random values
        return Base64.encodeBase64String(key);
    }


    public static String generateMasterKey(String password, String saltString) throws Exception {
        byte[] saltBytes = saltString.getBytes(StandardCharsets.UTF_8); // or Base64-decode if needed

        int iterations = 100_000;
        int keyLength = 256;

        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        byte[] masterKeyBytes = factory.generateSecret(spec).getEncoded();
        return Base64.encodeBase64String(masterKeyBytes);
    }
}
