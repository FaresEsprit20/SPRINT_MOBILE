/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.ListModel;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Maintenance;
import com.mycompany.myapp.entities.Session;
import com.mycompany.myapp.entities.User;
import java.util.ArrayList;
import com.mycompany.myapp.services.ServiceMaintenance;

public class RDVForm extends Form{
    
    Form current;
    public ArrayList<Maintenance> maintenances = new ArrayList<>();
    //private static User User = Session.getCurrentSession();
    private Resources theme;
    
    
    public RDVForm(Form previous,String head) {
        current = this;
        theme = UIManager.initFirstTheme("/theme");
        int countconf = 0;
        
        Image icon = theme.getImage("image1.png");
        Container cnt1 = BorderLayout.east(new Label(icon));
        cnt1.add(BorderLayout.SOUTH, new Label("EASY RIDE", "SidemenuTagline"));
        cnt1.setUIID("SideCommand");
        current.getToolbar().addComponentToSideMenu(cnt1);
        getToolbar()
                .addMaterialCommandToSideMenu("Home", FontImage.MATERIAL_HOME, e -> {
                    new AchrefMenuForm(current).show();
                    });
        
        if (head.equals("afficherrdv")){
            maintenances = ServiceMaintenance.getInstance().getAll(Session.getCurrentSession().getId());
            setTitle("liste des RDV");
            if (maintenances.size() == 0){
                setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
                ImageViewer img = new ImageViewer(theme.getImage("noappointment.png"));
                add(img);
                getToolbar().addMaterialCommandToSideMenu("Prendre un rendez vous", FontImage.MATERIAL_HOME, e -> {
                    new AddRDVForm(current).show();
                });
            }else{
                setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                for (Maintenance m : maintenances) {
                    Container maintenancecnt = new Container(new FlowLayout(Component.CENTER, Component.CENTER));
                    Container contentcnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                    ImageViewer img = null;
                    if (m.getEtat().equals("en attente")){
                        img = new ImageViewer(theme.getImage("enattente.png"));
                    }else{
                        img = new ImageViewer(theme.getImage("confirme.png"));
                        countconf = countconf + 1;
                    }
                    Button Show = new Button("Show more info");
                    FontImage.setMaterialIcon(Show, FontImage.MATERIAL_SELECT_ALL);
                    Show.addActionListener(e -> {
                        new showRDVForm(current,m,"update").show();
                    });
                    Label titre = new Label("Titre : "+m.getTitre());
                    String datetouse = m.getDate().replace('T',' ').substring(0, m.getDate().length() - 9);
                    Label Daterdv = new Label("Date : "+datetouse);
                    contentcnt.addAll(titre, Daterdv, Show);
                    maintenancecnt.addAll(img,contentcnt);
                    add(maintenancecnt);                    
                }
                int count = ServiceMaintenance.getInstance().countconfirmation(countconf);
                 if (count>0){
                     Dialog.show("Notification", "Vous avez "+count+" rdv confimé(s)", "OK", null);
                 };
            }
        }else{
            setTitle("Valider un RDV");
            maintenances = ServiceMaintenance.getInstance().getAllencours(Session.getCurrentSession().getId());
            if (maintenances.size() == 0){
                setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
                ImageViewer img = new ImageViewer(theme.getImage("noappointment.png"));
                add(img);
                getToolbar().addMaterialCommandToSideMenu("Prendre un rendez vous", FontImage.MATERIAL_HOME, e -> {
                    new AddRDVForm(current).show();
                });
            }else{
            for (Maintenance m : maintenances) {
                Container maintenancecnt = new Container(new FlowLayout(Component.CENTER, Component.CENTER));
                Container contentcnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                ImageViewer img = new ImageViewer(theme.getImage("encours.png"));
                Label Titre = new Label("Titre : "+m.getTitre());
                String datetouse = m.getDate().replace('T',' ').substring(0, m.getDate().length() - 9);
                Label Date = new Label("Date : "+datetouse);
                Button Show = new Button("Show more info");
                FontImage.setMaterialIcon(Show, FontImage.MATERIAL_SELECT_ALL);
                Show.addActionListener(e -> {
                    new showRDVForm(current,m,"validate").show();
                });
                contentcnt.addAll(Titre,Date,Show);
                maintenancecnt.addAll(img,contentcnt);
                add(maintenancecnt);
                
            }}
            
        }
        
        
        
        
    }
}
