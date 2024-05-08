package com.example.giefrontend1.Controllers.DTO;

public class CommandeDTO2 {
    private long numboncommande,idClient;
    private String dateCommande,dateReglement;
    private double prix;
    private String etat;

    public CommandeDTO2(){}
    public CommandeDTO2(long numboncommande,long idClient,String dateCommande,String dateReglement,double prix,String etat){
        this.dateCommande=dateCommande;
        this.dateReglement=dateReglement;
        this.etat=etat;
        this.prix=prix;
        this.idClient=idClient;
    }
}
