package com.example.catclinic.services;

import android.content.Context;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionManager {


    SessionManager sessionManager;

    public EncryptionManager(Context context) {
        this.sessionManager = new SessionManager(context);
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


    public String encryptSessionKey(String sessionKey, String masterKey) throws Exception {
        return encryptBase64Data(sessionKey, masterKey);
    }

    public String decrpytSessionKey(String sessionKey, String masterKey) throws Exception{
        return decryptBase64Data(sessionKey, masterKey);
    }


    private static String compressString(String srcTxt) throws IOException {
        ByteArrayOutputStream rstBao = new ByteArrayOutputStream();
        GZIPOutputStream zos = new GZIPOutputStream(rstBao);
        zos.write(srcTxt.getBytes(StandardCharsets.UTF_8));
        IOUtils.closeQuietly(zos);

        byte[] bytes = rstBao.toByteArray();
        return Base64.encodeBase64String(bytes);
    }

    public String encryptData(String data, String sessionKey) throws Exception {
        String compressedData = compressString(data);
        return encryptBase64Data(compressedData, sessionKey);
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

    public String decryptData(String data, String sessionKey) throws Exception {
        String decrpytedData = decryptBase64Data(data, sessionKey);
        return uncompressString(decrpytedData);
    }
}

