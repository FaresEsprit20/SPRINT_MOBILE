/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;
import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.maps.Coord;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Locations;
import com.mycompany.myapp.entities.Session;
import com.mycompany.myapp.entities.Type;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import com.mycompany.myapp.services.ServiceLocation;
import com.mycompany.myapp.services.ServiceType;
//import java.util.logging.Level;
//import java.util.logging.Logger;
/**
 *
 * @author bhk
 */
public class ListeLocationForm extends Form {

    private Resources theme;  
    public ArrayList<Locations> locations;

    public ListeLocationForm(Form previous, String role)  {
        theme = UIManager.initFirstTheme("/theme");
        setLayout(BoxLayout.y());
        if (role.equals("user")){
            setTitle("Liste des vélos");
            
            locations = ServiceLocation.getInstance().getlocations(Session.getCurrentSession().getId());
            System.out.println(locations);

            int count = 0;
            for (Locations l:locations){
                count = count + 1;
                Container cnt = new Container(BoxLayout.y());
                Container cntcontent = new Container(BoxLayout.x());
                Label type = new Label(l.getType());
                Label site = new Label(l.getSite());
                Label date = new Label(l.getDate_debut()+"->"+l.getDate_fin());
                ImageViewer img = new ImageViewer(theme.getImage("Image1.png"));
                cnt.addAll(type,site,date);
                if (l.getStatus().equals("en cours")){
                    Button btn = new Button("Supprimer");
                    FontImage.setMaterialIcon(btn, FontImage.MATERIAL_DELETE);
                    btn.addActionListener(e -> {
                        ServiceLocation.getInstance().supprimerlocation(l.getId());
                        new ListeLocationForm(previous,"user").show();
                    });
                    cnt.add(btn);
                }         

                cntcontent.addAll(img,cnt);
                add(cntcontent);
            }
        
            int notif = ServiceLocation.getInstance().locationnotif(count);
            if (notif>0){
                Dialog.show("Easy Ride", "Votre location a été confimée", "OK", null);
            }
        }
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

}
