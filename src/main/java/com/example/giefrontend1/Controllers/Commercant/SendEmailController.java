package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Parser.ParserContact;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.net.URL;
import java.util.ResourceBundle;

public class SendEmailController implements Initializable {

    @FXML
    public TextField recipientTextField;
    @FXML
    public TextField subjectTextField;
    @FXML
    public TextArea contentTextArea;
    @FXML
    public Button sendEmailBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onButtonClick(ActionEvent e){

        String recipient = recipientTextField.getText();
        String subject = subjectTextField.getText();
        String content = contentTextArea.getText();

        if(recipient.isEmpty() || subject.isEmpty() || content.isEmpty()){
            showAlert(AlertType.WARNING,"Erreur","Veuillez saisir tous les champs !");
            return;
        }

        String emailJson = "{"
                + "\"recipient\": \"" + recipient + "\","
                + "\"subject\": \"" + subject + "\","
                + "\"content\": \"" + content + "\""
                + "}";

        showAlert(AlertType.INFORMATION,"Envoi en cours","L'email est en cours d'envoi, veuillez patienter");
        boolean status = ParserContact.sendMail(emailJson);

        if(status){
            showAlert(AlertType.INFORMATION,"Succès","Email envoyé avec succès !");
        }
        else{
            showAlert(AlertType.ERROR,"Erreur","Une erreur s'est produite lors de l'envoi de l'email.");
        }

    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




}