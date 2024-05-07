package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Controllers.DTO.AchatDTO;
import com.example.giefrontend1.Controllers.DTO.ContactDTO;
import com.example.giefrontend1.Controllers.DTO.DetailAchatDTO;
import com.example.giefrontend1.Controllers.DTO.ProduitDTO;
import com.example.giefrontend1.Parser.ParserAchat;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


    private static boolean initialized = false;


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


}
