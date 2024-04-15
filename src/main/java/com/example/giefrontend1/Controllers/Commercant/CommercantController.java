package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CommercantController implements Initializable {
    public BorderPane admin_Parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observableValue,oldVal,newVal) -> {
            switch (newVal){
                case SEARCH_CLIENT -> admin_Parent.setCenter(Model.getInstance().getViewFactory().getFindClientView());
                case SEND_EMAIL -> admin_Parent.setCenter(Model.getInstance().getViewFactory().getSendGmailView());
                default -> admin_Parent.setCenter(Model.getInstance().getViewFactory().getCreateClientView());
            }
        } );


    }
}
