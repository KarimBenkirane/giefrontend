package com.example.giefrontend1.Parser;

import com.example.giefrontend1.Controllers.DTO.AdresseDTO;
import com.example.giefrontend1.Controllers.DTO.ContactDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ParserContact {
    private static final String url="http://localhost:4567/contacts";
    public static List<ContactDTO> GetAllContact() {
        OkHttpClient client = new OkHttpClient();
        String body = null;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            System.out.println(body);
            parse(body);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return parse(body);
    }
    public static List<ContactDTO> parse(String responseBody) {
        List<ContactDTO> listContact = new ArrayList<>();

        JsonArray users = new JsonParser().parse(responseBody).getAsJsonArray();

        for (int i = 0; i < users.size(); i++) {
            JsonObject member = users.get(i).getAsJsonObject();

            int id = member.get("id").getAsInt();
            String email = member.get("email").getAsString();

            // Vérifier si l'objet JSON contient la clé "raisonSociale"
            String fax = null;
            if (member.has("raisonSociale")) {
                String name = member.get("raisonSociale").getAsString();
                String formeJuridique = member.get("formeJuridique").getAsString();
                String telephone = member.get("telephone").getAsString();
                fax = member.get("fax").getAsString();

                JsonObject addressObj = member.get("adresse").getAsJsonObject();
                int addressId = addressObj.get("id").getAsInt();
                String rue = addressObj.get("rue").getAsString();
                int numeroRue = addressObj.get("numeroRue").getAsInt();
                String quartier = addressObj.get("quartier").getAsString();
                int codePostal = addressObj.get("codePostal").getAsInt();
                String ville = addressObj.get("ville").getAsString();
                String pays = addressObj.get("pays").getAsString();

                AdresseDTO address = new AdresseDTO(addressId, rue, numeroRue, quartier, codePostal, ville, pays);

                listContact.add(new ContactDTO(id, name, email, formeJuridique, telephone, address));
            } else { // Si la clé "raisonSociale" n'existe pas, supposons qu'il s'agit d'un particulier
                String nom = member.get("nom").getAsString();
                String prenom = member.get("prenom").getAsString();
                String telephone = member.get("telephone").getAsString();

                JsonObject addressObj = member.get("adresse").getAsJsonObject();
                int addressId = addressObj.get("id").getAsInt();
                String rue = addressObj.get("rue").getAsString();
                int numeroRue = addressObj.get("numeroRue").getAsInt();
                String quartier = addressObj.get("quartier").getAsString();
                int codePostal = addressObj.get("codePostal").getAsInt();
                String ville = addressObj.get("ville").getAsString();
                String pays = addressObj.get("pays").getAsString();

                AdresseDTO address = new AdresseDTO(addressId, rue, numeroRue, quartier, codePostal, ville, pays);

                String name = nom + " " + prenom;
                listContact.add(new ContactDTO(id, name, email, telephone, telephone, address));
            }
        }
        return listContact;
    }

}
