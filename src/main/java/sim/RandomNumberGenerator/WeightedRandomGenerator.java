package sim.RandomNumberGenerator;

import sim.Config;

import java.util.*;

public class WeightedRandomGenerator <T>
{
    private Integer[] items;
    private Integer[] weights;
    private Integer[] cumulativeWeights;

    private Random random;

    private Map<Integer,Integer> itemWeightMap;
    private List<Integer> cumulativeWeightsList;

    /**
     * @param items
     * @param weights
     */
    public WeightedRandomGenerator( Integer[] items, Integer[] weights )
    {
        super();
        this.items = items;
        this.weights = weights;
    }

    public WeightedRandomGenerator( Map<Integer,Integer> freqMap ){

        super();
        this.itemWeightMap = freqMap;
    }

    /**
     * initialize cumulativeWeights
     */
    public void initialize()
    {
        random = Config.getRandom();

        if( itemWeightMap != null )
            initializeMap();

        initializeArray();

    }

    private void initializeMap(){

        int i =0;
        items = new Integer[ itemWeightMap.keySet().size() ];
        weights = new Integer[ itemWeightMap.keySet().size() ];

        for( Integer key: itemWeightMap.keySet() ){

            items[i] = key;
            weights[i] = itemWeightMap.get( key );
            i++;
        }
    }

    private void initializeArray(){

        int sumTillNow = 0;
        cumulativeWeights = new Integer[weights.length];
        for (int i = 0; i < weights.length; i++) {
            sumTillNow += weights[i];
            cumulativeWeights[i] = sumTillNow;
        }
    }

    /**
     * Generates random index honoring the given weights
     * and returns corresponding item from the list.
     * @return
     */
    public Integer getRandomElement()
    {

       return getRandomElementFromArray();
    }

    private Integer getRandomElementFromArray() {

        // Generate random sample between 1 to sum(weights).
        // sum(weights) is available in cumulativeWeights[cumulativeWeights.length-1]
        int rand = random.nextInt( cumulativeWeights[cumulativeWeights.length-1]);
        int sample = rand + 1;
        //Find out index for which cumulativeWeight is less than or equal to sample
        int index = Arrays.binarySearch( cumulativeWeights, sample );
        //If exact match not found then binarysearch returns -(insertion point) -1
        //convert it to insertion point
        if (index < 0) {
            index = -(index+1);
        }
        return items[index];
    }

}
