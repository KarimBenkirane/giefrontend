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
    public Button Search_btn;
    public Button logout_btn;
    public Button sendMail_button;
    public Button consulterStockBtn;
    public Button mesAchatsBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListenners();
    }
    private void addListenners(){
        create_client_id.setOnAction(event -> onCreateClient());
        Search_btn.setOnAction(event -> onFindClient());
        sendMail_button.setOnAction(actionEvent -> onSendGmail());
        consulterStockBtn.setOnAction(event -> onConsulterStock());
        logout_btn.setOnAction(actionEvent -> onLogout());
        mesAchatsBtn.setOnAction(actionEvent -> onMesAchats());
        
    }

    @FXML
    public void onCreateClient(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.CREATE_CLIENT);
    }

    @FXML
    public void onConsulterStock(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.STOCK);
    }

    @FXML
    public void onMesAchats(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.MY_PURCHASES);
    }

    @FXML
    public void onFindClient(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.SEARCH_CLIENT);
    }

    public void onSendGmail() {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set(AdminMenuOptions.SEND_EMAIL);
    }

    @FXML
    public void onLogout(){
        Platform.exit();
    }
}
