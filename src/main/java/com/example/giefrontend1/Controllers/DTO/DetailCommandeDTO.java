package com.example.giefrontend1.Controllers.DTO;

public class DetailCommandeDTO {
    private int id;
    private ProduitDTO produitDTO;
    private int qteCommander;
    private double prixCommande,reduction;
    public DetailCommandeDTO(ProduitDTO produitDTO,int qteCommander,double prixCommande,double reduction, int id){
        this.produitDTO=produitDTO;
        this.qteCommander=qteCommander;
        this.prixCommande=prixCommande;
        this.reduction=reduction;
        this.id=id;
    }
    public DetailCommandeDTO(ProduitDTO produitDTO,int qteCommander,double prixCommande,double reduction){
        this.produitDTO=produitDTO;
        this.qteCommander=qteCommander;
        this.prixCommande=prixCommande;
        this.reduction=reduction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrixCommande() {
        return prixCommande;
    }

    public void setPrixCommande(double prixCommande) {
        this.prixCommande = prixCommande;
    }

    public ProduitDTO getProduitDTO() {
        return produitDTO;
    }

    public void setProduitDTO(ProduitDTO produitDTO) {
        this.produitDTO = produitDTO;
    }

    public int getQteCommante() {
        return qteCommander;
    }

    public void setQteCommante(int qteCommante) {
        this.qteCommander = qteCommante;
    }

    public double getReduction() {
        return reduction;
    }

    public void setReduction(double reduction) {
        this.reduction = reduction;
    }
    public long getIdProduit(){
        return this.produitDTO.getId();
    }
    public double getPrixProduit(){
        return this.produitDTO.getPrix();
    }


    @Override
    public String toString() {
        return "DetailCommandeDTO{" +
                "id=" + id +
                ", produitDTO=" + produitDTO +
                ", qteCommander=" + qteCommander +
                ", prixCommande=" + prixCommande +
                ", reduction=" + reduction +
                '}';
    }

    public ProduitDTO getProduitObjet() {
        return this.produitDTO;
    }

}
