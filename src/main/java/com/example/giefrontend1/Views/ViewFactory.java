package com.example.giefrontend1.Views;

import com.example.giefrontend1.Controllers.Commercant.CommercantController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
    private AccountType loginAccountType;
    private AnchorPane FindClientView;
    private AnchorPane CreateClientView;
    private AnchorPane ClientView;
    private AnchorPane UpdateContactView;
    private AnchorPane DeleteContactView;
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;

    public ViewFactory(){
        this.loginAccountType=AccountType.ADMIN;
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
    }


    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }


    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem(){
        return adminSelectedMenuItem;
    }
    public AnchorPane getCreateClientView() {
        if(CreateClientView == null){
            try{
                CreateClientView = new FXMLLoader(getClass().getResource("/com.example.giefrontend1/Admin/CreateClient.fxml")).load();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return CreateClientView;
    }
    public AnchorPane getClientView(){
        if(ClientView == null){
            try{
                ClientView = new FXMLLoader(getClass().getResource("/com.example.giefrontend1/Admin/Clients.fxml")).load();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return ClientView;
    }
    public AnchorPane getFindClientView(){
        if(FindClientView == null){
            try{
                FindClientView = new FXMLLoader(getClass().getResource("/com.example.giefrontend1/Admin/FindClient.fxml")).load();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return FindClientView;
    }
    public AnchorPane getUpdateClientView(){
        if(UpdateContactView== null){
            try{
                UpdateContactView = new FXMLLoader(getClass().getResource("/com.example.giefrontend1/Admin/UpdateClient.fxml")).load();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return UpdateContactView;
    } public AnchorPane getDeleteContacttView(){
        if(DeleteContactView== null){
            try{
                DeleteContactView = new FXMLLoader(getClass().getResource("/com.example.giefrontend1/Admin/DeleteContact.fxml")).load();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return DeleteContactView;
    }
    public void showAdminWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.giefrontend1/Admin/Contact.fxml"));
        CommercantController controller = new CommercantController();
        loader.setController(controller);
        createStage(loader);
    }
    public void closeStage(Stage stage){
        stage.close();
    }
    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.giefrontend1/login.fxml"));
        createStage(loader);
    }
    private void createStage(FXMLLoader loder){
        Scene scene =null;
        try{
            scene=new Scene(loder.load());
        }catch (Exception e){
            e.printStackTrace();
        }
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("GIE Entreprise");
        stage.show();
    }


}
