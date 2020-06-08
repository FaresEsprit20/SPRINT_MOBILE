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
public class Statistiques {
    
    private float livr,cours;

    public Statistiques() {
    }

    public Statistiques(float livr, float cours) {
        this.livr = livr;
        this.cours = cours;
    }

    
    
    /**
     * @return the livr
     */
    public float getLivr() {
        return livr;
    }

    /**
     * @param livr the livr to set
     */
    public void setLivr(float livr) {
        this.livr = livr;
    }

    /**
     * @return the cours
     */
    public float getCours() {
        return cours;
    }

    /**
     * @param cours the cours to set
     */
    public void setCours(float cours) {
        this.cours = cours;
    }
    
    
}
