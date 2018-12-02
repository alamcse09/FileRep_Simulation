package sim.Engine;

import org.apache.commons.math3.distribution.ParetoDistribution;
import org.apache.commons.math3.util.Pair;
import sim.Constants;
import sim.RandomNumberGenerator.WeightedRandomGenerator;
import sim.util.Util;

import java.util.*;

public class SimulationEngine {

    public static Map<Integer,Integer> initializeInitialDownloadFrequency(List<Integer> fileId, ArrayList<Integer> goodFileIds){

        Map<Integer,Integer> freq = new HashMap<>();

        initializeInitialDownloadFrequencyForGoodFile( fileId, freq, goodFileIds );
        initializeInitialDownloadFrequencyForRegularFIle( fileId,freq, goodFileIds );

        return freq;
    }

    public static Map<Integer,Double> initializeInitialScore(List<Integer> fileId) {

        Map<Integer,Double> freq = new HashMap<>();

        for( Integer id: fileId )
            freq.put( id, 0.5 );

        return freq;
    }

    private static void initializeInitialDownloadFrequencyForRegularFIle(List<Integer> fileId, Map<Integer, Integer> freq, ArrayList<Integer> goodFileIds) {

        for( int i = 0; i< fileId.size(); i++ ){

            if( !goodFileIds.contains( i ) )
                freq.put( fileId.get(i), Util.getRandomNumberInRange( Constants.weightOfBadFileMin, Constants.weightOfBadFileMax ) );
        }
    }

    private static void initializeInitialDownloadFrequencyForGoodFile(List<Integer> fileId, Map<Integer, Integer> freq, ArrayList<Integer> goodFileIds) {

        for( Integer id: goodFileIds ){

            freq.put( id, Util.getRandomNumberInRange( Constants.weightOfGoodFileMin, Constants.weightOfGoodFileMax ) );
        }
    }

    private static void reduceWeightOfGoodFiles( List<Integer> fileId, Map<Integer, Integer> freq ) {

        for( int i = 0 ; i< fileId.size()* Constants.fractionOfGoodFile ; i++ ){

            freq.put( fileId.get(i), freq.getOrDefault( fileId.get(i), 0 )/Constants.reductionRateOfWeightForBadUser );
        }
    }

    public static Map<Integer,Integer> simulateGoodUserDownload(Map<Integer, Integer> initialFreq, ArrayList<Integer> goodFileIds) {

        List<Integer> fileIds = new ArrayList<>();
        Map<Integer,Integer> freq = new HashMap<>();

        for( int i = 0;  i < Constants.numberOfUser*Constants.fractionOfGoodUser; i++ ){

            Collections.shuffle( goodFileIds );
            for( int j = 0, index=0; j<Constants.downloadPerUser && index<goodFileIds.size(); j++, index++ ){

                freq.put( goodFileIds.get(index) , freq.getOrDefault( goodFileIds.get(index), 0 ) + 1 );
            }
        }

        return  freq;
    }

    public static Map<Integer,Double> simulateDownloadByGoodUserByScore(Map<Integer, Double> initialScore, ArrayList<Integer> goodFileIds, List<Integer> goodUserId) {

        Map<Integer, Double> userScoreMap = new HashMap<>();

        
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

    public static Map<Integer,Integer> simulateDownloadByBadUser( List<Integer> fileId, Map<Integer,Integer> initialFreq ){

        Map<Integer,Integer> freqAfterDownloadedByBadUser = new HashMap<>( );

        List<Pair<Integer,Integer>> fileIdFreqPairList = new ArrayList<>();

        for( Integer id: fileId ){

            fileIdFreqPairList.add( new Pair( id, initialFreq.get( id ) ) );
        }

        Collections.sort(fileIdFreqPairList, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                return -o1.getSecond().compareTo( o2.getSecond() );
            }
        });

        ParetoDistribution pd = new ParetoDistribution( 1, 1.16 );

        for( int i = (int)( Constants.numberOfUser*Constants.fractionOfGoodUser ); i < Constants.numberOfUser; i++ ){

            Set<Integer> selectedFiles = new HashSet<>();

            for( int j = 0; j<Constants.downloadPerUser; j++ ) {

                while (true) {
                    Double sample = pd.sample();
                    Integer chosenRank = sample.intValue() % Constants.numberOfFile;

                    if( fileId.contains(chosenRank) ) {
                        Pair<Integer, Integer> fileIdFreqPair = fileIdFreqPairList.get(chosenRank);

                        if( selectedFiles.contains( fileIdFreqPair.getFirst() ) )
                            continue;

                        freqAfterDownloadedByBadUser.put(fileIdFreqPair.getFirst(), freqAfterDownloadedByBadUser.getOrDefault(fileIdFreqPair.getFirst(), 0) + 1);
                        break;
                    }
                }
            }
        }

        return freqAfterDownloadedByBadUser;
    }

    public static ArrayList<Integer> getGoodFileIds(List<Integer> fileId) {

        Set<Integer> goodFiles = new HashSet<>();

        while( goodFiles.size() < Constants.numberOfFile * Constants.fractionOfGoodFile ){

            int randomId = Util.getRandomNumberInRange( 0, fileId.size() );

            if( !goodFiles.contains( randomId ) )
                goodFiles.add( randomId );
        }

        return new ArrayList<Integer>(goodFiles);
    }

    public static List<Integer> getGoodUserId() {

        Set<Integer> goodUserId = new HashSet<>();

        while( goodUserId.size() < Constants.numberOfUser*Constants.fractionOfGoodUser ){

            int randomId = Util.getRandomNumberInRange( 0, Constants.numberOfUser );

            if( !goodUserId.contains( randomId ) )
                goodUserId.add( randomId );
        }

        return new ArrayList<>( goodUserId );
    }
}
