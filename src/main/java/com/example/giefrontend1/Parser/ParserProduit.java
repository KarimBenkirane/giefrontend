package com.example.giefrontend1.Parser;

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

public class ParserProduit {

    private static final String url = "http://localhost:4567";
    private static final OkHttpClient client = new OkHttpClient();

    public static List<ProduitDTO> getAllProduits() {
        List<ProduitDTO> produits = new ArrayList<>();
        String body = null;

        Request request = new Request.Builder()
                .url(url + "/api/produits/get/all")
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            produits = parseAllProduits(body);
            return produits;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public static List<String > getAllMarquesCategories(String endpoint) {
        List<String > categories = new ArrayList<>();
        String body = null;

        Request request = new Request.Builder()
                .url(url + "/api/produits/get/"+endpoint)
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            categories = parseAllMarquesCategories(body);
            return categories;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<String> parseAllMarquesCategories(String body) {
        List<String> liste = new ArrayList<>();
        JsonArray arrayCategories = new JsonParser().parse(body).getAsJsonArray();
        for(int i = 0; i < arrayCategories.size(); i++) {
            liste.add(arrayCategories.get(i).getAsString());
        }
        return liste;
    }


    private static List<ProduitDTO> parseAllProduits(String body) {
        List<ProduitDTO> parsedProduits = new ArrayList<>();
        JsonArray arrayProducts = JsonParser.parseString(body).getAsJsonArray();
        for(int i = 0 ; i < arrayProducts.size() ; i++) {
            JsonObject productJson = arrayProducts.get(i).getAsJsonObject();
            int id = productJson.get("id").getAsInt();
            String marque = productJson.has("marque") ?
                    productJson.get("marque").getAsString() : null;
            String modele = productJson.has("modele") ?
                    productJson.get("modele").getAsString() : null;
            String description = productJson.has("description") ?
                    productJson.get("description").getAsString() : null;
            String categorie = productJson.has("categorieProduit") ?
                    productJson.get("categorieProduit").getAsString() : null;
            int qteStock = productJson.has("qteStock") ?
                    productJson.get("qteStock").getAsInt() : 0;
            double prix = productJson.has("prix") ?
                    productJson.get("prix").getAsDouble() : 0;

            parsedProduits.add(new ProduitDTO(id,marque,modele,description,categorie,qteStock,prix));
        }

        return parsedProduits;
    }

    public static boolean createProduit(ProduitDTO newProduit) {
        Gson gson = new Gson();
        String json = gson.toJson(newProduit);
        System.out.println(json);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url + "/api/produits/add")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateProduit(ProduitDTO produitDTO, long id) {
        Gson gson = new Gson();
        String json = gson.toJson(produitDTO);
        System.out.println(json);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url + "/api/produits/update/"+id)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ProduitDTO getProduitByID(long id) {
        String body = null;
        ProduitDTO produitDTO = null;

        Request request = new Request.Builder()
                .url(url + "/api/produits/get/id/"+id)
                .build();

        try (Response response = client.newCall(request).execute()) {
            body = response.body().string();
            produitDTO = parseProduitByID(body);
            return produitDTO;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static ProduitDTO parseProduitByID(String body) {
        JsonObject productJson = JsonParser.parseString(body).getAsJsonObject();
        int id = productJson.get("id").getAsInt();
        String marque = productJson.has("marque") ?
                productJson.get("marque").getAsString() : null;
        String modele = productJson.has("modele") ?
                productJson.get("modele").getAsString() : null;
        String description = productJson.has("description") ?
                productJson.get("description").getAsString() : null;
        String categorie = productJson.has("categorieProduit") ?
                productJson.get("categorieProduit").getAsString() : null;
        int qteStock = productJson.has("qteStock") ?
                productJson.get("qteStock").getAsInt() : 0;
        double prix = productJson.has("prix") ?
                productJson.get("prix").getAsDouble() : 0;

        return new ProduitDTO(id,marque,modele,description,categorie,qteStock,prix);
    }


    public static boolean deleteProduitByID(long id) {
        Request request = new Request.Builder()
                .url(url + "/api/produits/delete/" + id)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<ProduitDTO> getProduitsByAdvSearch(Map<Object, Object> searchInfos) {
        Gson gson = new Gson();
        String json = gson.toJson(searchInfos);
        System.out.println(json);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request request = new Request.Builder()
                .url(url + "/api/produits/advSearch")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // Read response body and parse it
                String responseBody = response.body().string();
                return parseAllProduits(responseBody);
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

