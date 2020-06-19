/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Livraison;
import com.mycompany.myapp.entities.Reclamations;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Fares
 */

public class ServiceUsers {
    
    public static ServiceUsers instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public User User = new User();

    private ServiceUsers() {
        req = new ConnectionRequest();
    }
    public static ServiceUsers getInstance() {

        if (instance == null) {
            instance = new ServiceUsers();
        }
        return instance;
    }

    public User parseUser(String jsonText) {
   
        User UserL = new User();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> UserListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            if (UserListJson.get("type").equals("Login succeed")) {
                
                float id = Float.parseFloat(UserListJson.get("id").toString());
                UserL.setId((int) (id));
                UserL.setEmail(UserListJson.get("email").toString());
                if (UserListJson.get("role").toString().contains("ROLE_AGENT")) {
                    UserL.setRoles("Agent");
                }else if(UserListJson.get("role").toString().contains("ROLE_CLIENT")){
                       UserL.setRoles("Client");
                }else if(UserListJson.get("role").toString().contains("ROLE_CHEF_SITE")){
                       UserL.setRoles("ChefSite");
                } else{
                    return null;
                }
            
            
            } else {
                return null;
            }
        } catch (IOException ex) {
                ex.getMessage();
        }
        System.out.println(UserL.toString());
        return UserL;
    }

    public User Login(String username,String password) {
        String url =Statics.BASE_URL2 +"loginMobile/"+username+"/"+password;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                User = parseUser(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return User;
    }
    
}
