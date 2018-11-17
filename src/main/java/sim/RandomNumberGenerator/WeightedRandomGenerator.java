package sim.RandomNumberGenerator;

import java.util.Arrays;
import java.util.Random;

public class WeightedRandomGenerator <T>
{
    private T[] items;
    private int[] weights;
    private int[] cumulativeWeights;
    private Random random;

    /**
     * @param items
     * @param weights
     */
    public WeightedRandomGenerator( T[] items, int[] weights )
    {
        super();
        this.items = items;
        this.weights = weights;
    }

    /**
     * initialize cumulativeWeights
     */
    public void initialize()
    {
        random = new Random();

        int sumTillNow = 0;
        cumulativeWeights = new int[weights.length];
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
    public T getRandomElement()
    {
        // Generate random sample between 1 to sum(weights).
        // sum(weights) is available in cumulativeWeights[cumulativeWeights.length-1]
        int rand = random.nextInt(cumulativeWeights[cumulativeWeights.length-1]);
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
