package sim;

import org.apache.commons.math3.distribution.ParetoDistribution;
import org.jfree.ui.RefineryUtilities;
import org.omg.PortableInterceptor.INACTIVE;
import sim.Engine.SampleValueHistogram;
import sim.Engine.SimulationEngine;
import sim.RandomNumberGenerator.FileWeightManager;
import sim.RandomNumberGenerator.WeightedRandomGenerator;
import sim.util.Util;

import java.util.*;

public class Main {

    public static void main( String[] args ){

        ParetoDistribution paretoDistribution = new ParetoDistribution(1,1.5);
        Map<Long,Integer> freq = new TreeMap<>();
        for( int i =0;i<1000;i++){

            Long val = (long)paretoDistribution.sample();
            freq.put( val, freq.getOrDefault( val, 0 ) +1 );
        }

        System.out.println( freq );


        List<Integer> fileId = new ArrayList<>();
        for( int i = 0; i<Constants.numberOfFile; i++ ) fileId.add( i );

        Map<Integer, Integer > initialFreq = SimulationEngine.initializeInitialDownloadFrequency( fileId );
        Map<Integer, Integer > freqAfterDownloadedByGoodUser = SimulationEngine.simulateGoodUserDownload( initialFreq );
        Map<Integer, Integer > freqAfterDownloadedByBadUser = SimulationEngine.simulateBadUserDownload( fileId, initialFreq );

        /*System.out.println( initialFreq );
        System.out.println( freqAfterDownloadedByGoodUser );*/

        /*SampleValueHistogram chart = new SampleValueHistogram( "Download frequency", null, initialFreq, freqAfterDownloadedByGoodUser, freqAfterDownloadedByBadUser );
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);*/
    }
}
