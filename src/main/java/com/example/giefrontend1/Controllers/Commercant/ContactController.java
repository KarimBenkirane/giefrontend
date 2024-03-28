package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Parser.ParserContact;
import com.example.giefrontend1.Controllers.DTO.ContactDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ContactController implements Initializable {
    @FXML
    public ListView<ContactDTO> client_lstView;
    public ObservableList <ContactDTO> contactList ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactList = FXCollections.observableArrayList();
        client_lstView.setItems(contactsCollector());
    }
    public ObservableList<ContactDTO> contactsCollector() {
        return FXCollections.observableArrayList(ParserContact.getAllContacts());
    }
}
