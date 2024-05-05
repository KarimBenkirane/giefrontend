package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Controllers.DTO.ProduitDTO;
import com.example.giefrontend1.Parser.ParserProduit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProduitController implements Initializable {

    @FXML
    public TableColumn<?, ?> actionProduitColumn;

    @FXML
    public Button advSearchBtn;

    @FXML
    public TableColumn<?, ?> categorieProduitColumn;

    @FXML
    public Button createProduitBtn;

    @FXML
    public TableColumn<?, ?> descriptionProduitColumn;

    @FXML
    public TableColumn<?, ?> idProduitColumn;

    @FXML
    public TableColumn<?, ?> marqueProduitColumn;

    @FXML
    public TableColumn<?, ?> modeleProduitColumn;

    @FXML
    public TableColumn<?, ?> prixProduitColumn;

    @FXML
    public TableColumn<?, ?> qtStockProduitColumn;

    @FXML
    public TableView<?> searchResultTableView;

    public RadioButton allRadioBtn;

    @FXML
    public ToggleGroup availableStockGroup;

    @FXML
    public TextField descriptionSearchTextField;

    @FXML
    public RadioButton dispoRadioBtn;

    @FXML
    public RadioButton indispoRadioBtn;

    @FXML
    public ComboBox<String> marqueSearchComboBox;

    @FXML
    public ComboBox<String> updateCategorieComboBox;

    @FXML
    public TextField modeleSearchTextField;

    @FXML
    public TextField prixMaxSearchTextField;

    @FXML
    public TextField prixMinSearchTextField;

    @FXML
    public TextField qtStockSupSearchTextField;

    @FXML
    public Button searchProductsBtn;

    @FXML
    public Button modifierProduitBtn;

    @FXML
    public TextField updateDescriptionTextField;

    @FXML
    public ComboBox<String> updateMarqueComboBox;

    @FXML
    public ComboBox<String> categorieSearchComboBox;


    @FXML
    public TextField updateModeleTextField;

    @FXML
    public TextField updatePrixTextField;

    @FXML
    public TextField updateQtStockTextField;

    @FXML
    public ComboBox<String> createCategorieComboBox;

    @FXML
    public TextField createDescriptionTextField;

    @FXML
    public ComboBox<String> createMarqueComboBox;

    @FXML
    public TextField createNewMarqueTextField;

    @FXML
    public TextField createModeleTextField;

    @FXML
    public TextField createPrixTextField;

    @FXML
    public TextField createQtStockTextField;

    @FXML
    public Button creerProduitBtn;



    public FXMLLoader loadScene(String fxmlName,String windowTitle) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.giefrontend1/Admin/"+fxmlName+".fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(windowTitle);
        stage.show();
        return loader;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void populateComboBoxMarquesCategories(String type,ComboBox<String> comboBox){
        if(type.equals("categories")){
            if (ParserProduit.getAllMarquesCategories("categories") != null) {
                ObservableList<String> categories = FXCollections.observableArrayList(ParserProduit.getAllMarquesCategories("categories"));
                comboBox.getItems().addAll(categories);
            }
        }
        else if(type.equals("marques")){
            if (ParserProduit.getAllMarquesCategories("marques") != null) {
                ObservableList<String> marques = FXCollections.observableArrayList(ParserProduit.getAllMarquesCategories("marques"));
                comboBox.getItems().addAll(marques);
            }
        }
    }

    public void onCreateProductBtn(ActionEvent e) throws IOException {
        FXMLLoader loader = this.loadScene("StockCreate","Créer un nouveau un produit");
        ProduitController produitController = loader.getController();
        populateComboBoxMarquesCategories("categories",produitController.createCategorieComboBox);
        populateComboBoxMarquesCategories("marques",produitController.createMarqueComboBox);

        produitController.creerProduitBtn.setOnAction(event -> {
            String marque = null;

            if (produitController.createNewMarqueTextField.getText().isEmpty() && produitController.createMarqueComboBox.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez choisir la marque parmi la liste ou en saisir une !");
                return;
            } else if (!produitController.createNewMarqueTextField.getText().isEmpty() && produitController.createMarqueComboBox.getValue() == null) {
                marque = produitController.createNewMarqueTextField.getText();
            } else if (produitController.createNewMarqueTextField.getText().isEmpty() && produitController.createMarqueComboBox.getValue() != null) {
                marque = produitController.createMarqueComboBox.getValue();
            } else if ( !(produitController.createNewMarqueTextField.getText().isEmpty()) && (produitController.createMarqueComboBox.getValue() != null) ) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez choisir la marque parmi la liste ou en saisir une ! Pas les deux à la fois !");
                return;
            }

            if (produitController.createModeleTextField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez ne pas laisser le champ du modèle vide !");
                return;
            }
            String modele = produitController.createModeleTextField.getText();


            if (produitController.createCategorieComboBox.getValue() == null || produitController.createCategorieComboBox.getValue().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez choisir la catégorie !");
                return;
            }
            String categorie = produitController.createCategorieComboBox.getValue();


            if (produitController.createPrixTextField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez ne pas laisser le champ du prix vide !");
                return; // Return if price is empty to avoid NumberFormatException
            }
            double prix = Double.parseDouble(produitController.createPrixTextField.getText());

            int qteStock = produitController.createQtStockTextField.getText().isEmpty() ? 0 : Integer.parseInt(produitController.createQtStockTextField.getText());
            String description = produitController.createDescriptionTextField.getText();
            ProduitDTO produitDTO = new ProduitDTO(marque, modele, description, categorie, qteStock, prix);
            boolean status = ParserProduit.createProduit(produitDTO);
            if (status) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit crée avec succès !");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la création du produit.");
            }
        });

    }

    public void onUpdateProductBtn(ActionEvent e) throws IOException {
        FXMLLoader loader = this.loadScene("StockUpdate","Mettre à jour un produit");
        ProduitController produitController = loader.getController();
        populateComboBoxMarquesCategories("categories",produitController.updateCategorieComboBox);
        populateComboBoxMarquesCategories("marques",produitController.updateMarqueComboBox);

        produitController.modifierProduitBtn.setOnAction(event -> {
            String categorie = produitController.updateCategorieComboBox.getValue();
            String marque = produitController.updateMarqueComboBox.getValue();
            String modele = produitController.updateModeleTextField.getText();
            double prix = produitController.updatePrixTextField.getText().isEmpty() ? 0 : Double.parseDouble(produitController.updatePrixTextField.getText());
            int qteStock = produitController.updateQtStockTextField.getText().isEmpty() ? 0 : Integer.parseInt(produitController.updateQtStockTextField.getText());
            String description = produitController.updateDescriptionTextField.getText();

            ProduitDTO produitDTO = new ProduitDTO(marque,modele,description,categorie,qteStock,prix);
            boolean status = ParserProduit.updateProduit(produitDTO,8);
            if (status){
                System.out.println("Success");
            }else{
                System.out.println("failed");
            }
        });


    }

    public void onSearchProductBtn(ActionEvent e) throws IOException {
        FXMLLoader loader = this.loadScene("StockSearch","Rechercher un produit");
        ProduitController produitController = loader.getController();
        populateComboBoxMarquesCategories("categories",produitController.categorieSearchComboBox);
        populateComboBoxMarquesCategories("marques",produitController.marqueSearchComboBox);

    }

}
