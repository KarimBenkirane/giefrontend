package com.example.giefrontend1.Controllers.Vente;

import com.example.giefrontend1.Controllers.Commercant.AchatController;
import com.example.giefrontend1.Controllers.Commercant.SearchResultController;
import com.example.giefrontend1.Controllers.DTO.*;
import com.example.giefrontend1.Parser.ParserCommande;
import com.example.giefrontend1.Parser.ParserContact;
import com.example.giefrontend1.Parser.ParserProduit;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CommandeController implements Initializable {
    //create commande attributes
    public Label ConfirmationMsg;
    public Button CreateCommandeButton;
    public Tab Show_Commandes;
    public DatePicker DateCoammndeFiel;
    public DatePicker DateRegelemtField;
    public Button getTotalPriceButton;
    public TextField ShowTotalPriceField;
    public Spinner<Integer> QuantiterSpinner;
    public TextField ReductionTextField;
    //list all clients
    public TableView contactsTableView;
    public TableColumn ClientsIdColumn;
    public TableColumn ClientsnomColumn;
    public TableColumn ClientsprenomColumn;
    public TableColumn ClientsraisonSocialeColumn;
    public TableColumn ClientsformeJuridiqueColumn;
    public TableColumn ClientsemailColumn;
    public TableColumn ClientstelColumn;
    public TableColumn ClientsfaxColumn;
    public TableColumn ClientsrueColumn;
    public TableColumn ClientsidAddresseColumn;
    public TableColumn ClientsnumRueColumn;
    public TableColumn ClientscodePostalColumn;
    public TableColumn ClientsquartierColumn;
    public TableColumn ClientsvilleColumn;
    public TableColumn ClientspaysColumn;
    public ChoiceBox<ContactDTO> ClientChoiceBox;
    public ChoiceBox<ProduitDTO> produitChoicebox;
    //
    private List<DetailCommandeDTO> detailsCommandes = new ArrayList<>();
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
       setupTableView();
       populateClientChoiceBox();
       populateProduitChoiceBox();
       initQuantiterSpinner();

    }
    private void setupTableView() {
        // Call ParserCommande to get all commands
        List<CommandeDTO> commandes = ParserCommande.getAllCommandes();

        // Convert the list to ObservableList for the TableView
        ObservableList<CommandeDTO> observableList = FXCollections.observableArrayList(commandes);

        // Bind the properties of CommandeDTO to the TableView columns
        NumBonCommande_tblClm.setCellValueFactory(new PropertyValueFactory<>("id"));
        NumBonCommande_tblClm.setCellValueFactory(new PropertyValueFactory<>("numBonCommande"));
        DateCommande_tblmd.setCellValueFactory(new PropertyValueFactory<>("dateCommande"));
        DateReglement_tblClm.setCellValueFactory(new PropertyValueFactory<>("dateReglement"));
        PrixTotal_tblClm.setCellValueFactory(new PropertyValueFactory<>("totalCommande"));
        EtatCommande_tblClmn.setCellValueFactory(new PropertyValueFactory<>("etatCommande"));

        // Set the TableView data
        tableView.setItems(observableList);
    }
    public void onCreateCommande(ActionEvent actionEvent) {
        ContactDTO selectedClient = ClientChoiceBox.getValue();
        ProduitDTO selectedProduit = produitChoicebox.getValue();
        int quantite = QuantiterSpinner.getValue();
        double reduction = !ReductionTextField.getText().isEmpty() ? Double.parseDouble(ReductionTextField.getText()) : 0.0;
        String dateCommandeSQL = String.valueOf(DateCoammndeFiel.getValue());
        String dateReglementSQL = String.valueOf(DateRegelemtField.getValue());
        double prixProduit = selectedProduit.getPrix();
        double prixTotal = quantite * prixProduit;
        // Appliquer la réduction si elle est saisie
        if (reduction > 0) {
            double reductionValeur = (reduction / 100) * prixTotal;
            prixTotal -= reductionValeur;
        }
        List<DetailCommandeDTO> detailsCommande = new ArrayList<>();

        // Créer un nouveau détail de commande avec les valeurs récupérées
        DetailCommandeDTO detailCommande = new DetailCommandeDTO(selectedProduit, quantite, selectedProduit.getPrix(), reduction);

        // Ajouter le détail de commande à la liste
        detailsCommande.add(detailCommande);
        String etat ="INITIALISÉ";

        // Créer une nouvelle commande avec les valeurs récupérées et les détails de commande
        CommandeDTO newCommande = new CommandeDTO(selectedClient, detailsCommande, dateCommandeSQL, dateReglementSQL,prixTotal,etat);
        newCommande.setDetailsCommande(detailsCommande);
        // Appeler la méthode pour créer la commande dans le backend
        boolean commandeCreated = ParserCommande.createCommande(newCommande);

        Label confirmationMsg = new Label();
        confirmationMsg.setFont(Font.font("Arial", 14));
        confirmationMsg.setWrapText(true);

        if (commandeCreated) {
            confirmationMsg.setText("Commande créée avec succès !");
            confirmationMsg.setTextFill(Color.GREEN);
        } else {
            confirmationMsg.setText("Erreur lors de la création de la commande.");
            confirmationMsg.setTextFill(Color.RED);
        }

        // Ajouter le message de confirmation à votre interface utilisateur
        // Ici, ConfirmationMsg est le nom de votre élément de l'interface où vous voulez afficher le message
        ConfirmationMsg.setGraphic(confirmationMsg);
    }
    private void populateClientChoiceBox() {
        try {
            List<ContactDTO> contacts = getAllContacts();
            ClientChoiceBox.getItems().addAll(contacts);
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les erreurs, par exemple en affichant un message à l'utilisateur
        }
    }
    // Méthode pour récupérer tous les contacts depuis la base de données
    private List<ContactDTO> getAllContacts() {
        List<ContactDTO> contacts = new ArrayList<>();
        try {
            // Appeler votre méthode pour récupérer tous les contacts
            contacts = ParserContact.getAllContacts();
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les erreurs, par exemple en affichant un message à l'utilisateur
        }
        return contacts;
    }
    private void populateProduitChoiceBox() {
        try {
            List<ProduitDTO> produits = getAllProduits();
            produitChoicebox.getItems().addAll(produits);
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les erreurs, par exemple en affichant un message à l'utilisateur
        }
    }
    // Méthode pour récupérer tous les contacts depuis la base de données
    private List<ProduitDTO> getAllProduits() {
        List<ProduitDTO> produits = new ArrayList<>();
        try {
            // Appeler votre méthode pour récupérer tous les contacts
            produits = ParserProduit.getAllProduits();
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les erreurs, par exemple en affichant un message à l'utilisateur
        }
        return produits;
    }
    private void initQuantiterSpinner() {
        // Définir les valeurs minimales et maximales pour le Spinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 1);
        QuantiterSpinner.setValueFactory(valueFactory);
    }

    public void onButtonClick(ActionEvent actionEvent) {

        if(commandesRadioBtn.isSelected()) {
            String numBonCommande = NumBonCommandeTextField.getText();
            String etatCommande = EtatCommandeTextField.getText();
            String dateCommande = DateCommandeTextField.getText();


            if (numBonCommande != null) {
                // Appel de la méthode pour rechercher la commande par son numéro
                CommandeDTO commande = ParserCommande.getCommandeById(Long.parseLong(numBonCommande));

                // Si la commande est trouvée, retourner à l'onglet "All Commandes"
                TabPane tabPane = (TabPane) Show_Commandes.getTabPane();
                tabPane.getSelectionModel().select(Show_Commandes);

                // Mettre à jour le TableView avec la commande trouvée
                ObservableList<CommandeDTO> observableList = FXCollections.observableArrayList(commande);
                tableView.setItems(observableList);
            }
            if (etatCommande != null) {
                CommandeDTO commande = ParserCommande.getCommandeById(Long.parseLong(numBonCommande));
                //Mettre à jour le TableView avec la commande trouvée
                ObservableList<CommandeDTO> observableList = FXCollections.observableArrayList(commande);
                tableView.setItems(observableList);
            }
        }
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
    @FXML
    public void getTotalPrice(ActionEvent actionEvent) {
        ProduitDTO selectedProduit = produitChoicebox.getValue();
        if (selectedProduit != null) {
            int quantite = QuantiterSpinner.getValue();
            double prixProduit = selectedProduit.getPrix(); // Supposons que le prix soit stocké dans ProduitDTO
            double prixTotal = quantite * prixProduit;

            // Appliquer la réduction si elle est saisie
            if (!ReductionTextField.getText().isEmpty()) {
                double reductionPourcentage = Double.parseDouble(ReductionTextField.getText());
                // Convertir la réduction en pourcentage en une valeur absolue
                double reductionValeur = (reductionPourcentage / 100) * prixTotal;
                prixTotal -= reductionValeur;
            }

            ShowTotalPriceField.setText(String.valueOf(prixTotal));
        }
    }
}
