package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Models.Model;
import com.example.giefrontend1.Views.AdminMenuOptions;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CommercantMenuController implements Initializable {
    public Text Title_text;
    public Button create_client_id;
    public Button clients_btn;
    public Button Search_btn;
    public Button UpdateClient_btn;
    public Button logout_btn;
    public Button DeleteContact_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListenners();
    }
    private void addListenners(){
        create_client_id.setOnAction(event -> onCreateClient());
        clients_btn.setOnAction(event -> onClient());
        UpdateClient_btn.setOnAction(actionEvent -> onUpdateClient());
        Search_btn.setOnAction(event -> onFindClient());
        DeleteContact_btn.setOnAction(actionEvent -> onDeleteClient());
        logout_btn.setOnAction(actionEvent -> onLogout());
    }
    @FXML
    public void onCreateClient(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE_CLIENT);
    }
    @FXML
    public void onClient(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CLIENTS);
    }
    @FXML
    public void onFindClient(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.SEARCH_CLIENT);
    }
    @FXML
    public void onUpdateClient(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.Update_CLIENT);
    }
    @FXML
    public void onDeleteClient(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.DELETE_CLIENT);
    }
    @FXML
    public void onLogout(){
        Platform.exit();
    }
}
