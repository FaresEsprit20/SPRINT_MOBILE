/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Session;

/**
 *
 * @author bhk
 */
public class AccueilForm extends Form {

    Form current;
    private Resources theme;

    public AccueilForm() {
        theme = UIManager.initFirstTheme("/theme");
        current = this;
        setTitle("Accueil");
        setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
        Container cnt = new Container(BoxLayout.y());
        ImageViewer img = new ImageViewer(theme.getImage("image1.png"));
        Label l1 = new Label("Bienvenue dans Easy Ride");
        l1.setWidth(55);
          l1.setHeight(55);
        cnt.addAll(l1,img);
        addAll(cnt);

        current.getToolbar()
                .addCommandToLeftSideMenu("Login", null, ev -> {
                    new LoginForm(current).show();
                });

        //Formulaire Profile
//Toolbar ili howa el menu 
    }
}
