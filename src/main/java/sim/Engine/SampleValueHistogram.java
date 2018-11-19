package sim.Engine;

import java.awt.Font;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFrame;

import org.apache.commons.math3.distribution.IntegerDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.ParetoDistribution;
import org.apache.commons.math3.distribution.PascalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;
import org.apache.commons.math3.distribution.ZipfDistribution;
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


    public SampleValueHistogram(String applicationTitle ) {

        super(applicationTitle);
        draw();
    }

    public SampleValueHistogram( String applicationTitle, JFrame frame, Map<Integer,Integer> initialFreq, Map<Integer, Integer> freqAfterDownloadedByGoodUser, Map<Integer, Integer> freqAfterDownloadedByBadUser ) {

        super(applicationTitle);
        this.frame = frame;
        this.initialFreq = initialFreq;
        this.freqAfterDownloadedByGoodUser = freqAfterDownloadedByGoodUser;
        this.freqAfterDownloadedByBadUser = freqAfterDownloadedByBadUser;

        draw();
    }

    /*private CategoryDataset createDataset( Distribution distribution ) {

        if( distribution == Distribution.Zipf ) {
            IntegerDistribution zipf = new ZipfDistribution( numOfFile, param2 );
            return populateDataset( zipf );
        }

        RealDistribution real = null;

        if( distribution == Distribution.Pareto ) {

            real = new ParetoDistribution( param1, param2 );
        }
        else if( distribution == Distribution.Normal ) {

            real = new NormalDistribution( param1, param2 );
        }

        return populateDataset( real );
    }

    private DefaultCategoryDataset populateDataset( IntegerDistribution distro ) {

        Map<Integer, Integer> freq = new TreeMap<>();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for( int i = 1; i<=numOfSample; i++) {

            int rand = distro.sample();
            if( rand < 0 ) rand *= -1;

            freq.put( rand, freq.getOrDefault( rand, 0 ) + 1 );
        }

        for( int key : freq.keySet() ) {

            dataset.addValue( freq.get(key), "", String.valueOf( key ) );
        }

        return dataset;
    }

    private DefaultCategoryDataset populateDataset( RealDistribution distro ) {

        Map<Long, Integer> freq = new TreeMap<>();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for( int i = 1; i<=numOfSample; i++) {

            long rand = Math.round( distro.sample() ) % numOfFile;
            if( rand < 0 ) rand *= -1;
            freq.put( rand, freq.getOrDefault( rand, 0 ) + 1 );
        }

        for( long key : freq.keySet() ) {

            dataset.addValue( freq.get(key), "", String.valueOf( key ) );
        }

        return dataset;
    }*/

    private void draw( ) {

        CategoryDataset dataset = createDataset();

        JFreeChart barChart = ChartFactory.createBarChart( "", "File Id", "Num Of Download", dataset, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));

        CategoryAxis domainAxis = barChart.getCategoryPlot().getDomainAxis();
        Font domainFont = new Font( "Tahoma", Font.PLAIN, 5 );
        domainAxis.setTickLabelFont(domainFont);
        domainAxis.setLowerMargin( 0 );
        domainAxis.setUpperMargin( 0 );

        setContentPane(chartPanel);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    private CategoryDataset createDataset() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for( Integer key : initialFreq.keySet() ) {

            dataset.addValue( initialFreq.get(key), "Initial Downloads", String.valueOf( key ) );
            dataset.addValue( freqAfterDownloadedByGoodUser.get(key) + freqAfterDownloadedByBadUser.get(key), "After Downloaded by good and bad User", String.valueOf( key ) );
            //dataset.addValue( freqAfterDownloadedByBadUser.get(key), "After Downloaded by Bad User", String.valueOf( key ) );
        }

        return dataset;
    }

    @Override
    public void windowClosing(WindowEvent e) {

        if( frame != null )
            frame.setVisible( true );

        this.dispose();
    }

    public static void main(String[] args) {

        SampleValueHistogram chart = new SampleValueHistogram( "Download frequency" );
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}
