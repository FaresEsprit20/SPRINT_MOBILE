/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Livraison;
import com.mycompany.myapp.entities.Reclamations;
import com.mycompany.myapp.services.ServiceTask;
import java.io.IOException;


/**
 *
 * @author bhk
 */
public class AddTaskForm extends Form {

    private Resources theme;

    public AddTaskForm(Form previous) {
        setTitle("Ajouter une nouvelle Réclamation");
        setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
        theme = UIManager.initFirstTheme("/theme");
        Container cnt = new Container(BoxLayout.y());
        ImageViewer img = new ImageViewer(theme.getImage("livraison.jpg"));
        TextField tfTitre = new TextField("", "Titre");
        TextField tfSujet = new TextField("", "Sujet");
        TextField tfLivraison = new TextField("", "Numéro Livraison");
        Button btnValider = new Button("Ajouter Réclamation");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfTitre.getText().length() == 0) || (tfSujet.getText().length() == 0) || (tfLivraison.getText().length() == 0)) {
                    Dialog.show("Alert", "Svp Remplissez tous les champs", new Command("OK"));
                } else if ((tfTitre.getText().length() < 3) || (tfSujet.getText().length() < 5) || (isNumeric(tfLivraison.getText() )== false)) {
                    Dialog.show("Alert", "Champ Titre Minimum 3 et Champ Sujet Minimum 5 Champ Livraison doit etre une valeur Numerique et Déja Existente", new Command("OK"));
                } else if ((tfTitre.getText().length() > 20) || (tfSujet.getText().length() > 150)|| (isNumeric(tfLivraison.getText() )== false)) {
                    Dialog.show("Alert", "Champ Titre Maximum 20 et Champ Sujet Minimum 150 Champ Livraison doit etre une valeur Numerique et Déja Existente", new Command("OK"));
                } else {
                    try {
                        Reclamations t = new Reclamations();
                        t.setTitre(tfTitre.getText());
                        t.setSujet(tfSujet.getText());
                        t.setLivraisonId(tfLivraison.getText());

                        try {
                            if (ServiceTask.getInstance().addTask(t)) {
                                Dialog.show("Success", "Réclamation Ajoutée", new Command("OK"));
                            } else{
                                Dialog.show("ERROR", "Champ Livraison doit etre une valeur Déja Existente", new Command("OK"));
                            }
                        } catch (Exception ex) {
                           Dialog.show("ERROR", "Champ Livraison doit etre une valeur Déja Existente", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                }
            }
        });

        cnt.addAll(img, tfTitre, tfSujet, tfLivraison, btnValider);
        add(cnt);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

    
    public static boolean isNumeric(String strNum) {
    if (strNum == null) {
        return false;
    }
    try {
        double d = Double.parseDouble(strNum);
    } catch (NumberFormatException nfe) {
        return false;
    }
    
     try {
        double d = Double.parseDouble(strNum);
    } catch (Exception nfes) {
        return false;
    }
   
    return true;
}
    
}
