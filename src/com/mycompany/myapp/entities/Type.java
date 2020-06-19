/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author bhk
 */
public class Type {
    private int id;
    private String type;
    private String image;

    public Type(int id, String type, String image) {
        this.id = id;
        this.type = type;
        this.image = image;
    }

    public Type() {
    }
    
    
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String Type) {
        this.type = Type;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Type{" + "id=" + id + ", Type=" + type + ", image=" + image + '}';
    }



}
