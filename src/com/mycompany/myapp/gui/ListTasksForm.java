/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.db.Cursor;
import com.codename1.db.Row;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.entities.Livraison;
import com.mycompany.myapp.services.ServiceTask;
import com.codename1.messaging.Message;
import com.mycompany.myapp.entities.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bhk
 */
public class ListTasksForm extends Form {

    Form F2;
    MenuForm c = new MenuForm();
    Form current;
    
    public ListTasksForm(Form previous) {
        current = new MenuForm();
        setTitle("Liste des Livraisons");
        setLayout(new FlowLayout(BoxLayout.Y_AXIS));
        Container cnt = new Container(BoxLayout.y());
        cnt.setScrollableY(true);
        List<Livraison> list = new ArrayList();
        list = ServiceTask.getInstance().getAllTasks(Session.getCurrentSession().getId());
        for (Livraison p : list) {
            System.out.println(p);
        }
        for (Livraison p : list) {
            cnt.add(addItem(p));
        }
        add(cnt);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
    
    public Container addItem(Livraison p) {
        Container cnt2 = new Container(BoxLayout.x());
        MultiButton mb = new MultiButton("ID  :  " + p.getId());
        mb.setTextLine2("Titre  :  " + p.getTitre());
        
        FontImage.setMaterialIcon(mb, FontImage.MATERIAL_BADGE);
        cnt2.add(mb);
        //bouton Vers Détails
        mb.addActionListener((e) -> {
            System.out.println(p);
            F2 = new Form("Détails Livraison", BoxLayout.y());
            
            Button valider = new Button("Valider");
            Button supprimer = new Button("Supprimer");
            Label lbid = new Label("ID    :   " + p.getId());
            Label lbtitre = new Label("Titre   :   " + p.getTitre());
            Label lbetat = new Label("Etat    :  " + p.getEtat());
            Label lbprix = new Label("Prix    :  " + p.getPrix());
            Label lbadresse = new Label("Adresse    :  " + p.getAdresse());
            Label lbetel = new Label("Tel    :  " + p.getTel());
            Label lbdatecreation = new Label("Date Création    :   " + p.getDateCreation());
            Label cl1 = new Label("Détails Client    :    ");
            Label lbclid = new Label("ID    :   " + p.getClient().getId());
            Label lbclEmail = new Label("Email    :   " + p.getClient().getEmail());
            Label lbclAdresse = new Label("Adresse    :   " + p.getClient().getAdress());
            Label lbclTel = new Label("Tel    :   " + p.getClient().getTel());
            
            F2.addAll(lbid, lbtitre, lbetat, lbprix, lbadresse, lbetel, lbdatecreation, cl1,lbclid,lbclEmail,lbclAdresse,lbclTel, valider,supprimer);
            F2.getToolbar().addCommandToLeftBar("back", null, es -> c.show());
            F2.show();
            valider.addActionListener((ea) -> {                
                boolean list = ServiceTask.getInstance().editTask(p.getId());
                Dialog.show("Success", "Etat Changé", new Command("OK"));
                lbetat.setText("Livrée");
                Message m = new Message("Votre Livraison a été bien validée");
m.getAttachments().put("Votre Livraison a été bien validée", "text/plain");

Display.getInstance().sendMessage(new String[] {p.getClient().getEmail()}, "Bonjour", m);
                F2.show();
            });
            
             supprimer.addActionListener((eac) -> {                
                boolean list = ServiceTask.getInstance().deleteTask(p.getId());
                Dialog.show("Success", "Livraison Supprimée", new Command("OK"));
                lbetat.setText("Livrée");
                c.show();
            });
        });

        /* Form F3 = new Form("", BoxLayout.y());
       Form F4 = new Form("", BoxLayout.y());
            F2.getToolbar().addCommandToOverflowMenu("ajouter", null, es -> F3.show());
             F2.getToolbar().addCommandToOverflowMenu("afficher", null, es -> F4.show());
              Picker date = new Picker();
        date.setType(Display.PICKER_TYPE_DATE);
              Slider sl = new Slider();
        sl.setEditable(true);
        sl.setMinValue(27);
        sl.setMaxValue(42);
        Button btn = new Button("Valider");
          
       //Request
        con.setUrl(url);
            NetworkManager.getInstance().addToQueueAndWait(con);

        //Response
        con.addResponseListener((eaa)->{
        String reponse=new String(con.getResponseData());
            System.out.println(reponse);
        });   
            F2.addAll(lb1,lbimage);
             F2.getToolbar().addCommandToLeftBar("back", null, es -> f2.show());
             F3.addAll(date,sl,btn);
            F2.show();
         */
        return cnt2;
    }
    
}
