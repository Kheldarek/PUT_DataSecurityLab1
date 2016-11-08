package com.ps.put.od.cert;

import com.ps.put.od.cert.generator.CertificateUtils;
import com.ps.put.od.cert.generator.RSAHandler;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Main {

    public static void main(String[] args) {
        try {
            PrivateKey privateKey;
            PublicKey publicKey;
            if (RSAHandler.areKeysPresent()) {
                ObjectInputStream inputStream = null;
                inputStream = new ObjectInputStream(new FileInputStream(RSAHandler.PUB_KEY_FILE));
                publicKey = (PublicKey) inputStream.readObject();
                inputStream = new ObjectInputStream(new FileInputStream(RSAHandler.PRIV_KEY_FILE));
                privateKey = (PrivateKey) inputStream.readObject();
            }
            else
            {
                KeyPair pair= RSAHandler.generateKey();
                privateKey = pair.getPrivate();
                publicKey = pair.getPublic();
            }

            Certificate certificate = new Certificate("Piotr", "Sendrowski", "aaa", "777", "today", "tomorow");
            CertificateUtils cu = new CertificateUtils();
            String sha256 = cu.generateMessage(certificate);
            certificate.setSha256(cu.encrypt(cu.getHashCode(sha256),publicKey));

            cu.exportToXml(certificate, "cert.xml");
            Certificate newCert = cu.importFromXml("cert.xml");
            System.out.println(cu.validateCertificate(newCert,privateKey));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
