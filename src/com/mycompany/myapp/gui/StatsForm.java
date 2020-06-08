/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Component;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.services.ServiceTask;
import com.mycompany.myapp.entities.Session;
import com.mycompany.myapp.entities.DefaultCategoryRenderer;
/**
 *
 * @author Fares
 */
public class StatsForm extends Form {
    
    public StatsForm(Form previous) {
        setTitle("Statistiques");
        setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
       int id = Session.getCurrentSession().getId();
       System.out.println(" sd "+id);
        
       DefaultCategoryRenderer pix = new DefaultCategoryRenderer();
       pix.createPieChartForm().show();
      
     
       
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }
    
    
    
}
