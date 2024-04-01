package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Controllers.DTO.AdresseDTO;
import com.example.giefrontend1.Controllers.DTO.ContactDTO;
import com.example.giefrontend1.Parser.ParserContact;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateContactController implements Initializable {
    @FXML
    public TextField prenomTextField;

    @FXML
    public TextField nomTextField;

    @FXML
    public TextField emailTextField;

    @FXML
    public TextField phoneTextField;

    @FXML
    public TextField faxTextField;

    @FXML
    public TextField quartierTextField;

    @FXML
    public TextField rueTextField;

    @FXML
    public TextField numRueTextField;

    @FXML
    public TextField codePostalTextField;

    @FXML
    public TextField villeTextField;

    @FXML
    public TextField paysTextField;

    @FXML
    public Label statusLabel;

    @FXML
    public Button CreateContactBtn;

    @FXML
    public ChoiceBox<String> typeContactChoiceBox;

    @FXML
    public Label nomLabel; // deviendra raisonSociale pour l'entreprise

    @FXML
    public Label prenomLabel; // deviendra formeJuridique pour l'entreprise


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeContactChoiceBox.getItems().add("Particulier");
        typeContactChoiceBox.getItems().add("Entreprise");
        typeContactChoiceBox.setValue("Particulier");

        // Add a listener to the ChoiceBox value property
        typeContactChoiceBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.equals("Entreprise")){
                    nomLabel.setText("Raison Sociale");
                    prenomLabel.setText("Forme Juridique");
                } else {
                    nomLabel.setText("Nom");
                    prenomLabel.setText("Prénom");
                }
            }
        });

        CreateContactBtn.setOnAction(event -> createContact());
    }

    public void createContact() {

        String nom = null;
        String prenom = null;
        String email = null;
        String telephone = null;
        String fax = null;


        String quartier = null;
        String rue = null;
        int numeroRue = -1;
        int codePostal = -1;
        String ville = null;
        String pays = null;


        if(!nomTextField.getText().isEmpty())
            nom = nomTextField.getText();

        if(!prenomTextField.getText().isEmpty())
            prenom = prenomTextField.getText();

        if(!emailTextField.getText().isEmpty())
            email = emailTextField.getText();

        if(!phoneTextField.getText().isEmpty())
            telephone = phoneTextField.getText();

        if(!faxTextField.getText().isEmpty())
            fax = faxTextField.getText();



        if(!quartierTextField.getText().isEmpty())
            quartier = quartierTextField.getText();

        if(!rueTextField.getText().isEmpty())
            rue = rueTextField.getText();

        if(!numRueTextField.getText().isEmpty())
            numeroRue = Integer.parseInt(numRueTextField.getText());

        if(!codePostalTextField.getText().isEmpty())
            codePostal = Integer.parseInt(codePostalTextField.getText());

        if(!villeTextField.getText().isEmpty())
            ville = villeTextField.getText();

        if(!paysTextField.getText().isEmpty())
            pays = paysTextField.getText();

        AdresseDTO adresseDTO = new AdresseDTO(rue,numeroRue,quartier,codePostal,ville,pays);
        if(typeContactChoiceBox.getValue().equals("Particulier")){
            ContactDTO contactDTO = new ContactDTO(prenom,nom,email,telephone,fax,adresseDTO);
            boolean status = ParserContact.createParticulier(contactDTO);
            if(status)
                statusLabel.setText("Contact crée avec succès!");
            else{
                statusLabel.setText("Erreur lors de la création du contact");
            }
        }
        else{
            //nom et prénom deviennent raisonSociale et formeJuridique respectivement
            ContactDTO contactDTO = new ContactDTO(email,telephone,fax,adresseDTO,prenom,nom);
            boolean status = ParserContact.createEntreprise(contactDTO); // méthode createEntreprise à ajouter
            if(status)
                statusLabel.setText("Contact crée avec succès!");
            else{
                statusLabel.setText("Erreur lors de la création du contact");
            }
        }




    }
}
