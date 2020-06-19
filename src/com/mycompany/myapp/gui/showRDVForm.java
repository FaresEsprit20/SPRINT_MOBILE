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
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Maintenance;
import com.mycompany.myapp.entities.Session;
import com.mycompany.myapp.entities.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.mycompany.myapp.services.ServiceMaintenance;

public class showRDVForm extends Form {
    Form current;
    private static User User = Session.getCurrentSession();
    private Resources theme;

    public showRDVForm(Form previous, Maintenance m, String head) {
        current = this;
        setTitle(m.getTitre());
        setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
        theme = UIManager.initFirstTheme("/theme");
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        Container cnt = new Container(BoxLayout.y());
        String datetouse = m.getDate().replace('T',' ').substring(0, m.getDate().length() - 9);
        Label Description = new Label("Description : "+m.getDescription());
        Label Date = new Label("Date : "+datetouse);
        Container cnt1 = new Container(BoxLayout.x());
        ImageViewer img = null;
        if (head.equals("validate")){
            img = new ImageViewer(theme.getImage("encours.png"));
            Button Accept = new Button("Accept");
            Button Refuse = new Button("Refuse");
            FontImage.setMaterialIcon(Accept, FontImage.MATERIAL_DONE);
            FontImage.setMaterialIcon(Refuse, FontImage.MATERIAL_DELETE);
            Accept.addActionListener(e -> {
                ServiceMaintenance.getInstance().acceptRDVencours(m.getId());
                try {
                    new RDVForm(current, "validerrdv").show();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            });
            Refuse.addActionListener(e -> {
                ServiceMaintenance.getInstance().deleteRDV(m.getId());
                try {
                    new RDVForm(current, "validerrdv").show();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            });
            cnt1.addAll(Accept, Refuse);
        }else{
            if (m.getEtat().equals("confirmé")){
                img = new ImageViewer(theme.getImage("confirme.png"));
                Label error = new Label("Impossible de modifier ce rendez vous comme il est confirmé");
                Label error2 = new Label("rendez vous comme il est confirmé");
                cnt1.addAll(error,error2);
            }else{
                img = new ImageViewer(theme.getImage("enattente.png"));
                datetouse = m.getDate().replace('T',' ').substring(0, m.getDate().length() - 6);
                Date date1= null;
                try {
                    date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetouse);
                } catch (ParseException ex) {
                    System.out.println(ex);
                }
                Date today = new Date();
                long l = date1.getTime();
                long ltoday = today.getTime();
                if (l > ltoday){           
                    Button Delete = new Button("Delete");

                    FontImage.setMaterialIcon(Delete, FontImage.MATERIAL_DELETE);

                    Delete.addActionListener(e -> {
                        ServiceMaintenance.getInstance().deleteRDV(m.getId());
                        try {
                            new RDVForm(previous, "afficherrdv").show();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    });
                    cnt1.add(Delete);
                }else{
                    Label error = new Label("Impossible de modifier ce rendez vous comme ça date est ");
                    cnt1.add(error);
                }
            }
            
        }
        cnt.addAll(img,Description,Date,cnt1);
        
        add(cnt);
    }
    
    
    
}
