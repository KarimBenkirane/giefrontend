package com.example.giefrontend1.Controllers.DTO;

import javafx.beans.property.*;

public class CommandeDTO {
    private final LongProperty numBonCommande;
    private final LongProperty idClient;
    private final StringProperty dateCommande;
    private final StringProperty dateReglement;
    private final DoubleProperty totalCommande;
    private final StringProperty etatCommande;


    public CommandeDTO(long numBonCommande, long idClient, String dateCommande, String dateReglement, double totalCommande, String etatCommande) {
        this.numBonCommande = new SimpleLongProperty(numBonCommande);
        this.idClient = new SimpleLongProperty(idClient);
        this.dateCommande = new SimpleStringProperty(dateCommande);
        this.dateReglement = new SimpleStringProperty(dateReglement);
        this.totalCommande = new SimpleDoubleProperty(totalCommande);
        this.etatCommande = new SimpleStringProperty(etatCommande);
    }

    public long getNumBonCommande() {
        return numBonCommande.get();
    }

    public LongProperty numBonCommandeProperty() {
        return numBonCommande;
    }

    public long getIdClient() {
        return idClient.get();
    }

    public LongProperty idClientProperty() {
        return idClient;
    }

    public String getDateCommande() {
        return dateCommande.get();
    }

    public StringProperty dateCommandeProperty() {
        return dateCommande;
    }

    public String getDateReglement() {
        return dateReglement.get();
    }

    public StringProperty dateReglementProperty() {
        return dateReglement;
    }

    public double getTotalCommande() {
        return totalCommande.get();
    }

    public DoubleProperty totalCommandeProperty() {
        return totalCommande;
    }

    public String getEtatCommande() {
        return etatCommande.get();
    }

    public StringProperty etatCommandeProperty() {
        return etatCommande;
    }
}
