package sim.util;

import sim.Config;

import java.util.*;

import static java.util.Collections.reverseOrder;

public class Util {

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {

        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort( reverseOrder ( Map.Entry.comparingByValue() ) );

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = Config.getRandom();
        return r.nextInt((max - min) + 1) + min;
    }
}
