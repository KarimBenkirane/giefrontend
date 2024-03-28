package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Controllers.DTO.AdresseDTO;
import com.example.giefrontend1.Controllers.DTO.ContactDTO;
import com.example.giefrontend1.Parser.ParserContact;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateContactController implements Initializable {
    @FXML
    public TextField fName_tfld;
    @FXML
    public TextField lNAme_fld;
    @FXML
    public Label Error_lbl;
    @FXML
    public TextField adress_textfield;
    @FXML
    public TextField fax_textfield;
    @FXML
    public TextField phoneNumber_txtfield;
    @FXML
    public TextField email_txtfield;
    public TextField legalForm_txtField;
    public TextField CnameTxtField;
    public Button CreateClientParticular_btn;
    public Button CreateClientEnterprise_btn1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CreateClientParticular_btn.setOnAction(event -> createParticularContact());
        //CreateClientEnterprise_btn1.setOnAction(event -> createEnterpriseContact());
    }
    public void createParticularContact() {
        String firstName = fName_tfld.getText();
        String lastName = lNAme_fld.getText();
        String email = email_txtfield.getText();
        String phoneNumber = phoneNumber_txtfield.getText();
        String fax = fax_textfield.getText();
        String legalForm = legalForm_txtField.getText();
        String companyName = CnameTxtField.getText();
        String address = adress_textfield.getText();

        // Vérifier que les champs obligatoires sont remplis
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
            Error_lbl.setText("Veuillez remplir tous les champs obligatoires.");
            return;
        }

        AdresseDTO newAddress = new AdresseDTO(0, address, 0, "", 0, "", "");


        // Construire l'objet ContactDTO
        ContactDTO newContact;
        if (companyName.isEmpty()) {
            // Si c'est un particulier
            newContact = new ContactDTO(0, firstName + " " + lastName, email, phoneNumber, fax, fax, newAddress);
        } else {
            // Si c'est une entreprise
            newContact = new ContactDTO(0, companyName, email, legalForm, phoneNumber,fax,newAddress);
        }

        // Envoyer les données au backend pour créer le contact
        boolean success = ParserContact.createContact(newContact);
        System.out.println(success);

        // Afficher un message en cas de succès ou d'échec de la création
        if (success) {
            // Le contact a été créé avec succès
            Error_lbl.setText("Le contact a été créé avec succès.");
        } else {
            // Une erreur s'est produite lors de la création du contact
            Error_lbl.setText("Une erreur s'est produite lors de la création du contact.");
        }
    }
}
