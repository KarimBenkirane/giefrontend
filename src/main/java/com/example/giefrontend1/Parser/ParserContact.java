package com.example.giefrontend1.Parser;

import com.example.giefrontend1.Controllers.DTO.AdresseDTO;
import com.example.giefrontend1.Controllers.DTO.ContactDTO;
import com.google.gson.*;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ParserContact {
    private static final String url = "http://localhost:4567";
    private static final OkHttpClient client = new OkHttpClient();

    public static List<ContactDTO> getAllContacts() {
        List<ContactDTO> contacts = new ArrayList<>();
        String body = null;

        Request request = new Request.Builder()
                .url(url + "/api/contacts/all")
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            contacts = parseAllContacts(body);
            return contacts;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public static List<ContactDTO> parseAllContacts(String responseBody) {
        try {
            List<ContactDTO> allContacts = new ArrayList<>();

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


                    if (!(jsonEmail == null)) {
                        email = jsonEmail.getAsString();
                    }
                    if (!(jsonFax == null)) {
                        fax = jsonFax.getAsString();
                    }
                    if (!(jsonTelephone == null)) {
                        telephone = jsonTelephone.getAsString();
                    }
                    if (!(jsonRaisonSociale == null)) {
                        raisonSociale = jsonRaisonSociale.getAsString();
                    }
                    if (!(jsonFormeJuridique == null)) {
                        formeJuridique = jsonFormeJuridique.getAsString();
                    }


                    if (!(jsonRue == null)) {
                        rue = jsonRue.getAsString();
                    }
                    if (!(jsonNumeroRue == null)) {
                        numeroRue = jsonNumeroRue.getAsInt();
                    }
                    if (!(jsonCodePostal == null)) {
                        codePostal = jsonCodePostal.getAsInt();
                    }
                    if (!(jsonQuartier == null)) {
                        quartier = jsonQuartier.getAsString();
                    }
                    if (!(jsonVille == null)) {
                        ville = jsonVille.getAsString();
                    }
                    if (!(jsonPays == null)) {
                        pays = jsonPays.getAsString();
                    }


                    AdresseDTO adresse = new AdresseDTO(adresse_id, rue, numeroRue, quartier, codePostal, ville, pays);

                    allContacts.add(new ContactDTO(id, email, telephone, fax, adresse, formeJuridique, raisonSociale));
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


                    if (!(jsonEmail == null)) {
                        email = jsonEmail.getAsString();
                    }
                    if (!(jsonFax == null)) {
                        fax = jsonFax.getAsString();
                    }
                    if (!(jsonTelephone == null)) {
                        telephone = jsonTelephone.getAsString();
                    }
                    if (!(jsonNom == null)) {
                        nom = jsonNom.getAsString();
                    }
                    if (!(jsonPrenom == null)) {
                        prenom = jsonPrenom.getAsString();
                    }


                    if (!(jsonRue == null)) {
                        rue = jsonRue.getAsString();
                    }
                    if (!(jsonNumeroRue == null)) {
                        numeroRue = jsonNumeroRue.getAsInt();
                    }
                    if (!(jsonCodePostal == null)) {
                        codePostal = jsonCodePostal.getAsInt();
                    }
                    if (!(jsonQuartier == null)) {
                        quartier = jsonQuartier.getAsString();
                    }
                    if (!(jsonVille == null)) {
                        ville = jsonVille.getAsString();
                    }
                    if (!(jsonPays == null)) {
                        pays = jsonPays.getAsString();
                    }


                    AdresseDTO adresse = new AdresseDTO(adresse_id, rue, numeroRue, quartier, codePostal, ville, pays);

                    allContacts.add(new ContactDTO(id, prenom, nom, email, telephone, fax, adresse));
                }
            }
            return allContacts;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean createParticulier(ContactDTO newContact) {
        Gson gson = new Gson();
        String json = gson.toJson(newContact);
        System.out.println(json);

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

    public static boolean createEntreprise(ContactDTO newContact) {
        Gson gson = new Gson();
        String json = gson.toJson(newContact);
        System.out.println(json);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url + "/api/entreprises/add")
                .post(body)
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

    public static ContactDTO parseGetContactById(String responseBody) {
        try {
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


                if (!(jsonEmail == null)) {
                    email = jsonEmail.getAsString();
                }
                if (!(jsonFax == null)) {
                    fax = jsonFax.getAsString();
                }
                if (!(jsonTelephone == null)) {
                    telephone = jsonTelephone.getAsString();
                }
                if (!(jsonRaisonSociale == null)) {
                    raisonSociale = jsonRaisonSociale.getAsString();
                }
                if (!(jsonFormeJuridique == null)) {
                    formeJuridique = jsonFormeJuridique.getAsString();
                }


                if (!(jsonRue == null)) {
                    rue = jsonRue.getAsString();
                }
                if (!(jsonNumeroRue == null)) {
                    numeroRue = jsonNumeroRue.getAsInt();
                }
                if (!(jsonCodePostal == null)) {
                    codePostal = jsonCodePostal.getAsInt();
                }
                if (!(jsonQuartier == null)) {
                    quartier = jsonQuartier.getAsString();
                }
                if (!(jsonVille == null)) {
                    ville = jsonVille.getAsString();
                }
                if (!(jsonPays == null)) {
                    pays = jsonPays.getAsString();
                }


                AdresseDTO adresse = new AdresseDTO(adresse_id, rue, numeroRue, quartier, codePostal, ville, pays);

                return new ContactDTO(id, email, telephone, fax, adresse, formeJuridique, raisonSociale);


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


                if (!(jsonEmail == null)) {
                    email = jsonEmail.getAsString();
                }
                if (!(jsonFax == null)) {
                    fax = jsonFax.getAsString();
                }
                if (!(jsonTelephone == null)) {
                    telephone = jsonTelephone.getAsString();
                }
                if (!(jsonNom == null)) {
                    nom = jsonNom.getAsString();
                }
                if (!(jsonPrenom == null)) {
                    prenom = jsonPrenom.getAsString();
                }


                if (!(jsonRue == null)) {
                    rue = jsonRue.getAsString();
                }
                if (!(jsonNumeroRue == null)) {
                    numeroRue = jsonNumeroRue.getAsInt();
                }
                if (!(jsonCodePostal == null)) {
                    codePostal = jsonCodePostal.getAsInt();
                }
                if (!(jsonQuartier == null)) {
                    quartier = jsonQuartier.getAsString();
                }
                if (!(jsonVille == null)) {
                    ville = jsonVille.getAsString();
                }
                if (!(jsonPays == null)) {
                    pays = jsonPays.getAsString();
                }


                AdresseDTO adresse = new AdresseDTO(adresse_id, rue, numeroRue, quartier, codePostal, ville, pays);

                return new ContactDTO(id, prenom, nom, email, telephone, fax, adresse);
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
                .url(url + "/api/particuliers/get/nom/" + name)
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
        List<ContactDTO> contactsByName = new ArrayList<>();
        try {

            JsonArray users = new JsonParser().parse(responseBody).getAsJsonArray();

            for (int i = 0; i < users.size(); i++) {
                JsonObject member = users.get(i).getAsJsonObject();


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


                if (!(jsonEmail == null)) {
                    email = jsonEmail.getAsString();
                }
                if (!(jsonFax == null)) {
                    fax = jsonFax.getAsString();
                }
                if (!(jsonTelephone == null)) {
                    telephone = jsonTelephone.getAsString();
                }
                if (!(jsonNom == null)) {
                    nom = jsonNom.getAsString();
                }
                if (!(jsonPrenom == null)) {
                    prenom = jsonPrenom.getAsString();
                }


                if (!(jsonRue == null)) {
                    rue = jsonRue.getAsString();
                }
                if (!(jsonNumeroRue == null)) {
                    numeroRue = jsonNumeroRue.getAsInt();
                }
                if (!(jsonCodePostal == null)) {
                    codePostal = jsonCodePostal.getAsInt();
                }
                if (!(jsonQuartier == null)) {
                    quartier = jsonQuartier.getAsString();
                }
                if (!(jsonVille == null)) {
                    ville = jsonVille.getAsString();
                }
                if (!(jsonPays == null)) {
                    pays = jsonPays.getAsString();
                }


                AdresseDTO adresse = new AdresseDTO(adresse_id, rue, numeroRue, quartier, codePostal, ville, pays);

                contactsByName.add(new ContactDTO(id, prenom, nom, email, telephone, fax, adresse));
            }
            return contactsByName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateParticulier(ContactDTO particulier) {
        Gson gson = new Gson();
        String json = gson.toJson(particulier);
        System.out.println(json);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url + "/api/particuliers/change")
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateEntreprise(ContactDTO entreprise) {
        Gson gson = new Gson();
        String json = gson.toJson(entreprise);
        System.out.println(json);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url + "/api/entreprises/change")
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /// ajouter dans le backend la partie http de la mise a jour de l'adress
    public static boolean updateAdress(AdresseDTO adress) {
        Gson gson = new Gson();
        String json = gson.toJson(adress);
        System.out.println(json);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url + "/api/adresses/change")
                .put(body)
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
                .url(url + "/api/contacts/delete/" + contactId)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteAddress(int addressId) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url + "/api/adresses/delete/" + addressId)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<ContactDTO> getEntrepriseByRaisonSociale(String raisonSociale) {
        List<ContactDTO> entreprises = new ArrayList<>();
        String body = null;

        Request request = new Request.Builder()
                .url(url + "/api/entreprises/get/raisonsociale/" + raisonSociale)
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            entreprises = parserGetEntreprisesByRaisonSociale(body);
            return entreprises;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<ContactDTO> parserGetEntreprisesByRaisonSociale(String responseBody) {
        try {
            List<ContactDTO> entreprisesByRaisonSociale = new ArrayList<>();

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


                    if (!(jsonEmail == null)) {
                        email = jsonEmail.getAsString();
                    }
                    if (!(jsonFax == null)) {
                        fax = jsonFax.getAsString();
                    }
                    if (!(jsonTelephone == null)) {
                        telephone = jsonTelephone.getAsString();
                    }
                    if (!(jsonRaisonSociale == null)) {
                        raisonSociale = jsonRaisonSociale.getAsString();
                    }
                    if (!(jsonFormeJuridique == null)) {
                        formeJuridique = jsonFormeJuridique.getAsString();
                    }


                    if (!(jsonRue == null)) {
                        rue = jsonRue.getAsString();
                    }
                    if (!(jsonNumeroRue == null)) {
                        numeroRue = jsonNumeroRue.getAsInt();
                    }
                    if (!(jsonCodePostal == null)) {
                        codePostal = jsonCodePostal.getAsInt();
                    }
                    if (!(jsonQuartier == null)) {
                        quartier = jsonQuartier.getAsString();
                    }
                    if (!(jsonVille == null)) {
                        ville = jsonVille.getAsString();
                    }
                    if (!(jsonPays == null)) {
                        pays = jsonPays.getAsString();
                    }


                    AdresseDTO adresse = new AdresseDTO(adresse_id, rue, numeroRue, quartier, codePostal, ville, pays);

                    entreprisesByRaisonSociale.add(new ContactDTO(id, email, telephone, fax, adresse, formeJuridique, raisonSociale));
                }
            }
            return entreprisesByRaisonSociale;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ContactDTO> getEntrepriseByFormeJuridique(String formeJuridique) {
        List<ContactDTO> entreprises = new ArrayList<>();
        String body = null;

        Request request = new Request.Builder()
                .url(url + "/api/entreprises/get/formejuridique/" + formeJuridique)
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            entreprises = parserGetEntreprisesByFormeJuridique(body);
            return entreprises;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static List<ContactDTO> parserGetEntreprisesByFormeJuridique(String responseBody) {
        try {
            List<ContactDTO> entreprisesByFormeJuridique = new ArrayList<>();

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


                    if (!(jsonEmail == null)) {
                        email = jsonEmail.getAsString();
                    }
                    if (!(jsonFax == null)) {
                        fax = jsonFax.getAsString();
                    }
                    if (!(jsonTelephone == null)) {
                        telephone = jsonTelephone.getAsString();
                    }
                    if (!(jsonRaisonSociale == null)) {
                        raisonSociale = jsonRaisonSociale.getAsString();
                    }
                    if (!(jsonFormeJuridique == null)) {
                        formeJuridique = jsonFormeJuridique.getAsString();
                    }


                    if (!(jsonRue == null)) {
                        rue = jsonRue.getAsString();
                    }
                    if (!(jsonNumeroRue == null)) {
                        numeroRue = jsonNumeroRue.getAsInt();
                    }
                    if (!(jsonCodePostal == null)) {
                        codePostal = jsonCodePostal.getAsInt();
                    }
                    if (!(jsonQuartier == null)) {
                        quartier = jsonQuartier.getAsString();
                    }
                    if (!(jsonVille == null)) {
                        ville = jsonVille.getAsString();
                    }
                    if (!(jsonPays == null)) {
                        pays = jsonPays.getAsString();
                    }


                    AdresseDTO adresse = new AdresseDTO(adresse_id, rue, numeroRue, quartier, codePostal, ville, pays);

                    entreprisesByFormeJuridique.add(new ContactDTO(id, email, telephone, fax, adresse, formeJuridique, raisonSociale));
                }
            }
            return entreprisesByFormeJuridique;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ContactDTO> getContactsByEmail(String email) {

        List<ContactDTO> contactsByEmail = new ArrayList<>();
        String body = null;

        Request request = new Request.Builder()
                .url(url + "/api/contacts/get/email/" + email)
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            contactsByEmail = parserGetContactsByEmail(body);
            return contactsByEmail;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    private static List<ContactDTO> parserGetContactsByEmail(String body) {
        List<ContactDTO> contactsByEmail = new ArrayList<>();
        try {
            JsonArray users = new JsonParser().parse(body).getAsJsonArray();

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


                    if (!(jsonEmail == null)) {
                        email = jsonEmail.getAsString();
                    }
                    if (!(jsonFax == null)) {
                        fax = jsonFax.getAsString();
                    }
                    if (!(jsonTelephone == null)) {
                        telephone = jsonTelephone.getAsString();
                    }
                    if (!(jsonRaisonSociale == null)) {
                        raisonSociale = jsonRaisonSociale.getAsString();
                    }
                    if (!(jsonFormeJuridique == null)) {
                        formeJuridique = jsonFormeJuridique.getAsString();
                    }


                    if (!(jsonRue == null)) {
                        rue = jsonRue.getAsString();
                    }
                    if (!(jsonNumeroRue == null)) {
                        numeroRue = jsonNumeroRue.getAsInt();
                    }
                    if (!(jsonCodePostal == null)) {
                        codePostal = jsonCodePostal.getAsInt();
                    }
                    if (!(jsonQuartier == null)) {
                        quartier = jsonQuartier.getAsString();
                    }
                    if (!(jsonVille == null)) {
                        ville = jsonVille.getAsString();
                    }
                    if (!(jsonPays == null)) {
                        pays = jsonPays.getAsString();
                    }


                    AdresseDTO adresse = new AdresseDTO(adresse_id, rue, numeroRue, quartier, codePostal, ville, pays);

                    contactsByEmail.add(new ContactDTO(id, email, telephone, fax, adresse, formeJuridique, raisonSociale));


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


                    if (!(jsonEmail == null)) {
                        email = jsonEmail.getAsString();
                    }
                    if (!(jsonFax == null)) {
                        fax = jsonFax.getAsString();
                    }
                    if (!(jsonTelephone == null)) {
                        telephone = jsonTelephone.getAsString();
                    }
                    if (!(jsonNom == null)) {
                        nom = jsonNom.getAsString();
                    }
                    if (!(jsonPrenom == null)) {
                        prenom = jsonPrenom.getAsString();
                    }


                    if (!(jsonRue == null)) {
                        rue = jsonRue.getAsString();
                    }
                    if (!(jsonNumeroRue == null)) {
                        numeroRue = jsonNumeroRue.getAsInt();
                    }
                    if (!(jsonCodePostal == null)) {
                        codePostal = jsonCodePostal.getAsInt();
                    }
                    if (!(jsonQuartier == null)) {
                        quartier = jsonQuartier.getAsString();
                    }
                    if (!(jsonVille == null)) {
                        ville = jsonVille.getAsString();
                    }
                    if (!(jsonPays == null)) {
                        pays = jsonPays.getAsString();
                    }


                    AdresseDTO adresse = new AdresseDTO(adresse_id, rue, numeroRue, quartier, codePostal, ville, pays);

                    contactsByEmail.add(new ContactDTO(id, prenom, nom, email, telephone, fax, adresse));

                }
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return contactsByEmail;
    }


    public static List<ContactDTO> getParticuliersByFirstName(String fname) {
        List<ContactDTO> contacts = new ArrayList<>();
        String body = null;

        Request request = new Request.Builder()
                .url(url + "/api/particuliers/get/prenom/" + fname)
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            contacts = parseGetContactsByFirstName(body);
            return contacts;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public static List<ContactDTO> parseGetContactsByFirstName(String responseBody) {
        List<ContactDTO> contactsByFirstName = new ArrayList<>();
        try {

            JsonArray users = new JsonParser().parse(responseBody).getAsJsonArray();

            for (int i = 0; i < users.size(); i++) {
                JsonObject member = users.get(i).getAsJsonObject();


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


                if (!(jsonEmail == null)) {
                    email = jsonEmail.getAsString();
                }
                if (!(jsonFax == null)) {
                    fax = jsonFax.getAsString();
                }
                if (!(jsonTelephone == null)) {
                    telephone = jsonTelephone.getAsString();
                }
                if (!(jsonNom == null)) {
                    nom = jsonNom.getAsString();
                }
                if (!(jsonPrenom == null)) {
                    prenom = jsonPrenom.getAsString();
                }


                if (!(jsonRue == null)) {
                    rue = jsonRue.getAsString();
                }
                if (!(jsonNumeroRue == null)) {
                    numeroRue = jsonNumeroRue.getAsInt();
                }
                if (!(jsonCodePostal == null)) {
                    codePostal = jsonCodePostal.getAsInt();
                }
                if (!(jsonQuartier == null)) {
                    quartier = jsonQuartier.getAsString();
                }
                if (!(jsonVille == null)) {
                    ville = jsonVille.getAsString();
                }
                if (!(jsonPays == null)) {
                    pays = jsonPays.getAsString();
                }


                AdresseDTO adresse = new AdresseDTO(adresse_id, rue, numeroRue, quartier, codePostal, ville, pays);

                contactsByFirstName.add(new ContactDTO(id, prenom, nom, email, telephone, fax, adresse));
            }
            return contactsByFirstName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public static List<ContactDTO> getParticuliersByEmail(String email) {
        List<ContactDTO> contacts = new ArrayList<>();
        String body = null;

        Request request = new Request.Builder()
                .url(url + "/api/particuliers/get/email/" + email)
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            contacts = parseGetContactsByName(body); //La méthode du parseByName fait la même chose donc pas besoin de créer la même méthode deux fois
            return contacts;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public static List<ContactDTO> getEntreprisesByEmail(String email) {
        List<ContactDTO> contacts = new ArrayList<>();
        String body = null;

        Request request = new Request.Builder()
                .url(url + "/api/entreprises/get/email/" + email)
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            contacts = parserGetEntreprisesByRaisonSociale(body); //La méthode du parseByName fait la même chose donc pas besoin de créer la même méthode deux fois
            return contacts;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public static List<ContactDTO> getAllParticuliers() {
        List<ContactDTO> contacts = new ArrayList<>();
        String body = null;

        Request request = new Request.Builder()
                .url(url + "/api/particuliers/all")
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            contacts = parseGetContactsByFirstName(body);
            return contacts;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<ContactDTO> getAllEntreprises() {
        List<ContactDTO> contacts = new ArrayList<>();
        String body = null;

        Request request = new Request.Builder()
                .url(url + "/api/entreprises/all")
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            contacts = parserGetEntreprisesByRaisonSociale(body);
            return contacts;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}

