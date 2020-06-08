/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author Fares
 */
public class Evenements {
    private Integer id;
    private String nom_evenements;
    public int nombre;
    private String dateeve;
    private String lieuxeve;
    private String descreptioneve;
    private String datedebut;
    private String datefin;
    
   private String image;

   public Evenements(String nom_evenements, int nombre, String lieuxeve, String descreptioneve, String datedebut, String datefin, String image) {
        this.nom_evenements = nom_evenements;
        this.nombre = nombre;
        this.lieuxeve = lieuxeve;
        this.descreptioneve = descreptioneve;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.image = image;
    }
    public Evenements() {
    }

    public Evenements(Integer id, String nom_evenements, int nombre, String dateeve, String lieuxeve, String descreptioneve, String datedebut, String datefin, String image) {
        this.id = id;
        this.nom_evenements = nom_evenements;
        this.nombre = nombre;
        this.dateeve = dateeve;
        this.lieuxeve = lieuxeve;
        this.descreptioneve = descreptioneve;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.image = image;
    }

    public Evenements(String nom_evenements, int nombre, String dateeve, String lieuxeve, String descreptioneve, String datedebut, String datefin, String image) {
        this.nom_evenements = nom_evenements;
        this.nombre = nombre;
        this.dateeve = dateeve;
        this.lieuxeve = lieuxeve;
        this.descreptioneve = descreptioneve;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom_evenements() {
        return nom_evenements;
    }

    public void setNom_evenements(String nom_evenements) {
        this.nom_evenements = nom_evenements;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public String getDateeve() {
        return dateeve;
    }

    public void setDateeve(String dateeve) {
        this.dateeve = dateeve;
    }

    public String getLieuxeve() {
        return lieuxeve;
    }

    public void setLieuxeve(String lieuxeve) {
        this.lieuxeve = lieuxeve;
    }

    public String getDescreptioneve() {
        return descreptioneve;
    }

    public void setDescreptioneve(String descreptioneve) {
        this.descreptioneve = descreptioneve;
    }

    public String getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(String datedebut) {
        this.datedebut = datedebut;
    }

    public String getDatefin() {
        return datefin;
    }

    public void setDatefin(String datefin) {
        this.datefin = datefin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Evenements{" + "id=" + id + ", nom_evenements=" + nom_evenements + ", nombre=" + nombre + ", dateeve=" + dateeve + ", lieuxeve=" + lieuxeve + ", descreptioneve=" + descreptioneve + ", datedebut=" + datedebut + ", datefin=" + datefin + ", image=" + image + '}';
    }
    
    
}
