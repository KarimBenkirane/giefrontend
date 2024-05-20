package com.example.giefrontend1.Controllers.DTO;

import java.util.ArrayList;
import java.util.List;

public class CommandeDTO {
    private String dateCommande,dateReglement,etatCommande;
    private List<DetailCommandeDTO> detailsCommande;
    private ContactDTO client;
    private long idClient;
    private double totalCommande;
    private long numBonCommande;


    public CommandeDTO(long numBonCommande,ContactDTO client,List<DetailCommandeDTO> detailsCommande,String dateCommande1,String dateReglement1,double totalCommande1,String etatCommande1){
        this.numBonCommande=numBonCommande;
        this.client=client;
        this.detailsCommande=detailsCommande;
        this.dateCommande=dateCommande1;
        this.dateReglement=dateReglement1;
        this.totalCommande=totalCommande1;
        this.etatCommande=etatCommande1;

    }
    public CommandeDTO(ContactDTO client, List<DetailCommandeDTO> detailsCommande, String dateCommande1, String dateReglement1, double totalCommande1, String etatCommande1){
        this.numBonCommande=numBonCommande;
        this.detailsCommande=new ArrayList<>();
        this.client=client;
        this.dateCommande=dateCommande1;
        this.dateReglement=dateReglement1;
        this.totalCommande=totalCommande1;
        this.etatCommande=etatCommande1;

    }

    public CommandeDTO(long idClient1,List<DetailCommandeDTO> detailsCommande,String dateCommande1,String dateReglement1,double totalCommande1){
        this.idClient=idClient1;
        this.dateCommande=dateCommande1;
        this.dateReglement=dateReglement1;
        this.totalCommande=totalCommande1;

    }
    public long getNumBonCommande() {
        return numBonCommande;
    }

    public Long numBonCommande() {
        return numBonCommande;
    }

    public long getIdClient() {
        return idClient;
    }

    public Long idClient() {
        return idClient;
    }

    public String getDateCommande() {
        return dateCommande;
    }

    public String dateCommande() {
        return dateCommande;
    }

    public String getDateReglement() {
        return dateReglement;
    }

    public String dateReglement() {
        return dateReglement;
    }

    public double getTotalCommande() {
        return totalCommande;
    }

    public Double totalCommande() {
        return totalCommande;
    }

    public String getEtatCommande() {
        return etatCommande;
    }

    public String etatCommande() {
        return etatCommande;
    }

    public void setDetailsCommande(List<DetailCommandeDTO> detailsCommande) {
        this.detailsCommande = detailsCommande;
    }

    public List<DetailCommandeDTO> getDetailsCommande() {
        return detailsCommande;
    }

    public void setEtatCommande(String etatCommande) {
        this.etatCommande = etatCommande;
    }

    @Override
    public String toString() {
        return "CommandeDTO{" +
                "dateCommande='" + dateCommande + '\'' +
                ", dateReglement='" + dateReglement + '\'' +
                ", etatCommande='" + etatCommande + '\'' +
                ", detailsCommande=" + detailsCommande +
                ", client=" + client +
                ", idClient=" + idClient +
                ", totalCommande=" + totalCommande +
                ", numBonCommande=" + numBonCommande +
                '}';
    }

    public long getClientId() {
        return client != null ? client.getId() : 0;
    }


}
