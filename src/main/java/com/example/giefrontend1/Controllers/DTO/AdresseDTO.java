package com.example.giefrontend1.Controllers.DTO;

public class AdresseDTO {
    private int id;
    private String rue;
    private int numeroRue;
    private String quartier;
    private int codePostal;
    private String ville;
    private String pays;

    public AdresseDTO(int id, String rue, int numeroRue, String quartier, int codePostal, String ville, String pays) {
        this.id = id;
        this.rue = rue;
        this.numeroRue = numeroRue;
        this.quartier = quartier;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public int getNumeroRue() {
        return numeroRue;
    }

    public void setNumeroRue(int numeroRue) {
        this.numeroRue = numeroRue;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Override
    public String toString() {
        return "Adresse:" +
                "id=" + id +
                ", rue='" + rue + '\'' +
                ", numeroRue=" + numeroRue +
                ", quartier='" + quartier + '\'' +
                ", codePostal=" + codePostal +
                ", ville='" + ville + '\'' +
                ", pays='" + pays + '\'' +
                '}';
    }
}
