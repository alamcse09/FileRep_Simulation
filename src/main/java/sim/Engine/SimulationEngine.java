package sim.Engine;

import org.apache.commons.math3.distribution.ParetoDistribution;
import org.apache.commons.math3.util.Pair;
import sim.Constants;
import sim.RandomNumberGenerator.WeightedRandomGenerator;
import sim.util.Util;

import java.util.*;

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

    public static Map<Integer,Integer> simulateGoodUserDownload(Map<Integer, Integer> initialFreq, ArrayList<Integer> goodFileIds) {

        List<Integer> fileIds = new ArrayList<>( goodFileIds );
        Map<Integer,Integer> freq = new HashMap<>( initialFreq );

        for( int i = 0;  i < Constants.numberOfUser*Constants.fractionOfGoodUser; i++ ){

            Collections.shuffle( fileIds );
            for( int j = 0, index=0; j<Constants.downloadPerUser && index<fileIds.size(); j++, index++ ){

                freq.put( fileIds.get(index) , freq.getOrDefault( fileIds.get(index), 0 ) + 1 );
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

    public static Map<Integer,Integer> simulateDownloadByBadUser( List<Integer> fileId, Map<Integer,Integer> initialFreq ){

        Map<Integer,Integer> freqAfterDownloadedByBadUser = new HashMap<>( initialFreq );

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

            while( true ) {
                Double sample = pd.sample();
                Integer chosenRank = sample.intValue() % Constants.numberOfFile;

                if (fileId.contains(chosenRank)) {
                    Pair<Integer, Integer> fileIdFreqPair = fileIdFreqPairList.get(chosenRank);
                    freqAfterDownloadedByBadUser.put(fileIdFreqPair.getFirst(), freqAfterDownloadedByBadUser.getOrDefault(fileIdFreqPair.getFirst(), 0 ) + 1);
                    break;
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
}
