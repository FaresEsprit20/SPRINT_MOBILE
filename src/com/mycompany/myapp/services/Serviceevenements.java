/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;
/**
 *
 * @author Fares
 */
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Evenements;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 *
 * @author suare
 */
public class Serviceevenements {
       public  String  result="";
        public ArrayList<Evenements> evenement;

        private ConnectionRequest req;
    public static Serviceevenements instance=null;

        public boolean resultOK;
         private Serviceevenements() {
         req = new ConnectionRequest();
    }

    public static Serviceevenements getInstance() {
        if (instance == null) {
            instance = new Serviceevenements();
        }
        return instance;
    }
    
        public boolean ModifierEvenement(Evenements t) {
           String url = Statics.BASE_URL + "/ModifierE?idEvenement="+t.getId()+"&nomEvenement=" + t.getNom_evenements() + "&nombre=" + t.getNombre()  + "&lieuxeve=" + t.getLieuxeve()+ "&descreptioneve=" + t.getDescreptioneve()+ "&image=" + t.getImage();
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
public boolean addevenements(Evenements e)
{
      String url = Statics.BASE_URL + "/new?nomEvenements="+ e.getNom_evenements() + "&nombre=" + e.getNombre()  + "&lieuxeve=" + e.getLieuxeve()+ "&descreptioneve=" + e.getDescreptioneve()+ "&datedebut=" + e.getDatedebut()+ "&datefin=" + e.getDatefin()+ "&dateve=" + e.getDateeve();
        
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
  public ArrayList<Evenements> parseTasks(String jsonText){
        try {
            evenement=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Evenements t = new Evenements();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setNom_evenements(obj.get("nomEvenements").toString());
                t.setNombre(((int)Float.parseFloat(obj.get("nombre").toString())));
                t.setLieuxeve(obj.get("lieuxeve").toString());
                 Map<String, Object> dateBd=(Map<String, Object>) obj.get("dateeve");
                     Date ts=new Date();  
                     int date;
                
                     date=(int) Float.parseFloat(dateBd.get("timestamp").toString());
                     
                     ts.setTime(date);
                     
                 t.setDateeve(ts.toString());
                 
                  Map<String, Object> datedeb=(Map<String, Object>) obj.get("datedebut");
                   
      
                     date=(int) Float.parseFloat(datedeb.get("timestamp").toString());
                     
                     ts.setTime(date);
                     
                 t.setDatedebut(ts.toString());
                 
                    Map<String, Object> datefin=(Map<String, Object>) obj.get("datefin");
                   
      
                     date=(int) Float.parseFloat(datefin.get("timestamp").toString());
                     
                     ts.setTime(date);
                     
                 t.setDatefin(ts.toString());
                
                try{
                   t.setDescreptioneve(obj.get("descreptioneve").toString());
                }catch(NullPointerException e){ }
                try{
                    t.setImage(obj.get("image").toString());
                }catch(NullPointerException e){ }
                    

                   
                evenement.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return evenement;
    }
  public ArrayList<Evenements> getAllEvenements(){
        String url = Statics.BASE_URL+"/affiche";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                evenement = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return evenement;
    }
   public String DeleteEvent(Evenements d){
          String url = Statics.BASE_URL + "/deleteEventMobile/?id=" + d.getId();
          
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        System.out.println(url);
                   

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                try {
                    String data = new String(req.getResponseData());
                    JSONParser j = new JSONParser();
                    Map<String, Object> tasksListJson;
                    tasksListJson = j.parseJSON(new CharArrayReader(data.toCharArray()));
                   result=(String) tasksListJson.get("body");

                } catch (IOException ex) {
                    ex.getMessage();
                }
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }
    
    public boolean EditEvent(Evenements d) {
        String url = Statics.BASE_URL+ "/EditEventMobile/?id"+ "&descreptioneve=" + d.getDescreptioneve()+ "&lieuxeve=" + d.getLieuxeve()+"&nomEvenements=" + d.getNom_evenements()+ "&nombre=" + d.getNombre()


                ;

            System.out.println(url);
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
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

    public boolean Participate(int idEvent, int idUser){
        String url = Statics.BASE_URL2 + "Addpart/" + idEvent + "/" +idUser;
        
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
    
}
