package com.example.catclinic.services;

import android.content.Context;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class EncryptionManager {


    SessionManager sessionManager;

    public EncryptionManager(Context context) {
        this.sessionManager = new SessionManager(context);
    }

    private static String compressString(String srcTxt) throws IOException {
        ByteArrayOutputStream rstBao = new ByteArrayOutputStream();
        GZIPOutputStream zos = new GZIPOutputStream(rstBao);
        zos.write(srcTxt.getBytes(StandardCharsets.UTF_8));
        IOUtils.closeQuietly(zos);

        byte[] bytes = rstBao.toByteArray();
        return Base64.encodeBase64String(bytes);
    }

    public String encryptData(String data) throws IOException {
        return compressString(data);
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

    public String decryptData(String data) throws IOException {
        return uncompressString(data);
    }
}

