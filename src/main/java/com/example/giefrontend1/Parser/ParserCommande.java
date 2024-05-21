package com.example.giefrontend1.Parser;

import com.example.giefrontend1.Controllers.DTO.*;
import com.google.gson.*;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParserCommande {
    private static final String url = "http://localhost:4567";
    private static final OkHttpClient client = new OkHttpClient();

    public static List<CommandeDTO> getAllCommandes() {
        List<CommandeDTO> commandes = new ArrayList<>();
        String body = null;

        Request request = new Request.Builder()
                .url(url + "/api/commande/get/all")
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            commandes = parseAllCommandes(body);
            return commandes;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<CommandeDTO> parseAllCommandes(String responseBody) {
        List<CommandeDTO> allCommandes = new ArrayList<>();
        JsonArray commandesArray = JsonParser.parseString(responseBody).getAsJsonArray();
        for(int i = 0 ; i < commandesArray.size() ; i++) {
            JsonObject commandeJson = commandesArray.get(i).getAsJsonObject();
            long numBonCommande = commandeJson.get("numBonCommande").getAsLong();
            String dateCommande = commandeJson.has("dateCommande") ?
                    commandeJson.get("dateCommande").getAsString() : null;
            String dateReglement = commandeJson.has("dateReglement") ?
                    commandeJson.get("dateReglement").getAsString() : null;
            String etatCommande = commandeJson.has("etatCommande") ?
                    commandeJson.get("etatCommande").getAsString() : null;
            double totalCommande = commandeJson.has("totalCommande") ?
                    commandeJson.get("totalCommande").getAsDouble() : 0;
            ContactDTO client = null;
            if(commandeJson.has("client")){
                JsonObject clientJson = commandeJson.get("client").getAsJsonObject();
                int client_id = clientJson.get("id").getAsInt();
                client = ParserContact.getContactByID(client_id);
            }

            // Parsing details de commande
            List<DetailCommandeDTO> detailsCommande = null;
            if (commandeJson.has("detailsCommande")) {
                detailsCommande = new ArrayList<>();
                JsonArray detailsCommandeArray = commandeJson.getAsJsonArray("detailsCommande");
                for (JsonElement detailElement : detailsCommandeArray) {
                    JsonObject detailJson = detailElement.getAsJsonObject();
                    // Parsing chaque détail de commande
                    int id = detailJson.get("id").getAsInt();
                    int qteCommande = detailJson.get("qteCommande").getAsInt();
                    double prixUnitaire = detailJson.get("prixUnitaire").getAsDouble();
                    double reduction = detailJson.get("reduction").getAsDouble();
                    JsonObject produitJson = detailJson.get("produitObjet").getAsJsonObject();
                    int produit_id = produitJson.get("id").getAsInt();
                    ProduitDTO produitDTO = ParserProduit.getProduitByID(produit_id);
                    // Ajouter le détail de commande à la liste
                    detailsCommande.add(new DetailCommandeDTO(produitDTO,qteCommande, prixUnitaire, reduction));
                }
            }

            CommandeDTO commandeDTO = new CommandeDTO(numBonCommande,client,null , dateCommande, dateReglement, totalCommande, etatCommande);
            allCommandes.add(commandeDTO);
        }

        return allCommandes;
    }
    public static CommandeDTO getCommandeById(long id){
        String body =null;
        CommandeDTO commandeDTO=null;
        Request request = new Request.Builder()
                .url(url+"/api/commande/get/id/"+id)
                .build();
        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
             commandeDTO = parseCommandeByID(body);
            return commandeDTO;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    private static CommandeDTO parseCommandeByID(String body) {
        // Parse JSON string into a JsonObject
        JsonObject commandeJson = JsonParser.parseString(body).getAsJsonObject();

        long numBonCommande = commandeJson.get("numBonCommande").getAsLong();
        String dateCommande = commandeJson.has("dateCommande") ?
                commandeJson.get("dateCommande").getAsString() : null;
        String dateReglement = commandeJson.has("dateReglement") ?
                commandeJson.get("dateReglement").getAsString() : null;
        String etatCommande = commandeJson.has("etatCommande") ?
                commandeJson.get("etatCommande").getAsString() : null;
        double totalCommande = commandeJson.has("totalCommande") ?
                commandeJson.get("totalCommande").getAsDouble() : 0;
        ContactDTO client = null;
        if(commandeJson.has("client")){
            JsonObject clientJson = commandeJson.get("client").getAsJsonObject();
            int client_id = clientJson.get("id").getAsInt();
            client = ParserContact.getContactByID(client_id);
        }

        // Parsing details de commande
        List<DetailCommandeDTO> detailsCommande = null;
        if (commandeJson.has("detailsCommande")) {
            detailsCommande = new ArrayList<>();
            JsonArray detailsCommandeArray = commandeJson.getAsJsonArray("detailsCommande");
            for (JsonElement detailElement : detailsCommandeArray) {
                JsonObject detailJson = detailElement.getAsJsonObject();
                // Parsing chaque détail de commande
                int id = detailJson.get("id").getAsInt();
                int qteCommande = detailJson.get("qteCommande").getAsInt();
                double prixUnitaire = detailJson.get("prixUnitaire").getAsDouble();
                double reduction = detailJson.get("reduction").getAsDouble();
                JsonObject produitJson = detailJson.get("produitObjet").getAsJsonObject();
                int produit_id = produitJson.get("id").getAsInt();
                ProduitDTO produitDTO = ParserProduit.getProduitByID(produit_id);
                // Ajouter le détail de commande à la liste
                detailsCommande.add(new DetailCommandeDTO(produitDTO,qteCommande, prixUnitaire, reduction));
            }
        }
        return new CommandeDTO(numBonCommande,client,detailsCommande,dateCommande,dateReglement,totalCommande,etatCommande);

    }
    public static boolean createCommande(CommandeDTO newCommande) {
        Gson gson = new Gson();
        String json = gson.toJson(newCommande);
        //System.out.println(json);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url + "/api/commandes/add")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<CommandeDTO> getAdvSearch(Map<String, Object> searchMap) {

        Gson gson = new Gson();
        String json = gson.toJson(searchMap);
        System.out.println(json);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url + "/api/commandes/advSearch")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // Read response body and parse it
                String responseBody = response.body().string();
                return parseAllCommandes(responseBody);
            } else {
                System.err.println("Error: " + response.code() + " " + response.message());
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    public static boolean updateCommande(CommandeDTO commandeDTO, long id) {
        Gson gson = new Gson();
        String json = gson.toJson(commandeDTO);
        System.out.println(json);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url + "/api/commandes/update/"+id)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<DetailCommandeDTO> getDetailsCommandesByID(long id) {
        String body = null;
        List<DetailCommandeDTO> detailCommandeDTO = null;

        Request request = new Request.Builder()
                .url(url + "/api/commandes/get/detailscommandes/"+id)
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            detailCommandeDTO = parseDetailsCommandesByID(body);
            return detailCommandeDTO;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static List<DetailCommandeDTO> parseDetailsCommandesByID(String body) {
        JsonArray detailsCommandeJson = JsonParser.parseString(body).getAsJsonArray();
        List<DetailCommandeDTO> detailCommandeDTO = null;
        detailCommandeDTO = new ArrayList<>();
        for(int j = 0 ; j < detailsCommandeJson.size() ; j++){
            JsonObject detailJson = detailsCommandeJson.get(j).getAsJsonObject();
            int detail_id = detailJson.get("id").getAsInt();
            int qteAchetee = detailJson.get("qteCommande").getAsInt();
            double prixAchat = detailJson.get("prixCommande").getAsDouble();
            double reduction = detailJson.get("reduction").getAsDouble();
            JsonObject produitJson = detailJson.get("produitObjet").getAsJsonObject();
            int produit_id = produitJson.get("id").getAsInt();
            ProduitDTO produitDTO = ParserProduit.getProduitByID(produit_id);
            detailCommandeDTO.add(new DetailCommandeDTO(produitDTO,qteAchetee,prixAchat,reduction,detail_id));

        }
        return detailCommandeDTO;
    }



}
