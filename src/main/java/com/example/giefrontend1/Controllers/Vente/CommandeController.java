package com.example.giefrontend1.Controllers.Vente;

import com.example.giefrontend1.Controllers.DTO.*;
import com.example.giefrontend1.Parser.ParserCommande;
import com.example.giefrontend1.Parser.ParserContact;
import com.example.giefrontend1.Parser.ParserProduit;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.*;

import javafx.scene.chart.NumberAxis;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class CommandeController implements Initializable {
    //create commande attributes
    public Label ConfirmationMsg;
    public Button CreateCommandeButton;
    public Tab Show_Commandes;

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

    public TabPane mainTabPane;
    @FXML
    public TableView<DetailCommandeDTO> DetailCommandeTblView;
    @FXML
    public TableColumn<DetailCommandeDTO, Integer> detailCommande_ID;
    @FXML
    public TableColumn<DetailCommandeDTO, Long> Produit_ID;
    @FXML
    public TableColumn<DetailCommandeDTO, Double> remise_clmn;
    @FXML
    public TableColumn<DetailCommandeDTO, Integer> qte_clmn_DC;
    @FXML
    public TableColumn<DetailCommandeDTO, Double> prix_prod_clm;


    public Tab detailCommandeTab;
    public TableColumn<DetailCommandeDTO,String> Commande_id;
    public Label StatutLabel;
    public DatePicker DateCommandeField;
    public DatePicker DateReglementField;
    //

    // list all commande attributes
    @FXML
    public TableColumn<CommandeDTO, String> NumClient_tblClm;
    @FXML
    public TableColumn<CommandeDTO, Long> NumBonCommande_tblClm;
    public TableColumn<CommandeDTO, String> DateCommande_tblmd;
    public TableColumn<CommandeDTO, String> DateReglement_tblClm;
    public TableColumn<CommandeDTO, Double> PrixTotal_tblClm;
    public TableColumn<CommandeDTO, String> EtatCommande_tblClmn;
    public TableView<CommandeDTO> tableView;
    public Tab StatistiquesTab;
    @FXML
    public LineChart<Number, Number> lineChart;

    @FXML
    public BarChart<String, Number> barChart;

    @FXML
    public PieChart pieChart;

    List<DetailCommandeDTO> detailsCommande = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableView();
        populateClientChoiceBox();
        populateProduitChoiceBox();
        initQuantiterSpinner();
        initializeStatutComboBox();
        //initializeCharts();
        setupLineChart();
    }

    /* public void initializeCharts() {
         setupLineChart();
         setupBarChart();
         setupPieChart();
     }

     public void setupLineChart() {
         XYChart.Series<Number, Number> series = new XYChart.Series<>();
         series.setName("Gains Over Time");

         // Example data - replace with actual data from ParserCommande or other sources
         series.getData().add(new XYChart.Data<>(1, 200));
         series.getData().add(new XYChart.Data<>(2, 400));
         series.getData().add(new XYChart.Data<>(3, 300));

         lineChart.getData().add(series);
     }

     public void setupBarChart() {
         XYChart.Series<String, Number> series = new XYChart.Series<>();
         series.setName("Total Sales per Product");

         // Example data - replace with actual data from ParserCommande or other sources
         series.getData().add(new XYChart.Data<>("Product A", 150));
         series.getData().add(new XYChart.Data<>("Product B", 200));
         series.getData().add(new XYChart.Data<>("Product C", 250));

         barChart.getData().add(series);
     }

     public void setupPieChart() {
         ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                 new PieChart.Data("Product A", 30),
                 new PieChart.Data("Product B", 25),
                 new PieChart.Data("Product C", 45)
         );

         pieChart.setData(pieChartData);

         pieChartData.forEach(data ->
                 data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e ->
                         showAlert(Alert.AlertType.INFORMATION, "Product Info", data.getName() + ": " + data.getPieValue())
                 )
         );
     }*/
    public void updateChart(){setupLineChart();}
    public void setupLineChart() {
        // Clear any previous data in the chart
        lineChart.getData().clear();

        // Create a series to hold the data
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Prix Total des Commandes");

        // Retrieve all commandes
        List<CommandeDTO> commandes = ParserCommande.getAllCommandes();
        // Define a DateTimeFormatter for parsing the date with French locale
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, uuuu", Locale.FRENCH);

        // Sort commandes by date
        commandes.sort(Comparator.comparing(commande -> LocalDate.parse(commande.getDateCommande(), formatter)));

        // Get the first date
        LocalDate firstDate = LocalDate.parse(commandes.get(0).getDateCommande(), formatter);

        // Iterate over the commandes and add data points to the series
        for (CommandeDTO commande : commandes) {
            // Parse the date using the formatter
            LocalDate dateCommande = LocalDate.parse(commande.getDateCommande(), formatter);
            double prixTotal = commande.getTotalCommande();

            series.getData().add(new XYChart.Data<>(ChronoUnit.DAYS.between(firstDate, dateCommande), prixTotal)); // Convert LocalDate to days from the first date
        }

        // Add the series to the chart
        lineChart.getData().add(series);

        // Set custom label formatter for x-axis
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("MMM/yyyy", Locale.FRENCH);
        ((NumberAxis) lineChart.getXAxis()).setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                long days = object.longValue();
                LocalDate date = firstDate.plusDays(days);
                return customFormatter.format(date);
            }

            @Override
            public Number fromString(String string) {
                // Not needed for axis label formatting
                return null;
            }
        });
    }

    @FXML
    public void refreshTableView() {
        tableView.getItems().clear();
        tableView.getItems().addAll(ParserCommande.getAllCommandes());
    }

    public void showCommandeDetails(long id) {
        // Obtention de tous les détails de la commande par ID
        List<DetailCommandeDTO> detailCommandeDTOS = ParserCommande.getDetailsCommandesByID(id);
        // Création de la liste observable pour les détails de commande
        ObservableList<DetailCommandeDTO> observableList = FXCollections.observableArrayList(detailCommandeDTOS);

        // Configuration des cellules de la TableView
        detailCommande_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Produit_ID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getProduitDTO().getId()));
        qte_clmn_DC.setCellValueFactory(new PropertyValueFactory<>("qteCommante"));

        // Configuration de la colonne pour afficher les prix
        prix_prod_clm.setCellValueFactory(new PropertyValueFactory<>("reduction"));

        // Configuration de la colonne pour afficher les remises en pourcentage
        remise_clmn.setCellValueFactory(new PropertyValueFactory<>("prixCommande"));
        remise_clmn.setCellFactory(column -> new TableCell<DetailCommandeDTO, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item + " %");
                }
            }
        });

        Commande_id.setCellValueFactory(cellData -> {
            ProduitDTO produitDTO = cellData.getValue().getProduitObjet();
            if (produitDTO != null) {
                return new SimpleStringProperty(produitDTO.getMarque() + " " + produitDTO.getModele());
            } else {
                return new SimpleStringProperty("");
            }
        });

        // Mise à jour des données dans la TableView
        DetailCommandeTblView.setItems(observableList);

        detailCommande_ID.setMinWidth(100);
        Produit_ID.setMinWidth(100);
        remise_clmn.setMinWidth(100);
        qte_clmn_DC.setMinWidth(100);
        prix_prod_clm.setMinWidth(100);
        Commande_id.setMinWidth(100);

        DetailCommandeTblView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    private void setupTableView() {
        List<CommandeDTO> commandes = ParserCommande.getAllCommandes();
        ObservableList<CommandeDTO> observableList = FXCollections.observableArrayList(commandes);

        NumBonCommande_tblClm.setCellValueFactory(data -> new SimpleLongProperty(data.getValue().getNumBonCommande()).asObject());
        NumClient_tblClm.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClientId()));
        DateCommande_tblmd.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDateCommande()));
        DateReglement_tblClm.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDateReglement()));
        PrixTotal_tblClm.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTotalCommande()).asObject());
        EtatCommande_tblClmn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEtatCommande()));

        tableView.setItems(observableList);

        // Vérifiez si les colonnes existent déjà pour éviter les doublons
        boolean detailsColumnExists = tableView.getColumns().stream().anyMatch(col -> col.getText().equals("Details"));
        boolean editColumnExists = tableView.getColumns().stream().anyMatch(col -> col.getText().equals("Modifier statut"));

        if (!detailsColumnExists) {
            TableColumn<CommandeDTO, Void> detailsColumn = new TableColumn<>("Details");
            detailsColumn.setCellFactory(param -> new TableCell<>() {
                private final Button detailsButton = new Button("Details");

                {
                    detailsButton.setOnAction(event -> {
                        CommandeDTO selectedCommande = getTableView().getItems().get(getIndex());
                        if (selectedCommande != null) {
                            showCommandeDetails(selectedCommande.getNumBonCommande());
                            mainTabPane.getSelectionModel().select(detailCommandeTab);
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(detailsButton);
                    }
                }
            });

            tableView.getColumns().add(detailsColumn);
        }

        if (!editColumnExists) {
            TableColumn<CommandeDTO, Void> editColumn = new TableColumn<>("Modifier statut");
            editColumn.setCellFactory(param -> new TableCell<>() {

                private final ChoiceBox<String> updateStatusChoiceBox = new ChoiceBox<>();

                {
                    updateStatusChoiceBox.setOnAction(event -> {
                        String newStatus = updateStatusChoiceBox.getValue();
                        CommandeDTO selectedCommande = getTableView().getItems().get(getIndex());
                        if (selectedCommande != null && selectedCommande.getEtatCommande().equals("CONFIRMÉ")) {
                            selectedCommande.setEtatCommande(newStatus);
                            boolean status = ParserCommande.updateCommande(selectedCommande, selectedCommande.getNumBonCommande());
                            if (status) {
                                showAlert(Alert.AlertType.INFORMATION, "Succès", "Statut modifié avec succès !");
                                tableView.getItems().clear();
                                tableView.getItems().setAll(ParserCommande.getAllCommandes());
                                tableView.refresh();
                            } else {
                                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la modification du statut.");
                            }
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        CommandeDTO selectedCommande = getTableView().getItems().get(getIndex());
                        if (selectedCommande != null && selectedCommande.getEtatCommande().equals("CONFIRMÉ")) {
                            updateStatusChoiceBox.getItems().setAll("ANNULÉ", "LIVRÉ");
                            setGraphic(updateStatusChoiceBox);
                        } else {

                        }
                    }
                }
            });

            tableView.getColumns().add(editColumn);
        }

        NumClient_tblClm.setPrefWidth(100);
        NumBonCommande_tblClm.setPrefWidth(100);
        DateCommande_tblmd.setPrefWidth(150); // Colonnes de date peuvent nécessiter plus d'espace
        DateReglement_tblClm.setPrefWidth(150);
        PrixTotal_tblClm.setPrefWidth(100);
        EtatCommande_tblClmn.setPrefWidth(100);

        // Activer le redimensionnement automatique des colonnes
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void addToBasket(ActionEvent actionEvent){
        ProduitDisplay selectedDisplayProduit = (ProduitDisplay) produitChoicebox.getValue();


        if(produitChoicebox.getValue() == null | this.ClientChoiceBox.getValue() == null){
            showAlert(Alert.AlertType.WARNING,"Erreur","Veuillez choisir le client et le produit !");
            return;
        }

        if(DateCommandeField.getValue() == null | DateReglementField == null){
            showAlert(Alert.AlertType.WARNING,"Erreur","Veuillez spécifier la date de règlement et de commande !");
            return;
        }

        ProduitDTO selectedProduit = selectedDisplayProduit.getProduit();

        int quantite = QuantiterSpinner.getValue();
        double reduction = !ReductionTextField.getText().isEmpty() ? Double.parseDouble(ReductionTextField.getText()) : 0.0;
        String dateCommandeSQL = String.valueOf(DateCommandeField.getValue());
        String dateReglementSQL = String.valueOf(DateReglementField.getValue());
        double prixProduit = selectedProduit.getPrix();
        double prixTotal = quantite * prixProduit;
        // Appliquer la réduction si elle est saisie
        if (reduction > 0) {
            double reductionValeur = (reduction / 100) * prixTotal;
            prixTotal -= reductionValeur;
        }


        // Créer un nouveau détail de commande avec les valeurs récupérées
        DetailCommandeDTO detailCommande = new DetailCommandeDTO(selectedProduit, quantite, selectedProduit.getPrix(), reduction);
        //System.out.println(detailCommande);

        // Ajouter le détail de commande à la liste
        this.detailsCommande.add(detailCommande);

        showAlert(Alert.AlertType.INFORMATION,"Succès","Produit ajouté avec succès !");
    }

    public void annulerCommande(){
        if(this.detailsCommande != null){
            this.detailsCommande.clear();
            showAlert(Alert.AlertType.INFORMATION,"Succès","Commande annulée avec succès !");
        }
    }

    public void onCreateCommande(ActionEvent actionEvent) {

        if(ClientChoiceBox.getValue() == null){
            showAlert(Alert.AlertType.WARNING,"Erreur","Veuillez choisir un contact !");
            return;
        }

        if(DateReglementField.getValue() == null | DateCommandeField.getValue() == null){
            showAlert(Alert.AlertType.WARNING,"Erreur","Veuillez choisir les dates de commande et de règlement !");
            return;
        }

        DisplayContact selectedDisplayContact = (DisplayContact) ClientChoiceBox.getValue();
        ContactDTO selectedClient = selectedDisplayContact.getContact();


        //System.out.println(selectedClient);

        if(this.detailsCommande.isEmpty()){
            showAlert(Alert.AlertType.WARNING,"Erreur","Veuillez choisir des produits pour votre commande !");
            return;
        }

        if(this.ClientChoiceBox.getValue() == null){
            showAlert(Alert.AlertType.WARNING,"Erreur","Veuillez choisir le client pour qui ajouter la commande ! !");
            return;
        }


        ProduitDisplay selectedDisplayProduit = (ProduitDisplay) produitChoicebox.getValue();
        ProduitDTO selectedProduit = selectedDisplayProduit.getProduit();

        int quantite = QuantiterSpinner.getValue();
        double reduction = !ReductionTextField.getText().isEmpty() ? Double.parseDouble(ReductionTextField.getText()) : 0.0;
        String dateCommandeSQL = String.valueOf(DateCommandeField.getValue());
        String dateReglementSQL = String.valueOf(DateReglementField.getValue());
        double prixProduit = selectedProduit.getPrix();
        double prixTotal = 0;
        for(DetailCommandeDTO detailCommandeDTO : this.detailsCommande){
            double prixTemp = (detailCommandeDTO.getPrixCommande() * detailCommandeDTO.getQteCommante());
            prixTemp -= prixTemp * (detailCommandeDTO.getReduction()/100);
            prixTotal += prixTemp;
        }

        // Créer une nouvelle commande avec les valeurs récupérées et les détails de commande
        CommandeDTO newCommande = new CommandeDTO(selectedClient, detailsCommande, dateCommandeSQL, dateReglementSQL, prixTotal, "CONFIRMÉ");
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
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Commande créée avec succès !");

        } else {
            confirmationMsg.setText("Erreur lors de la création de la commande.");
            confirmationMsg.setTextFill(Color.RED);
            showAlert(Alert.AlertType.ERROR, "ERROR", "Un problème est survenu lors de la création de la commande !");

        }
        this.detailsCommande.clear();
        StatutLabel.setGraphic(confirmationMsg);
    }

    private void populateClientChoiceBox() {
        try {
            List<ContactDTO> contacts = new ArrayList<>(); // Initialisez une nouvelle liste de ContactDTO
            for (ContactDTO contact : getAllContacts()) {
                String displayString;
                // Afficher seulement une partie du contact sous forme de String
                if (contact.getRaisonSociale() == null) {
                    displayString = contact.getPrenom() + " " + contact.getNom();
                } else {
                    displayString = contact.getRaisonSociale() + " - " + "("+contact.getFormeJuridique()+ ")";
                }
                // Ajouter l'objet DisplayContact à la ChoiceBox
                ClientChoiceBox.getItems().add(new DisplayContact(contact, displayString));
                clientSearchComboBox.getItems().add(new DisplayContact(contact, displayString));
                // Ajouter l'objet ContactDTO extrait de DisplayContact à la liste de contacts
                contacts.add(contact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void populateProduitChoiceBox() {
        try {
            List<ProduitDTO> produits = getAllProduits();
            for (ProduitDTO produit : produits) {
                String displayString;
                displayString = produit.getMarque() + " " + produit.getModele();
                produitChoicebox.getItems().add(new ProduitDisplay(produit,displayString));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gérer les erreurs, par exemple en affichant un message à l'utilisateur
        }
    }


    class DisplayContact extends ContactDTO {
        private ContactDTO contact;
        private String displayString;

        public DisplayContact(ContactDTO contact, String displayString) {
            this.contact = contact;
            this.displayString = displayString;
        }

        public ContactDTO getContact() {
            return contact;
        }

        @Override
        public String toString() {
            return displayString;
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



    class ProduitDisplay extends ProduitDTO{
        private ProduitDTO produit;
        private String displayString;

        public ProduitDisplay(ProduitDTO produit, String displayString) {
            this.produit = produit;
            this.displayString = displayString;
        }

        public ProduitDTO getProduit() {
            return produit;
        }

        @Override
        public String toString() {
            return displayString;
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
        statutSearchComboBox.getItems().addAll(Arrays.asList("CONFIRMÉ", "ANNULÉ", "LIVRÉ"));
    }

    private void initQuantiterSpinner() {
        // Définir les valeurs minimales et maximales pour le Spinner
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 1);
        QuantiterSpinner.setValueFactory(valueFactory);
    }


    @FXML
    public void getTotalPrice(ActionEvent actionEvent) {
        ProduitDisplay selectedDisplayProduit = (ProduitDisplay) produitChoicebox.getValue();
        ProduitDTO selectedProduit = selectedDisplayProduit.getProduit();

        if (selectedProduit != null) {
            int quantite = QuantiterSpinner.getValue();
            double prixProduit = selectedProduit.getPrix();
            double prixTotal = quantite * prixProduit;

            // Vérifier si ReductionTextField contient une valeur parsable en double
            if (!ReductionTextField.getText().isEmpty()) {
                try {
                    double reductionPourcentage = Double.parseDouble(ReductionTextField.getText());
                    // Convertir la réduction en pourcentage en une valeur absolue
                    double reductionValeur = (reductionPourcentage / 100) * prixTotal;
                    prixTotal -= reductionValeur;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

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
        DisplayContact selectedDisplayContact = (DisplayContact) clientSearchComboBox.getValue();
        ContactDTO selectedClient = selectedDisplayContact != null ? selectedDisplayContact.getContact() : null;
        //System.out.println(selectedClient);
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
        //System.out.println(commandes);

        // Convertir la liste en ObservableList
        ObservableList<CommandeDTO> observableList = FXCollections.observableArrayList(commandes);

        // Mettre à jour les données du TableView
        tableView.setItems(observableList);

        mainTabPane.getSelectionModel().select(Show_Commandes);

    }
}