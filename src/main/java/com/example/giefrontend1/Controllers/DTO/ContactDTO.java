package com.example.giefrontend1.Controllers.DTO;

public class ContactDTO {
    private int id;
    private String prenom;
    private String nom;
    private String email;
    private String telephone;
    private String fax;
    private AdresseDTO adresse;

    private String formeJuridique;
    private String raisonSociale;

    public ContactDTO() {
    }


    //Constructeur pour créer les instances des particuliers
    public ContactDTO(int id, String prenom, String nom, String email, String telephone, String fax, AdresseDTO adresse) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.fax = fax;
        this.adresse = adresse;
    }

    public ContactDTO(String prenom, String nom, String email, String telephone, String fax, AdresseDTO adresse) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.fax = fax;
        this.adresse = adresse;
    }

    //Constructeur pour créer les instances des entreprises
    public ContactDTO(int id, String email, String telephone, String fax, AdresseDTO adresse, String formeJuridique, String raisonSociale) {
        this.id = id;
        this.email = email;
        this.telephone = telephone;
        this.fax = fax;
        this.adresse = adresse;
        this.formeJuridique = formeJuridique;
        this.raisonSociale = raisonSociale;
    }

    public ContactDTO(String email, String telephone, String fax, AdresseDTO adresse, String formeJuridique, String raisonSociale) {
        this.email = email;
        this.telephone = telephone;
        this.fax = fax;
        this.adresse = adresse;
        this.formeJuridique = formeJuridique;
        this.raisonSociale = raisonSociale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public  String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public AdresseDTO getAdresse() {
        return adresse;
    }

    public void setAdresse(AdresseDTO adresse) {
        this.adresse = adresse;
    }

    public String getFormeJuridique() {
        return formeJuridique;
    }

    public void setFormeJuridique(String formeJuridique) {
        this.formeJuridique = formeJuridique;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    @Override
    public String toString(){
        if(this.nom == null){
            return "Entreprise{" +
                    "id=" + id +
                    ", raisonSociale='" + raisonSociale + '\'' +
                    ", formeJuridique='" + formeJuridique + '\'' +
                    ", email='" + email + '\'' +
                    ", telephone='" + telephone + '\'' +
                    ", fax='" + fax + '\'' +
                    ", address=" + adresse +
                    '}';
        }
        else{
            return "Particulier{" +
                    "id=" + id +
                    ", nom='" + nom + '\'' +
                    ", prenom='" + prenom + '\'' +
                    ", email='" + email + '\'' +
                    ", telephone='" + telephone + '\'' +
                    ", fax='" + fax + '\'' +
                    ", address=" + adresse +
                    '}';
        }
    }

}



