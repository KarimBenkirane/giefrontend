package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Controllers.DTO.ContactDTO;
import com.example.giefrontend1.Parser.ParserContact;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchResultController implements Initializable {

    @FXML
    public AnchorPane contactsAnchorPane;
    @FXML
    public AnchorPane contactsSideBarAnchorPane;
    @FXML
    public Button modifierContactBtn;
    @FXML
    public Button supprimerContactBtn;

    @FXML
    public Label resultAdresseLabel;

    @FXML
    public Label resultEmailLabel;

    @FXML
    public Label resultFaxLabel;

    @FXML
    public Label resultNomLabel;

    @FXML
    public Label resultPrenomLabel;

    @FXML
    public Label resultTelLabel;

    @FXML
    public Label resultIDLabel;

    @FXML
    public TableView<ContactDTO> contactsTableView;

    @FXML
    public TableColumn<ContactDTO, String> formeJuridiqueColumn;

    @FXML
    public TableColumn<ContactDTO, String> raisonSocialeColumn;

    @FXML
    public TableColumn<ContactDTO, Integer> codePostalColumn;

    @FXML
    public TableColumn<ContactDTO, String> emailColumn;

    @FXML
    public TableColumn<ContactDTO, String> faxColumn;

    @FXML
    public TableColumn<ContactDTO, Integer> idAddresseColumn;

    @FXML
    public TableColumn<ContactDTO, String> nomColumn;

    @FXML
    public TableColumn<ContactDTO, Integer> numRueColumn;

    @FXML
    public TableColumn<ContactDTO, String> paysColumn;

    @FXML
    public TableColumn<ContactDTO, String> prenomColumn;

    @FXML
    public TableColumn<ContactDTO, String> quartierColumn;

    @FXML
    public TableColumn<ContactDTO, String> rueColumn;

    @FXML
    public TableColumn<ContactDTO, String> telColumn;

    @FXML
    public TableColumn<ContactDTO, String> villeColumn;

    @FXML
    public TableColumn<ContactDTO, Integer> idColumn;

    int contactId;
    String type;

    public SearchResultController(){

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.contactsSideBarAnchorPane.setVisible(false);
        contactsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int id = newValue.getId();
                this.contactId = id;
                String email = newValue.getEmail();
                String telephone = newValue.getTelephone();
                String fax = newValue.getFax();
                String adresseComplete = "";


                if(newValue.getAdresse().getRue() != null) {
                    adresseComplete += newValue.getAdresse().getRue() + " ";
                }

                if(newValue.getAdresse().getNumeroRue() != -1) {
                    adresseComplete += newValue.getAdresse().getNumeroRue() + " ";
                }

                if(newValue.getAdresse().getQuartier() != null) {
                    adresseComplete += newValue.getAdresse().getQuartier() + " ";
                }

                if(newValue.getAdresse().getCodePostal() != -1) {
                    adresseComplete += newValue.getAdresse().getCodePostal() + " ";
                }

                if(newValue.getAdresse().getVille() != null) {
                    adresseComplete += newValue.getAdresse().getVille() + " ";
                }

                if(newValue.getAdresse().getPays() != null) {
                    adresseComplete += newValue.getAdresse().getPays();
                }


                if(newValue.getRaisonSociale() == null){
                    //Particulier
                    String nom = newValue.getNom();
                    String prenom = newValue.getPrenom();
                    this.type = "Particulier";

                    this.resultNomLabel.setText(nom);
                    this.resultPrenomLabel.setText(prenom);
                    this.resultTelLabel.setText(telephone);
                    this.resultFaxLabel.setText(fax);
                    this.resultAdresseLabel.setText(adresseComplete);
                    this.resultEmailLabel.setText(email);
                    this.resultIDLabel.setText(String.valueOf(id));

                    this.contactsSideBarAnchorPane.setVisible(true);
                }
                else{
                    //Entreprise
                    this.type = "Entreprise";
                    String raisonSociale = newValue.getRaisonSociale();
                    String formeJuridique = newValue.getFormeJuridique();

                    this.resultNomLabel.setText(raisonSociale);
                    this.resultPrenomLabel.setText(formeJuridique);
                    this.resultTelLabel.setText(telephone);
                    this.resultFaxLabel.setText(fax);
                    this.resultAdresseLabel.setText(adresseComplete);
                    this.resultEmailLabel.setText(email);
                    this.resultIDLabel.setText(String.valueOf(id));
                    this.contactsSideBarAnchorPane.setVisible(true);
                }

            }
        });
    }


    public void onDeleteBtnClick(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment supprimer ce contact ?");

        // Customize the buttons in the confirmation dialog
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        // Show the confirmation dialog and wait for the user's response
        ButtonType result = alert.showAndWait().orElse(ButtonType.NO);

        // If the user clicks YES, proceed with the deletion
        if (result == ButtonType.YES) {
            boolean success = ParserContact.deleteContact(contactId);
            if (success) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Succès");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Le contact a été supprimé avec succès !");
                successAlert.showAndWait();

                this.contactsTableView.refresh();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Erreur lors de la suppression du contact.");
                errorAlert.showAndWait();
            }
        }

    }


    public void onUpdateBtnClick(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.giefrontend1/Admin/UpdateClient.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        UpdateContactController updateController = loader.getController();
        if(this.type.equals("Particulier")){
            updateController.typeContactChoiceBox.setValue("Particulier");
            updateController.typeContactChoiceBox.setDisable(true);
            updateController.idTextField.setText(String.valueOf(contactId));
            updateController.idTextField.setDisable(true);
        }
        else if(this.type.equals("Entreprise")){
            updateController.typeContactChoiceBox.setValue("Entreprise");
            updateController.typeContactChoiceBox.setDisable(true);
            updateController.idTextField.setText(String.valueOf(contactId));
            updateController.idTextField.setDisable(true);
        }

        this.contactsTableView.refresh();

    }




}
