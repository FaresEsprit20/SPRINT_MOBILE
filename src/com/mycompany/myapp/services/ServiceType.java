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
import com.mycompany.myapp.entities.Type;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import static com.mycompany.myapp.services.ServiceLocation.instance;
import com.mycompany.myapp.utils.Statics;

/**
 *
 * @author Dali
 */
public class ServiceType {
    
    public ArrayList<Type> type;
    public static ServiceType instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    
    private ServiceType() {
        req = new ConnectionRequest();
    }

    public static ServiceType getInstance() {
        if (instance == null) {
            instance = new ServiceType();
        }
        return instance;
    }
    
    
    public ArrayList<Type> parseTasks(String jsonText) {
        
        try {
            
            type = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Type t = new Type();
                t.setId((int)((double)obj.get("id")));
                t.setType(obj.get("type").toString());
                t.setImage(obj.get("image").toString());
                type.add(t);
            }
        } catch (IOException ex) {
                //Logger.getLogger(ServiceType.class.getName()).log(Level.SEVERE, null, ex);
        }
        return type;
        
    }
    
    public ArrayList<Type> getAllTypes() {
        String url = Statics.BASE_URL2 + "mobile/gettypes";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                type = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return type;
    }
    
    
}
