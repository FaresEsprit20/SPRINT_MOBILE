/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.entities.Locations;
import com.mycompany.myapp.entities.Session;
import com.mycompany.myapp.gui.LoginForm;
import java.io.IOException;
import com.mycompany.myapp.services.ServiceLocation;

public class MenuChefsiteForm extends Form {

    Form current;
  
   
    public MenuChefsiteForm() {
        current = this;
        setTitle("Menu");
        setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
        Container cnt = new Container(BoxLayout.y());
        Label l1 = new Label("Choisir une Option");
        cnt.add(l1);
            //Button btnListLoc = new Button("Liste des location");
            Button bntValiderLocation = new Button("Valider un retour");
            bntValiderLocation.addActionListener(e -> {
                    new ListeLocationChefsiteForm(current, "chefsite").show();
                            });
            /*bntValiderLocation.addActionListener(e -> {
                try {
                    new AddLocationForm(current).show();
                } catch (IOException ex) {
                    System.out.println(ex.toString());
                }
                            });*/
            cnt.addAll( bntValiderLocation);
        
        
        Button dec = new Button("Déconnection");
        
        cnt.add(dec);
        addAll(cnt);
        dec.addActionListener( (e) -> {
              Session.close();
            new LoginForm(current).show();
        }        
        );
    }
}
