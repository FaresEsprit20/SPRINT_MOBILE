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
import com.mycompany.myapp.entities.Client;
import com.mycompany.myapp.entities.Agent;
import com.mycompany.myapp.entities.Livraison;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.entities.Session;
import com.mycompany.myapp.entities.Statistiques;
import com.mycompany.myapp.utils.Statics;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bhk
 */
public class ServiceTask {

    public ArrayList<Livraison> tasks;
    public ArrayList<Statistiques> stats;
    public ArrayList<Reclamation> recs;
    public static ServiceTask instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceTask() {
        req = new ConnectionRequest();
    }

    public static ServiceTask getInstance() {
        if (instance == null) {
            instance = new ServiceTask();
        }
        return instance;
    }

    public boolean addTask(Reclamation t) throws Exception{
        String url = Statics.BASE_URL + "/reclamation/" + t.getTitre() + "/" + t.getSujet() + "/" + t.getLivraisonId() +"/"+Session.getCurrentSession().getId() + "/add/";
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

    public Client getClient(String jsonText) {
        Client client = new Client();

        return client;
    }

      public ArrayList<Statistiques> parseStats(String jsonText) {
        stats = new ArrayList<>();
         Statistiques t = new Statistiques();
         
        JSONParser j = new JSONParser();
        JSONArray jsonarray = new JSONArray(jsonText);
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject obj = jsonarray.getJSONObject(i);
           float livr = (float) obj.getDouble("Livr");
           float cours = (float) obj.getDouble("cours");
           t.setCours(cours);
           t.setLivr(livr);
            stats.add(t);
            System.out.println(stats);
        }
        return stats;
    }
    
    public ArrayList<Statistiques> getAllStats(int id) {
        String url = Statics.BASE_URL + "/stats/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                stats = parseStats(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return stats;
    }
    
    public ArrayList<Livraison> parseTasks(String jsonText) {
        tasks = new ArrayList<>();
        JSONParser j = new JSONParser();
        JSONArray jsonarray = new JSONArray(jsonText);
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject obj = jsonarray.getJSONObject(i);
            JSONObject client = obj.getJSONObject("client");
            JSONObject agent = obj.getJSONObject("agent");
            // System.out.println(String.format("details => %s", client.toString()));
            String email = client.getString("email");
            String adressecl = client.getString("adress");
            String telcl = client.getString("tel");
            int ida = client.getInt("id");
            String emaila = agent.getString("email");
            int idaa = agent.getInt("id");
            System.out.println(email);
            System.out.println(ida);
            Livraison t = new Livraison();
            int id = obj.getInt("id");
            float prix = (float) obj.getDouble("prix");
            String titre = obj.getString("titre");
            String etat = obj.getString("etat");
            String adresse = obj.getString("adresse");
            String tel = obj.getString("tel");
            String date = obj.getString("dateCreation");
            Client cl = new Client();
            cl.setId(ida);
            cl.setEmail(email);
            cl.setAdress(adressecl);
            cl.setTel(telcl);
            Agent ag = new Agent();
            ag.setId(idaa);
            ag.setEmail(email);
            t.setId(id);
            t.setPrix(prix);
            t.setTitre(titre);
            t.setEtat(etat);
            t.setAdresse(adresse);
            t.setTel(tel);
            t.setDateCreation(date);
            t.setClient(cl);
            t.setAgent(ag);
            tasks.add(t);
            System.out.println(tasks);
        }
        return tasks;
    }

    public ArrayList<Reclamation> parseRecs(String jsonText) {
        try {
            recs = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
            for (Map<String, Object> obj : list) {
                Reclamation t = new Reclamation();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);
                t.setTitre(obj.get("titre").toString());
                t.setSujet(obj.get("sujet").toString());
                t.setDateCreation(obj.get("dateCreation").toString());
                t.setLivraisonId(obj.get("livraison").toString());

                recs.add(t);
            }

        } catch (IOException ex) {

        }
        return recs;
    }

    public ArrayList<Livraison> getAllTasks(int id) {
        String url = Statics.BASE_URL + "/livraison/all/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }

    public boolean deleteTask(int id) {
        String url = Statics.BASE_URL + "/livraison/view/" + id + "/delete";
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

    public boolean editTask(int id) {
        String url = Statics.BASE_URL + "/livraison/view/" + id + "/valider";
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

    public boolean deleteRec(int id) {
        String url = Statics.BASE_URL + "/reclamation/view/" + id + "/delete";
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

    public boolean editRec(int id, String titre, String sujet) {
        String url = Statics.BASE_URL + "/reclamation/" + id + "/" + titre + "/" + sujet + "/edit/";
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

    public ArrayList<Reclamation> getAllRecs(int id) {
        String url = Statics.BASE_URL + "/reclamation/all/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                recs = parseRecs(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return recs;
    }
}
