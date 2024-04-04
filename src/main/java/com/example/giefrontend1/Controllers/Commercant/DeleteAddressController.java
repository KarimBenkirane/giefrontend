package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Parser.ParserContact;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteAddressController implements Initializable {
    public TextField addressIdTextField;
    public Button delete_btn;
    public Label label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        delete_btn.setOnAction(event ->{
            int addressId;
            try{
                addressId = Integer.parseInt(addressIdTextField.getText());
            }catch (NumberFormatException e){
                label.setText("Veuillez entrer un identifiant de contact valide.");
                return;
            }
            boolean success = ParserContact.deleteAddress(addressId);
            if (success) {
                label.setText("Suppression effectuée avec succès!");
            } else {
                label.setText("Une erreur s'est produite lors de la suppression da l'adress.");
            }
        });
    }
}
