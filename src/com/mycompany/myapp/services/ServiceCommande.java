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
import com.mycompany.myapp.entities.Commande;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrateur
 */
public class ServiceCommande {
        public ArrayList<Commande> commandes;
  //  Date date1;
    
    
    public static ServiceCommande instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceCommande() {
         req = new ConnectionRequest();
    }

    public static ServiceCommande getInstance() {
        if (instance == null) {
            instance = new ServiceCommande();
        }
        return instance;
    }
    
    
    
    public boolean addTask(Commande c) {
        String url = Statics.BASE_URL + "/commande/new?refCmd=" + c.getRef() +  "&dateCmd=" + c.getDate()+  "&etatCmd=" + c.getEtat()+ "&prixCmd=" + c.getPrix() + "&idUser=" + c.getId_user();
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
    
    
    
    public ArrayList<Commande> parseTasks(String jsonText){
        try {
            
            commandes=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            for(Map<String,Object> obj : list){
                Commande c = new Commande();
                
                c.setRef(obj.get("refCmd").toString());
                
                //c.setDate(obj.get("dateCmd").toString());
              

                c.setEtat(obj.get("etatCmd").toString());
                float prix = Float.parseFloat(obj.get("prixCmd").toString());
                c.setPrix(prix);
                
                
                
               
               
                
                commandes.add(c);
            }
             
            
            
        } catch (IOException ex) {
            
        }
        return commandes;
    }
    
    public ArrayList<Commande> getAllTasks(){
        String url = Statics.BASE_URL+"/commande/all";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                commandes = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return commandes;
    }
    
    public boolean editCommande(Commande c) {
        String url = Statics.BASE_URL + "/commande/edit/"+ c.getRef() ;
        req.setUrl(url);
        req.setPost(false);
        
                                                req.addArgument("etatCmd", c.getEtat());
                                                req.addArgument("prixCmd", c.getPrix().toString());
                                               
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; 
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return true;
    }
    
    public boolean deleteCommande(String id) {
        String url = Statics.BASE_URL + "/commande/delete/" + id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return true;
    }
    

    
    
}
