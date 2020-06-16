/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.InfiniteScrollAdapter;
import com.codename1.components.MultiButton;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Util;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Commande;
import com.mycompany.myapp.services.ServiceCommande;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author Administrateur
 */
public class IndexCommande extends Form {
         static Form  currentForm;
         

  //  User User = Session.getCurrentSession();

    public IndexCommande(Form previous, Resources theme) {

        currentForm = this;
        currentForm.setTitle("liste des commandes");
        currentForm.setLayout(BoxLayout.y());
        
Style s = UIManager.getInstance().getComponentStyle("MultiLine1");
        FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);
        EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 3, p.getHeight() * 3), false);
        java.util.List<Commande> commandes = ServiceCommande.getInstance().getAllTasks();
        MultiButton[] cmps = new MultiButton[commandes.size()];
         FontImage e = FontImage.createMaterial(FontImage.MATERIAL_EDIT_ATTRIBUTES, s);
          FontImage d = FontImage.createMaterial(FontImage.MATERIAL_DELETE, s);
          
           
                     
              
          
           Container cn = new Container(new BoxLayout(BoxLayout.Y_AXIS));
              TextField search=new TextField("","Search");
              Button Save = new Button("Search");
              
               Button devGuide = new Button(" PDF");
devGuide.addActionListener(epdf -> {
    FileSystemStorage fs = FileSystemStorage.getInstance();
    String fileName = fs.getAppHomePath() + "listeComande.pdf";
    if(!fs.exists(fileName)) {
        
        try {
            
            Util.copy(Display.getInstance().getResourceAsStream(getClass(), "/listeComande.pdf"), fs.openOutputStream(fileName));
        } catch (IOException ex) {
            System.out.println("cc");
        }

        
    }
    Display.getInstance().execute(fileName);
    
});


              
              cn.addAll(devGuide,search,Save);
                Save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                     int i =0;
                   for(Commande c : commandes){
                       if(c.getRef().equals(search.getText())){
                           System.out.println("");
                           Container detail=new Container(new BoxLayout(BoxLayout.Y_AXIS));
                           setTitle("detail du commande");
               Label nom =new Label("le reference est:"+c.getRef());
               Label l =new Label("l'etat du commande est:"+c.getEtat());

               Label lll =new Label("le prix est:"+String.valueOf(c.getPrix()));
               
               
               Button btnback = new Button("Back");
                btnback.addActionListener(e-> new IndexCommande(currentForm, theme).show());
                 
               detail.addAll(nom,l,lll,btnback);
               
               
              
               detail.addPointerPressedListener(ev -> {
                    Detail(c,theme).show();
               });
            
                
               Form cform = new Form(BoxLayout.y());
                cform.add(detail);
                cform.show();
                
               
            }
                }
                   
               
                }
               
               
                
            });
        InfiniteScrollAdapter.createInfiniteScroll(this.getContentPane(), () -> {
            int i =0;
             
            
            for(Commande c : commandes){
               
                cmps[i] = new MultiButton(c.getRef());
                cmps[i].setTextLine2("Etat  :  " + c.getEtat());
               
               cmps[i].addPointerReleasedListener(ev -> {
                Detail(c,theme).show();
            });
                
                             
                
                i++;
            }
            add(cn);
            InfiniteScrollAdapter.addMoreComponents(this.getContentPane(),cmps, false);
      
        });
       
           
        currentForm.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            previous.showBack();
        });
        }

  
    

    
    
    
    
    
  
  
  public Form Detail(Commande d, Resources theme) {

        Form Detail = new Form(BoxLayout.y());

       Label lref = new Label("Reference");
        Label ldate = new Label("Date");
        Label letat = new Label("Etat");
        Label lprix = new Label("prix");

      
        
        TextField tfref = new TextField(null, "");
        tfref.setText(d.getRef());
        
       // TextField tfdate = new TextField(null, "");
       // tfdate.setText(String.valueOf(d.getDate()));
        
        TextField tfetat = new TextField(null, "");
        tfetat.setText(String.valueOf(d.getEtat()));
         TextField tfprix = new TextField(null, "");
        tfprix.setText(String.valueOf(d.getPrix()));
        
      
        Container Container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container.addAll(lref, tfref,letat, tfetat,lprix,tfprix);
       
        Detail.add(Container);

        Container ButtonsContainer = new Container(new FlowLayout());

        Button Delete = new Button("Delete");
        Button Edit = new Button("Edit");
        ButtonsContainer.addAll(Edit, Delete);

        Detail.add(ButtonsContainer);
        Detail.revalidate();
        Delete.addActionListener(ev -> {
            boolean result = ServiceCommande.getInstance().deleteCommande(d.getRef());
          
            if (result) {
                Dialog.show("Success", "ok", new Command("OK"));
                new IndexCommande(IndexCommande.currentForm, theme).show();
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }

        });

        Edit.addActionListener(ev -> {
            d.setRef(tfref.getText());
            Date currentDatetime = new Date(System.currentTimeMillis());
            d.setDate(currentDatetime);
            d.setPrix(Float.parseFloat(tfprix.getText()));
            
            d.setEtat(tfetat.getText());
            d.setId_user(5);
            
//            d.setDescreptioneve(tfdesc.getText());
           
            
            if (ServiceCommande.getInstance().editCommande(d)) {
                Dialog.show("Success", "Edited", new Command("OK"));
                Message m = new Message("Reference : "+tfref.getText()+" Prix : "+tfprix.getText()+" Etat : "+tfetat.getText());

Display.getInstance().sendMessage(new String[] {"chadisassi@gmail.com"}, "Commande a ete modifie ", m);
                new IndexCommande(IndexCommande.currentForm, theme).show();
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }
        });

        Detail.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            new IndexCommande(currentForm, theme).show();
        });

        return Detail;
}
}