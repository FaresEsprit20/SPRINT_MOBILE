/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.entities.Evenements;
import com.mycompany.myapp.services.Serviceevenements;
/**
 *
 * @author Fares
 */
public class addeventform extends Form{

    public addeventform(Form previous) {
        setTitle("ajouter un evenements");
        
         setLayout(BoxLayout.y());

        TextField tfNomevent = new TextField("","nom evenements");
        TextField tfNombre = new TextField("","nombre des participants");
       /* Picker tfdate=new Picker();*/

        TextField tflieux = new TextField("","lieux evenements");
        TextField tfdescreption = new TextField("","descreption evements");
       
        
 
Picker tfDate_Fin_Evenement = new Picker();
 tfDate_Fin_Evenement.setType(Display.PICKER_TYPE_DATE);
 
 Picker deb = new Picker();
 deb.setType(Display.PICKER_TYPE_TIME);
 
 Picker fin = new Picker();
 fin.setType(Display.PICKER_TYPE_TIME);
 
 
 TextField tfimage = new TextField("","image");


                        Button btnValider = new Button("Add event");

         btnValider.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent evt) {
 if ((tfNomevent.getText().length()==0)||(tfNombre.getText().length()==0)||(tflieux.getText().length()==0)||(tfdescreption.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    System.out.println(tfDate_Fin_Evenement.getText());
                     System.out.println(deb.getText());
                      System.out.println(fin.getText());
                                            System.out.println(tfimage.getText());

                    try {
                  
                        Evenements e = new Evenements(tfNomevent.getText(),Integer.parseInt(tfNombre.getText()),tfDate_Fin_Evenement.getText(),tflieux.getText(),tfdescreption.getText(),deb.getText(),fin.getText(),tfimage.getText());
//     Evenements e = new Evenements(tfNomevent.getText(),Integer.parseInt(tfNombre.getText()),tflieux.getText(),tfdescreption.getText(),tfDate_Fin_Evenement.getText(),tfimage.getText());
                      String imgUrl = "http://localhost/untitled4/web/velo/" + e.getImage();
  
if( Serviceevenements.getInstance().addevenements(e))
                            Dialog.show("Success","evenements ajouter avec succées",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }   
            }
        
            
                 
                 
                 
            });
                 
                 
                       addAll(tfNomevent,tfNombre,tflieux,tfdescreption,deb,fin,tfDate_Fin_Evenement,tfimage,btnValider);
  
        getToolbar().addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK,e->previous.showBack());  
    }
    
    
}

