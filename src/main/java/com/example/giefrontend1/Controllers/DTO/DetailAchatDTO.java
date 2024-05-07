package com.example.giefrontend1.Controllers.DTO;

public class DetailAchatDTO {

    private int id;
    private ProduitDTO produitObjet;
    private int qteAchetee;
    private double prixAchat;
    private double reduction;

    public DetailAchatDTO(ProduitDTO produitObjet, int qteAchetee, double prixAchat, double reduction, int id) {
        this.produitObjet = produitObjet;
        this.qteAchetee = qteAchetee;
        this.prixAchat = prixAchat;
        this.reduction = reduction;
        this.id = id;
    }

    public DetailAchatDTO(ProduitDTO produitObjet, int qteAchetee, double prixAchat, double reduction) {
        this.produitObjet = produitObjet;
        this.qteAchetee = qteAchetee;
        this.prixAchat = prixAchat;
        this.reduction = reduction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProduitDTO getProduitObjet() {
        return produitObjet;
    }

    public void setProduitObjet(ProduitDTO produitObjet) {
        this.produitObjet = produitObjet;
    }

    public int getQteAchetee() {
        return qteAchetee;
    }

    public void setQteAchetee(int qteAchetee) {
        this.qteAchetee = qteAchetee;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public double getReduction() {
        return reduction;
    }

    public void setReduction(double reduction) {
        this.reduction = reduction;
    }

    @Override
    public String toString() {
        return "DetailAchatDTO{" +
                "id=" + id +
                ", produitObjet=" + produitObjet +
                ", qteAchetee=" + qteAchetee +
                ", prixAchat=" + prixAchat +
                ", reduction=" + reduction +
                '}';
    }
}
