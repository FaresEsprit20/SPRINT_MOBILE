/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.DefaultCategoryRenderer;
import com.mycompany.myapp.entities.Session;
import java.io.IOException;

/**
 *
 * @author Fares
 */
public class AchrefMenuForm extends Form {

    Form current;
    private Resources theme;

    public AchrefMenuForm(Form previous) {
        current = this;
        setTitle("Menu");
        setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
        Container cnt = new Container(BoxLayout.y());
        Label l1 = new Label("Choisir une Option");

        Button btnAddevent = new Button("Ajouter un evenement");
        Button btnAddpart = new Button("Participer à un evenement");
        Button btnListevent = new Button("Liste des evenements");
        
        Button btnAddLoc = new Button("Louer Velo");
        Button btnListLoc = new Button("Liste des location");
        
        btnListLoc.addActionListener(e -> {
                    new ListeLocationForm(current, "user").show();
                            });
            btnAddLoc.addActionListener(e -> {
                try {
                    new AddLocationForm(current).show();
                } catch (IOException ex) {
                    System.out.println(ex.toString());
                }
                            });
        
        Button dec = new Button("Déconnection");
        /*
        btnModifierEvenement.addActionListener(e -> new ModifierEvenement(current).show());
        btnSupprimerEvenement.addActionListener(e -> new SupprimerEvenement(current).show());
         */
        btnAddevent.addActionListener(e -> new addeventform(current).show());
        btnAddpart.addActionListener(e -> new parteventform(current).show());

        btnListevent.addActionListener(e -> new listeeventform(current, theme).show());
        dec.addActionListener(e -> {
            Session.close();
            new LoginForm(current).show();
                });
        cnt.addAll(btnAddevent, btnAddpart, btnListevent, btnListLoc, btnAddLoc, dec);
        addAll(cnt);

        current.getToolbar()
                .addCommandToLeftSideMenu("Ajouter évenement", null, ev -> {
                    new addeventform(current).show();
                });
        current.getToolbar()
                .addCommandToLeftSideMenu("Participer a un evenement", null, ev -> {
                    new parteventform(current).show();
                }
                );
        current.getToolbar()
                .addCommandToLeftSideMenu("Liste de evenements", null, ev -> {
                    new listeeventform(current, theme).show();
                }
                );

    }

}
