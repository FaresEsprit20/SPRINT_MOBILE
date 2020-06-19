/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Session;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceUsers;

/**
 *
 * @author Fares
 */
public class LoginForm extends Form {

    Form current;
    MenuForm c = new MenuForm();
    private static User User;
    
private Resources theme;

    public LoginForm(Form previous) {
         theme = UIManager.initFirstTheme("/theme");
        current = this;
        setTitle("Se Connecter");
        setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
        Container cnt = new Container(BoxLayout.y());
        ImageViewer img = new ImageViewer(theme.getImage("login.jpg"));
        TextField username = new TextField(null, "username");
        TextField password = new TextField(null, "password");
        password.setConstraint(TextField.PASSWORD);
        Button login = new Button("login");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                {
                    try{
                    if ((username.getText().length() == 0) || (password.getText().length() == 0)) {
                        Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                        current.show();
                        
                    } else {
                        User = ServiceUsers.getInstance().Login(username.getText(), password.getText());
                    }
                   
                    if (User != null && User.getRoles().equals("Agent")) {
                        username.setText("");
                        password.setText("");
                        Session.start(User);
                        c.show();
                    }else if(User != null && User.getRoles().equals("Client")){
                         username.setText("");
                        password.setText("");
                        Session.start(User);
                       new AchrefMenuForm(current).show();
                    }else if(User != null && User.getRoles().equals("ChefSite")){
                        Session.start(User);
                       new MenuChefsiteForm().show();
                    } else {
                        Dialog.show("Alert", "Authentification Failed", new Command("OK"));
                    }
                     }catch(Exception E){
                   System.out.println ("");
                    }
                }
            }
        });
        cnt.addAll(img,username, password, login);
        addAll(cnt);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

}
