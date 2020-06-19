/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.CENTER;
import static com.codename1.ui.CN.SOUTH;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
//import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.Constraint;
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import com.mycompany.myapp.entities.Session;
import com.mycompany.myapp.entities.User;
import java.util.Date;
import com.mycompany.myapp.services.ServiceMaintenance;


public class AddRDVForm extends Form{
    
    Form current;
    
    private static User User = Session.getCurrentSession();
    private Resources theme;

    public AddRDVForm(Form previous) {
        setTitle("Prendre RDV");
        setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
        theme = UIManager.initFirstTheme("/theme");
        ImageViewer img = new ImageViewer(theme.getImage("reparer.jpg"));
        
        TextModeLayout tm = new TextModeLayout(4, 2);
        Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        TextComponent titre = new TextComponent().labelAndHint("Titre");
        content.add(titre);
        
        TextComponent description = new TextComponent().labelAndHint("Description").multiline(true).rows(3);
        content.add(description);
        
        PickerComponent datePicker = PickerComponent.createDateTime(new Date()).label("Date du rendez-vous");
        content.add(datePicker);
        
        content.setScrollableY(true);
        
        Button submit = new Button("Valider");
        FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE); 
        
        submit.addActionListener(e -> {
            boolean aa;
            aa = ServiceMaintenance.getInstance().addRDV(User.getId(), titre.getText(), description.getText(), datePicker.getPicker().getDate().getTime());
            if (aa){
                Dialog.show("Easy Ride", "Demande de rendez-vous envoyée", "OK", null);
                new AchrefMenuForm(current).show();
            }else{
                Dialog.show("Easy Ride", "Error, please try again", "OK", null);
            }
        });
        
        
        
        
        Container cnt2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        cnt2.addAll(content, submit);
        add(cnt2);
        
        Validator val = new Validator();
        val.setShowErrorMessageForFocusedComponent(true);
        val.addConstraint(titre, 
                new GroupConstraint(
                        new LengthConstraint(8 ,"Please enter a valid title"), 
                        new RegexConstraint("^([a-zA-Z ]*)$", "Please only latin characters"))).
                addSubmitButtons(submit);
        
        val.addConstraint(description, 
                new GroupConstraint(
                        new LengthConstraint(10, "use more than 10 caracters"), 
                        new RegexConstraint("^([a-zA-Z ]*)$", "only use latin characters "))).
                addSubmitButtons(submit);
        
        val.addConstraint(datePicker, new Constraint() {
            @Override
            public boolean isValid(Object value) {
                boolean res = false;
                Date today = new Date();
                long l = datePicker.getPicker().getDate().getTime();
                long ltoday = today.getTime();
                
                if (ltoday < l){
                    res = true;
                }
                return res;
            }

            @Override
            public String getDefaultFailMessage() {
                return "Date must be after today";
            }
        });
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
           
        
        
    }
    
}
