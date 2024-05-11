package com.example.giefrontend1.Controllers.DTO;

import java.util.List;

public class AchatDTO {

    private long id;
    private ContactDTO fournisseur;
    private List<DetailAchatDTO> detailsAchat;
    private String dateAchat;
    private double prix;
    private String statutAchat;

    public AchatDTO(long id, ContactDTO fournisseur, List<DetailAchatDTO> detailsAchat, String dateAchat, double prix, String statutAchat) {
        this.id = id;
        this.fournisseur = fournisseur;
        this.detailsAchat = detailsAchat;
        this.dateAchat = dateAchat;
        this.prix = prix;
        this.statutAchat = statutAchat;
    }

    public AchatDTO(ContactDTO fournisseur, List<DetailAchatDTO> detailsAchat, String dateAchat, double prix, String statutAchat) {
        this.fournisseur = fournisseur;
        this.detailsAchat = detailsAchat;
        this.dateAchat = dateAchat;
        this.prix = prix;
        this.statutAchat = statutAchat;
    }

    public AchatDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ContactDTO getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(ContactDTO fournisseur) {
        this.fournisseur = fournisseur;
    }

    public List<DetailAchatDTO> getDetailsAchat() {
        return detailsAchat;
    }

    public void setDetailsAchat(List<DetailAchatDTO> detailsAchat) {
        this.detailsAchat = detailsAchat;
    }

    public String getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(String dateAchat) {
        this.dateAchat = dateAchat;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getStatutAchat() {
        return statutAchat;
    }

    public void setStatutAchat(String statutAchat) {
        this.statutAchat = statutAchat;
    }

    @Override
    public String toString() {
        return "AchatDTO{" +
                "id=" + id +
                ", fournisseur=" + fournisseur +
                ", detailsAchat=" + detailsAchat +
                ", dateAchat='" + dateAchat + '\'' +
                ", prix=" + prix +
                ", statutAchat='" + statutAchat + '\'' +
                '}';
    }
}
