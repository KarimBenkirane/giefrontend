package com.example.giefrontend1.Controllers.Vente;

import com.example.giefrontend1.Controllers.DTO.*;
import com.example.giefrontend1.Parser.ParserCommande;
import com.example.giefrontend1.Parser.ParserContact;
import com.example.giefrontend1.Parser.ParserProduit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

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
    public ChoiceBox<ContactDTO> ClientChoiceBox;
    public ChoiceBox<ProduitDTO> produitChoicebox;
    //Search Commande
    public ComboBox<ContactDTO> clientSearchComboBox;
    public ComboBox<String> statutSearchComboBox;
    public DatePicker apresDatePicker;
    public DatePicker avantDatePicker;
    public TextField prixMinSearchTextField;
    public TextField prixMaxSearchTextField;
    public Button RechercherButton;
    public Tab Search_commande;
    public TabPane mainTabPane;
    @FXML
    public TableView<CommandeDTO> DetailCommandeTblView;
    @FXML
    public TableColumn<CommandeDTO, Integer> detailCommande_ID;
    @FXML
    public TableColumn<CommandeDTO, Integer> Commande_ID;
    @FXML
    public TableColumn<CommandeDTO, Integer> Produit_ID;
    @FXML
    public TableColumn<CommandeDTO, Double> remise_clmn;
    @FXML
    public TableColumn<CommandeDTO, Integer> qte_clmn_DC;
    @FXML
    public TableColumn<CommandeDTO, Double> prix_prod_clm;
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





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableView();
        populateClientChoiceBox();
        populateProduitChoiceBox();
        initQuantiterSpinner();
        initializeStatutComboBox();
        //setupDCTableView();
    }

    private void setupTableView() {
        // Call ParserCommande to get all commands
        List<CommandeDTO> commandes = ParserCommande.getAllCommandes();

        // Convert the list to ObservableList for the TableView
        ObservableList<CommandeDTO> observableList = FXCollections.observableArrayList(commandes);

        // Bind the properties of CommandeDTO to the TableView columns
        NumClient_tblClm.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        NumBonCommande_tblClm.setCellValueFactory(new PropertyValueFactory<>("numBonCommande"));
        DateCommande_tblmd.setCellValueFactory(new PropertyValueFactory<>("dateCommande"));
        DateReglement_tblClm.setCellValueFactory(new PropertyValueFactory<>("dateReglement"));
        PrixTotal_tblClm.setCellValueFactory(new PropertyValueFactory<>("totalCommande"));
        EtatCommande_tblClmn.setCellValueFactory(new PropertyValueFactory<>("etatCommande"));


        // Set the TableView data
        tableView.setItems(observableList);
    }private void setupDCTableView() {
        List<CommandeDTO> commandes = ParserCommande.getAllCommandes();

        // Convert the list to ObservableList for the TableView
        ObservableList<CommandeDTO> observableList = FXCollections.observableArrayList(commandes);

        // Bind the properties of CommandeDTO to the TableView columns
        detailCommande_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Commande_ID.setCellValueFactory(new PropertyValueFactory<>("commandeID"));
        Produit_ID.setCellValueFactory(new PropertyValueFactory<>("produitID"));
        remise_clmn.setCellValueFactory(new PropertyValueFactory<>("remise"));
        qte_clmn_DC.setCellValueFactory(new PropertyValueFactory<>("qteCommander"));
        prix_prod_clm.setCellValueFactory(new PropertyValueFactory<>("prixCommande"));


        // Set the TableView data
        DetailCommandeTblView.setItems(observableList);
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
        String etat = "INITIALISÉ";

        // Créer une nouvelle commande avec les valeurs récupérées et les détails de commande
        CommandeDTO newCommande = new CommandeDTO(selectedClient, detailsCommande, dateCommandeSQL, dateReglementSQL, prixTotal, etat);
        newCommande.setDetailsCommande(detailsCommande);
        // Appeler la méthode pour créer la commande dans le backend
        boolean commandeCreated = ParserCommande.createCommande(newCommande);

        Label confirmationMsg = new Label();
        confirmationMsg.setFont(Font.font("Arial", 14));
        confirmationMsg.setWrapText(true);

        if (commandeCreated) {
            confirmationMsg.setText("Commande créée avec succès !");
            this.tableView.getItems().setAll(ParserCommande.getAllCommandes());
            confirmationMsg.setTextFill(Color.GREEN);
        } else {
            confirmationMsg.setText("Erreur lors de la création de la commande.");
            confirmationMsg.setTextFill(Color.RED);
        }

        ConfirmationMsg.setGraphic(confirmationMsg);
    }

    private void populateClientChoiceBox() {
        try {
            List<ContactDTO> contacts = getAllContacts();
            ClientChoiceBox.getItems().addAll(contacts);
            clientSearchComboBox.getItems().addAll(contacts);
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

    private void initializeStatutComboBox() {
        statutSearchComboBox.getItems().addAll(Arrays.asList("INITIALISÉ", "ANNULÉ", "CONFIRMÉ", "LIVRÉ"));
    }

    private void initQuantiterSpinner() {
        // Définir les valeurs minimales et maximales pour le Spinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 1);
        QuantiterSpinner.setValueFactory(valueFactory);
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

    public void onShowAllCommande(Event event) {
        this.tableView.getItems().addAll(ParserCommande.getAllCommandes());
    }

    // Méthode pour mettre à jour la TableView avec les résultats de la recherche
    private void updateTableView(List<CommandeDTO> commandes) {
        ObservableList<CommandeDTO> observableList = FXCollections.observableArrayList(commandes);
        tableView.setItems(observableList);
    }

    // Méthode appelée lors de la recherche de commande
    public void onClientSearch(ActionEvent actionEvent) {
        ContactDTO selectedClient = clientSearchComboBox.getValue();
        String statut = statutSearchComboBox.getValue();
        LocalDate localDateApres = apresDatePicker.getValue();
        LocalDate localDateAvant = avantDatePicker.getValue();
        String dateApres = null;
        String dateAvant = null;

        if (localDateApres != null) {
            dateApres = String.valueOf(localDateApres);
        }
        if (localDateAvant != null) {
            dateAvant = String.valueOf(localDateAvant);
        }

        String prixMin = this.prixMinSearchTextField.getText().isEmpty() ? null : this.prixMinSearchTextField.getText();
        String prixMax = this.prixMaxSearchTextField.getText().isEmpty() ? null : this.prixMaxSearchTextField.getText();

        Map<String, Object> searchMap = new HashMap<>();

        // Ajouter seulement le prénom du client à la map de recherche
        if (selectedClient != null) {
            if (selectedClient.getRaisonSociale() != null) {
                searchMap.put("client", selectedClient.getRaisonSociale());
            } else {
                searchMap.put("client", selectedClient.getNom());
            }
        }
        if (statut != null) {
            searchMap.put("etatCommande", statut);
        }

        searchMap.put("dateApres", dateApres);
        searchMap.put("dateAvant", dateAvant);
        searchMap.put("prixMin", prixMin);
        searchMap.put("prixMax", prixMax);

        List<CommandeDTO> commandes = ParserCommande.getAdvSearch(searchMap);

        // Convertir la liste en ObservableList
        ObservableList<CommandeDTO> observableList = FXCollections.observableArrayList(commandes);

        // Mettre à jour les données du TableView
        tableView.setItems(observableList);

        mainTabPane.getSelectionModel().select(Show_Commandes);

    }
}





