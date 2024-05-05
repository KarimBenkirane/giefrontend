package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Controllers.DTO.ProduitDTO;
import com.example.giefrontend1.Parser.ParserProduit;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
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
import javafx.stage.Stage;

import javax.swing.text.html.parser.Parser;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProduitController implements Initializable {

    @FXML
    public Button advSearchBtn;

    @FXML
    public Button createProduitBtn;

    @FXML
    public TableColumn<ProduitDTO, Long> idProduitColumn;

    @FXML
    public TableColumn<ProduitDTO, String> marqueProduitColumn;

    @FXML
    public TableColumn<ProduitDTO, String> modeleProduitColumn;

    @FXML
    public TableColumn<ProduitDTO, String> descriptionProduitColumn;

    @FXML
    public TableColumn<ProduitDTO, String> categorieProduitColumn;

    @FXML
    public TableColumn<ProduitDTO, Integer> qtStockProduitColumn;

    @FXML
    public TableColumn<ProduitDTO, Double> prixProduitColumn;

    @FXML
    public TableView<ProduitDTO> searchResultTableView;

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
    private static boolean initialized = false;


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

        if(!initialized){// Set cell value factories for each column
            idProduitColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
            marqueProduitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarque()));
            modeleProduitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModele()));
            descriptionProduitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
            categorieProduitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategorie()));
            qtStockProduitColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQteStock()).asObject());
            prixProduitColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrix()).asObject());

            // Populate TableView with data
            List<ProduitDTO> produits = ParserProduit.getAllProduits();
            searchResultTableView.getItems().addAll(produits);
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

            double prix = 0;
            // Get the price text from the text field
            String priceText = produitController.createPrixTextField.getText();

            // Check if the text is empty or null
            if (!priceText.isEmpty()) {
                try {
                    // Try parsing the text to a double
                    double price = Double.parseDouble(priceText);

                    // Check if the parsed number is positive
                    if (price > 0) {
                        // The price is valid, assign it to the prix variable
                        prix = price;
                    } else {
                        // Show an error message for non-positive price
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Le prix doit être un nombre positif.");
                        return; // Return to stop further execution
                    }
                } catch (NumberFormatException nfe) {
                    // Show an error message for invalid number format
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Le prix doit être un nombre valide.");
                    return; // Return to stop further execution
                }
            } else {
                // Show an error message for empty price
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir le prix.");
                return; // Return to stop further execution
            }


            // Get the quantity text from the text field
            String quantityText = produitController.createQtStockTextField.getText();

            // Check if the text is empty or null, and set qteStock to 0 as default
            int qteStock = 0;

            if (!quantityText.isEmpty()) {
                try {
                    // Try parsing the text to an integer
                    int quantity = Integer.parseInt(quantityText);

                    // Check if the parsed integer is positive
                    if (quantity >= 0) {
                        // Set qteStock to the parsed integer
                        qteStock = quantity;
                    } else {
                        // Show an error message for negative quantity
                        showAlert(Alert.AlertType.ERROR, "Erreur", "La quantité doit être un nombre entier positif ou 0.");
                        return; // Return to stop further execution
                    }
                } catch (NumberFormatException nfex) {
                    // Show an error message for invalid integer format
                    showAlert(Alert.AlertType.ERROR, "Erreur", "La quantité doit être un nombre entier positif ou 0.");
                    return; // Return to stop further execution
                }
            }

            String description = produitController.createDescriptionTextField.getText();
            ProduitDTO produitDTO = new ProduitDTO(marque, modele, description, categorie, qteStock, prix);
            boolean status = ParserProduit.createProduit(produitDTO);
            if (status) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit crée avec succès !");
                ProduitController.this.searchResultTableView.getItems().addAll(ParserProduit.getAllProduits());
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
