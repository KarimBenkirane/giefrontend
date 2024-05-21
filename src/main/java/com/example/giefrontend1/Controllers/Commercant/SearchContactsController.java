package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Controllers.DTO.ContactDTO;
import com.example.giefrontend1.Parser.ParserContact;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchContactsController implements Initializable {

    public ToggleGroup typeClientGroup;
    private Stage openedResultStage;

    @FXML
    public RadioButton particuliersRadioBtn;
    @FXML
    public RadioButton entreprisesRadioBtn;
    @FXML
    public RadioButton allRadioBtn;
    @FXML
    public Label nomLabel;
    @FXML
    public Label prenomLabel;
    @FXML
    public Label raisonSocialeLabel;
    @FXML
    public Label formeJuridiqueLabel;
    @FXML
    public Button rechercherBtn;
    @FXML
    public TextField emailTextField;
    @FXML
    public TextField nomTextField;
    @FXML
    public TextField prenomTextField;
    @FXML
    public TextField raisonSocialeTextField;
    @FXML
    public ComboBox<String> formeJuridiqueComboBox;


    public static String searchType;
    public static String searchEmail;
    public static String searchNom;
    public static String searchPrenom;
    public static String searchRaisonSociale;
    public static String searchFormeJuridique;


    public SearchContactsController() {

    }



    @FXML
    public void radioBtnMethod(ActionEvent e) throws Exception {

        if (entreprisesRadioBtn.isSelected()) {
            this.raisonSocialeLabel.setVisible(true);
            this.formeJuridiqueLabel.setVisible(true);
            this.raisonSocialeTextField.setVisible(true);
            this.formeJuridiqueComboBox.setVisible(true);

            this.nomTextField.setVisible(false);
            this.nomLabel.setVisible(false);
            this.prenomLabel.setVisible(false);
            this.prenomTextField.setVisible(false);


        }

        else if (particuliersRadioBtn.isSelected()){
            this.raisonSocialeLabel.setVisible(false);
            this.formeJuridiqueLabel.setVisible(false);
            this.raisonSocialeTextField.setVisible(false);
            this.formeJuridiqueComboBox.setVisible(false);

            this.nomTextField.setVisible(true);
            this.nomLabel.setVisible(true);
            this.prenomLabel.setVisible(true);
            this.prenomTextField.setVisible(true);


        }

        else if(allRadioBtn.isSelected()){
            this.raisonSocialeLabel.setVisible(false);
            this.formeJuridiqueLabel.setVisible(false);
            this.raisonSocialeTextField.setVisible(false);
            this.formeJuridiqueComboBox.setVisible(false);

            this.nomTextField.setVisible(false);
            this.nomLabel.setVisible(false);
            this.prenomLabel.setVisible(false);
            this.prenomTextField.setVisible(false);
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        formeJuridiqueComboBox.getItems().addAll("Tout chercher","SARL","SA","SAS");

        addListenerToTextField(nomTextField);
        addListenerToTextField(prenomTextField);
        addListenerToTextField(raisonSocialeTextField);
        addListenerToTextField(emailTextField);
        addListenerToComboBox(formeJuridiqueComboBox);

    }


    public void onButtonClick(ActionEvent e) throws Exception{
        if(this.openedResultStage != null){
            this.openedResultStage.close();
        }
        FXMLLoader loader = new FXMLLoader(SearchContactsController.class.getResource("/com.example.giefrontend1/Admin/SearchResults.fxml"));
        Parent root = loader.load();
        SearchResultController resultController = loader.getController();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        stage.setScene(new Scene(root));
        stage.setTitle("Résultat de la recherche");
        stage.setMaximized(false);
        stage.show();
        this.openedResultStage = stage;

        List<ContactDTO> contacts = new ArrayList<>();
        String email = this.emailTextField.getText();
        searchEmail = email;

        if(!allRadioBtn.isSelected()){

            if(particuliersRadioBtn.isSelected()) {
                String nom = this.nomTextField.getText();
                String prenom = this.prenomTextField.getText();

                searchNom = nom;
                searchPrenom = prenom;

                if (!nom.isEmpty()) {
                    contacts = ParserContact.getParticuliersByNom(nom);
                    searchType = "particuliersByNom";
                }
                if (!prenom.isEmpty()) {
                    contacts = ParserContact.getParticuliersByPrenom(prenom);
                    searchType = "particuliersByPrenom";
                }
                if(!email.isEmpty()){
                    contacts = ParserContact.getParticuliersByEmail(email);
                    searchType = "particuliersByEmail";
                }

                if(nom.isEmpty() && prenom.isEmpty() && email.isEmpty()){
                    contacts = ParserContact.getAllParticuliers();
                    searchType = "particuliersAll";
                }
            }

            if(entreprisesRadioBtn.isSelected()){
                String raisonSociale = this.raisonSocialeTextField.getText();
                String formeJuridique = this.formeJuridiqueComboBox.getValue();
                if(formeJuridique.equals("Tout chercher")){
                    formeJuridique = "";
                }

                searchRaisonSociale = raisonSociale;
                searchFormeJuridique = formeJuridique;

                if(!email.isEmpty()){
                    contacts = ParserContact.getEntreprisesByEmail(email);
                    searchType = "entreprisesByEmail";
                }
                if(!raisonSociale.isEmpty()){
                    contacts = ParserContact.getEntrepriseByRaisonSociale(raisonSociale);
                    searchType = "entreprisesByRaisonSociale";

                }
                if(!formeJuridique.isEmpty()){
                    contacts = ParserContact.getEntrepriseByFormeJuridique(formeJuridique);
                    searchType = "entreprisesByFormeJuridique";
                }
                if(formeJuridique.isEmpty() && raisonSociale.isEmpty() && email.isEmpty()){
                    contacts = ParserContact.getAllEntreprises();
                    searchType = "entreprisesAll";
                }
            }

        }
        else{
            if(email.isEmpty()){
                contacts = ParserContact.getAllContacts();
                searchType = "contactsAll";
            }
            else{
                contacts = ParserContact.getContactsByEmail(email);
                searchType = "contactsByEmail";
            }
        }

        System.out.println(contacts);

        if(contacts == null){
            openedResultStage.close();
            showAlert(Alert.AlertType.ERROR,"Erreur","Une erreur s'est produite !");
        }
        else if(contacts.isEmpty()){
            openedResultStage.close();
            showAlert(Alert.AlertType.INFORMATION,"Aucun contact trouvé","Aucun contact trouvé !");
        }
        else{
             ObservableList<ContactDTO> contactList = FXCollections.observableArrayList();
            contactList.addAll(contacts);


            resultController.idColumn.setCellValueFactory(new PropertyValueFactory<ContactDTO,Integer>("id"));
            resultController.nomColumn.setCellValueFactory(new PropertyValueFactory<ContactDTO,String >("nom"));
            resultController.prenomColumn.setCellValueFactory(new PropertyValueFactory<ContactDTO,String >("prenom"));
            resultController.raisonSocialeColumn.setCellValueFactory(new PropertyValueFactory<ContactDTO,String >("raisonSociale"));
            resultController.formeJuridiqueColumn.setCellValueFactory(new PropertyValueFactory<ContactDTO,String >("formeJuridique"));
            resultController.emailColumn.setCellValueFactory(new PropertyValueFactory<ContactDTO,String >("email"));
            resultController.telColumn.setCellValueFactory(new PropertyValueFactory<ContactDTO,String >("telephone"));
            resultController.faxColumn.setCellValueFactory(new PropertyValueFactory<ContactDTO,String >("fax"));
            resultController.idAddresseColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAdresse().getId()).asObject());
            resultController.rueColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse().getRue()));
            resultController.numRueColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAdresse().getNumeroRue()).asObject());
            resultController.codePostalColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAdresse().getCodePostal()).asObject());
            resultController.quartierColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse().getQuartier()));
            resultController.villeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse().getVille()));
            resultController.paysColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse().getPays()));


            resultController.contactsTableView.setItems(contactList);
        }



    }



    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void addListenerToComboBox(ComboBox<String> comboBox) {
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty() && !"Tout chercher".equals(newValue)) {
                disableAllControlsExcept(comboBox);
            } else {
                enableAllControls();
            }
        });
    }

    private void addListenerToTextField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                disableAllControlsExcept(textField);
            } else {
                enableAllControls();
            }
        });
    }


    private void disableAllControlsExcept(ComboBox<?> exceptComboBox) {
        nomTextField.setDisable(true);
        prenomTextField.setDisable(true);
        raisonSocialeTextField.setDisable(true);
        emailTextField.setDisable(true);
        formeJuridiqueComboBox.setDisable(true);

        exceptComboBox.setDisable(false);
    }

    private void disableAllControlsExcept(TextField exceptTextField) {
        nomTextField.setDisable(true);
        prenomTextField.setDisable(true);
        raisonSocialeTextField.setDisable(true);
        emailTextField.setDisable(true);
        formeJuridiqueComboBox.setDisable(true);

        exceptTextField.setDisable(false);
    }

    private void enableAllControls() {
        nomTextField.setDisable(false);
        prenomTextField.setDisable(false);
        raisonSocialeTextField.setDisable(false);
        emailTextField.setDisable(false);
        formeJuridiqueComboBox.setDisable(false);
    }














}


















