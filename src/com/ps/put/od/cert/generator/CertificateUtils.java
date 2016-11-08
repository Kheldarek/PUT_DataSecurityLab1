package com.ps.put.od.cert.generator;

import com.ps.put.od.cert.Certificate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.security.*;
import java.util.Base64;

/**
 * Created by Kheldar on 2016-11-08.
 */
public class CertificateUtils {

    public String getHashCode(String message)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(message.getBytes());
            String hash = byte2Hex(md.digest());

            md.reset();
            return hash;

        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public String byte2Hex(byte[] bytes)
    {
        return Base64.getEncoder().encodeToString(bytes);//String.format("%064x", new java.math.BigInteger(1, bytes));
    }

    public String generateMessage(Certificate certificate)
    {
        return certificate.getMail() + certificate.getName() + certificate.getPhone() + certificate.getPublisher() + certificate.getSurname() + certificate.getValidFrom()
                + certificate.getValidTo() + certificate.getVersion();
    }

    public void exportToXml(Certificate certificate, String fileName) throws IOException
    {
        XMLEncoder e = new XMLEncoder(
                new BufferedOutputStream(
                        new FileOutputStream(fileName)));
        e.writeObject(certificate);
        e.close();
    }

    public Certificate importFromXml(String fileName) throws IOException
    {
        XMLDecoder d = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(fileName)));
        return (Certificate)d.readObject();
    }

    public boolean validateCertificate(Certificate certificate, PrivateKey privateKey) throws BadPaddingException,NoSuchPaddingException,NoSuchAlgorithmException,IllegalBlockSizeException,InvalidKeyException
    {
        String sha256Org =decrypt(certificate.getSha256(),privateKey);
        String sha256New = getHashCode(generateMessage(certificate));

        return sha256New.equals(sha256Org);

    }

    public String encrypt(String message,PublicKey publicKey) throws BadPaddingException,NoSuchPaddingException,NoSuchAlgorithmException,IllegalBlockSizeException,InvalidKeyException
    {
        byte[] bytes = RSAHandler.encrypt(message,publicKey);
        return byte2Hex(bytes);
    }

    public String decrypt(String message, PrivateKey privateKey) throws BadPaddingException,NoSuchPaddingException,NoSuchAlgorithmException,IllegalBlockSizeException,InvalidKeyException
    {
        return  RSAHandler.decrypt(Base64.getDecoder().decode(message), privateKey);
    }
}
