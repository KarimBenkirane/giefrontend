package com.example.giefrontend1.Controllers.Commercant;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.example.giefrontend1.Controllers.DTO.ContactDTO;
import com.example.giefrontend1.Parser.ParserContact;

public class FindEntrepriseController implements Initializable {
    @FXML
    public TextField raisonSocialeTextField;
    @FXML
    public TextField formeJuridiqueTextField;
    @FXML
    public Button searchRaisonSocialeButton;
    @FXML
    public Button searchFormeJuridiqueButton;
    @FXML
    public ListView<String> raisonSocialeListView;
    @FXML
    public ListView<String> formeJuridiqueListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchRaisonSocialeButton.setOnAction(event -> {
            raisonSocialeListView.getItems().clear();
            String entrepriseRaisonSociale = raisonSocialeTextField.getText();
            if (entrepriseRaisonSociale.isEmpty()) {
                raisonSocialeListView.getItems().add("Veuillez saisir la raison sociale!");
                return;
            }


            List<ContactDTO> entreprises = ParserContact.getEntrepriseByRaisonSociale(entrepriseRaisonSociale);
            if (entreprises == null) {
                raisonSocialeListView.getItems().add("Une erreur s'est produite.");
                return;
            }
            if (entreprises.isEmpty()) {
                raisonSocialeListView.getItems().add("Aucune entreprise ayant pour raison sociale " + entrepriseRaisonSociale + " n'a été trouvée.");
                return;
            }
            for (ContactDTO contactDTO : entreprises) {
                raisonSocialeListView.getItems().add(contactDTO.toString());
            }


        });


        searchFormeJuridiqueButton.setOnAction(event -> {
            formeJuridiqueListView.getItems().clear();
            String entrepriseFormeJuridique = formeJuridiqueTextField.getText();
            if (entrepriseFormeJuridique.isEmpty()) {
                formeJuridiqueListView.getItems().add("Veuillez saisir la forme juridique!");
                return;
            }


            List<ContactDTO> entreprises = ParserContact.getEntrepriseByFormeJuridique(entrepriseFormeJuridique);
            if (entreprises == null) {
                formeJuridiqueListView.getItems().add("Une erreur s'est produite.");
                return;
            }
            if (entreprises.isEmpty()) {
                formeJuridiqueListView.getItems().add("Aucune entreprise ayant la forme juridique " + entrepriseFormeJuridique + " n'a été trouvée.");
                return;
            }
            for (ContactDTO contactDTO : entreprises) {
                formeJuridiqueListView.getItems().add(contactDTO.toString());
            }


        });


    }
}
