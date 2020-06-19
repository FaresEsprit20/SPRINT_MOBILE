/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;
import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.googlemaps.MapContainer;
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
/**
 *
 * @author bhk
 */
public class ListeLocationChefsiteForm extends Form {

    private Resources theme;  
    public ArrayList<Locations> locations;

    public ListeLocationChefsiteForm(Form previous, String role)  {
        theme = UIManager.initFirstTheme("/theme");
        setLayout(BoxLayout.y());
        
            setTitle("Valider un retour");
            locations = ServiceLocation.getInstance().getlocationsparsite(Session.getCurrentSession().getId());
            
            for (Locations l:locations){
                Container cnt = new Container(BoxLayout.y());
                Container cntcontent = new Container(BoxLayout.x());
                Label type = new Label(l.getType());
                Label date = new Label("date retour prevue : "+l.getDate_fin());
                ImageViewer img = new ImageViewer(theme.getImage("Image1.png"));
                cnt.addAll(type,date);
                Button btn = new Button("valider");
                CheckBox cb1 = new CheckBox("Endommagée");
                FontImage.setMaterialIcon(btn, FontImage.MATERIAL_DONE);
                btn.addActionListener(e-> {
                    String etat = "non";
                    String retard = "oui";

                    Date d = new Date();
                    Date date1 = null;
                    try {
                        date1=new SimpleDateFormat("yyyy-MM-dd").parse(l.getDate_fin());
                    } catch (ParseException ex) {
                        System.out.println(ex.toString());
                    }
                    if (date1.getTime()>=d.getTime()){
                        retard = "non";
                    }
                    if (cb1.isSelected())
                    {
                        etat = "oui";
                    }
                    ServiceLocation.getInstance().validerLocation(l.getId(),etat,retard);
                    new ListeLocationChefsiteForm(previous,"chefsite").show();
                });
                cnt.add(cb1);
                cnt.add(btn);

                cntcontent.addAll(img,cnt);
                add(cntcontent);
            
            
            
        }
        
        
        
        
        
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

}
