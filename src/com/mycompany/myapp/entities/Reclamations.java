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
public class Reclamations {
    private int id,livr,cours;
    private String titre, sujet, LivraisonId,dateCreation;

    public Reclamations() {

    }

    public Reclamations(String titre, String sujet, String LivraisonId) {
        this.titre = titre;
        this.sujet = sujet;
        this.LivraisonId = LivraisonId;
    }
    
    
    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }
    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }
    /**
     * @return the sujet
     */
    public String getSujet() {
        return sujet;
    }
    /**
     * @param sujet the sujet to set
     */
    public void setSujet(String sujet) {
        this.sujet = sujet;
    }
    /**
     * @return the LivraisonId
     */
    public String getLivraisonId() {
        return LivraisonId;
    }
    /**
     * @param LivraisonId the LivraisonId to set
     */
    public void setLivraisonId(String LivraisonId) {
        this.LivraisonId = LivraisonId;
    }
    /**
     * @return the dateCreation
     */
    public String getDateCreation() {
        return dateCreation;
    }
    /**
     * @param dateCreation the dateCreation to set
     */
    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
      @Override
    public String toString() {
        return "Reclamation{" + "id=" + getId() + ", titre=" + getTitre() + ", Sujet=" + getSujet()  /* + ", Livraison=" + getLivraisonId()*/ +'}';
    }

    /**
     * @return the livr
     */
    public int getLivr() {
        return livr;
    }

    /**
     * @param livr the livr to set
     */
    public void setLivr(int livr) {
        this.livr = livr;
    }

    /**
     * @return the cours
     */
    public int getCours() {
        return cours;
    }

    /**
     * @param cours the cours to set
     */
    public void setCours(int cours) {
        this.cours = cours;
    }

    
}
