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
public class User {
    protected int id;
    protected String username;
    protected String nomComplet;
    protected String email;
    protected String password;
    protected String roles;
    protected int tel;
    protected String adresse;

    public User(){
        
    }

    public User(String username, String nomComplet, String email, String password, String roles, int tel, String adresse) {
        this.username = username;
        this.nomComplet = nomComplet;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.tel = tel;
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", nomComplet=" + nomComplet + ", email=" + email + ", password=" + password + ", roles=" + roles + ", tel=" + tel + ", adresse=" + adresse + '}';
    }

    
    
} 
   
