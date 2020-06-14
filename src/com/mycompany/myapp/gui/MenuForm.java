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
import com.mycompany.myapp.entities.DefaultCategoryRenderer;
import com.mycompany.myapp.entities.Session;
import com.mycompany.myapp.gui.LoginForm;

/**
 *
 * @author bhk
 */
public class MenuForm extends Form {

    Form current;
     Form fx = new Form();
  
   
    public MenuForm() {
        current = this;
        setTitle("Menu");
  setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
   Container cnt = new Container(BoxLayout.y());
       Label l1 = new Label("Choisir une Option");

        Button btnListTasks = new Button("Liste des Livraisons");
        Button btnListRecFarah = new Button("Liste des Réclamations");
        Button btnListRecs = new Button("Liste des Réclamations Concernant livraison");
        Button btnAddRecs = new Button("Ajouter une réclamation Concernant livraison");
        Button btnStats = new Button("Statistiques");
        Button dec = new Button("Déconnection");
       
        btnListRecFarah.addActionListener(l -> new ReclamationIndex().show());
        btnListTasks.addActionListener(e -> new ListTasksForm(current).show());
        btnListRecs.addActionListener(e -> new ListReclamations(current).show());
        btnAddRecs.addActionListener(e -> new AddTaskForm(current).show());
        cnt.addAll(l1,btnListRecFarah,btnListTasks, btnListRecs, btnAddRecs, btnStats, dec);
        addAll(cnt);
        dec.addActionListener( (e) -> {
              Session.close();
            new LoginForm(current).show();
        }        
        );
        
        btnStats.addActionListener( (ej) -> {
            DefaultCategoryRenderer pix = new DefaultCategoryRenderer();
           
      
       fx =  pix.createPieChartForm();
           // new StatsForm(current).show();
           fx.show();
        }        
        );

        //Formulaire Profile
        
//Toolbar ili howa el menu 
        DefaultCategoryRenderer pix = new DefaultCategoryRenderer();
    
        current.getToolbar()
                .addCommandToLeftSideMenu("Liste des Livraisons", null, ev -> {
                    new ListTasksForm(current).show();
                });
        current.getToolbar()
                .addCommandToLeftSideMenu("Liste des Reclamations", null, ev -> {
                    new ListReclamations(current).show();
                }
                );
        current.getToolbar()
                .addCommandToLeftSideMenu("Ajouter Réclamation", null, ev -> {
                    new AddTaskForm(current).show();
                }
                );
          current.getToolbar()
                .addCommandToLeftSideMenu("Statistiques", null, ev -> {
                      fx.show();
                }
                );
          
             fx.getToolbar()
                .addCommandToLeftSideMenu("Liste des Livraisons", null, ev -> {
                    new ListTasksForm(fx).show();
                });
        fx.getToolbar()
                .addCommandToLeftSideMenu("Liste des Reclamations", null, ev -> {
                    new ListReclamations(fx).show();
                }
                );
        fx.getToolbar()
                .addCommandToLeftSideMenu("Ajouter Réclamation", null, ev -> {
                    new AddTaskForm(fx).show();
                }
                );
        

    }
}
