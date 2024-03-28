package com.example.giefrontend1.Controllers.Commercant;

import com.example.giefrontend1.Controllers.DTO.ContactDTO;
import com.example.giefrontend1.Parser.ParserContact;
import com.google.gson.JsonNull;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FindContactController implements Initializable {
    public Button search_btn;
    public TextField SearchContactbyiD_txtField;
    public Button searchbyID_btn;
    public ListView searchByID_listView;
    public Text SearchByName_btn;
    public TextField SearchContactbyName;
    public Button SearchbyName_btn;
    public ListView SearchByName_listView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchbyID_btn.setOnAction(event -> {
            searchByID_listView.getItems().clear();
            int contactId;
            try {
                contactId = Integer.parseInt(SearchContactbyiD_txtField.getText());
            } catch (NumberFormatException e) {
                searchByID_listView.getItems().add("Veuillez entrer un identifiant de contact valide.");
                return;
            }

            ContactDTO contactDTO = ParserContact.getContactById(contactId);
            if(contactDTO == null){
                searchByID_listView.getItems().add("ID introuvable!");
                return;
            }
            int id = contactDTO.getId();
            String email = contactDTO.getEmail();
            String fax = contactDTO.getFax();
            String telephone = contactDTO.getTelephone();
            String nom = contactDTO.getLast_name();
            String prenom = contactDTO.getFirst_name();
            String formeJuridique = contactDTO.getFormeJuridique();
            String raisonSociale = contactDTO.getRaisonSociale();

            //Particulier
            if(nom != null){
                searchByID_listView.getItems().add(id + " " + nom + " " + prenom + " " + email + " " + telephone + " " + fax);
            }
            //Entreprise
            if(raisonSociale != null || formeJuridique != null) {
                searchByID_listView.getItems().add(id + " " + raisonSociale + " " + formeJuridique + " " + email + " " + telephone + " " + fax);
            }
        });



        SearchbyName_btn.setOnAction(event -> {
            SearchByName_listView.getItems().clear();
            String contactName = SearchContactbyName.getText();
            if(contactName.equals("")){
                SearchByName_listView.getItems().add("Veuillez saisir le nom!");
                return;
            }


            List<ContactDTO> contacts = ParserContact.getParticuliersByName(contactName);
            if(contacts == null || (contacts != null && contacts.isEmpty())){
                SearchByName_listView.getItems().add("Une erreur s'est produite.");
                return;
            }
            for(ContactDTO contactDTO : contacts){
                int id = contactDTO.getId();
                String email = contactDTO.getEmail();
                String fax = contactDTO.getFax();
                String telephone = contactDTO.getTelephone();
                String nom = contactDTO.getLast_name();
                String prenom = contactDTO.getFirst_name();
                String formeJuridique = contactDTO.getFormeJuridique();
                String raisonSociale = contactDTO.getRaisonSociale();

                //Particulier
                if(nom != null){
                    SearchByName_listView.getItems().add(id + " " + nom + " " + prenom + " " + email + " " + telephone + " " + fax);
                }
                //Entreprise
                if(raisonSociale != null || formeJuridique != null) {
                    SearchByName_listView.getItems().add(id + " " + raisonSociale + " " + formeJuridique + " " + email + " " + telephone + " " + fax);
                }
            }


        });
    }

}
