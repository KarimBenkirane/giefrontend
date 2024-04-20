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
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchContactsController implements Initializable {

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
    public TextField formeJuridiqueTextField;


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
            this.formeJuridiqueTextField.setVisible(true);

            this.nomTextField.setVisible(false);
            this.nomLabel.setVisible(false);
            this.prenomLabel.setVisible(false);
            this.prenomTextField.setVisible(false);


        }

        else if (particuliersRadioBtn.isSelected()){
            this.raisonSocialeLabel.setVisible(false);
            this.formeJuridiqueLabel.setVisible(false);
            this.raisonSocialeTextField.setVisible(false);
            this.formeJuridiqueTextField.setVisible(false);

            this.nomTextField.setVisible(true);
            this.nomLabel.setVisible(true);
            this.prenomLabel.setVisible(true);
            this.prenomTextField.setVisible(true);


        }

        else if(allRadioBtn.isSelected()){
            this.raisonSocialeLabel.setVisible(false);
            this.formeJuridiqueLabel.setVisible(false);
            this.raisonSocialeTextField.setVisible(false);
            this.formeJuridiqueTextField.setVisible(false);

            this.nomTextField.setVisible(false);
            this.nomLabel.setVisible(false);
            this.prenomLabel.setVisible(false);
            this.prenomTextField.setVisible(false);
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void onButtonClick(ActionEvent e) throws Exception{
        if(this.openedResultStage != null){
            this.openedResultStage.close();
        }
        FXMLLoader loader = new FXMLLoader(SearchContactsController.class.getResource("/com.example.giefrontend1/Admin/SearchResults.fxml"));
        Parent root = loader.load();
        SearchResultController resultController = loader.getController();
        Stage stage = new Stage();
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
                String formeJuridique = this.formeJuridiqueTextField.getText();

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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite !");
            alert.showAndWait();
        }
        else if(contacts.isEmpty()){
            openedResultStage.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aucun contact trouvé");
            alert.setHeaderText(null);
            alert.setContentText("Aucun contact trouvé !");
            alert.showAndWait();
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















}


















