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
import com.mycompany.myapp.entities.Maintenance;
import com.mycompany.myapp.entities.Session;
import com.mycompany.myapp.entities.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.mycompany.myapp.utils.Statics;

public class ServiceMaintenance {
    
    public ArrayList<Maintenance> maintenances;
    public static ServiceMaintenance instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    //private static User User = Session.getCurrentSession();
    
    private ServiceMaintenance() {
        req = new ConnectionRequest();
    }

    public static ServiceMaintenance getInstance() {
        if (instance == null) {
            instance = new ServiceMaintenance();
        }
        return instance;
    }
    
    public ArrayList<Maintenance> parseRdv(String jsonText) {
        
        try {
            
            maintenances = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Maintenance m = new Maintenance();
                m.setId((int) ((double) obj.get("id")));
                m.setTitre(obj.get("titre").toString());
                m.setDescription(obj.get("description").toString());
                m.setDate(obj.get("dateRDV").toString());
                m.setEtat(obj.get("etat").toString());
                maintenances.add(m);
            }
        } catch (IOException ex) {
                //Logger.getLogger(ServiceType.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maintenances;
        
    }
    
    public ArrayList<Maintenance> getAll(int id) {
        String url = Statics.BASE_URL3 + "mobile/getrdv/"+ id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                maintenances = parseRdv(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return maintenances;
    }
    
    
    public boolean addRDV(int id,String titre, String description, long date) {
        String url = Statics.BASE_URL3 +"mobile/add/"+ id + "/" + titre + "/" + description + "/" + date;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public ArrayList<Maintenance> getAllencours(int id) {
        String url = Statics.BASE_URL3 + "mobile/getrdvencours/" + id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                maintenances = parseRdv(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return maintenances;
    }
    
    public boolean CheckEncours(int id){
        boolean output = true;
        maintenances = getAllencours(id);
        if (maintenances.size() == 0){
            output = false;
        }
        return output;
    }
    
    public boolean deleteRDV(int id) {
        String url = Statics.BASE_URL3 +"mobile/delete/"+ id ;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public boolean acceptRDVencours(int id){
        String url = Statics.BASE_URL3 +"mobile/accept/"+ id ;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public int countconfirmation(int count){
        int a = 0;
        try {
            Database db = null;
            Cursor cur = null;
            Row row = null;
            String sql = "";
            String table = "maintenace";
            db = Database.openOrCreate("MyDB.db");
            sql = "CREATE TABLE if not exists 'maintenance' ('id' int NOT NULL,'type' VARCHAR(250) NOT NULL,'count' int NOT NULL);";
            db.execute(sql);
            System.out.println("table created");
            
            sql = "select count(*) From maintenance where id="+Session.getCurrentSession().getId();
            cur = db.executeQuery(sql);
            System.out.println("check if table has data");
            System.out.println(cur.getRow().getInteger(0));
            
            if (cur.getRow().getInteger(0)>0){
                System.out.println("table has data");
                sql = "SELECT count FROM maintenance WHERE type='confirmé' and id ="+Session.getCurrentSession().getId()+";";
                System.out.println(sql);
                cur = db.executeQuery(sql);
                while (cur.next()){
                    row = cur.getRow();
                    a = row.getInteger(0);
                    System.out.println("previously "+a+" confirmed");
                    System.out.println("nom "+count+" confirmed");
                    a = count - a;
                    sql = "UPDATE maintenance set count = "+count+" where type = 'confirmé' and id ="+Session.getCurrentSession().getId()+";";
                    System.out.println(sql);
                    db.execute(sql);
                }
            }else{
                System.out.println("table in new");
                sql = "insert into 'maintenance' values ("+Session.getCurrentSession().getId()+",'confirmé', "+count+");";
                db.execute(sql);
                a = -1;
            }
            Util.cleanup(db);
            Util.cleanup(cur);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        return a;
    }
    
}
