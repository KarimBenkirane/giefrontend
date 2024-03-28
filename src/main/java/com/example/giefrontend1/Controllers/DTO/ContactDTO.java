package com.example.giefrontend1.Controllers.DTO;

public class ContactDTO {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String telephone;
    private String fax;
    private AdresseDTO address;

    private String formeJuridique;
    private String raisonSociale;

    public ContactDTO(){}


    //Constructeur pour créer les instances des particuliers
    public ContactDTO(int id, String first_name, String last_name, String email, String telephone, String fax, AdresseDTO address) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.telephone = telephone;
        this.fax = fax;
        this.address = address;
    }

    //Constructeur pour créer les instances des entreprises
    public ContactDTO(int id, String email, String telephone, String fax, AdresseDTO address, String formeJuridique, String raisonSociale) {
        this.id = id;
        this.email = email;
        this.telephone = telephone;
        this.fax = fax;
        this.address = address;
        this.formeJuridique = formeJuridique;
        this.raisonSociale = raisonSociale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public AdresseDTO getAddress() {
        return address;
    }

    public void setAddress(AdresseDTO address) {
        this.address = address;
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
}
