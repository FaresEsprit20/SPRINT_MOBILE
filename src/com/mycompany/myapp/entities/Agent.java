/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Fares
 */
public class Agent {

    private Integer id;
    private String nomComplet;
    private String adress;
    private String tel;
    private String dateCreation;
    public String username;
    public String usernameCanonical;
    public Object salt;
    private String email;
    public String emailCanonical;
    public String password;
    public Object plainPassword;
    public String lastLogin;
    public Object confirmationToken;
    public List<String> roles = null;
    public Boolean accountNonExpired;
    public Boolean accountNonLocked;
    public Boolean credentialsNonExpired;
    public Boolean enabled;
    public Boolean superAdmin;
    public Object passwordRequestedAt;
    public List<Object> groups = null;
    public List<Object> groupNames = null;
    public Object initializer;
    public Object cloner;
    public Boolean isInitialized;
    private String adresse;

    /**
     * No args constructor for use in serialization
     *
     */
    public Agent() {
    }

    /**
     *
     * @param lastLogin
     * @param dateCreation
     * @param roles
     * @param isInitialized
     * @param adress
     * @param emailCanonical
     * @param enabled
     * @param password
     * @param plainPassword
     * @param confirmationToken
     * @param tel
     * @param id
     * @param email
     * @param accountNonLocked
     * @param cloner
     * @param salt
     * @param credentialsNonExpired
     * @param groups
     * @param passwordRequestedAt
     * @param initializer
     * @param groupNames
     * @param adresse
     * @param nomComplet
     * @param accountNonExpired
     * @param usernameCanonical
     * @param superAdmin
     * @param username
     */
    public Agent(Integer id, String nomComplet, String adress, String tel, String dateCreation, String username, String usernameCanonical, Object salt, String email, String emailCanonical, String password, Object plainPassword, String lastLogin, Object confirmationToken, List<String> roles, Boolean accountNonExpired, Boolean accountNonLocked, Boolean credentialsNonExpired, Boolean enabled, Boolean superAdmin, Object passwordRequestedAt, List<Object> groups, List<Object> groupNames, Object initializer, Object cloner, Boolean isInitialized, String adresse) {
        super();
        this.id = id;
        this.nomComplet = nomComplet;
        this.adress = adress;
        this.tel = tel;
        this.dateCreation = dateCreation;
        this.username = username;
        this.usernameCanonical = usernameCanonical;
        this.salt = salt;
        this.email = email;
        this.emailCanonical = emailCanonical;
        this.password = password;
        this.plainPassword = plainPassword;
        this.lastLogin = lastLogin;
        this.confirmationToken = confirmationToken;
        this.roles = roles;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.superAdmin = superAdmin;
        this.passwordRequestedAt = passwordRequestedAt;
        this.groups = groups;
        this.groupNames = groupNames;
        this.initializer = initializer;
        this.cloner = cloner;
        this.isInitialized = isInitialized;
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).append("nomComplet", getNomComplet()).append("adress", getAdress()).append("tel", getTel()).append("dateCreation", dateCreation).append("username", username).append("usernameCanonical", usernameCanonical).append("salt", salt).append("email", email).append("emailCanonical", emailCanonical).append("password", password).append("plainPassword", plainPassword).append("lastLogin", lastLogin).append("confirmationToken", confirmationToken).append("roles", roles).append("accountNonExpired", accountNonExpired).append("accountNonLocked", accountNonLocked).append("credentialsNonExpired", credentialsNonExpired).append("enabled", enabled).append("superAdmin", superAdmin).append("passwordRequestedAt", passwordRequestedAt).append("groups", groups).append("groupNames", groupNames).append("initializer", initializer).append("cloner", cloner).append("isInitialized", isInitialized).append("adresse", adresse).toString();
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nomComplet
     */
    public String getNomComplet() {
        return nomComplet;
    }

    /**
     * @param nomComplet the nomComplet to set
     */
    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    /**
     * @return the adress
     */
    public String getAdress() {
        return adress;
    }

    /**
     * @param adress the adress to set
     */
    public void setAdress(String adress) {
        this.adress = adress;
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
     * @param dateCreation the dateCreation to set
     */
    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param adresse the adresse to set
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

}
