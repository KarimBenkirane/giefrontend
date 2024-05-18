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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.text.html.parser.Parser;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

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
    public TextArea descriptionSearchTextArea;

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
    public TextArea updateDescriptionTextArea;

    @FXML
    public ComboBox<String> updateMarqueComboBox;

//    @FXML
//    public CheckBox accessChk;
//
//    @FXML
//    public CheckBox appvChk;
//
//    @FXML
//    public CheckBox avChk;
//
//    @FXML
//    public CheckBox compoChk;
//
//    @FXML
//    public CheckBox imprChk;
//
//    @FXML
//    public CheckBox logChk;
//
//    @FXML
//    public CheckBox ordTabChk;
//
//    @FXML
//    public CheckBox otherChk;
//
//    @FXML
//    public CheckBox periphChk;
//
//    @FXML
//    public CheckBox rsxChk;
//
//    @FXML
//    public CheckBox sphoneChk;

    @FXML
    public TextField updateModeleTextField;

    @FXML
    public TextField updatePrixTextField;

    @FXML
    public TextField updateQtStockTextField;

    @FXML
    public ComboBox<String> createCategorieComboBox;

    @FXML
    public TextArea createDescriptionTextArea;

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

    @FXML
    public Button refreshBtn;

    @FXML
    public MenuButton SearchCategoriesMenuButton;

    private static boolean initialized = false;
    private List<CheckMenuItem> catMenuItems;


    public FXMLLoader loadScene(String fxmlName,String windowTitle) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.giefrontend1/Admin/"+fxmlName+".fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(windowTitle);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        stage.show();
        return loader;
    }

    public List<CheckMenuItem> getAllCategoriesFromMenuButton(MenuButton menuButton) {
        List<CheckMenuItem> checkMenuItems = new ArrayList<>();
        for(MenuItem menuItem : menuButton.getItems()){
            if(menuItem instanceof CheckMenuItem){
                checkMenuItems.add((CheckMenuItem) menuItem);
            }

        }
        return checkMenuItems;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!initialized) {
            // Set cell value factories for each column
            idProduitColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
            marqueProduitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarque()));
            modeleProduitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModele()));
            descriptionProduitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
            categorieProduitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategorie()));
            qtStockProduitColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQteStock()).asObject());
            prixProduitColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrix()).asObject());

            // Add edit button column
            TableColumn<ProduitDTO, Void> editColumn = new TableColumn<>("Modifier");
            editColumn.setCellFactory(column -> {
                return new TableCell<>() {
                    private final Button editButton = new Button("Modifier");

                    {
                        editButton.setOnAction(event -> {
                            ProduitDTO produit = getTableView().getItems().get(getIndex());
                            long produit_id = produit.getId();
                            FXMLLoader loader = null;
                            try {
                                loader = ProduitController.this.loadScene("StockUpdate","Mettre à jour un produit");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            ProduitController produitController = loader.getController();
                            populateComboBoxMarquesCategories("categories",produitController.updateCategorieComboBox);
                            populateComboBoxMarquesCategories("marques",produitController.updateMarqueComboBox);

                            produitController.updateCategorieComboBox.setValue(produit.getCategorie());
                            produitController.updateMarqueComboBox.setValue(produit.getMarque());
                            produitController.updateModeleTextField.setText(produit.getModele());
                            produitController.updatePrixTextField.setText(String.valueOf(produit.getPrix()));
                            produitController.updateQtStockTextField.setText(String.valueOf(produit.getQteStock()));
                            produitController.updateDescriptionTextArea.setText(produit.getDescription());

                            produitController.modifierProduitBtn.setOnAction(evt -> {

                                String categorie = produitController.updateCategorieComboBox.getValue();
                                String marque = produitController.updateMarqueComboBox.getValue();
                                String description = produitController.updateDescriptionTextArea.getText();
                                String modele = null;
                                if(produitController.updateModeleTextField.getText().isEmpty()) {
                                    showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir le modèle !");
                                    return;
                                }else{
                                    modele = produitController.updateModeleTextField.getText();
                                }

                                if (produitController.updatePrixTextField.getText().isEmpty()) {
                                    showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez ne pas laisser le champ du prix vide !");
                                    return; // Return if price is empty to avoid NumberFormatException
                                }

                                double prix = 0;
                                // Get the price text from the text field
                                String priceText = produitController.updatePrixTextField.getText();

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
                                String quantityText = produitController.updateQtStockTextField.getText();

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

                                ProduitDTO produitDTO = new ProduitDTO(marque,modele,description,categorie,qteStock,prix);
                                boolean status = ParserProduit.updateProduit(produitDTO,produit_id);
                                if (status) {
                                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit modifié avec succès !");
                                    Stage stage = (Stage) produitController.updateQtStockTextField.getScene().getWindow();
                                    stage.close();
                                    ProduitController.this.searchResultTableView.getItems().clear();
                                    ProduitController.this.searchResultTableView.getItems().addAll(ParserProduit.getAllProduits());
                                    ProduitController.this.searchResultTableView.refresh();

                                } else {
                                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification du produit.");
                                }

                            });


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
                };
            });

            // Add delete button column
            TableColumn<ProduitDTO, Void> deleteColumn = new TableColumn<>("Supprimer");
            deleteColumn.setCellFactory(column -> {
                return new TableCell<>() {
                    private final Button deleteButton = new Button("Supprimer");

                    {
                        deleteButton.setOnAction(event -> {
                            ProduitDTO produit = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText(null);
                            alert.setContentText("Voulez-vous vraiment supprimer ce produit ? Note: si ce produit est rattaché à un achat ou une commande, vous ne pourrez pas le supprimer !");

                            // Customize the buttons in the confirmation dialog
                            alert.getButtonTypes().setAll(ButtonType.NO, ButtonType.YES);

                            // Show the confirmation dialog and wait for the user's response
                            ButtonType result = alert.showAndWait().orElse(ButtonType.NO);

                            // If the user clicks YES, proceed with the deletion
                            if (result == ButtonType.YES) {
                                boolean success = ParserProduit.deleteProduitByID(produit.getId());
                                if (success) {
                                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit supprimé avec succès !");
                                    ProduitController.this.searchResultTableView.getItems().clear();
                                    ProduitController.this.searchResultTableView.getItems().addAll(ParserProduit.getAllProduits());
                                    ProduitController.this.searchResultTableView.refresh();
                                } else {
                                    showAlert(Alert.AlertType.ERROR,"Erreur","Erreur lors de la suppression du produit.");
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
                            setGraphic(deleteButton);
                        }
                    }
                };

            });
            // Add columns to TableView
            searchResultTableView.getColumns().addAll(editColumn, deleteColumn);

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

            String description = produitController.createDescriptionTextArea.getText();
            ProduitDTO produitDTO = new ProduitDTO(marque, modele, description, categorie, qteStock, prix);
            boolean status = ParserProduit.createProduit(produitDTO);
            if (status) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit crée avec succès !");
                Stage stage = (Stage) produitController.createPrixTextField.getScene().getWindow();
                stage.close();
                ProduitController.this.searchResultTableView.getItems().clear();
                ProduitController.this.searchResultTableView.getItems().addAll(ParserProduit.getAllProduits());
                ProduitController.this.searchResultTableView.refresh();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la création du produit.");
            }
        });

    }

    public void onRefreshBtn(ActionEvent e){
        this.searchResultTableView.getItems().clear();
        this.searchResultTableView.getItems().addAll(ParserProduit.getAllProduits());
        this.searchResultTableView.refresh();
    }

    public void onSearchProductBtn(ActionEvent e) throws IOException {
        FXMLLoader loader = this.loadScene("StockSearch","Rechercher un produit");
        ProduitController produitController = loader.getController();
        produitController.marqueSearchComboBox.getItems().add("Toutes les marques");
        populateComboBoxMarquesCategories("marques",produitController.marqueSearchComboBox);

        List<CheckMenuItem> checkMenuItems = produitController.getAllCategoriesFromMenuButton(produitController.SearchCategoriesMenuButton);

        ProduitController.this.catMenuItems = checkMenuItems;
        produitController.marqueSearchComboBox.setValue("Toutes les marques");

        produitController.searchProductsBtn.setOnAction(event -> {
            String marque = produitController.marqueSearchComboBox.getValue() == null ?
                    null:
                    produitController.marqueSearchComboBox.getValue();
            List<String> categories = new ArrayList<>();
            for(CheckMenuItem checkMenuItem : ProduitController.this.catMenuItems) {
                if(checkMenuItem.isSelected()) {
                    categories.add(checkMenuItem.getText());
                }
            }
            if(categories.contains("Toutes les catégories")){
                categories = null;
            }
            System.out.println(categories);
            String modele = produitController.modeleSearchTextField.getText().isEmpty() ?
                    null:
                    produitController.modeleSearchTextField.getText();
            if(marque != null && marque.equals("Toutes les marques")){
                marque = null;
            }
            String description = produitController.descriptionSearchTextArea.getText().isEmpty() ?
                    null:
                    produitController.descriptionSearchTextArea.getText();

            Double prixMin = produitController.prixMinSearchTextField.getText().isEmpty() ?
                    null:
                    Double.parseDouble(produitController.prixMinSearchTextField.getText());
            Double prixMax = produitController.prixMaxSearchTextField.getText().isEmpty() ?
                    null:
                    Double.parseDouble(produitController.prixMaxSearchTextField.getText());

            Integer qtStock = produitController.qtStockSupSearchTextField.getText().isEmpty() ?
                    null:
                    Integer.parseInt(produitController.qtStockSupSearchTextField.getText());
            String disponibilite = "tout";
            if(produitController.dispoRadioBtn.isSelected())
                disponibilite = "disponible";
            else if(produitController.indispoRadioBtn.isSelected())
                disponibilite = "indisponible";

            Map<Object , Object> searchInfos = new HashMap<>();
            searchInfos.put("marque",marque);
            searchInfos.put("categorie",categories);
            searchInfos.put("modele",modele);
            searchInfos.put("description",description);
            searchInfos.put("prixMin",prixMin);
            searchInfos.put("prixMax",prixMax);
            searchInfos.put("qtStock",qtStock);
            searchInfos.put("disponibilite",disponibilite);

            List<ProduitDTO> searchResult = ParserProduit.getProduitsByAdvSearch(searchInfos);
            if(searchResult == null){
                showAlert(Alert.AlertType.ERROR,"Erreur","Une erreur s'est produite.");
            }else if (searchResult.isEmpty()){
                showAlert(Alert.AlertType.INFORMATION,"Aucun produit trouvé","Aucun produit ne correspond à votre recherche !");
                Stage stage = (Stage) produitController.dispoRadioBtn.getScene().getWindow();
                stage.close();
            }else{
                ProduitController.this.searchResultTableView.getItems().clear();
                ProduitController.this.searchResultTableView.getItems().addAll(searchResult);
                showAlert(Alert.AlertType.INFORMATION,"Succès","Produits trouvés !");
                Stage stage = (Stage) produitController.dispoRadioBtn.getScene().getWindow();
                stage.close();
            }
        });
    }

    public void disableQteStockTextField(){
        this.qtStockSupSearchTextField.setDisable(true);
    }
    public void enableQteStockTextField(){
        this.qtStockSupSearchTextField.setDisable(false);
    }

}
