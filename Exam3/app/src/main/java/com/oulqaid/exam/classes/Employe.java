package com.oulqaid.exam.classes;

import java.util.Date;

public class Employe {

    private Long id;
    private String nom;
    private String prenom;
    private String dateNaissance;

    private Service service;
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getPrenom() {
        return prenom;
    }


    public String getDateNaissance() {
        return dateNaissance;
    }


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

    public void setService(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }
    public Employe(Long id, String nom,String prenom, String dateNaissance, String photo, Service service){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance= dateNaissance;

        this.service=service;
    }
}
