package com.ps.put.od.cert.generator;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.*;

/**
 * Created by Kheldar on 2016-11-08.
 */
public class RSAHandler {

    public static final String ALGORITHM = "RSA";
    public static final String PRIV_KEY_FILE = "priv.key";
    public static final String PUB_KEY_FILE = "pub.key";
    private static int keysize=4096;

    public static KeyPair generateKey()
    {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);

            keyGen.initialize(keysize);
            final KeyPair key = keyGen.generateKeyPair();

            File privateKeyFile = new File(PRIV_KEY_FILE);
            File publicKeyFile = new File(PUB_KEY_FILE);

            // Create files to store public and private key
            if (privateKeyFile.getParentFile() != null) {
                privateKeyFile.getParentFile().mkdirs();
            }
            privateKeyFile.createNewFile();

            if (publicKeyFile.getParentFile() != null) {
                publicKeyFile.getParentFile().mkdirs();
            }
            publicKeyFile.createNewFile();

            // Saving the Public key in a file
            ObjectOutputStream publicKeyOS = new ObjectOutputStream(
                    new FileOutputStream(publicKeyFile));
            publicKeyOS.writeObject(key.getPublic());
            publicKeyOS.close();

            // Saving the Private key in a file
            ObjectOutputStream privateKeyOS = new ObjectOutputStream(
                    new FileOutputStream(privateKeyFile));
            privateKeyOS.writeObject(key.getPrivate());
            privateKeyOS.close();
            return key;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static boolean areKeysPresent() {

        File privateKey = new File(PRIV_KEY_FILE);
        File publicKey = new File(PUB_KEY_FILE);

        if (privateKey.exists() && publicKey.exists()) {
            return true;
        }
        return false;
    }

    public static byte[] encrypt(String text, PublicKey key)throws BadPaddingException,NoSuchPaddingException,NoSuchAlgorithmException,IllegalBlockSizeException,InvalidKeyException {
        byte[] cipherText = null;
             final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text.getBytes());

        return cipherText;
    }

    public static String decrypt(byte[] text, PrivateKey key) throws BadPaddingException,NoSuchPaddingException,NoSuchAlgorithmException,IllegalBlockSizeException,InvalidKeyException {
        byte[] dectyptedText = null;

            final Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, key);
            cipher.update(text);
            dectyptedText = cipher.doFinal();



        return new String(dectyptedText);
    }

}
