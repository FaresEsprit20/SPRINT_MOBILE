/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author bhk
 */
public class Livraison {

    
    private int id;
    private String titre;
    private String etat;
    private String dateCreation;
    private String dateLivraisonn;
    private String adresse;
    private Float prix;
    private String tel;
    private Agent agent;
    private Client client;

    /**
     * No args constructor for use in serialization
     *
     */
    public Livraison() {
    }

    public Livraison(String titre, String etat, String dateCreation, String dateLivraisonn, String adresse, Float prix, String tel, Agent agent, Client client) {
        this.titre = titre;
        this.etat = etat;
        this.dateCreation = dateCreation;
        this.dateLivraisonn = dateLivraisonn;
        this.adresse = adresse;
        this.prix = prix;
        this.tel = tel;
        this.agent = agent;
        this.client = client;
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
     * @return the etat
     */
    public String getEtat() {
        return etat;
    }

    /**
     * @param etat the etat to set
     */
    public void setEtat(String etat) {
        this.etat = etat;
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
     * @return the dateLivraisonn
     */
    public String getDateLivraisonn() {
        return dateLivraisonn;
    }

    /**
     * @param dateLivraisonn the dateLivraisonn to set
     */
    public void setDateLivraisonn(String dateLivraisonn) {
        this.dateLivraisonn = dateLivraisonn;
    }

    /**
     * @return the adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @return the prix
     */
    public Float getPrix() {
        return prix;
    }

    /**
     * @param prix the prix to set
     */
    public void setPrix(Float prix) {
        this.prix = prix;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the agent
     */
    public Agent getAgent() {
        return agent;
    }

    /**
     * @param agent the agent to set
     */
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    /**
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Livraison{" + "id=" + id + ", titre=" + titre + ", etat=" + etat + ", dateCreation=" + dateCreation + ", dateLivraisonn=" + dateLivraisonn + ", adresse=" + adresse + ", prix=" + prix + ", tel=" + tel + ", agent=" + agent + ", client=" + client + '}';
    }

    /**
     * @param adresse the adresse to set
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }



}
