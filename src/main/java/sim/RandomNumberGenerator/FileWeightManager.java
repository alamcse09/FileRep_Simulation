package sim.RandomNumberGenerator;

import org.apache.commons.math3.distribution.ParetoDistribution;
import sim.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileWeightManager {

    private static ParetoDistribution pd = new ParetoDistribution( 1, 0.001 );

    public static Map<Integer,Integer> initializeFileWeights( List<Integer> fileIds ){

        Map<Integer,Integer> freq = new HashMap<>();

        for( int i = 0; i<fileIds.size(); i++ ){

            Double sample = pd.sample();

            Double projectedValue = ( (Constants.numberOfFile*sample)/Long.MAX_VALUE );

            Long roundedValue = Math.round( projectedValue );

            freq.put( roundedValue.intValue() , freq.getOrDefault( roundedValue.intValue(), 0 ) + 1 );
        }
        System.out.println( freq );

        return freq;
    }

}
