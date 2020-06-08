/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;
/**
 *
 * @author Fares
 */
import com.codename1.components.InfiniteScrollAdapter;
import com.codename1.components.MultiButton;
import com.codename1.components.SignatureComponent;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import static com.mycompany.myapp.gui.listeeventform.currentForm;
import com.mycompany.myapp.entities.Evenements;
import com.mycompany.myapp.entities.Session;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.Serviceevenements;
import com.mycompany.myapp.utils.Statics;
/**
 *
 * @author suare
 */
public class listeeventform extends Form{
      static Form  currentForm;

    User User = Session.getCurrentSession();

    public listeeventform(Form previous, Resources theme) {

        currentForm = this;
        currentForm.setTitle("liste des evenements");
        currentForm.setLayout(BoxLayout.y());
        
Style s = UIManager.getInstance().getComponentStyle("MultiLine1");
        FontImage p = FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s);
        EncodedImage placeholder = EncodedImage.createFromImage(p.scaled(p.getWidth() * 3, p.getHeight() * 3), false);
        java.util.List<Evenements> event = Serviceevenements.getInstance().getAllEvenements();
        MultiButton[] cmps = new MultiButton[event.size()];
         FontImage e = FontImage.createMaterial(FontImage.MATERIAL_EDIT_ATTRIBUTES, s);
          FontImage d = FontImage.createMaterial(FontImage.MATERIAL_DELETE, s);
           Container cn = new Container(new BoxLayout(BoxLayout.Y_AXIS));
              TextField search=new TextField("","Search");
              Button Save = new Button("Search");
              cn.addAll(search,Save);
                Save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                     int i =0;
                   for(Evenements prod : event){
                       if(prod.getNom_evenements().equals(search.getText())){
                           System.out.println("");
                           Container detail=new Container(new BoxLayout(BoxLayout.Y_AXIS));
                           setTitle("detail du evenement");
               Label nom =new Label("le nom du l'evenement est:"+prod.getNom_evenements());
               Label l =new Label("le nombre maximale des participants est:"+String.valueOf(prod.getNombre()));

                Label ll =new Label("la date du l'evenement est:"+String.valueOf(prod.getDateeve()));
                 Label lll =new Label("la date debut du l'evenement est:"+String.valueOf(prod.getDatedebut()));
                  Label llll =new Label("la date fin du l'evenement est:"+prod.getDatefin());
               detail.addAll(nom,l,ll,lll,llll);
              
               detail.addPointerPressedListener(ev -> {
                    EventDetail(prod,theme).show();
               });
            
                
               Form listeeventform = new Form(BoxLayout.y());
                listeeventform.add(detail);
                listeeventform.show();
                
               
            }
                }
                   
               
                }
               
               
                
            });
        InfiniteScrollAdapter.createInfiniteScroll(this.getContentPane(), () -> {
            int i =0;
             
            
            for(Evenements prod : event){
               
                cmps[i] = new MultiButton(prod.getNom_evenements());
                String imgUrl = "http://localhost/pi-project/web/velo/" + prod.getImage();
               cmps[i].addPointerReleasedListener(ev -> {
                EventDetail(prod,theme).show();
            });
                
                               try{
                    cmps[i].setIcon(URLImage.createToStorage(placeholder, String.valueOf(prod.getId()) + i , imgUrl));
                }catch(IllegalArgumentException ex){
                    cmps[i].setIcon(FontImage.createMaterial(FontImage.MATERIAL_PORTRAIT, s)); 
                    
                }
                
                i++;
            }
            add(cn);
            InfiniteScrollAdapter.addMoreComponents(this.getContentPane(),cmps, false);
      
        });
       
           
        currentForm.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            previous.showBack();
        });}
    

    
    
    
    
    
  
  
  public Form EventDetail(Evenements d, Resources theme) {

        Form EventDetail = new Form(BoxLayout.y());

       Label nom = new Label("Nom evenement");
        Label nb = new Label("Nombre");
        Label lieux = new Label("Lieux");
        Label descr = new Label("Description");

      
        //Label image = new Label("image");

        //SpanLabel Message = new SpanLabel("Descrption: \n" + c.getDescription() + "\n" + "Created AT: " + c.getDate() + "\n" + "Members: " + c.getNombreplace());
    
        TextField tfnom = new TextField(null, "");
        tfnom.setText(d.getNom_evenements());
        
        TextField tfnb = new TextField(null, "");
        tfnb.setText(String.valueOf(d.getNombre()));
        
        TextField tflieux = new TextField(null, "");
        tflieux.setText(String.valueOf(d.getLieuxeve()));
         TextField tfdescr = new TextField(null, "");
        tfdescr.setText(String.valueOf(d.getDescreptioneve()));
        
      
        Container Container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container.addAll(nom, tfnom, nb, tfnb, lieux, tflieux,descr,tfdescr);
       
        EventDetail.add(Container);

        Container ButtonsContainer = new Container(new FlowLayout());

        Button Delete = new Button("Delete");
        Button Edit = new Button("Edit");
        ButtonsContainer.addAll(Edit, Delete);

        EventDetail.add(ButtonsContainer);
        EventDetail.revalidate();
        Delete.addActionListener(ev -> {
            String result = Serviceevenements.getInstance().DeleteEvent(d);
          
            if (!result.equals("Error")) {
                Dialog.show("Success", result, new Command("OK"));
                new listeeventform(listeeventform.currentForm, theme).show();
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }

        });

        Edit.addActionListener(ev -> {
            d.setNom_evenements(tfnom.getText());
            d.setNombre(Integer.parseInt(tfnb.getText()));
            
            d.setLieuxeve(tflieux.getText());
//            d.setDescreptioneve(tfdesc.getText());
           
            
            if (Serviceevenements.getInstance().EditEvent(d)) {
                Dialog.show("Success", "Edited", new Command("OK"));
                new listeeventform(listeeventform.currentForm, theme).show();
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }
        });

        EventDetail.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            new listeeventform(currentForm, theme).show();
        });

        return EventDetail;
}
    
}
