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

        List<Integer> fileId = new ArrayList<>();
        for( int i = 0; i<Constants.numberOfFile; i++ ) fileId.add( i );

        Map<Integer, Integer > initialFreq = SimulationEngine.initializeInitialDownloadFrequency( fileId );
        ArrayList<Integer> goodFileIds = SimulationEngine.getGoodFileIds( fileId );
        Map<Integer, Integer > freqAfterDownloadedByGoodUser = SimulationEngine.simulateGoodUserDownload( initialFreq, goodFileIds );
        Map<Integer, Integer > freqAfterDownloadedByBadUser = SimulationEngine.simulateDownloadByBadUser( fileId, initialFreq );
        //Map<Integer, Integer > freqAfterDownloadedByBadUser = null;

        System.out.println( initialFreq );
        System.out.println( freqAfterDownloadedByGoodUser );

        SampleValueHistogram chart = new SampleValueHistogram( "Download frequency", null, initialFreq, freqAfterDownloadedByGoodUser, freqAfterDownloadedByBadUser, goodFileIds );
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}
