package com.example.giefrontend1.Parser;

import com.example.giefrontend1.Controllers.DTO.AchatDTO;
import com.example.giefrontend1.Controllers.DTO.ContactDTO;
import com.example.giefrontend1.Controllers.DTO.DetailAchatDTO;
import com.example.giefrontend1.Controllers.DTO.ProduitDTO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParserAchat {

    private static final String url = "http://localhost:4567";
    private static final OkHttpClient client = new OkHttpClient();

    public static List<AchatDTO> getAllAchats() {
        List<AchatDTO> achats = new ArrayList<>();
        String body = null;

        Request request = new Request.Builder()
                .url(url + "/api/achats/get/all")
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            achats = parseAllAchats(body);
            return achats;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static List<AchatDTO> parseAllAchats(String body) {
        List<AchatDTO> parsedAchats = new ArrayList<>();
        JsonArray arrayAchats = JsonParser.parseString(body).getAsJsonArray();
        for(int i = 0 ; i < arrayAchats.size() ; i++) {
            JsonObject achatJson = arrayAchats.get(i).getAsJsonObject();
            long id = achatJson.get("id").getAsLong();
            String dateAchat = achatJson.has("dateAchat") ?
                    achatJson.get("dateAchat").getAsString() : null;
            String statutAchat = achatJson.has("statutAchat") ?
                    achatJson.get("statutAchat").getAsString() : null;
            double prix = achatJson.has("prix") ?
                    achatJson.get("prix").getAsDouble() : 0;
            ContactDTO fournisseur = null;
            if(achatJson.has("fournisseur")){
                JsonObject fournisseurJson = achatJson.get("fournisseur").getAsJsonObject();
                int fournisseur_id = fournisseurJson.get("id").getAsInt();
                fournisseur = ParserContact.getContactByID(fournisseur_id);
            }
            List<DetailAchatDTO> detailsAchats = null;
            if(achatJson.has("detailsAchat")){
                detailsAchats = new ArrayList<>();
                JsonArray detailsAchatJson = achatJson.get("detailsAchat").getAsJsonArray();
                for(int j = 0 ; j < detailsAchatJson.size() ; j++){
                    JsonObject detailJson = detailsAchatJson.get(j).getAsJsonObject();
                    int detail_id = detailJson.get("id").getAsInt();
                    int qteAchetee = detailJson.get("qteAchetee").getAsInt();
                    double prixAchat = detailJson.get("prixAchat").getAsDouble();
                    double reduction = detailJson.get("reduction").getAsDouble();
                    JsonObject produitJson = detailJson.get("produitObjet").getAsJsonObject();
                    int produit_id = produitJson.get("id").getAsInt();
                    ProduitDTO produitDTO = ParserProduit.getProduitByID(produit_id);
                    detailsAchats.add(new DetailAchatDTO(produitDTO,qteAchetee,prixAchat,reduction,detail_id));
                }
            }

            parsedAchats.add(new AchatDTO(id,fournisseur,detailsAchats,dateAchat,prix,statutAchat));
        }

        return parsedAchats;
    }

    public static AchatDTO getAchatByID(int id) {
        String body = null;
        AchatDTO achatDTO = null;

        Request request = new Request.Builder()
                .url(url + "/api/achats/get/id/"+id)
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            achatDTO = parseAchatByID(body);
            return achatDTO;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<DetailAchatDTO> getDetailsAchatsByID(long id) {
        String body = null;
        List<DetailAchatDTO> detailAchatDTO = null;

        Request request = new Request.Builder()
                .url(url + "/api/achats/get/detailsachats/"+id)
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            detailAchatDTO = parseDetailsAchatsByID(body);
            return detailAchatDTO;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static List<DetailAchatDTO> parseDetailsAchatsByID(String body) {
        JsonArray detailsAchatJson = JsonParser.parseString(body).getAsJsonArray();
        List<DetailAchatDTO> detailsAchats = null;
        detailsAchats = new ArrayList<>();
        for(int j = 0 ; j < detailsAchatJson.size() ; j++){
            JsonObject detailJson = detailsAchatJson.get(j).getAsJsonObject();
            int detail_id = detailJson.get("id").getAsInt();
            int qteAchetee = detailJson.get("qteAchetee").getAsInt();
            double prixAchat = detailJson.get("prixAchat").getAsDouble();
            double reduction = detailJson.get("reduction").getAsDouble();
            JsonObject produitJson = detailJson.get("produitObjet").getAsJsonObject();
            int produit_id = produitJson.get("id").getAsInt();
            ProduitDTO produitDTO = ParserProduit.getProduitByID(produit_id);
            detailsAchats.add(new DetailAchatDTO(produitDTO,qteAchetee,prixAchat,reduction,detail_id));

        }
    return detailsAchats;
    }

    private static AchatDTO parseAchatByID(String body) {

        JsonObject achatJson = JsonParser.parseString(body).getAsJsonObject();
        int id = achatJson.get("id").getAsInt();
        String dateAchat = achatJson.has("dateAchat") ?
                achatJson.get("dateAchat").getAsString() : null;
        String statutAchat = achatJson.has("statutAchat") ?
                achatJson.get("statutAchat").getAsString() : null;
        double prix = achatJson.has("prix") ?
                achatJson.get("prix").getAsDouble() : 0;
        ContactDTO fournisseur = null;
        if(achatJson.has("fournisseur")){
            JsonObject fournisseurJson = achatJson.get("fournisseur").getAsJsonObject();
            int fournisseur_id = fournisseurJson.get("id").getAsInt();
            fournisseur = ParserContact.getContactByID(fournisseur_id);
        }
        List<DetailAchatDTO> detailsAchats = null;
        if(achatJson.has("detailsAchat")){
            detailsAchats = new ArrayList<>();
            JsonArray detailsAchatJson = achatJson.get("detailsAchat").getAsJsonArray();
            for(int j = 0 ; j < detailsAchatJson.size() ; j++){
                JsonObject detailJson = detailsAchatJson.get(j).getAsJsonObject();
                int detail_id = detailJson.get("id").getAsInt();
                int qteAchetee = detailJson.get("qteAchetee").getAsInt();
                double prixAchat = detailJson.get("prixAchat").getAsDouble();
                double reduction = detailJson.get("reduction").getAsDouble();
                JsonObject produitJson = detailJson.get("produitObjet").getAsJsonObject();
                int produit_id = produitJson.get("id").getAsInt();
                ProduitDTO produitDTO = ParserProduit.getProduitByID(produit_id);
                detailsAchats.add(new DetailAchatDTO(produitDTO,qteAchetee,prixAchat,reduction));
            }
        }
        return new AchatDTO(id,fournisseur,detailsAchats,dateAchat,prix,statutAchat);

    }

    public static boolean createAchat(AchatDTO newAchat) {
        Gson gson = new Gson();
        String json = gson.toJson(newAchat);
        System.out.println(json);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url + "/api/achats/add")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateAchat(AchatDTO achatDTO, long id) {
        Gson gson = new Gson();
        String json = gson.toJson(achatDTO);
        System.out.println(json);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url + "/api/achats/update/"+id)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static List<String > getAllFournisseursAchats() {
        List<String > fournisseurs = new ArrayList<>();
        String body = null;

        Request request = new Request.Builder()
                .url(url + "/api/achats/get/fournisseurs")
                        .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            fournisseurs = parseAllFournisseurs(body);
            return fournisseurs;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<String> parseAllFournisseurs(String body) {
        List<String> liste = new ArrayList<>();
        JsonArray arrayFournisseurs = new JsonParser().parse(body).getAsJsonArray();
        for(int i = 0; i < arrayFournisseurs.size(); i++) {
            liste.add(arrayFournisseurs.get(i).getAsString());
        }
        return liste;
    }


    public static List<AchatDTO> getAdvSearch(Map<String, String> searchMap) {

        Gson gson = new Gson();
        String json = gson.toJson(searchMap);
        System.out.println(json);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url + "/api/achats/advSearch")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // Read response body and parse it
                String responseBody = response.body().string();
                return parseAllAchats(responseBody);
            } else {
                System.err.println("Error: " + response.code() + " " + response.message());
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}

