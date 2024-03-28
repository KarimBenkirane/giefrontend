package com.example.giefrontend1.Parser;

import com.example.giefrontend1.Controllers.DTO.AdresseDTO;
import com.example.giefrontend1.Controllers.DTO.ContactDTO;
import com.google.gson.*;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ParserContact {
    private static final String url="http://localhost:4567";
    private static final  OkHttpClient client = new OkHttpClient();

    public static List<ContactDTO> GetAllContact() {

        String body = null;

        Request request = new Request.Builder()
                .url(url+"/api/contacts/all")
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            parseGetallContact(body);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return parseGetallContact(body);
    }
    public static List<ContactDTO> parseGetallContact(String responseBody) {
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

                listContact.add(new ContactDTO(id, name, email, formeJuridique, telephone, fax, address));
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
                listContact.add(new ContactDTO(id, name, email, telephone, telephone, fax, address));
            }
        }
        return listContact;
    }
    public static boolean createContact(ContactDTO newContact) {
        Gson gson = new Gson();
        String json = gson.toJson(newContact);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url + "/api/particuliers/add")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean deleteContact(int contactId) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url +"/api/contacts/delete/" + contactId)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static ContactDTO getContactById(int id) {
        String body = null;

        Request request = new Request.Builder()
                .url(url + "/api/contacts/get/" + id)
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            ContactDTO contactDTO = parseGetContactById(body);
            return contactDTO;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ContactDTO parseGetContactById(String responseBody){
        try{
            JsonObject member = new JsonParser().parse(responseBody).getAsJsonObject();

            int id = member.get("id").getAsInt();
            String email = member.get("email").getAsString();

            // Vérifier si l'objet JSON contient la clé "raisonSociale"
            if (member.has("raisonSociale")) {
                String raisonSociale = member.get("raisonSociale").getAsString();
                String formeJuridique = member.get("formeJuridique").getAsString();
                String telephone = member.get("telephone").getAsString();
                String fax = member.get("fax").getAsString();

                JsonObject addressObj = member.get("adresse").getAsJsonObject();
                int addressId = addressObj.get("id").getAsInt();
                String rue = addressObj.get("rue").getAsString();
                int numeroRue = addressObj.get("numeroRue").getAsInt();
                String quartier = addressObj.get("quartier").getAsString();
                int codePostal = addressObj.get("codePostal").getAsInt();
                String ville = addressObj.get("ville").getAsString();
                String pays = addressObj.get("pays").getAsString();

                AdresseDTO address = new AdresseDTO(addressId, rue, numeroRue, quartier, codePostal, ville, pays);

                return new ContactDTO(id, email, telephone, fax, address, formeJuridique, raisonSociale);
            } else { // Si la clé "raisonSociale" n'existe pas, supposons qu'il s'agit d'un particulier
                String nom = member.get("nom").getAsString();
                String prenom = member.get("prenom").getAsString();
                String telephone = member.get("telephone").getAsString();
                String fax = member.get("fax").getAsString();

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
                return new ContactDTO(id, prenom, nom, email, telephone, fax, address);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public static List<ContactDTO> getParticuliersByName(String name) {
        List<ContactDTO> contacts = new ArrayList<>();
        String body = null;

        Request request = new Request.Builder()
                .url(url+"/api/particuliers/get/"+name)
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            contacts = parseGetContactsByName(body);
            return contacts;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }



    public static List<ContactDTO> parseGetContactsByName(String responseBody) {
        try{
            List<ContactDTO> contactsByName = new ArrayList<>();

            JsonArray users = new JsonParser().parse(responseBody).getAsJsonArray();

            for (int i = 0; i < users.size(); i++) {
                JsonObject member = users.get(i).getAsJsonObject();

                int id = member.get("id").getAsInt();
                String email = member.get("email").getAsString();

                // Vérifier si l'objet JSON contient la clé "raisonSociale"
                if (member.has("raisonSociale")) {
                    String raisonSociale = member.get("raisonSociale").getAsString();
                    String formeJuridique = member.get("formeJuridique").getAsString();
                    String telephone = member.get("telephone").getAsString();
                    String fax = member.get("fax").getAsString();

                    JsonObject addressObj = member.get("adresse").getAsJsonObject();
                    int addressId = addressObj.get("id").getAsInt();
                    String rue = addressObj.get("rue").getAsString();
                    int numeroRue = addressObj.get("numeroRue").getAsInt();
                    String quartier = addressObj.get("quartier").getAsString();
                    int codePostal = addressObj.get("codePostal").getAsInt();
                    String ville = addressObj.get("ville").getAsString();
                    String pays = addressObj.get("pays").getAsString();

                    AdresseDTO address = new AdresseDTO(addressId, rue, numeroRue, quartier, codePostal, ville, pays);

                    contactsByName.add(new ContactDTO(id, email, telephone,fax, address, formeJuridique, raisonSociale));
                } else { // Si la clé "raisonSociale" n'existe pas, supposons qu'il s'agit d'un particulier
                    String nom = member.get("nom").getAsString();
                    String prenom = member.get("prenom").getAsString();
                    String telephone = member.get("telephone").getAsString();
                    String fax = member.get("fax").getAsString();

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
                    contactsByName.add(new ContactDTO(id, prenom,nom, email, telephone,fax, address));
                }
            }
            return contactsByName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


