/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Commande;
import static com.mycompany.myapp.gui.IndexCommande.currentForm;
import com.mycompany.myapp.services.ServiceCommande;
import java.util.Date;

/**
 *
 * @author Administrateur
 */
public class AddCommandeForm extends Form {

     private Resources theme;
    public AddCommandeForm(Form previous) {
        
         setTitle("Add a new Commande");
        setLayout(BoxLayout.y());
        
        TextField tfRef = new TextField("","Reference");
      //  TextField tfDate= new TextField("", "Status: 0 - 1");
        TextField tfEtat = new TextField("","Etat");
        ComboBox combobox = new ComboBox();
                                            combobox.addItem("En cours");
                                            combobox.addItem("Valider");
                                            combobox.addItem("Annuler");
                                            
        TextField tfPrix= new TextField("", "Prix");
        TextField tfuser= new TextField("", "id user");
        
        Button btnValider = new Button("Add Commande");
        Date currentDatetime = new Date(System.currentTimeMillis());
        
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfRef.getText().length()==0)||(tfPrix.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    
                    String prixC = tfPrix.getText();
                    float prix = Float.parseFloat(prixC);
                    String idC=tfuser.getText();
                    int id = Integer.parseInt(idC);
                    
                    Date currentDatetime = new Date(System.currentTimeMillis());
        
                    try {
                        Commande t = new Commande(tfRef.getText(),currentDatetime,combobox.getSelectedItem().toString(),prix,id);
                        if( ServiceCommande.getInstance().addTask(t))
                        { Dialog.show("Success","Commande a été ajouté",new Command("OK"));
                        new IndexCommande(currentForm, theme).show();
                        } else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        addAll(tfRef,combobox,tfPrix,tfuser,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
                
        
    }
    
    
    
    
}
