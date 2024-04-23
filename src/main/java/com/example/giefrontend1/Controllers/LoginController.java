package com.example.giefrontend1.Controllers;

import com.example.giefrontend1.Models.Model;
import com.example.giefrontend1.Views.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    public ChoiceBox<AccountType> account_selector;

    @FXML
    public Label Account_name_lbl;

    @FXML
    public TextField Acount_name_field;

    @FXML
    public Label passwd_lbl;

    @FXML
    public TextField psswd_field;

    @FXML
    public Button login_btn;

    @FXML
    public Label err_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       account_selector.setItems(FXCollections.observableArrayList(AccountType.ADMIN,AccountType.CLIENT,AccountType.LIVREUR));
       account_selector.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginAccountType(account_selector.getValue()));
       account_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
       login_btn.setOnAction(event -> onLogin());
    }
    private void onLogin() {
        Stage stage = (Stage) err_lbl.getScene().getWindow();
        Model model = Model.getInstance();
        model.getViewFactory().closeStage(stage);
        String username = Acount_name_field.getText();
        String password = psswd_field.getText();

        if (model.getViewFactory().getLoginAccountType() == AccountType.ADMIN) {
            if ("ADMIN".equals(username) && "123".equals(password)) {
                model.getViewFactory().showAdminWindow();
            } else {
                err_lbl.setText("Invalid username or password");
                model.getViewFactory().showLoginWindow();
            }
        } else {
            model.getViewFactory().showLoginWindow();
        }
    }



}
