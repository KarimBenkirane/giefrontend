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
                case CLIENTS -> admin_Parent.setCenter(Model.getInstance().getViewFactory().getClientView());
                case SEARCH_CLIENT -> admin_Parent.setCenter(Model.getInstance().getViewFactory().getFindClientView());
                case Update_CLIENT -> admin_Parent.setCenter(Model.getInstance().getViewFactory().getUpdateClientView());
                case DELETE_CLIENT -> admin_Parent.setCenter(Model.getInstance().getViewFactory().getDeleteContacttView());
                case DELETE_ADRESS -> admin_Parent.setCenter(Model.getInstance().getViewFactory().getDeleteAdressViewView());
                case UPDATE_ADRESS -> admin_Parent.setCenter(Model.getInstance().getViewFactory().getUpdateAdressView());
                case SEARCH_ENTREPRISE-> admin_Parent.setCenter(Model.getInstance().getViewFactory().getFindEntrepriseView());
                default -> admin_Parent.setCenter(Model.getInstance().getViewFactory().getCreateClientView());
            }
        } );


    }
}
