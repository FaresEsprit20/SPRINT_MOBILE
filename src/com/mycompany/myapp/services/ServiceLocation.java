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
public class ServiceLocation {
    
    public static ServiceLocation instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ArrayList<Locations> locations;

    private ServiceLocation() {
        req = new ConnectionRequest();
    }

    public static ServiceLocation getInstance() {
        if (instance == null) {
            instance = new ServiceLocation();
        }
        return instance;
    }

    
    public ArrayList<Locations> parseLocation(String jsonText) {
   
        locations = new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            
            for (Map<String, Object> obj : list) {
                Locations l = new Locations();
                l.setId((int)((double)obj.get("id")));
                
                String datedebut = obj.get("dateDebut").toString();
                l.setDate_debut(datedebut.substring(0, datedebut.indexOf("T")));
                
                String datefin = obj.get("dateFin").toString();
                l.setDate_fin(datefin.substring(0, datefin.indexOf("T")));
                
                l.setStatus(obj.get("status").toString());
                
                String str = obj.get("idSite").toString();
                int pos = str.indexOf("emplacement");
                int posegal = str.substring(pos).indexOf("=");
                int posvirgule = str.substring(pos).indexOf(",");
                l.setSite(str.substring(pos).substring(posegal+1,posvirgule));
                
                str = obj.get("idType").toString();
                pos = str.indexOf("type");
                posegal = str.substring(pos).indexOf("=");
                posvirgule = str.substring(pos).indexOf(",");
                l.setType(str.substring(pos).substring(posegal+1,posvirgule));
                locations.add(l);
            }
        } catch (IOException ex) {
                ex.getMessage();
        }
        
        return locations;
    }

    public ArrayList<Locations> getlocations(int id) {
        String url =Statics.BASE_URL2 +"mobile/getlocation/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                locations = parseLocation(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return locations;
    }
    
    public ArrayList<Locations> getlocationsparsite(int id) {
        String url =Statics.BASE_URL2 +"mobile/getlocationsparsite/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                locations = parseLocation(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return locations;
    }
    
    public void validerLocation(int id, String retard,String etat) {
        String url = Statics.BASE_URL2 +"mobile/valider/"+ id +"/"+retard+"/"+etat ;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public boolean supprimerlocation(int id) {
        String url = Statics.BASE_URL2 +"mobile/deletelocation/"+ id ;
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
    public boolean addLocation(long datedebut,long datefin, String idtype,String site, int iduser) {
        String url = Statics.BASE_URL + "mobile/ajout/" + iduser + "/" + datedebut + "/" + datefin +"/"+ idtype + "/" + site;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public int locationnotif(int count){
        int a = 0;
        try {
            String sql = "";
            
            Database db = Database.openOrCreate("MyDataBase.db");
            sql = "CREATE TABLE if not exists 'location' ('id' int NOT NULL,'count' int NOT NULL);";
            System.out.println(sql);
            db.execute(sql);
            
            sql = "SELECT count FROM location WHERE id ="+Session.getCurrentSession().getId()+";";
            System.out.println(sql);
            Cursor cur = db.executeQuery(sql);
            if (cur.next()){
                Row row = cur.getRow();
                a = row.getInteger(0);
                a = count - a;
                sql = "UPDATE location set count = "+count+" where id ="+Session.getCurrentSession().getId()+";";
                System.out.println(sql);
                db.execute(sql);
            }else{
                sql = "insert into 'location' values ("+Session.getCurrentSession().getId()+","+count+");";
                System.out.println(sql);
                db.execute(sql);
            }
            
            Util.cleanup(db);
            Util.cleanup(cur);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        return a;
    }
    
}
