/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.mycompany.myapp.entities.Session;
import com.mycompany.myapp.gui.AddTaskForm;
import com.mycompany.myapp.gui.ListReclamations;
import com.mycompany.myapp.gui.ListTasksForm;
import com.mycompany.myapp.gui.MenuForm;
import com.mycompany.myapp.services.ServiceTask;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Fares
 */
public class DefaultCategoryRenderer {

MenuForm cs ;

private DefaultRenderer buildCategoryRenderer(int[] colors) {
    DefaultRenderer renderer = new DefaultRenderer();
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(15);
    renderer.setMargins(new int[]{20, 30, 15, 0});
    for (int color : colors) {
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(color);
        renderer.addSeriesRenderer(r);
    }
    return renderer;
}

/**
 * Builds a category series using the provided values.
 *
 * @param titles the series titles
 * @param values the values
 * @return the category series
 */
protected CategorySeries buildCategoryDataset(String title, double[] values) {
    CategorySeries series = new CategorySeries(title);
    List<Statistiques> list = new ArrayList();
    Statistiques s = new Statistiques();
      list = ServiceTask.getInstance().getAllStats(Session.getCurrentSession().getId());
   for (Statistiques p : list) {
       s.setLivr(p.getLivr());
        s.setCours(p.getCours());
            System.out.println(p.getCours());
            System.out.println(p.getLivr());
        }
    
        series.add("Livrées ", s.getLivr());
        series.add("En Cours ", s.getCours());
    

    return series;
}

public Form createPieChartForm() {
    // Generate the values
    double[] values = new double[]{12, 14};

    // Set up the renderer
    int[] colors = new int[]{ColorUtil.BLUE, ColorUtil.GRAY};
    DefaultRenderer renderer = buildCategoryRenderer(colors);
    renderer.setZoomButtonsVisible(true);
    renderer.setZoomEnabled(true);
    renderer.setChartTitleTextSize(20);
    renderer.setDisplayValues(true);
    renderer.setShowLabels(true);
    SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
    r.setGradientEnabled(true);
    r.setGradientStart(0, ColorUtil.BLUE);
    r.setGradientStop(0, ColorUtil.GREEN);
    r.setHighlighted(true);

    // Create the chart ... pass the values and renderer to the chart object.
    PieChart chart = new PieChart(buildCategoryDataset("Project budget", values), renderer);

    // Wrap the chart in a Component so we can add it to a form
    ChartComponent c = new ChartComponent(chart);

    // Create a form and show it.
    Form f = new Form("Statistiques", new BorderLayout());
    f.add(BorderLayout.CENTER, c);
     f.getToolbar()
                .addCommandToLeftSideMenu("Liste des Livraisons", null, ev -> {
                    new ListTasksForm(f).show();
                });
        f.getToolbar()
                .addCommandToLeftSideMenu("Liste des Reclamations", null, ev -> {
                    new ListReclamations(f).show();
                }
                );
        f.getToolbar()
                .addCommandToLeftSideMenu("Ajouter Réclamation", null, ev -> {
                    new AddTaskForm(f).show();
                }
                );
         f.getToolbar()
                .addCommandToLeftSideMenu("Menu", null, ev -> {
                    new MenuForm().show();
                }
                );

    return f;

}


    
}
