package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Parser.ParserContact;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteContact implements Initializable {
    public ListView dleteContact_listView;
    public Button deleteContact_btn;
    public TextField dleteContact_txtField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deleteContact_btn.setOnAction(event -> {
            int contactId;
            try {
                contactId = Integer.parseInt(dleteContact_txtField.getText());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un identifiant de contact valide.");
                return;
            }

            boolean success = ParserContact.deleteContact(contactId);
            if (success) {
                System.out.println("Contact supprimé avec succès!");
            } else {
                System.out.println("Échec de la suppression du contact.");
            }
        });
    }
}


