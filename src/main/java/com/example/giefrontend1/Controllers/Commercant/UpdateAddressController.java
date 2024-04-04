package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Controllers.DTO.AdresseDTO;
import com.example.giefrontend1.Parser.ParserContact;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateAddressController implements Initializable {
    public TextField Id_txtField;
    public TextField quartierTextField;
    public TextField rueTextField;
    public TextField numRueTextField;
    public TextField codePostalTextField;
    public TextField villeTextField;
    public TextField paysTextField;
    public Button updateAddress_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
           updateAddress_btn.setOnAction(actionEvent -> onUpdateAdress());

    }
    public void onUpdateAdress() {
        String quartier = null;
        String rue = null;
        int numeroRue = -1;
        int codePostal = -1;
        String ville = null;
        String pays = null;

        if(!quartierTextField.getText().isEmpty())
            quartier = quartierTextField.getText();

        if(!rueTextField.getText().isEmpty())
            rue = rueTextField.getText();

        if(!numRueTextField.getText().isEmpty())
            numeroRue = Integer.parseInt(numRueTextField.getText());

        if(!codePostalTextField.getText().isEmpty())
            codePostal = Integer.parseInt(codePostalTextField.getText());

        if(!villeTextField.getText().isEmpty())
            ville = villeTextField.getText();

        if(!paysTextField.getText().isEmpty())
            pays = paysTextField.getText();

        AdresseDTO adresseDTO = new AdresseDTO(rue,numeroRue,quartier,codePostal,ville,pays);
        ParserContact.updateAdress(adresseDTO);
    }
}
