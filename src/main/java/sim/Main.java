package sim;

import sim.Engine.SimulationEngine;
import sim.RandomNumberGenerator.FileWeightManager;
import sim.RandomNumberGenerator.WeightedRandomGenerator;
import sim.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main( String[] args ){

        List<Integer> fileId = new ArrayList<>();

        for( int i = 0; i<Constants.numberOfFile; i++ ) fileId.add( i );

        Map<Integer, Integer > initialFreq = SimulationEngine.initializeInitialDownloadFrequency( fileId );

        System.out.println( initialFreq );
    }
}
