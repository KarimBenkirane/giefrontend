package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Controllers.DTO.AchatDTO;
import com.example.giefrontend1.Controllers.DTO.ContactDTO;
import com.example.giefrontend1.Controllers.DTO.DetailAchatDTO;
import com.example.giefrontend1.Controllers.DTO.ProduitDTO;
import com.example.giefrontend1.Parser.ParserAchat;
import com.example.giefrontend1.Parser.ParserContact;
import com.example.giefrontend1.Parser.ParserProduit;
import com.google.gson.JsonArray;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AchatController implements Initializable {

    private Stage stageDetailsAchats = null;
    @FXML
    public Button advSearchAchatBtn;

    @FXML
    public TableColumn<AchatDTO, String> dateAchatColumn;

    @FXML
    public TableColumn<AchatDTO, String> fournisseurAchatColumn;

    @FXML
    public TableColumn<AchatDTO, Long> idAchatColumn;

    @FXML
    public TableView<AchatDTO> mesAchatsTableView;

    @FXML
    public TableColumn<AchatDTO, Double> prixAchatColumn;

    @FXML
    public TableColumn<AchatDTO, String> statutAchatColumn;

    @FXML
    public TableColumn<DetailAchatDTO, Integer> mesDetailsIDColumn;

    @FXML
    public TableColumn<DetailAchatDTO, Double> mesDetailsPrixColumn;

    @FXML
    public TableColumn<DetailAchatDTO, String> mesDetailsProduitColumn;

    @FXML
    public TableColumn<DetailAchatDTO, String> mesDetailsProduitMarqueColumn;

    @FXML
    public TableColumn<DetailAchatDTO, String> mesDetailsProduitModeleColumn;

    @FXML
    public TableColumn<DetailAchatDTO, String> mesDetailsPrixUnitaireColumn;

    @FXML
    public TableColumn<DetailAchatDTO, Integer> mesDetailsQtAcheteeColumn;

    @FXML
    public TableColumn<DetailAchatDTO, String> mesDetailsReductionColumn;

    @FXML
    public TableView<DetailAchatDTO> mesDetailsTableView;

    @FXML
    public Button newAchatBtn;

    @FXML
    public TextField reductionTextField;


    @FXML
    public TableView<ContactDTO> contactsTableView;
    @FXML
    public TableColumn<ContactDTO, Integer> FournisseursIdColumn;

    @FXML
    public TableColumn<ContactDTO, Integer> FournisseurscodePostalColumn;

    @FXML
    public TableColumn<ContactDTO, String> FournisseursemailColumn;

    @FXML
    public TableColumn<ContactDTO, String> FournisseursfaxColumn;

    @FXML
    public TableColumn<ContactDTO, String> FournisseursformeJuridiqueColumn;

    @FXML
    public TableColumn<ContactDTO, Integer> FournisseursidAddresseColumn;

    @FXML
    public TableColumn<ContactDTO, String> FournisseursnomColumn;

    @FXML
    public TableColumn<ContactDTO, Integer> FournisseursnumRueColumn;

    @FXML
    public TableColumn<ContactDTO, String> FournisseurspaysColumn;

    @FXML
    public TableColumn<ContactDTO, String> FournisseursprenomColumn;

    @FXML
    public TableColumn<ContactDTO, String> FournisseursquartierColumn;

    @FXML
    public TableColumn<ContactDTO, String> FournisseursraisonSocialeColumn;

    @FXML
    public TableColumn<ContactDTO, String> FournisseursrueColumn;

    @FXML
    public TableColumn<ContactDTO, String> FournisseurstelColumn;

    @FXML
    public TableColumn<ContactDTO, String> FournisseursvilleColumn;

    @FXML
    public TableView<DetailAchatDTO> DetailsAchatsTableView;

    @FXML
    public TableColumn<DetailAchatDTO, Double> DetailsPrixColumn;

    @FXML
    public TableColumn<DetailAchatDTO, Double> DetailsPrixUnitaireColumn;

    @FXML
    public TableColumn<DetailAchatDTO, String> DetailsProduitColumn;

    @FXML
    public TableColumn<DetailAchatDTO, Integer> DetailsQtAcheteeColumn;

    @FXML
    public TableColumn<DetailAchatDTO, String> DetailsReductionColumn;

    @FXML
    public TableColumn<DetailAchatDTO, String> DetailsProduitMarqueColumn;

    @FXML
    public TableColumn<DetailAchatDTO, String> DetailsProduitModeleColumn;

    @FXML
    public TableView<ProduitDTO> produitTableView;

    @FXML
    public TableColumn<ProduitDTO, String> ProductsModeleProduitColumn;

    @FXML
    public TableColumn<ProduitDTO, Integer> ProductsQtStockProduitColumn;

    @FXML
    public TableColumn<ProduitDTO, String> ProductsmarqueProduitColumn;

    @FXML
    public TableColumn<ProduitDTO, Double> ProductsprixProduitColumn;
    @FXML
    public TableColumn<ProduitDTO, String> productsCategorieProduitColumn;

    @FXML
    public TableColumn<ProduitDTO, String> productsDescriptionProduitColumn;

    @FXML
    public TableColumn<ProduitDTO, Long> productsIdProduitColumn;

    @FXML
    public Button addToBasketBtn;

    @FXML
    public Spinner<Integer> spinnerQt;

    private static boolean initialized = false;



    @FXML
    public Button confirmAchatBtn;
    @FXML
    public Button nextBtn;
    @FXML
    public Label statusLabel;
    private int selected_fournisseur_id;
    private List<ProduitDTO> liste_produits_achat;
    private long selected_produit_id;
    private List<DetailAchatDTO> detailsAchats = new ArrayList<>();


    public AchatController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!initialized){ // Call ParserAchat.getAllAchats() to get the list of purchases
            List<AchatDTO> achatsList = ParserAchat.getAllAchats();

            // Convert the list to an ObservableList
            ObservableList<AchatDTO> achatData = FXCollections.observableArrayList(achatsList);

            // Set the data to the TableView
            mesAchatsTableView.setItems(achatData);

            // Set cell value factories for each column
            idAchatColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            prixAchatColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
            statutAchatColumn.setCellValueFactory(new PropertyValueFactory<>("statutAchat"));
            fournisseurAchatColumn.setCellValueFactory(cellData -> {
                ContactDTO fournisseur = cellData.getValue().getFournisseur();
                if (fournisseur != null) {
                    String fournisseurName = fournisseur.getNom() != null ? fournisseur.getNom() : fournisseur.getRaisonSociale();
                    return new SimpleStringProperty(fournisseurName);
                } else {
                    return new SimpleStringProperty("");
                }
            });
            dateAchatColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateAchat()));

            // Create "Details" column with buttons dynamically
            TableColumn<AchatDTO, Void> detailsColumn = new TableColumn<>("Details");
            detailsColumn.setCellFactory(param -> new TableCell<>() {
                private final Button detailsButton = new Button("Details");

                {
                    detailsButton.setOnAction(event -> {
                        AchatDTO selectedAchat = getTableView().getItems().get(getIndex());
                        long achat_id = selectedAchat.getId();
                        System.out.println(achat_id);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.giefrontend1/Admin/MyPurchasesDetails.fxml"));
                        try {
                            Parent root = loader.load();
                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setTitle("Details de mon Achat");
                            stage.show();

                            AchatController.this.stageDetailsAchats = stage;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        AchatController achatController = loader.getController();

                        // Call a method to get your list of DetailAchatDTO objects
                        List<DetailAchatDTO> detailAchatDTOList = ParserAchat.getDetailsAchatsByID(achat_id);

                        // Convert the list to an ObservableList
                        ObservableList<DetailAchatDTO> detailAchatData = FXCollections.observableArrayList(detailAchatDTOList);

                        // Set the data to the TableView
                        achatController.mesDetailsTableView.setItems(detailAchatData);

                        // Set cell value factories for each column
                        achatController.mesDetailsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                        achatController.mesDetailsPrixColumn.setCellValueFactory(new PropertyValueFactory<>("prixAchat"));
                        achatController.mesDetailsQtAcheteeColumn.setCellValueFactory(new PropertyValueFactory<>("qteAchetee"));
//                        achatController.mesDetailsReductionColumn.setCellValueFactory(new PropertyValueFactory<>("reduction"));

                        // Set a custom cell value factory for the produit column
                        achatController.mesDetailsProduitMarqueColumn.setCellValueFactory(cellData -> {
                            ProduitDTO produitDTO = cellData.getValue().getProduitObjet();
                            if (produitDTO != null) {
                                return new SimpleStringProperty(produitDTO.getMarque());
                            } else {
                                return new SimpleStringProperty("");
                            }
                        });

                        achatController.mesDetailsProduitModeleColumn.setCellValueFactory(cellData -> {
                            ProduitDTO produitDTO = cellData.getValue().getProduitObjet();
                            if (produitDTO != null) {
                                return new SimpleStringProperty(produitDTO.getModele());
                            } else {
                                return new SimpleStringProperty("");
                            }
                        });

                        achatController.mesDetailsPrixUnitaireColumn.setCellValueFactory(cellData -> {
                            ProduitDTO produitDTO = cellData.getValue().getProduitObjet();
                            if (produitDTO != null) {
                                return new SimpleStringProperty(String.valueOf(produitDTO.getPrix()));
                            } else {
                                return new SimpleStringProperty("");
                            }
                        });

                        achatController.mesDetailsReductionColumn.setCellValueFactory(cellData -> {
                            DetailAchatDTO detailAchatDTO = cellData.getValue();
                            if (detailAchatDTO != null) {
                                return new SimpleStringProperty(String.valueOf(detailAchatDTO.getReduction() * 100) + "%");
                            } else {
                                return new SimpleStringProperty("");
                            }
                        });



                        if(achatController.mesDetailsTableView.getItems().isEmpty()){
                            showAlert(Alert.AlertType.INFORMATION,"Aucun produit acheté !","Aucun produit n'a été ajouté à cet achat !");
                            AchatController.this.stageDetailsAchats.close();
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

            mesAchatsTableView.getColumns().add(detailsColumn);
            initialized = true;
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void onCreateAchat(ActionEvent e) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.giefrontend1/Admin/MyPurchasesCreate.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Créer un nouvel achat");
        stage.show();



        AchatController achatController = loader.getController();

        //Populating Fournisseur TableView:
        achatController.FournisseursIdColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        achatController.FournisseursprenomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrenom()));
        achatController.FournisseursnomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNom()));
        achatController.FournisseursemailColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        achatController.FournisseurstelColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelephone()));
        achatController.FournisseursfaxColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFax()));
        achatController.FournisseursrueColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAdresse().getRue()));
        achatController.FournisseursnumRueColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAdresse().getNumeroRue()).asObject());
        achatController.FournisseurscodePostalColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAdresse().getCodePostal()).asObject());
        achatController.FournisseursvilleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAdresse().getVille()));
        achatController.FournisseurspaysColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAdresse().getPays()));
        achatController.FournisseursquartierColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAdresse().getQuartier()));
        achatController.FournisseursformeJuridiqueColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFormeJuridique()));
        achatController.FournisseursraisonSocialeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRaisonSociale()));
        achatController.FournisseursidAddresseColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getAdresse().getId()).asObject());

        // Retrieve data from ParserContact.getAllContacts()
        List<ContactDTO> allContacts = ParserContact.getAllContacts();

        // Populate the TableView with data
        ObservableList<ContactDTO> contactsList = FXCollections.observableArrayList(allContacts);
        achatController.contactsTableView.setItems(contactsList);

        // Retrieve data from ParserContact.getAllContacts()
        List<ContactDTO> allFournisseurs = ParserContact.getAllContacts();

        // Populate the TableView with data
        ObservableList<ContactDTO> fournisseursList = FXCollections.observableArrayList(allFournisseurs);
        achatController.contactsTableView.setItems(fournisseursList);

        achatController.contactsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ContactDTO>() {
            @Override
            public void changed(ObservableValue<? extends ContactDTO> observable, ContactDTO oldValue, ContactDTO newValue) {
                if (newValue != null) {
                    // Do something with the selected ContactDTO, for example:
                    int selected_fournisseur = newValue.getId();
                    AchatController.this.selected_fournisseur_id = selected_fournisseur;
                }
            }
        });
        //End of populating Fournisseurs TableView and added Listener.

        //Populating Produits TableView:

        achatController.ProductsModeleProduitColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getModele()));
        achatController.ProductsQtStockProduitColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQteStock()).asObject());
        achatController.ProductsmarqueProduitColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMarque()));
        achatController.ProductsprixProduitColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrix()).asObject());
        achatController.productsCategorieProduitColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategorie()));
        achatController.productsDescriptionProduitColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));
        achatController.productsIdProduitColumn.setCellValueFactory(data -> new SimpleLongProperty(data.getValue().getId()).asObject());

        // Retrieve data from wherever you get it
        List<ProduitDTO> produits = ParserProduit.getAllProduits();

        // Populate the TableView with data
        ObservableList<ProduitDTO> produitsList = FXCollections.observableArrayList(produits);
        achatController.produitTableView.setItems(produitsList);

        achatController.produitTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProduitDTO>() {
            @Override
            public void changed(ObservableValue<? extends ProduitDTO> observable, ProduitDTO oldValue, ProduitDTO newValue) {
                if (newValue != null) {
                    long selected_produit_id = newValue.getId();
                    AchatController.this.selected_produit_id = selected_produit_id;
                }
            }
        });


        achatController.addToBasketBtn.setOnAction(event -> {
            if(achatController.produitTableView.getSelectionModel().getSelectedItem() == null){
                showAlert(Alert.AlertType.ERROR,"Erreur","Veuillez sélectionner un produit avant de l'ajouter au panier !");
            }else{
                int qtAchetee = achatController.spinnerQt.getValue();
                ProduitDTO produitDTO = ParserProduit.getProduitByID(AchatController.this.selected_produit_id);
                double prix_achat = (produitDTO.getPrix() * qtAchetee);
                double reduction = 0;
                if(achatController.reductionTextField.getText().isEmpty()){
                    reduction = 0;
                }else{
                    try{
                        reduction = Double.parseDouble(achatController.reductionTextField.getText()) / 100;
                        if(reduction*100 < 0 || reduction*100 > 100){
                            showAlert(Alert.AlertType.ERROR,"Erreur","Veuillez saisir la réduction correctement !");
                            return;
                        }
                    }catch (NumberFormatException nfexc){
                        showAlert(Alert.AlertType.ERROR,"Erreur","Veuillez saisir la réduction correctement !");
                        return;
                    }
                }
                double prix_reduit = prix_achat - (prix_achat*reduction);
                DetailAchatDTO detailAchatDTO = new DetailAchatDTO(produitDTO,qtAchetee,prix_reduit,reduction);
                AchatController.this.detailsAchats.add(detailAchatDTO);
                System.out.println(detailAchatDTO);
                showAlert(Alert.AlertType.INFORMATION,"Succès","Produit ajouté au panier avec succès !");
            }
        });

        achatController.nextBtn.setOnAction(event ->

        {

            if (achatController.contactsTableView.isVisible()) {
                if (achatController.contactsTableView.getSelectionModel().getSelectedItem() == null) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez choisir un fournisseur avant de procéder !");
                } else {
                    ContactDTO fournisseur = ParserContact.getContactByID(AchatController.this.selected_fournisseur_id);
                    System.out.println(fournisseur);
                    achatController.statusLabel.setText("Veuillez choisir vos produits");
                    achatController.reductionTextField.setVisible(true);
                    achatController.contactsTableView.setVisible(false);
                    achatController.produitTableView.setVisible(true);
                    achatController.addToBasketBtn.setVisible(true);
                    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
                    achatController.spinnerQt.setValueFactory(valueFactory);
                    achatController.spinnerQt.setVisible(true);

                }


            } else if (achatController.produitTableView.isVisible()) {
                if(AchatController.this.detailsAchats.isEmpty()){
                    showAlert(Alert.AlertType.ERROR,"Erreur","Veuillez choisir des produits avant de procéder !");
                    return;
                }

                //Populate Details

                achatController.DetailsPrixColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrixAchat()).asObject());
                achatController.DetailsPrixUnitaireColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getProduitObjet().getPrix() / data.getValue().getQteAchetee()).asObject());
                achatController.DetailsProduitColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduitObjet().toString()));
                achatController.DetailsQtAcheteeColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQteAchetee()).asObject());
                achatController.DetailsReductionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReduction()*100 +"%"));
                achatController.DetailsProduitMarqueColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduitObjet().getMarque()));
                achatController.DetailsProduitModeleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduitObjet().getModele()));

                // Retrieve data from wherever you get it
                List<DetailAchatDTO> detailsAchats = AchatController.this.detailsAchats;

                // Populate the TableView with data
                ObservableList<DetailAchatDTO> detailsAchatsList = FXCollections.observableArrayList(detailsAchats);
                achatController.DetailsAchatsTableView.setItems(detailsAchatsList);

                achatController.statusLabel.setText("Résumé de votre achat");
                achatController.reductionTextField.setVisible(false);
                achatController.confirmAchatBtn.setVisible(true);
                achatController.nextBtn.setVisible(false);
                achatController.spinnerQt.setVisible(false);
                achatController.addToBasketBtn.setVisible(false);
                achatController.produitTableView.setVisible(false);
                achatController.DetailsAchatsTableView.setVisible(true);

            }
        });

        achatController.confirmAchatBtn.setOnAction(event -> {
            //pour chaque détail achat, aller prendre la qt achetée l'ajouter au produit de base dans le stock
            //et créer l'instance achat qui va être persistée!

            System.out.println("Achat confirmé !");
        });

    }
}
