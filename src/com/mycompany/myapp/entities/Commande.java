/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author Administrateur
 */
public class Commande {
    private String ref;
    private Date date;
    private String etat;
    private Float prix;
    private int id_user;
    
    

    public Commande() {
    }
    
    

    public Commande(String ref, Date date, String etat, Float prix, int id_user) {
        this.ref = ref;
        this.date = date;
        this.etat = etat;
        this.prix = prix;
        this.id_user=id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_user() {
        return id_user;
    }
    
    public Commande(String ref, String etat, Float prix) {
        this.ref = ref;
        
        this.etat = etat;
        this.prix = prix;
    }

    public String getRef() {
        return ref;
    }

    public Date getDate() {
        return date;
    }

    public String getEtat() {
        return etat;
    }

    public Float getPrix() {
        return prix;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Commande{" + "ref=" + ref + ", date=" + date + ", etat=" + etat + ", prix=" + prix + '}';
    }
    
}
