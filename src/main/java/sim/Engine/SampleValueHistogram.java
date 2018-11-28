package sim.Engine;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class SampleValueHistogram extends ApplicationFrame {

    private static final long serialVersionUID = 1L;

    private int numOfFile = 100;
    private int numOfSample = 1000;
    private double param1 = 1;
    private double param2 = 2;
    private JFrame frame = null;
    Map<Integer,Integer> initialFreq;
    Map<Integer,Integer> freqAfterDownloadedByGoodUser;
    Map<Integer,Integer> freqAfterDownloadedByBadUser;
    ArrayList<Integer> goodFileIds;

    public SampleValueHistogram( String applicationTitle ) {

        super(applicationTitle);
        draw();
    }

    public SampleValueHistogram( String applicationTitle, JFrame frame, Map<Integer,Integer> initialFreq, Map<Integer, Integer> freqAfterDownloadedByGoodUser, Map<Integer, Integer> freqAfterDownloadedByBadUser,ArrayList<Integer> goodFileIds ) {

        super(applicationTitle);
        this.frame = frame;
        this.initialFreq = initialFreq;
        this.freqAfterDownloadedByGoodUser = freqAfterDownloadedByGoodUser;
        this.freqAfterDownloadedByBadUser = freqAfterDownloadedByBadUser;
        this.goodFileIds = goodFileIds;

        draw();
    }

    private void draw( ) {

        CategoryDataset dataset = createDataset();

        JFreeChart barChart = ChartFactory.createStackedBarChart3D( "", "File Id", "Num Of Download", dataset, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));

        CategoryAxis domainAxis = barChart.getCategoryPlot().getDomainAxis();
        Font domainFont = new Font( "Tahoma", Font.PLAIN, 5 );
        domainAxis.setTickLabelFont(domainFont);
        domainAxis.setLowerMargin( 0 );
        domainAxis.setUpperMargin( 0 );

        /*JFreeChart barChart2 = ChartFactory.createBarChart( "", "File Id", "Num Of Download", dataset, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel2 = new ChartPanel(barChart2);
        chartPanel2.setPreferredSize(new java.awt.Dimension(560, 367));

        CategoryAxis domainAxis2 = barChart2.getCategoryPlot().getDomainAxis();
        Font domainFont2 = new Font( "Siyam Rupali", Font.PLAIN, 5 );
        domainAxis2.setTickLabelFont(domainFont2);
        domainAxis2.setLowerMargin( 0 );
        domainAxis2.setUpperMargin( 0 );

        JPanel panel = new JPanel();

        panel.setSize(new java.awt.Dimension(560, 367));
        panel.add( chartPanel );
        panel.add( chartPanel2 );*/

        JPanel panel = new JPanel();
        JTextField textField = new JTextField();
        textField.setText( "Good File Id's" + goodFileIds.toString() );

        panel.add( textField );
        chartPanel.add( panel );

        setContentPane( chartPanel );
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    private CategoryDataset createDataset() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for( Integer key : initialFreq.keySet() ) {
            dataset.addValue( initialFreq.get(key), "Initial Downloads", String.valueOf( key ) );
            dataset.addValue( freqAfterDownloadedByBadUser.get(key), "After Downloaded by Bad User", String.valueOf( key ) );
            dataset.addValue( freqAfterDownloadedByGoodUser.get(key), "After Downloaded by good User", String.valueOf( key ) );
        }

        return dataset;
    }

    @Override
    public void windowClosing(WindowEvent e) {

        if( frame != null )
            frame.setVisible( true );

        this.dispose();
    }
}
