/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;


import com.codename1.components.InfiniteScrollAdapter;
import com.codename1.components.MultiButton;
import com.codename1.messaging.Message;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.URLImage;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import static com.mycompany.myapp.gui.listeeventform.currentForm;
import com.mycompany.myapp.entities.Evenements;
import com.mycompany.myapp.entities.Session;
import com.mycompany.myapp.services.Serviceevenements;

/**
 *
 * @author suare
 */
public class parteventform extends Form {

    public parteventform(Form previous) {
        setTitle("liste evenements pour participer");

        Style s = UIManager.getInstance().getComponentStyle("MultiLine1");
        FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);
        EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 3, p.getHeight() * 3), false);
        java.util.List<Evenements> event = Serviceevenements.getInstance().getAllEvenements();
        MultiButton[] cmps = new MultiButton[event.size()];
        FontImage e = FontImage.createMaterial(FontImage.MATERIAL_EDIT_ATTRIBUTES, s);
        FontImage d = FontImage.createMaterial(FontImage.MATERIAL_DELETE, s);
        InfiniteScrollAdapter.createInfiniteScroll(this.getContentPane(), () -> {
            int i = 0;
            for (Evenements prod : event) {
                cmps[i] = new MultiButton(prod.getNom_evenements());
                cmps[i].setEmblem(FontImage.createMaterial(FontImage.MATERIAL_WEB_ASSET, s));
                cmps[i].addActionListener(cliqueEvent -> {
                    //idUser
                    //id
                    int idEvent = prod.getId();
                    Session session = new Session();
                    int idUser = Session.getCurrentSession().getId();
                    System.out.println("event " + idEvent + " userId " + idUser);
                    boolean b = Serviceevenements.getInstance().Participate(idEvent, idUser);
                    if (b) { 
                        
                                            Message m = new Message("Votre particpation a bien été validé.vous avez participer àlévenement    "+prod.getNom_evenements()
                            + " pourrez nous contacter à tout moment pour plus d'informations ou besoins.\n Cordialement \n L'équipe de Winners+ ");
                    Display.getInstance().sendMessage(new String[] {""+session.getCurrentSession().getEmail()},"Username :"+prod.getNom_evenements(), m);
                    System.out.println("Mail succes");
                        Dialog.show("Participate", "Success", "OK", "");
                    } else {
                        Dialog.show("Participate", "Error", "OK", "");
                    }

                });
                String imgUrl = "http://localhost/untitled4/web/velo/" + prod.getImage();

                try {
                    cmps[i].setIcon(URLImage.createToStorage(placeholder, String.valueOf(prod.getId()) + i, imgUrl));
                } catch (IllegalArgumentException ex) {
                    cmps[i].setIcon(FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s));

                }

                i++;
            }
            InfiniteScrollAdapter.addMoreComponents(this.getContentPane(), cmps, false);
        });

        getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            previous.showBack();
        });

    }

}

