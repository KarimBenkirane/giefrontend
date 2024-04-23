package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Controllers.DTO.AdresseDTO;
import com.example.giefrontend1.Controllers.DTO.ContactDTO;
import com.example.giefrontend1.Parser.ParserContact;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UpdateContactController implements Initializable {
    @FXML
    public TextField idTextField;

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
    public Button UpdateContactBtn;

    @FXML
    public ChoiceBox<String> typeContactChoiceBox;

    @FXML
    public Label nomLabel; // deviendra raisonSociale pour l'entreprise

    @FXML
    public Label prenomLabel; // deviendra formeJuridique pour l'entreprise

    public ObservableList<ContactDTO> contactDTOObservableList;


    public UpdateContactController() {
    }

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
                    nomLabel.setText("Raison Sociale:");
                    prenomLabel.setText("Forme Juridique:");
                } else {
                    nomLabel.setText("Nom:");
                    prenomLabel.setText("Prénom:");
                }
            }
        });

        UpdateContactBtn.setOnAction(event -> updateContact());
    }

    public void updateContact() {

        if(idTextField.getText().isEmpty()){
            statusLabel.setText("Veuillez fournir un ID !");
            return;
        }
        int id = Integer.parseInt(idTextField.getText());
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


        if(!(nomTextField.getText().isEmpty()))
            nom = nomTextField.getText();

        if(!(prenomTextField.getText().isEmpty()))
            prenom = prenomTextField.getText();

        if(!(emailTextField.getText().isEmpty()))
            email = emailTextField.getText();

        if(!(phoneTextField.getText().isEmpty()))
            telephone = phoneTextField.getText();

        if(!(faxTextField.getText().isEmpty()))
            fax = faxTextField.getText();



        if(!(quartierTextField.getText().isEmpty()))
            quartier = quartierTextField.getText();

        if(!(rueTextField.getText().isEmpty()))
            rue = rueTextField.getText();

        if(!(numRueTextField.getText().isEmpty()))
            try{
                numeroRue = Integer.parseInt(numRueTextField.getText());
            }catch (NumberFormatException e){
                showAlert(AlertType.ERROR,"Erreur","Veuillez saisir un numéro de rue valide !");
                return;
            }

        if(!(codePostalTextField.getText().isEmpty()))
            try{
                codePostal = Integer.parseInt(codePostalTextField.getText());
            }catch (NumberFormatException e){
                showAlert(AlertType.ERROR,"Erreur","Veuillez saisir un code postal valide !");
                return;
            }

        if(!(villeTextField.getText().isEmpty()))
            ville = villeTextField.getText();

        if(!(paysTextField.getText().isEmpty()))
            pays = paysTextField.getText();

        AdresseDTO adresseDTO = new AdresseDTO(rue,numeroRue,quartier,codePostal,ville,pays);
        if(typeContactChoiceBox.getValue().equals("Particulier")){
            ContactDTO contactDTO = new ContactDTO(id,prenom,nom,email,telephone,fax,adresseDTO);
            System.out.println(contactDTO);
            boolean status = ParserContact.updateParticulier(contactDTO);
            if(status) {
                if (contactDTOObservableList != null) {
                    contactDTOObservableList.setAll(SearchResultController.getUpdatedContactList());
                }
                showAlert(AlertType.INFORMATION, "Succès", "Contact modifié avec succès!");
            }
            else{
                showAlert(AlertType.ERROR, "Erreur", "Erreur lors de la modification du contact");
            }
        }
        else{
            //nom et prénom deviennent raisonSociale et formeJuridique respectivement
            ContactDTO contactDTO = new ContactDTO(id,email,telephone,fax,adresseDTO,prenom,nom);
            System.out.println(contactDTO);
            boolean status = ParserContact.updateEntreprise(contactDTO);
            if(status) {
                if (contactDTOObservableList != null) {
                    contactDTOObservableList.setAll(SearchResultController.getUpdatedContactList());
                }
                showAlert(AlertType.INFORMATION, "Succès", "Contact modifié avec succès!");
            }
            else{
                showAlert(AlertType.ERROR, "Erreur", "Erreur lors de la modification du contact");
            }
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
