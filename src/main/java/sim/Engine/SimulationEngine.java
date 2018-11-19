package sim.Engine;

import sim.Constants;
import sim.RandomNumberGenerator.WeightedRandomGenerator;
import sim.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SimulationEngine {

    public static Map<Integer,Integer> initializeInitialDownloadFrequency( List<Integer> fileId ){

        Map<Integer,Integer> freq = new HashMap<>();

        initializeInitialDownloadFrequencyForGoodFile( fileId, freq );
        initializeInitialDownloadFrequencyForRegularFIle( fileId,freq );

        return freq;
    }

    private static void initializeInitialDownloadFrequencyForRegularFIle(List<Integer> fileId, Map<Integer, Integer> freq) {

        for( int i = (int)(fileId.size()* Constants.fractionOfGoodFile); i< fileId.size(); i++ ){

            freq.put( fileId.get(i), Util.getRandomNumberInRange( Constants.weightOfBadFileMin, Constants.weightOfBadFileMax ) );
        }
    }

    private static void initializeInitialDownloadFrequencyForGoodFile(List<Integer> fileId, Map<Integer, Integer> freq) {

        for( int i = 0 ; i< fileId.size()* Constants.fractionOfGoodFile ; i++ ){

            freq.put( fileId.get(i), Util.getRandomNumberInRange( Constants.weightOfGoodFileMin, Constants.weightOfGoodFileMax ) );
        }
    }

    private static void reduceWeightOfGoodFiles( List<Integer> fileId, Map<Integer, Integer> freq ) {

        for( int i = 0 ; i< fileId.size()* Constants.fractionOfGoodFile ; i++ ){

            freq.put( fileId.get(i), freq.getOrDefault( fileId.get(i), 0 )/Constants.reductionRateOfWeightForBadUser );
        }
    }

    public static Map<Integer,Integer> simulateGoodUserDownload(Map<Integer, Integer> initialFreq) {

        WeightedRandomGenerator<Integer> weightedRandomGenerator = new WeightedRandomGenerator<Integer>( initialFreq );
        weightedRandomGenerator.initialize();

        Map<Integer,Integer> freq = new HashMap<>( initialFreq );

        for( int i = 0;  i < Constants.numberOfUser*Constants.fractionOfGoodUser; i++ ){

            for( int j = 0; j<Constants.downloadPerUser; j++ ){

                Integer fileId = weightedRandomGenerator.getRandomElement();
                freq.put( fileId, freq.getOrDefault( fileId, 0 ) + 1 );
            }
        }

        return  freq;
    }

    public static Map<Integer,Integer> simulateBadUserDownload( List<Integer> fileId, Map<Integer, Integer> initialFreq) {

        Map<Integer,Integer> initialFrequencyClone = new HashMap<>( initialFreq );

        reduceWeightOfGoodFiles( fileId, initialFrequencyClone );

        WeightedRandomGenerator<Integer> weightedRandomGenerator = new WeightedRandomGenerator<Integer>( initialFrequencyClone );
        weightedRandomGenerator.initialize();

        Map<Integer,Integer> freq = new HashMap<>( initialFreq );

        for( int i = (int)( Constants.numberOfUser*Constants.fractionOfGoodUser ); i < Constants.numberOfUser; i++ ){

            for( int j = 0; j<Constants.downloadPerUser; j++ ){

                Integer id = weightedRandomGenerator.getRandomElement();
                freq.put( id, freq.getOrDefault( id, 0 ) + 1 );
            }
        }

        return  freq;
    }
}
