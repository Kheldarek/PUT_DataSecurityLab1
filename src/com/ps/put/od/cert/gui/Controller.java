package com.ps.put.od.cert.gui;


import com.ps.put.od.cert.Certificate;
import com.ps.put.od.cert.generator.CertificateUtils;
import com.ps.put.od.cert.generator.RSAHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Controller {


    @FXML
    TextField nameField;
    @FXML
    TextField surnameField;
    @FXML
    TextField mailField;
    @FXML
    TextField phoneField;
    @FXML
    TextField validToField;
    @FXML
    TextField validFromField;
    @FXML
    Button generateBtn;
    @FXML
    Button validateBtn;

    CertificateUtils cu;
    PrivateKey privateKey;
    PublicKey publicKey;

    public Controller()
    {
        cu = new CertificateUtils();

        try {
            if (RSAHandler.areKeysPresent()) {
                ObjectInputStream inputStream = null;
                inputStream = new ObjectInputStream(new FileInputStream(RSAHandler.PUB_KEY_FILE));
                publicKey = (PublicKey) inputStream.readObject();
                inputStream = new ObjectInputStream(new FileInputStream(RSAHandler.PRIV_KEY_FILE));
                privateKey = (PrivateKey) inputStream.readObject();
            } else {
                KeyPair pair = RSAHandler.generateKey();
                privateKey = pair.getPrivate();
                publicKey = pair.getPublic();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }


    public void generateCert()
    {
        try {


            Certificate certificate = new Certificate(nameField.getText(), surnameField.getText(), mailField.getText(), phoneField.getText(), validFromField.getText(), validToField.getText());
            String sha256 = cu.generateMessage(certificate);
            certificate.setSha256(cu.encrypt(cu.getHashCode(sha256),publicKey));

            cu.exportToXml(certificate, "cert.xml");
            showMessage(Alert.AlertType.CONFIRMATION,"Generator","Certificate generated successfully","SUCCESS");

        } catch (Exception e) {
            System.err.println(e.getMessage());
            showMessage(Alert.AlertType.ERROR,"Generator","Error: " + e.getMessage(),"GENERATION FAILED");
        }
    }

    public void validateCertificate()
    {
       try {
           Certificate newCert = cu.importFromXml("cert.xml");
           if(cu.validateCertificate(newCert,privateKey))
           showMessage(Alert.AlertType.CONFIRMATION,"Validator","Certificate is valid","SUCCESS");
           else
               showMessage(Alert.AlertType.ERROR,"Validator","Certificate is invalid","VALIDATION FAILED");

       }
       catch (Exception e)
       {
           showMessage(Alert.AlertType.ERROR,"Validator","Error: " + e.getMessage(),"VALIDATION FAILED");

       }
    }

    public void showMessage(Alert.AlertType type, String title, String content, String header)
    {
        Alert alert = new Alert(type);
        alert.setContentText(content);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }


}
