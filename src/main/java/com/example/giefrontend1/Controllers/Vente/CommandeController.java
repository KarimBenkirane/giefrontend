package com.example.giefrontend1.Controllers.Vente;

import com.example.giefrontend1.Controllers.DTO.CommandeDTO;
import com.example.giefrontend1.Parser.ParserCommande;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CommandeController implements Initializable {
    //create commande attributes
    public Label ConfirmationMsg;
    public Button CreateCommandeButton;
    public ChoiceBox ClientField;
    public ChoiceBox ProductField;
    public TextField QuantityField;
    public Tab Show_Commandes;
// list all commande attributes
    public TableColumn<CommandeDTO, Long> NumClient_tblClm;
    public TableColumn<CommandeDTO, Long> NumBonCommande_tblClm;
    public TableColumn<CommandeDTO, String> DateCommande_tblmd;
    public TableColumn<CommandeDTO, String> DateReglement_tblClm;
    public TableColumn<CommandeDTO, Double> PrixTotal_tblClm;
    public TableColumn<CommandeDTO, String> EtatCommande_tblClmn;
    public TableView<CommandeDTO> tableView;
    // search attributes
    public RadioButton commandesRadioBtn;
    public ToggleGroup typeCommandeGroup;
    public RadioButton detailcommandeRadioBtn;
    public Label dateCommande_lbl;
    public TextField num_DetailCommande;
    public Label etat_lbl;
    public Label idCommande_lbl;
    public TextField NumBonCommandeTextField;
    public TextField EtatCommandeTextField;
    public TextField DateCommandeTextField;
    public Button rechercherBtn;
    public Label num_detailCommande_lbl;
    public TextField num_detailCommandeTextField;
    public TextField num_ProduitTextField;
    public Label num_Produit_lbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Appel de la méthode getAllCommandes pour obtenir la liste des commandes
        List<CommandeDTO> commandes = ParserCommande.getAllCommandes();

        // Conversion de la liste en ObservableList pour la TableView
        ObservableList<CommandeDTO> observableList = FXCollections.observableArrayList(commandes);

        // Liaison des propriétés de la CommandeDTO aux colonnes de la TableView
        NumClient_tblClm.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        NumBonCommande_tblClm.setCellValueFactory(new PropertyValueFactory<>("numBonCommande"));
        DateCommande_tblmd.setCellValueFactory(new PropertyValueFactory<>("dateCommande"));
        DateReglement_tblClm.setCellValueFactory(new PropertyValueFactory<>("dateReglement"));
        PrixTotal_tblClm.setCellValueFactory(new PropertyValueFactory<>("totalCommande"));
        EtatCommande_tblClmn.setCellValueFactory(new PropertyValueFactory<>("etatCommande"));

        // Définir les données de la TableView
        tableView.setItems(observableList);

    }

    public void cdetailcommandeBtnMethod(ActionEvent actionEvent) {
    }

    public void onButtonClick(ActionEvent actionEvent) {
        String numBonCommande = NumBonCommandeTextField.getText();
        // Appel de la méthode pour rechercher la commande par son numéro
        CommandeDTO commande = ParserCommande.getCommandeById(Long.parseLong(numBonCommande));

        // Si la commande est trouvée, retourner à l'onglet "All Commandes"
        TabPane tabPane = (TabPane) Show_Commandes.getTabPane();
        tabPane.getSelectionModel().select(Show_Commandes);

        // Mettre à jour le TableView avec la commande trouvée
        ObservableList<CommandeDTO> observableList = FXCollections.observableArrayList(commande);
        tableView.setItems(observableList);

    }

    public void commandeBtnMethod(ActionEvent actionEvent) {


    }

    public void handleRadioSelection(ActionEvent actionEvent) {
        if (commandesRadioBtn.isSelected()) {
            // Afficher les éléments liés à la commande
            this.EtatCommandeTextField.setVisible(true);
            this.DateCommandeTextField.setVisible(true);
            this.EtatCommandeTextField.setVisible(true);
            this.etat_lbl.setVisible(true);
            this.dateCommande_lbl.setVisible(true);
            this.num_detailCommande_lbl.setVisible(false);
            this.num_Produit_lbl.setVisible(false);
            this.num_detailCommandeTextField.setVisible(false);
            this.num_ProduitTextField.setVisible(false);

        }
        else if(detailcommandeRadioBtn.isSelected()) {
            // Afficher les éléments liés au détail de la commande
            this.EtatCommandeTextField.setVisible(false);
            this.DateCommandeTextField.setVisible(false);
            this.etat_lbl.setVisible(false);
            this.dateCommande_lbl.setVisible(false);
            this.num_detailCommande_lbl.setVisible(true);
            this.num_Produit_lbl.setVisible(true);
            this.num_detailCommandeTextField.setVisible(true);
            this.num_ProduitTextField.setVisible(true);

        }
    }

}
