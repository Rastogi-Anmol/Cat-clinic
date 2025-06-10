package com.example.catclinic.services;

import android.content.Context;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionManager {


    SessionManager sessionManager;

    public EncryptionManager(Context context) {
        this.sessionManager = new SessionManager(context);
    }

    public String encryptSessionKey(String sessionKey) throws Exception {
        String masterKey = this.sessionManager.getEncryptionKey();
        return encryptBase64Data(sessionKey, masterKey);
    }

    public String decryptSessionKey(String sessionKey) throws Exception{
        String masterKey = this.sessionManager.getEncryptionKey();
        return decryptBase64Data(sessionKey, masterKey);
    }

    public String encryptData(String data, String sessionKey) throws Exception {
        String compressedData = compressString(data);
        return encryptBase64Data(compressedData, sessionKey);
    }

    public String decryptData(String data, String sessionKey) throws Exception {
        String decryptedData = decryptBase64Data(data, sessionKey);
        return uncompressString(decryptedData);
    }

    public static String generateSalt()
    {
        return generateRandomKey(16);
    }

    public static String generateSessionKey()
    {
        return generateRandomKey(32);
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

    private static String generateRandomKey(int numBytes) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[numBytes];
        secureRandom.nextBytes(key);  // Fills with secure random values
        return Base64.encodeBase64String(key);
    }

    private static String encryptBase64Data(String base64PlainText, String base64Key) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(base64Key);
        byte[] plainBytes = Base64.decodeBase64(base64PlainText);

        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);

        byte[] ciphertext = cipher.doFinal(plainBytes);

        byte[] combined = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(ciphertext, 0, combined, iv.length, ciphertext.length);

        return Base64.encodeBase64String(combined);
    }

    private static String decryptBase64Data(String base64Combined, String base64Key) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(base64Key);
        byte[] combined = Base64.decodeBase64(base64Combined);

        byte[] iv = new byte[12];
        byte[] ciphertext = new byte[combined.length - 12];
        System.arraycopy(combined, 0, iv, 0, 12);
        System.arraycopy(combined, 12, ciphertext, 0, ciphertext.length);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);

        byte[] decrypted = cipher.doFinal(ciphertext);
        return Base64.encodeBase64String(decrypted);
    }

    private static String compressString(String srcTxt) throws IOException {
        ByteArrayOutputStream rstBao = new ByteArrayOutputStream();
        GZIPOutputStream zos = new GZIPOutputStream(rstBao);
        zos.write(srcTxt.getBytes(StandardCharsets.UTF_8));
        IOUtils.closeQuietly(zos);

        byte[] bytes = rstBao.toByteArray();
        return Base64.encodeBase64String(bytes);
    }

    private static String uncompressString(String zippedBase64Str) throws IOException {
        byte[] bytes = Base64.decodeBase64(zippedBase64Str);
        GZIPInputStream zi = null;
        String result;
        try {
            zi = new GZIPInputStream(new ByteArrayInputStream(bytes));
            result = IOUtils.toString(zi, StandardCharsets.UTF_8);
        } finally {
            IOUtils.closeQuietly(zi);
        }
        return result;
    }



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


}

