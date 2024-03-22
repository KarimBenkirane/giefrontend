package com.example.giefrontend1.Controllers.DTO;

public class ContactDTO {
    private int id;
    private String name;
    private String email;
    private String telephone;
    private String fax;
    private AdresseDTO address;

    public ContactDTO(int id, String name, String email, String telephone, String fax, AdresseDTO address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.fax = fax;
        this.address = address;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", fax='" + fax + '\'' +
                ", address=" + address ;
    }
}
