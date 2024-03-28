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
            // Vérifier si l'objet JSON contient la clé "raisonSociale"
            if (member.has("raisonSociale")) {
                int id = member.get("id").getAsInt();
                String email = null;
                String fax = null;
                String telephone = null;
                String raisonSociale = null;
                String formeJuridique = null;


                JsonElement jsonEmail = member.get("email");
                JsonElement jsonFax = member.get("fax");
                JsonElement jsonTelephone = member.get("telephone");
                JsonElement jsonRaisonSociale = member.get("raisonSociale");
                JsonElement jsonFormeJuridique = member.get("formeJuridique");


                JsonObject addressObj = member.get("adresse").getAsJsonObject();


                int adresse_id = addressObj.get("id").getAsInt();
                String rue = null;
                int numeroRue = -1;
                int codePostal = -1;
                String quartier = null;
                String ville = null;
                String pays = null;

                JsonElement jsonRue = addressObj.get("rue");
                JsonElement jsonNumeroRue = addressObj.get("numeroRue");
                JsonElement jsonCodePostal = addressObj.get("codePostal");
                JsonElement jsonQuartier = addressObj.get("quartier");
                JsonElement jsonVille = addressObj.get("ville");
                JsonElement jsonPays = addressObj.get("pays");



                if(!(jsonEmail instanceof JsonNull)) {
                    email = jsonEmail.getAsString();
                }
                if(!(jsonFax instanceof JsonNull)) {
                    fax = jsonFax.getAsString();
                }
                if(!(jsonTelephone instanceof JsonNull)) {
                    telephone = jsonTelephone.getAsString();
                }
                if(!(jsonRaisonSociale instanceof JsonNull)) {
                    raisonSociale = jsonRaisonSociale.getAsString();
                }
                if(!(jsonFormeJuridique instanceof JsonNull)) {
                    formeJuridique = jsonFormeJuridique.getAsString();
                }


                if(!(jsonRue instanceof JsonNull)) {
                    rue = jsonRue.getAsString();
                }
                if(!(jsonNumeroRue instanceof JsonNull)) {
                    numeroRue = jsonNumeroRue.getAsInt();
                }
                if(!(jsonCodePostal instanceof JsonNull)) {
                    codePostal = jsonCodePostal.getAsInt();
                }
                if(!(jsonQuartier instanceof JsonNull)) {
                    quartier = jsonQuartier.getAsString();
                }
                if(!(jsonVille instanceof JsonNull)) {
                    ville = jsonVille.getAsString();
                }
                if(!(jsonPays instanceof JsonNull)) {
                    pays = jsonPays.getAsString();
                }



                AdresseDTO address = new AdresseDTO(adresse_id, rue, numeroRue, quartier, codePostal, ville, pays);

                return new ContactDTO(id, email, telephone, fax, address, formeJuridique, raisonSociale);


            } else { // Si la clé "raisonSociale" n'existe pas, supposons qu'il s'agit d'un particulier
                int id = member.get("id").getAsInt();
                String email = null;
                String fax = null;
                String telephone = null;
                String nom = null;
                String prenom = null;


                JsonElement jsonEmail = member.get("email");
                JsonElement jsonFax = member.get("fax");
                JsonElement jsonTelephone = member.get("telephone");
                JsonElement jsonNom = member.get("nom");
                JsonElement jsonPrenom = member.get("prenom");


                JsonObject addressObj = member.get("adresse").getAsJsonObject();


                int adresse_id = addressObj.get("id").getAsInt();
                String rue = null;
                int numeroRue = -1;
                int codePostal = -1;
                String quartier = null;
                String ville = null;
                String pays = null;

                JsonElement jsonRue = addressObj.get("rue");
                JsonElement jsonNumeroRue = addressObj.get("numeroRue");
                JsonElement jsonCodePostal = addressObj.get("codePostal");
                JsonElement jsonQuartier = addressObj.get("quartier");
                JsonElement jsonVille = addressObj.get("ville");
                JsonElement jsonPays = addressObj.get("pays");



                if(!(jsonEmail instanceof JsonNull)) {
                    email = jsonEmail.getAsString();
                }
                if(!(jsonFax instanceof JsonNull)) {
                    fax = jsonFax.getAsString();
                }
                if(!(jsonTelephone instanceof JsonNull)) {
                    telephone = jsonTelephone.getAsString();
                }
                if(!(jsonNom instanceof JsonNull)) {
                    nom = jsonNom.getAsString();
                }
                if(!(jsonPrenom instanceof JsonNull)) {
                    prenom = jsonPrenom.getAsString();
                }


                if(!(jsonRue instanceof JsonNull)) {
                    rue = jsonRue.getAsString();
                }
                if(!(jsonNumeroRue instanceof JsonNull)) {
                    numeroRue = jsonNumeroRue.getAsInt();
                }
                if(!(jsonCodePostal instanceof JsonNull)) {
                    codePostal = jsonCodePostal.getAsInt();
                }
                if(!(jsonQuartier instanceof JsonNull)) {
                    quartier = jsonQuartier.getAsString();
                }
                if(!(jsonVille instanceof JsonNull)) {
                    ville = jsonVille.getAsString();
                }
                if(!(jsonPays instanceof JsonNull)) {
                    pays = jsonPays.getAsString();
                }



                AdresseDTO address = new AdresseDTO(adresse_id, rue, numeroRue, quartier, codePostal, ville, pays);

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

                // Vérifier si l'objet JSON contient la clé "raisonSociale"
                if (member.has("raisonSociale")) {
                    int id = member.get("id").getAsInt();
                    String email = null;
                    String fax = null;
                    String telephone = null;
                    String raisonSociale = null;
                    String formeJuridique = null;


                    JsonElement jsonEmail = member.get("email");
                    JsonElement jsonFax = member.get("fax");
                    JsonElement jsonTelephone = member.get("telephone");
                    JsonElement jsonRaisonSociale = member.get("raisonSociale");
                    JsonElement jsonFormeJuridique = member.get("formeJuridique");


                    JsonObject addressObj = member.get("adresse").getAsJsonObject();


                    int adresse_id = addressObj.get("id").getAsInt();
                    String rue = null;
                    int numeroRue = -1;
                    int codePostal = -1;
                    String quartier = null;
                    String ville = null;
                    String pays = null;

                    JsonElement jsonRue = addressObj.get("rue");
                    JsonElement jsonNumeroRue = addressObj.get("numeroRue");
                    JsonElement jsonCodePostal = addressObj.get("codePostal");
                    JsonElement jsonQuartier = addressObj.get("quartier");
                    JsonElement jsonVille = addressObj.get("ville");
                    JsonElement jsonPays = addressObj.get("pays");



                    if(!(jsonEmail instanceof JsonNull)) {
                        email = jsonEmail.getAsString();
                    }
                    if(!(jsonFax instanceof JsonNull)) {
                        fax = jsonFax.getAsString();
                    }
                    if(!(jsonTelephone instanceof JsonNull)) {
                        telephone = jsonTelephone.getAsString();
                    }
                    if(!(jsonRaisonSociale instanceof JsonNull)) {
                        raisonSociale = jsonRaisonSociale.getAsString();
                    }
                    if(!(jsonFormeJuridique instanceof JsonNull)) {
                        formeJuridique = jsonFormeJuridique.getAsString();
                    }


                    if(!(jsonRue instanceof JsonNull)) {
                        rue = jsonRue.getAsString();
                    }
                    if(!(jsonNumeroRue instanceof JsonNull)) {
                        numeroRue = jsonNumeroRue.getAsInt();
                    }
                    if(!(jsonCodePostal instanceof JsonNull)) {
                        codePostal = jsonCodePostal.getAsInt();
                    }
                    if(!(jsonQuartier instanceof JsonNull)) {
                        quartier = jsonQuartier.getAsString();
                    }
                    if(!(jsonVille instanceof JsonNull)) {
                        ville = jsonVille.getAsString();
                    }
                    if(!(jsonPays instanceof JsonNull)) {
                        pays = jsonPays.getAsString();
                    }



                    AdresseDTO address = new AdresseDTO(adresse_id, rue, numeroRue, quartier, codePostal, ville, pays);

                    contactsByName.add(new ContactDTO(id, email, telephone,fax, address, formeJuridique, raisonSociale));
                } else { // Si la clé "raisonSociale" n'existe pas, supposons qu'il s'agit d'un particulier
                    int id = member.get("id").getAsInt();
                    String email = null;
                    String fax = null;
                    String telephone = null;
                    String nom = null;
                    String prenom = null;


                    JsonElement jsonEmail = member.get("email");
                    JsonElement jsonFax = member.get("fax");
                    JsonElement jsonTelephone = member.get("telephone");
                    JsonElement jsonNom = member.get("nom");
                    JsonElement jsonPrenom = member.get("prenom");


                    JsonObject addressObj = member.get("adresse").getAsJsonObject();


                    int adresse_id = addressObj.get("id").getAsInt();
                    String rue = null;
                    int numeroRue = -1;
                    int codePostal = -1;
                    String quartier = null;
                    String ville = null;
                    String pays = null;

                    JsonElement jsonRue = addressObj.get("rue");
                    JsonElement jsonNumeroRue = addressObj.get("numeroRue");
                    JsonElement jsonCodePostal = addressObj.get("codePostal");
                    JsonElement jsonQuartier = addressObj.get("quartier");
                    JsonElement jsonVille = addressObj.get("ville");
                    JsonElement jsonPays = addressObj.get("pays");



                    if(!(jsonEmail instanceof JsonNull)) {
                        email = jsonEmail.getAsString();
                    }
                    if(!(jsonFax instanceof JsonNull)) {
                        fax = jsonFax.getAsString();
                    }
                    if(!(jsonTelephone instanceof JsonNull)) {
                        telephone = jsonTelephone.getAsString();
                    }
                    if(!(jsonNom instanceof JsonNull)) {
                        nom = jsonNom.getAsString();
                    }
                    if(!(jsonPrenom instanceof JsonNull)) {
                        prenom = jsonPrenom.getAsString();
                    }


                    if(!(jsonRue instanceof JsonNull)) {
                        rue = jsonRue.getAsString();
                    }
                    if(!(jsonNumeroRue instanceof JsonNull)) {
                        numeroRue = jsonNumeroRue.getAsInt();
                    }
                    if(!(jsonCodePostal instanceof JsonNull)) {
                        codePostal = jsonCodePostal.getAsInt();
                    }
                    if(!(jsonQuartier instanceof JsonNull)) {
                        quartier = jsonQuartier.getAsString();
                    }
                    if(!(jsonVille instanceof JsonNull)) {
                        ville = jsonVille.getAsString();
                    }
                    if(!(jsonPays instanceof JsonNull)) {
                        pays = jsonPays.getAsString();
                    }



                    AdresseDTO address = new AdresseDTO(adresse_id, rue, numeroRue, quartier, codePostal, ville, pays);

                    contactsByName.add(new ContactDTO(id, prenom,nom, email, telephone,fax, address));
                }
            }
            return contactsByName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


