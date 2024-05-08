package com.example.giefrontend1.Parser;

import com.example.giefrontend1.Controllers.DTO.CommandeDTO;
import com.example.giefrontend1.Controllers.DTO.ContactDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        for (JsonElement element : commandesArray) {
            JsonObject commandeJson = element.getAsJsonObject();

            long numBonCommande = commandeJson.get("numBonCommande").getAsLong();
            long idClient = commandeJson.getAsJsonObject("client").get("id").getAsLong();
            String dateCommande = commandeJson.get("dateCommande").getAsString();
            String dateReglement = commandeJson.get("dateReglement").getAsString();
            double totalCommande = commandeJson.get("totalCommande").getAsDouble();
            String etatCommande = commandeJson.get("etatCommande").getAsString();
            CommandeDTO commandeDTO = new CommandeDTO(numBonCommande, idClient, dateCommande, dateReglement, totalCommande, etatCommande);
            allCommandes.add(commandeDTO);
        }

        return allCommandes;
    }
    public static CommandeDTO getCommandeById(long id){
        CommandeDTO commande ;
        String body =null;
        Request request = new Request.Builder()
                .url(url+"/api/commande/get/id/"+id)
                .build();
        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            commande = parseCommande(body);
            return commande;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public static CommandeDTO parseCommande(String responseBody) {
        JsonObject commandeJson = JsonParser.parseString(responseBody).getAsJsonObject();

        long numBonCommande = commandeJson.get("numBonCommande").getAsLong();
        long idClient = commandeJson.getAsJsonObject("client").get("id").getAsLong();
        String dateCommande = commandeJson.get("dateCommande").getAsString();
        String dateReglement = commandeJson.get("dateReglement").getAsString();
        double totalCommande = commandeJson.get("totalCommande").getAsDouble();
        String etatCommande = commandeJson.get("etatCommande").getAsString();

        // Vous devrez ajuster cette partie pour obtenir les détails des commandes si nécessaire
        // Par exemple, vous pourriez itérer sur les "detailsCommandes" si cela est nécessaire.

        return new CommandeDTO(numBonCommande, idClient, dateCommande, dateReglement, totalCommande, etatCommande);
    }


}
