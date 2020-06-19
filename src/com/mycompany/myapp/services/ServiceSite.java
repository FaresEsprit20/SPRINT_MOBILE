/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.Util;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Locations;
import com.mycompany.myapp.entities.Session;
import com.mycompany.myapp.entities.Site;
import com.mycompany.myapp.entities.Type;
import com.mycompany.myapp.entities.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.mycompany.myapp.utils.Statics;

/**
 *
 * @author bhk
 */
public class ServiceSite {
    
    public static ServiceSite instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ArrayList<Site> Sites;

    private ServiceSite() {
        req = new ConnectionRequest();
    }

    public static ServiceSite getInstance() {
        if (instance == null) {
            instance = new ServiceSite();
        }
        return instance;
    }

    
    
    
    public ArrayList<Site> parseSite(String jsonText) {
   
        Sites = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            
            for (Map<String, Object> obj : list) {
                Site l = new Site();
                l.setId((int)((double)obj.get("id")));
                l.setEmplacement(obj.get("emplacement").toString());
                l.setLongitude((double)obj.get("longitude"));
                l.setLatitude((double)obj.get("latitude"));
                Sites.add(l);
            }
        } catch (IOException ex) {
                ex.getMessage();
        }
        
        return Sites;
    }

    public ArrayList<Site> getsites() {
        String url =Statics.BASE_URL2 +"mobile/getsites";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                Sites = parseSite(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Sites;
    }
    
    public ArrayList<Site> parsecoor(String jsonText) {
   
        Sites = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            
            for (Map<String, Object> obj : list) {
                Site l = new Site();
                l.setId((int)((double)obj.get("id")));
                l.setEmplacement(obj.get("emplacement").toString());
                l.setLongitude((double)obj.get("longitude"));
                l.setLatitude((double)obj.get("latitude"));
                Sites.add(l);
            }
        } catch (IOException ex) {
                ex.getMessage();
        }
        
        return Sites;
    }

    public ArrayList<Site> getcoordonneessite(String nomsite) {
        String url =Statics.BASE_URL2 +"mobile/getcoor/"+nomsite;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                Sites = parsecoor(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Sites;
    }
    
}
