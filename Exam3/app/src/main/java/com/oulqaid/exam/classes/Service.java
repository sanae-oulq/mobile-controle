package com.oulqaid.exam.classes;

public class Service {

    private Long id;
    private String nom;

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Service(Long id, String nom){
        this.id= id;
        this.nom = nom;
    }
}
