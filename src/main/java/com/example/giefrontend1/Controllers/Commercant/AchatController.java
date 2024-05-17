package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Controllers.DTO.AchatDTO;
import com.example.giefrontend1.Controllers.DTO.ContactDTO;
import com.example.giefrontend1.Controllers.DTO.DetailAchatDTO;
import com.example.giefrontend1.Controllers.DTO.ProduitDTO;
import com.example.giefrontend1.Parser.ParserAchat;
import com.example.giefrontend1.Parser.ParserContact;
import com.example.giefrontend1.Parser.ParserProduit;
import com.google.gson.JsonArray;
import javafx.beans.property.*;
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

import javax.swing.text.html.parser.Parser;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class AchatController implements Initializable {

    private Stage changeStatutStage;
    private Stage resumeAchatStage;
    private AchatDTO selectedAchatToResume;
    private AchatDTO selectedAchatToUpdateStatus;
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
    public Button retourBtn;

    @FXML
    public Button saveAchatBtn;

    @FXML
    public Spinner<Integer> spinnerQt;

    private static boolean initialized = false;


    @FXML
    public TableView<DetailAchatDTO> repriseDetailsAchatsTableView;

    @FXML
    public TableColumn<DetailAchatDTO, Double> repriseDetailsPrixColumn;

    @FXML
    public TableColumn<DetailAchatDTO, Double> repriseDetailsPrixUnitaireColumn;

    @FXML
    public TableColumn<DetailAchatDTO, String> repriseDetailsProduitColumn;

    @FXML
    public TableColumn<DetailAchatDTO, Integer> repriseDetailsQtAcheteeColumn;

    @FXML
    public TableColumn<DetailAchatDTO, String> repriseDetailsReductionColumn;


    @FXML
    public TableView<ProduitDTO> repriseproduitTableView;
    @FXML
    public TableColumn<ProduitDTO,String> repriseProductsModeleProduitColumn;

    @FXML
    public TableColumn<ProduitDTO,Integer> repriseProductsQtStockProduitColumn;

    @FXML
    public TableColumn<ProduitDTO,String> repriseProductsmarqueProduitColumn;

    @FXML
    public TableColumn<ProduitDTO,Double> repriseProductsprixProduitColumn;

    @FXML
    public TableColumn<ProduitDTO,String> repriseproductsCategorieProduitColumn;

    @FXML
    public TableColumn<ProduitDTO,String> repriseproductsDescriptionProduitColumn;

    @FXML
    public TableColumn<ProduitDTO,Long> repriseproductsIdProduitColumn;




    @FXML
    public Button repriseaddToBasketBtn;

    @FXML
    public Button repriseconfirmAchatBtn;

    @FXML
    public Button reprisenextBtn;





    @FXML
    public TextField reprisereductionTextField;

    @FXML
    public Button repriseretourBtn;

    @FXML
    public Button reprisesaveAchatBtn;

    @FXML
    public Spinner<Integer> reprisespinnerQt;

    @FXML
    public Label reprisestatusLabel;


    @FXML
    public Button RechercherButton;

    @FXML
    public DatePicker apresDatePicker;

    @FXML
    public DatePicker avantDatePicker;

    @FXML
    public ComboBox<String> fournisseurSearchComboBox;

    @FXML
    public TextField prixMaxSearchTextField;

    @FXML
    public TextField prixMinSearchTextField;

    @FXML
    public ComboBox<String> statutSearchComboBox;


    @FXML
    public Button confirmAchatBtn;
    @FXML
    public Button nextBtn;
    @FXML
    public Label statusLabel;
    private int selected_fournisseur_id;
    private List<ProduitDTO> liste_produits_achat;
    private long selected_produit_id;
    private long selected_produit_id_reprise;
    private List<DetailAchatDTO> detailsAchats = new ArrayList<>();
    private List<DetailAchatDTO> detailsAchatsReprise = new ArrayList<>();
    @FXML
    public Button updateStatusButton;

    @FXML
    public ComboBox<String> updateStatusComboBox;
    private Stage createAchatStage;


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


                        achatController.mesDetailsProduitColumn.setCellValueFactory(cellData -> {
                            ProduitDTO produitDTO = cellData.getValue().getProduitObjet();
                            if (produitDTO != null) {
                                return new SimpleStringProperty(produitDTO.getMarque() + " " + produitDTO.getModele());
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
            // Create "Edit" column with buttons dynamically
            TableColumn<AchatDTO, Void> editColumn = new TableColumn<>("Modifier statut");
            editColumn.setCellFactory(param -> new TableCell<>() {
                private final Button editButton = new Button("Modifier statut");

                {
                    editButton.setOnAction(event -> {
                        AchatDTO selectedAchat = getTableView().getItems().get(getIndex());
                        if(!selectedAchat.getStatutAchat().equals("CONFIRMÉ")){
                            showAlert(Alert.AlertType.ERROR,"Erreur","Vous ne pouvez pas modifier le statut de cet achat !");
                        }else{
                            AchatController.this.selectedAchatToUpdateStatus = selectedAchat;
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.giefrontend1/Admin/MyPurchasesUpdate.fxml"));
                            Parent root = null;
                            try {
                                root = loader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            AchatController achatController = loader.getController();
                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            AchatController.this.changeStatutStage = stage;
                            stage.setScene(scene);
                            stage.setTitle("Modifier statut achat");
                            stage.show();
                            achatController.updateStatusComboBox.setValue("LIVRÉ");
                            achatController.updateStatusComboBox.getItems().add("ANNULÉ");
                            achatController.updateStatusComboBox.getItems().add("LIVRÉ");;

                            achatController.updateStatusButton.setOnAction(evnt -> {
                                String statut = achatController.updateStatusComboBox.getValue();
                                AchatController.this.selectedAchatToUpdateStatus.setStatutAchat(statut);

                                boolean status = ParserAchat.updateAchat(AchatController.this.selectedAchatToUpdateStatus,AchatController.this.selectedAchatToUpdateStatus.getId());
                                if(status){
                                    showAlert(Alert.AlertType.INFORMATION,"Succès","Statut modifié avec succès !");
                                    AchatController.this.changeStatutStage.close();
                                    AchatController.this.mesAchatsTableView.getItems().clear();
                                    AchatController.this.mesAchatsTableView.getItems().setAll(ParserAchat.getAllAchats());
                                    AchatController.this.mesAchatsTableView.refresh();
                                }else{
                                    showAlert(Alert.AlertType.ERROR,"Erreur","Une erreur s'est produite lors de la modification du statut.");
                                }
                            });
                        }

                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(editButton);
                    }
                }
            });

            mesAchatsTableView.getColumns().add(editColumn);

            TableColumn<AchatDTO, Void> reprendreColumn = new TableColumn<>("Reprendre achat");
            reprendreColumn.setCellFactory(param -> new TableCell<>() {
                private final Button reprendreButton = new Button("Reprendre achat");

                {
                    reprendreButton.setOnAction(event -> {
                        AchatDTO selectedAchat = getTableView().getItems().get(getIndex());
                        AchatController.this.selectedAchatToResume = selectedAchat;
                        if(!selectedAchat.getStatutAchat().equals("INITIALISÉ")){
                            showAlert(Alert.AlertType.ERROR,"Erreur","Vous ne pouvez pas modifier les détails de cet achat !");
                        }else{
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.giefrontend1/Admin/MyPurchasesResume.fxml"));
                            try {
                                Parent root = loader.load();
                                Stage stage  = new Stage();
                                AchatController.this.resumeAchatStage = stage;
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.setTitle("Reprendre mon achat");
                                stage.show();

                                stage.setOnCloseRequest(evnt -> AchatController.this.detailsAchatsReprise.clear());

                                AchatController achatController = loader.getController();




                                achatController.repriseDetailsPrixColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrixAchat()).asObject());
                                achatController.repriseDetailsPrixUnitaireColumn.setCellValueFactory(data -> {
                                    ProduitDTO produitDTO = data.getValue().getProduitObjet();
                                    DoubleProperty prixProperty = new SimpleDoubleProperty(produitDTO.getPrix());
                                    return prixProperty.asObject();
                                });
                                achatController.repriseDetailsProduitColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduitObjet().toString()));
                                achatController.repriseDetailsQtAcheteeColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQteAchetee()).asObject());
                                achatController.repriseDetailsReductionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReduction()*100 +"%"));
                                achatController.repriseDetailsProduitColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduitObjet().getMarque()+ " " +data.getValue().getProduitObjet().getModele()));

                                // Retrieve data from wherever you get it

                                List<DetailAchatDTO> detailsAchats = selectedAchat.getDetailsAchat();
                                AchatController.this.detailsAchatsReprise.addAll(detailsAchats);
                                System.out.println(detailsAchats);

                                // Populate the TableView with data
                                ObservableList<DetailAchatDTO> detailsAchatsList = FXCollections.observableArrayList(detailsAchats);
                                achatController.repriseDetailsAchatsTableView.setItems(detailsAchatsList);

                                TableColumn<DetailAchatDTO, Void> deleteColumn = new TableColumn<>("Supprimer produit");
                                deleteColumn.setCellFactory(param -> new TableCell<>() {
                                    private final Button deleteButton = new Button("Supprimer");

                                    {
                                        deleteButton.setOnAction(event -> {
                                            DetailAchatDTO selectedDetailAchat = getTableView().getItems().get(getIndex());
                                            System.out.println(selectedDetailAchat.getId());
                                            AchatController.this.detailsAchatsReprise.remove(selectedDetailAchat);
                                            achatController.repriseDetailsAchatsTableView.getItems().clear();
                                            achatController.repriseDetailsAchatsTableView.getItems().addAll(AchatController.this.detailsAchatsReprise);
                                            achatController.repriseDetailsAchatsTableView.refresh();
                                            showAlert(Alert.AlertType.INFORMATION,"Succès","Produit supprimé avec succès !");


                                        });
                                    }

                                    @Override
                                    protected void updateItem(Void item, boolean empty) {
                                        super.updateItem(item, empty);
                                        if (empty) {
                                            setGraphic(null);
                                        } else {
                                            setGraphic(deleteButton);
                                        }
                                    }
                                });

                                achatController.repriseDetailsAchatsTableView.getColumns().add(deleteColumn);

                                //Populate Products

                                achatController.repriseProductsModeleProduitColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getModele()));
                                achatController.repriseProductsQtStockProduitColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQteStock()).asObject());
                                achatController.repriseProductsmarqueProduitColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMarque()));
                                achatController.repriseProductsprixProduitColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrix()).asObject());
                                achatController.repriseproductsCategorieProduitColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategorie()));
                                achatController.repriseproductsDescriptionProduitColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));
                                achatController.repriseproductsIdProduitColumn.setCellValueFactory(data -> new SimpleLongProperty(data.getValue().getId()).asObject());

                                // Retrieve data from wherever you get it
                                List<ProduitDTO> produits = ParserProduit.getAllProduits();

                                // Populate the TableView with data
                                ObservableList<ProduitDTO> produitsList = FXCollections.observableArrayList(produits);
                                achatController.repriseproduitTableView.setItems(produitsList);

                                achatController.repriseproduitTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProduitDTO>() {
                                    @Override
                                    public void changed(ObservableValue<? extends ProduitDTO> observable, ProduitDTO oldValue, ProduitDTO newValue) {
                                        if (newValue != null) {
                                            long selected_produit_id_reprise = newValue.getId();
                                            AchatController.this.selected_produit_id_reprise = selected_produit_id_reprise;
                                        }
                                    }
                                });


                                achatController.repriseaddToBasketBtn.setOnAction(evnt -> {
                                    if(achatController.repriseproduitTableView.getSelectionModel().getSelectedItem() == null){
                                        showAlert(Alert.AlertType.ERROR,"Erreur","Veuillez sélectionner un produit avant de l'ajouter au panier !");
                                    }else{
                                        int qtAchetee = achatController.reprisespinnerQt.getValue();
                                        ProduitDTO produitDTO = ParserProduit.getProduitByID(AchatController.this.selected_produit_id_reprise);
                                        double prix_achat = (produitDTO.getPrix() * qtAchetee);
                                        double reduction = 0;
                                        if(achatController.reprisereductionTextField.getText().isEmpty()){
                                            reduction = 0;
                                        }else{
                                            try{
                                                reduction = Double.parseDouble(achatController.reprisereductionTextField.getText()) / 100;
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
                                        boolean exists = false;
                                        for(DetailAchatDTO detailAchatDTO : AchatController.this.detailsAchatsReprise){
                                            if( (detailAchatDTO.getProduitObjet().getModele().equals(produitDTO.getModele())) && (detailAchatDTO.getProduitObjet().getDescription().equals(produitDTO.getDescription())) && (detailAchatDTO.getReduction() == reduction) && (detailAchatDTO.getProduitObjet().getMarque().equals(produitDTO.getMarque())) ){
                                                exists = true;
                                                showAlert(Alert.AlertType.INFORMATION,"Succès","Produit ajouté au panier avec succès !");
                                                detailAchatDTO.setQteAchetee(detailAchatDTO.getQteAchetee() + qtAchetee);
                                                detailAchatDTO.setPrixAchat(detailAchatDTO.getPrixAchat() + prix_reduit);
                                                System.out.println(detailAchatDTO);
                                                break;
                                            }
                                        }
                                        if(!exists){
                                            DetailAchatDTO detailAchatDTO = new DetailAchatDTO(produitDTO,qtAchetee,prix_reduit,reduction);
                                            AchatController.this.detailsAchatsReprise.add(detailAchatDTO);
                                            System.out.println(detailAchatDTO);
                                            showAlert(Alert.AlertType.INFORMATION,"Succès","Produit ajouté au panier avec succès !");
                                        }  }
                                });

                                achatController.repriseretourBtn.setOnAction(evt ->{
                                    if(achatController.repriseDetailsAchatsTableView.isVisible()){
                                        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
                                        achatController.reprisespinnerQt.setValueFactory(valueFactory);
                                        achatController.reprisespinnerQt.setVisible(true);
                                        achatController.repriseDetailsAchatsTableView.setVisible(false);
                                        achatController.repriseproduitTableView.setVisible(true);
                                        achatController.reprisenextBtn.setVisible(true);
                                        achatController.repriseretourBtn.setVisible(true);
                                        achatController.repriseretourBtn.setDisable(true);
                                        achatController.reprisestatusLabel.setText("Veuillez choisir vos produits");
                                        achatController.reprisereductionTextField.setVisible(true);
                                        achatController.reprisespinnerQt.setVisible(true);
                                        achatController.repriseaddToBasketBtn.setVisible(true);
                                        achatController.repriseconfirmAchatBtn.setVisible(false);
                                        achatController.reprisesaveAchatBtn.setVisible(false);
                                    }
                                });

                                achatController.reprisenextBtn.setOnAction(evt -> {

                                    if (achatController.repriseproduitTableView.isVisible()) {
                                        if(AchatController.this.detailsAchatsReprise.isEmpty()){
                                            showAlert(Alert.AlertType.ERROR,"Erreur","Veuillez choisir des produits avant de procéder !");
                                            return;
                                        }
                                        achatController.repriseDetailsAchatsTableView.getItems().clear();
                                        achatController.repriseDetailsAchatsTableView.refresh();
                                        achatController.repriseDetailsAchatsTableView.getItems().setAll(AchatController.this.detailsAchatsReprise);
                                        achatController.repriseretourBtn.setVisible(true);
                                        achatController.repriseretourBtn.setDisable(false);
                                        achatController.reprisestatusLabel.setText("Résumé de votre achat");
                                        achatController.reprisereductionTextField.setVisible(false);
                                        achatController.repriseconfirmAchatBtn.setVisible(true);
                                        achatController.reprisesaveAchatBtn.setVisible(true);
                                        achatController.reprisenextBtn.setVisible(false);
                                        achatController.reprisespinnerQt.setVisible(false);
                                        achatController.repriseaddToBasketBtn.setVisible(false);
                                        achatController.repriseproduitTableView.setVisible(false);
                                        achatController.repriseDetailsAchatsTableView.setVisible(true);

                                    }
                                });

                                achatController.reprisesaveAchatBtn.setOnAction(evnt -> {
                                    AchatDTO achatDTO = AchatController.this.selectedAchatToResume;
                                    double prix_total = 0;
                                    for(DetailAchatDTO detailAchatDTO : AchatController.this.detailsAchatsReprise){
                                        prix_total += detailAchatDTO.getPrixAchat();
                                    }
                                    achatDTO.setPrix(prix_total);
                                    achatDTO.setStatutAchat("INITIALISÉ");
                                    achatDTO.setDetailsAchat(AchatController.this.detailsAchatsReprise);
                                    System.out.println(achatDTO);
                                    boolean status = ParserAchat.updateAchat(achatDTO,achatDTO.getId());
                                    if(status) {
                                        showAlert(Alert.AlertType.INFORMATION, "Succès", "Achat sauvegardé avec succès !");
                                        achatController.repriseconfirmAchatBtn.setDisable(true);
                                        achatController.reprisesaveAchatBtn.setDisable(true);
                                        achatController.repriseretourBtn.setDisable(true);
                                        achatController.repriseDetailsAchatsTableView.setDisable(true);
                                        AchatController.this.resumeAchatStage.close();
                                        AchatController.this.mesAchatsTableView.getItems().clear();
                                        AchatController.this.mesAchatsTableView.getItems().addAll(ParserAchat.getAllAchats());
                                        AchatController.this.mesAchatsTableView.refresh();
                                    }else{
                                        showAlert(Alert.AlertType.ERROR,"Erreur","Une erreur s'est produite lors de la sauvegarde de cet achat.");
                                    }
                                });
//
//
                                achatController.repriseconfirmAchatBtn.setOnAction(evt -> {
                                    // Créer une alerte de confirmation
                                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                                    confirmationAlert.setTitle("Confirmation de la reprise d'achat");
                                    confirmationAlert.setHeaderText("Voulez-vous vraiment confirmer cette reprise d'achat ?");
                                    confirmationAlert.setContentText("Cliquez sur OK pour confirmer ou sur Annuler pour annuler.");

                                    // Obtenir la réponse de l'utilisateur
                                    Optional<ButtonType> result = confirmationAlert.showAndWait();
                                    if (result.isPresent() && result.get() == ButtonType.OK) {
                                        // L'utilisateur a cliqué sur OK, continuer avec le traitement de la reprise d'achat
                                        AchatDTO achatDTO = AchatController.this.selectedAchatToResume;
                                        double prix_total = 0;
                                        for (DetailAchatDTO detailAchatDTO : AchatController.this.detailsAchatsReprise) {
                                            ProduitDTO produitDTO = detailAchatDTO.getProduitObjet();
                                            long produit_id = produitDTO.getId();
                                            int qteAchetee = detailAchatDTO.getQteAchetee();
                                            produitDTO.setQteStock(produitDTO.getQteStock() + qteAchetee);
                                            ParserProduit.updateProduit(produitDTO, produit_id);
                                            prix_total += detailAchatDTO.getPrixAchat();
                                        }
                                        achatDTO.setPrix(prix_total);
                                        achatDTO.setStatutAchat("CONFIRMÉ");
                                        System.out.println(achatDTO);
                                        boolean status = ParserAchat.updateAchat(achatDTO, achatDTO.getId());
                                        if (status) {
                                            showAlert(Alert.AlertType.INFORMATION, "Succès", "Achat ajouté avec succès !");
                                            achatController.repriseconfirmAchatBtn.setDisable(true);
                                            achatController.reprisesaveAchatBtn.setDisable(true);
                                            achatController.repriseretourBtn.setDisable(true);
                                            achatController.repriseDetailsAchatsTableView.setDisable(true);
                                            AchatController.this.resumeAchatStage.close();
                                            AchatController.this.mesAchatsTableView.getItems().clear();
                                            AchatController.this.mesAchatsTableView.getItems().addAll(ParserAchat.getAllAchats());
                                            AchatController.this.mesAchatsTableView.refresh();
                                        } else {
                                            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'ajout de cet achat.");
                                        }
                                    }
                                });



                            } catch (IOException e) {
                                throw new RuntimeException(e);
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
                        setGraphic(reprendreButton);
                    }
                }
            });

            mesAchatsTableView.getColumns().add(reprendreColumn);

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

    public void onRefreshAchats(){
        AchatController.this.mesAchatsTableView.getItems().clear();
        AchatController.this.mesAchatsTableView.getItems().addAll(ParserAchat.getAllAchats());
        AchatController.this.mesAchatsTableView.refresh();
    }

    public void onCreateAchat(ActionEvent e) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.giefrontend1/Admin/MyPurchasesCreate.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        AchatController.this.createAchatStage = stage;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Créer un nouvel achat");
        stage.show();

        stage.setOnCloseRequest(event -> AchatController.this.detailsAchats.clear());


        AchatController achatController = loader.getController();

        TableColumn<DetailAchatDTO, Void> deleteColumn = new TableColumn<>("Supprimer produit");
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    DetailAchatDTO selectedDetailAchat = getTableView().getItems().get(getIndex());
                    System.out.println(selectedDetailAchat.getId());
                    AchatController.this.detailsAchats.remove(selectedDetailAchat);
                    achatController.DetailsAchatsTableView.getItems().clear();
                    achatController.DetailsAchatsTableView.getItems().addAll(AchatController.this.detailsAchats);
                    achatController.DetailsAchatsTableView.refresh();
                    showAlert(Alert.AlertType.INFORMATION,"Succès","Produit supprimé avec succès !");


                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        achatController.DetailsAchatsTableView.getColumns().add(deleteColumn);

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
                boolean exists = false;
                for(DetailAchatDTO detailAchatDTO : AchatController.this.detailsAchats){
                    if( (detailAchatDTO.getProduitObjet().getModele().equals(produitDTO.getModele())) && (detailAchatDTO.getProduitObjet().getDescription().equals(produitDTO.getDescription())) && (detailAchatDTO.getReduction() == reduction) && (detailAchatDTO.getProduitObjet().getMarque().equals(produitDTO.getMarque())) ){
                        exists = true;
                        showAlert(Alert.AlertType.INFORMATION,"Succès","Produit ajouté au panier avec succès !");
                        detailAchatDTO.setQteAchetee(detailAchatDTO.getQteAchetee() + qtAchetee);
                        detailAchatDTO.setPrixAchat(detailAchatDTO.getPrixAchat() + prix_reduit);
                        System.out.println(detailAchatDTO);
                        break;
                    }
                }
                if(!exists){
                    DetailAchatDTO detailAchatDTO = new DetailAchatDTO(produitDTO,qtAchetee,prix_reduit,reduction);
                    AchatController.this.detailsAchats.add(detailAchatDTO);
                    System.out.println(detailAchatDTO);
                    showAlert(Alert.AlertType.INFORMATION,"Succès","Produit ajouté au panier avec succès !");
                }

            }
        });




        achatController.retourBtn.setOnAction(event ->{
            if(achatController.produitTableView.isVisible()){
                achatController.produitTableView.setVisible(false);
                achatController.contactsTableView.setVisible(true);
                achatController.retourBtn.setVisible(false);
                achatController.reductionTextField.setVisible(false);
                achatController.spinnerQt.setVisible(false);
                achatController.addToBasketBtn.setVisible(false);
                achatController.retourBtn.setVisible(true);
                achatController.retourBtn.setDisable(true);
                achatController.statusLabel.setText("Veuillez choisir votre fournisseur");
            }else if(achatController.DetailsAchatsTableView.isVisible()){
                achatController.DetailsAchatsTableView.setVisible(false);
                achatController.produitTableView.setVisible(true);
                achatController.nextBtn.setVisible(true);
                achatController.statusLabel.setText("Veuillez choisir vos produits");
                achatController.reductionTextField.setVisible(true);
                achatController.spinnerQt.setVisible(true);
                achatController.addToBasketBtn.setVisible(true);
                achatController.confirmAchatBtn.setVisible(false);
                achatController.saveAchatBtn.setVisible(false);
            }
        });

        achatController.nextBtn.setOnAction(event -> {

            if (achatController.contactsTableView.isVisible()) {
                if (achatController.contactsTableView.getSelectionModel().getSelectedItem() == null) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez choisir un fournisseur avant de procéder !");
                } else {
                    ContactDTO fournisseur = ParserContact.getContactByID(AchatController.this.selected_fournisseur_id);
                    System.out.println(fournisseur);
                    achatController.statusLabel.setText("Veuillez choisir vos produits");
                    achatController.retourBtn.setDisable(false);
                    achatController.reductionTextField.setVisible(true);
                    achatController.contactsTableView.setVisible(false);
                    achatController.produitTableView.setVisible(true);
                    achatController.addToBasketBtn.setVisible(true);
                    achatController.retourBtn.setVisible(true);
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

                achatController.DetailsAchatsTableView.getItems().clear();
                achatController.DetailsAchatsTableView.refresh();

                achatController.DetailsPrixColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPrixAchat()).asObject());
                achatController.DetailsPrixUnitaireColumn.setCellValueFactory(data -> {
                    ProduitDTO produitDTO = data.getValue().getProduitObjet();
                    DoubleProperty prixProperty = new SimpleDoubleProperty(produitDTO.getPrix());
                    return prixProperty.asObject();
                });
                achatController.DetailsProduitColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduitObjet().toString()));
                achatController.DetailsQtAcheteeColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQteAchetee()).asObject());
                achatController.DetailsReductionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReduction()*100 +"%"));
                achatController.DetailsProduitColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduitObjet().getMarque()+ " " +data.getValue().getProduitObjet().getModele()));

                // Retrieve data from wherever you get it
                List<DetailAchatDTO> detailsAchats = AchatController.this.detailsAchats;
                System.out.println(detailsAchats);

                // Populate the TableView with data
                ObservableList<DetailAchatDTO> detailsAchatsList = FXCollections.observableArrayList(detailsAchats);
                achatController.DetailsAchatsTableView.setItems(detailsAchatsList);


                achatController.statusLabel.setText("Résumé de votre achat");
                achatController.reductionTextField.setVisible(false);
                achatController.confirmAchatBtn.setVisible(true);
                achatController.saveAchatBtn.setVisible(true);
                achatController.nextBtn.setVisible(false);
                achatController.spinnerQt.setVisible(false);
                achatController.addToBasketBtn.setVisible(false);
                achatController.produitTableView.setVisible(false);
                achatController.DetailsAchatsTableView.setVisible(true);

            }
        });

        achatController.saveAchatBtn.setOnAction(event -> {
            ContactDTO fournisseur = ParserContact.getContactByID(AchatController.this.selected_fournisseur_id);
            AchatDTO achatDTO = new AchatDTO(fournisseur,AchatController.this.detailsAchats, String.valueOf(LocalDate.now()),0,"INITIALISÉ");
            double prix_total = 0;
            for(DetailAchatDTO detailAchatDTO : AchatController.this.detailsAchats){
                prix_total += detailAchatDTO.getPrixAchat();
            }
            achatDTO.setPrix(prix_total);
            System.out.println(achatDTO);
            boolean status = ParserAchat.createAchat(achatDTO);
            if(status) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Achat sauvegardé avec succès !");
                achatController.confirmAchatBtn.setDisable(true);
                achatController.saveAchatBtn.setDisable(true);
                achatController.retourBtn.setDisable(true);
                AchatController.this.createAchatStage.close();
                AchatController.this.mesAchatsTableView.getItems().clear();
                AchatController.this.mesAchatsTableView.getItems().addAll(ParserAchat.getAllAchats());
                AchatController.this.mesAchatsTableView.refresh();
            }else{
                showAlert(Alert.AlertType.ERROR,"Erreur","Une erreur s'est produite lors de la sauvegarde de cet achat.");
            }
        });


        achatController.confirmAchatBtn.setOnAction(event -> {
            // Créer une alerte de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de l'achat");
            confirmationAlert.setHeaderText("Voulez-vous vraiment confirmer cet achat ?");
            confirmationAlert.setContentText("Cliquez sur OK pour confirmer ou sur Annuler pour annuler.");

            // Obtenir la réponse de l'utilisateur
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // L'utilisateur a cliqué sur OK, continuer avec le traitement de l'achat
                ContactDTO fournisseur = ParserContact.getContactByID(AchatController.this.selected_fournisseur_id);
                AchatDTO achatDTO = new AchatDTO(fournisseur, AchatController.this.detailsAchats, String.valueOf(LocalDate.now()), 0, "CONFIRMÉ");
                double prix_total = 0;
                for (DetailAchatDTO detailAchatDTO : AchatController.this.detailsAchats) {
                    ProduitDTO produitDTO = detailAchatDTO.getProduitObjet();
                    long produit_id = produitDTO.getId();
                    int qteAchetee = detailAchatDTO.getQteAchetee();
                    produitDTO.setQteStock(produitDTO.getQteStock() + qteAchetee);
                    ParserProduit.updateProduit(produitDTO, produit_id);
                    prix_total += detailAchatDTO.getPrixAchat();
                }
                achatDTO.setPrix(prix_total);
                System.out.println(achatDTO);
                boolean status = ParserAchat.createAchat(achatDTO);
                if (status) {
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Achat ajouté avec succès !");
                    achatController.confirmAchatBtn.setDisable(true);
                    achatController.saveAchatBtn.setDisable(true);
                    achatController.retourBtn.setDisable(true);
                    AchatController.this.createAchatStage.close();
                    AchatController.this.mesAchatsTableView.getItems().clear();
                    AchatController.this.mesAchatsTableView.getItems().addAll(ParserAchat.getAllAchats());
                    AchatController.this.mesAchatsTableView.refresh();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'ajout de cet achat.");
                }
            }
        });


    }

    public void onAdvSearchBtn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.giefrontend1/Admin/MyPurchasesSearch.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Recherche avancée des achats");
        stage.show();
        AchatController achatController = loader.getController();

        stage.setOnCloseRequest(event -> {
            AchatController.this.mesAchatsTableView.getItems().clear();
            AchatController.this.mesAchatsTableView.getItems().addAll(ParserAchat.getAllAchats());
            AchatController.this.mesAchatsTableView.refresh();
        });

        achatController.fournisseurSearchComboBox.setValue("Tous les fournisseurs");
        achatController.fournisseurSearchComboBox.getItems().add("Tous les fournisseurs");
        achatController.fournisseurSearchComboBox.getItems().addAll(ParserAchat.getAllFournisseursAchats());


        achatController.statutSearchComboBox.setValue("Tous les statuts");
        achatController.statutSearchComboBox.getItems().add("Tous les statuts");
        achatController.statutSearchComboBox.getItems().addAll(Arrays.asList("INITIALISÉ","ANNULÉ","CONFIRMÉ","LIVRÉ"));


        achatController.RechercherButton.setOnAction(event -> {
            String fournisseur = achatController.fournisseurSearchComboBox.getValue();
            String statut = achatController.statutSearchComboBox.getValue();
            LocalDate localDateApres = achatController.apresDatePicker.getValue();
            LocalDate localDateAvant = achatController.avantDatePicker.getValue();
            String dateApres = null;
            String dateAvant = null;

            if(localDateApres != null){
                dateApres = String.valueOf(localDateApres);
            }
            if(localDateAvant != null){
                dateAvant = String.valueOf(localDateAvant);
            }

            String prixMin = achatController.prixMinSearchTextField.getText().isEmpty() ?
                    null:
                    achatController.prixMinSearchTextField.getText();

            String prixMax = achatController.prixMaxSearchTextField.getText().isEmpty() ?
                    null:
                    achatController.prixMaxSearchTextField.getText();

            Map<String,String> searchMap = new HashMap<>();
            if(!fournisseur.equals("Tous les fournisseurs")){
                searchMap.put("fournisseur",fournisseur);
            }
            if(!statut.equals("Tous les statuts")){
                searchMap.put("statut",statut);
            }
            searchMap.put("dateApres",dateApres);
            searchMap.put("dateAvant",dateAvant);
            searchMap.put("prixMin",prixMin);
            searchMap.put("prixMax",prixMax);


            List<AchatDTO> achats = ParserAchat.getAdvSearch(searchMap);
            if(achats == null){
                showAlert(Alert.AlertType.ERROR,"Erreur","Une erreur s'est produite.");
            }else if (achats.isEmpty()){
                showAlert(Alert.AlertType.INFORMATION,"Information","Aucun achat trouvé ayant ces critères");
                AchatController.this.mesAchatsTableView.getItems().clear();
            }else{
                showAlert(Alert.AlertType.INFORMATION,"Succès","Achats trouvés !");
                stage.close();
                AchatController.this.mesAchatsTableView.getItems().clear();
                AchatController.this.mesAchatsTableView.getItems().addAll(achats);
                AchatController.this.mesAchatsTableView.refresh();
            }

        });




    }


}
