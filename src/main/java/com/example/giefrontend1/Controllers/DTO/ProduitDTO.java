package com.example.giefrontend1.Controllers.DTO;

public class ProduitDTO {

    private long id;
    private String marque,modele,description,categorie;
    private int qteStock;
    private double prix;

    public ProduitDTO(long id, String marque, String modele, String description, String categorie, int qteStock, double prix) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.description = description;
        this.categorie = categorie;
        this.qteStock = qteStock;
        this.prix = prix;
    }

    public ProduitDTO(String marque, String modele, String description, String categorie, int qteStock, double prix) {
        this.marque = marque;
        this.modele = modele;
        this.description = description;
        this.categorie = categorie;
        this.qteStock = qteStock;
        this.prix = prix;
    }

    public ProduitDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getQteStock() {
        return qteStock;
    }

    public void setQteStock(int qteStock) {
        this.qteStock = qteStock;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "ProduitDTO{" +
                "id=" + id +
                ", marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", description='" + description + '\'' +
                ", categorie='" + categorie + '\'' +
                ", qteStock=" + qteStock +
                ", prix=" + prix +
                '}';
    }
}
