/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;
import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.googlemaps.MapContainer;
import com.codename1.maps.Coord;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
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
import com.codename1.ui.PickerComponent;
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
import com.codename1.ui.validation.Constraint;
import com.codename1.ui.validation.Validator;
import com.mycompany.myapp.entities.Session;
import com.mycompany.myapp.entities.Site;
import com.mycompany.myapp.entities.Type;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import com.mycompany.myapp.services.ServiceLocation;
import com.mycompany.myapp.services.ServiceSite;
import com.mycompany.myapp.services.ServiceType;
//import java.util.logging.Level;
//import java.util.logging.Logger;
/**
 *
 * @author bhk
 */
public class AddLocationForm extends Form {

    private Resources theme;
    private static final String HTML_API_KEY = "AIzaSyC8_AwMJsyLaPoKngVdt-UcvBR1V8TZqFs";
    private String url;
    private String idtype;
    

    public AddLocationForm(Form previous) throws IOException {
        
        setTitle("Louer Velo");
        //setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
        setLayout(BoxLayout.y());
        theme = UIManager.initFirstTheme("/theme");
        Container cnt = new Container(BoxLayout.y());
        
        PickerComponent datedebutPicker = PickerComponent.createDate(new Date()).label("date debut");
        
        Validator val = new Validator();
        val.addConstraint(datedebutPicker, new Constraint() {
            @Override
            public boolean isValid(Object value) {
                boolean res = false;
                Date today = new Date();
                long l = datedebutPicker.getPicker().getDate().getTime();
                long ltoday = today.getTime();
                
                if (ltoday < l){
                    res = true;
                }
                return res;
            }

            @Override
            public String getDefaultFailMessage() {
                return "Date debut doit etre > aujourd'hui";
            }
        });
        
        PickerComponent datefinPicker = PickerComponent.createDate(new Date()).label("date fin");
        val.addConstraint(datefinPicker, new Constraint() {
            @Override
            public boolean isValid(Object value) {
                boolean res = false;
                Date today = new Date();
                long l = datefinPicker.getPicker().getDate().getTime();
                long ltoday = datedebutPicker.getPicker().getDate().getTime();
                
                if (ltoday < l){
                    res = true;
                }
                return res;
            }

            @Override
            public String getDefaultFailMessage() {
                return "Date fin doit etre sup date debut";
            }
        });
        
        cnt.addAll(datedebutPicker,datefinPicker);
        Label typevelo = new Label("select type velo");
        cnt.add(typevelo);
        url = "file:///C:/wamp64/www/PIDALI/web/uploads/images/";
        java.util.List<Type> types = ServiceType.getInstance().getAllTypes();
        ButtonGroup bntg = new ButtonGroup();
        Container cntimages = new Container(BoxLayout.x());
        for (Type t:types){
            Container cntimage = new Container(BoxLayout.x());
            System.out.println(t.getImage());
            Image img1 = theme.getImage("image1.png");
            Image i = URLImage.createToStorage((EncodedImage) img1, t.getImage(), url+t.getImage());
            System.out.println(url+t.getImage());
            ImageViewer img2 = new ImageViewer(i);
            RadioButton rb = new RadioButton();
            bntg.add(rb);
            rb.addActionListener(e->{
                idtype = String.valueOf(t.getId());
            });
            cntimage.addAll(rb,img2);
            cntimages.add(cntimage);
        }
        cnt.add(cntimages);
        
        final MapContainer mapcnt = new MapContainer(HTML_API_KEY);
        mapcnt.setCameraPosition(new Coord(36.802717, 10.0751443));
        
        Style st = new Style();
        st.setFgColor(0xff0000);
        st.setBgTransparency(0);
        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE,st);
        
        
        Label Site = new Label("Select Site");
        Picker stringPicker = new Picker();
        stringPicker.setType(Display.PICKER_TYPE_STRINGS);
        ArrayList<Site> sitelist = ServiceSite.getInstance().getsites() ;
        
        String str[] = new String[sitelist.size()]; 
        for (int j = 0; j < sitelist.size(); j++) { 
            str[j] = sitelist.get(j).getEmplacement(); 
        } 
        stringPicker.setStrings(str);
        
        stringPicker.addActionListener(l->{
            System.out.println(stringPicker.getValue().toString());
            for (Site s:sitelist){
                if (s.getEmplacement().equals(stringPicker.getValue().toString())){
                    
                    mapcnt.zoom(new Coord(s.getLatitude(), s.getLongitude()), 18);
                    mapcnt.addMarker(
                    EncodedImage.createFromImage(markerImg, false),
                    mapcnt.getCameraPosition(),
                    stringPicker.getValue().toString(),
                    "Optional long description",
                     evt -> {
                            
                     }
            );
                    ToastBar.showMessage("You selected "+stringPicker.getValue().toString(), FontImage.MATERIAL_PLACE);
                    break;
                }
            }
            
        });
        
        Button btnMoveCamera = new Button("Reset Camera");
        btnMoveCamera.addActionListener(e->{
            mapcnt.zoom(new Coord(36.802717, 10.0751443), 10);
            mapcnt.setCameraPosition(new Coord(36.802717, 10.0751443));
        });
        
        /*Button btnClearAll = new Button("Clear All");
        btnClearAll.addActionListener(e->{
            mapcnt.clearMapLayers();
        });*/
        
        
        add(cnt);
        add(stringPicker);
        add(mapcnt);
        add(btnMoveCamera);
        //add(btnAddPath);
        Button bntsubmit = new Button("submit");
        bntsubmit.addActionListener(l->{
            long datedebut = datedebutPicker.getPicker().getDate().getTime();
            long datefin = datefinPicker.getPicker().getDate().getTime();
            ServiceLocation.getInstance().addLocation(datedebut,datefin,idtype,stringPicker.getValue().toString(),Session.getCurrentSession().getId());
            new ListeLocationForm(previous, "user").show();
        });
        add(bntsubmit);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

}
