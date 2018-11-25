package sim;

import java.util.Random;

public class Config {

    private static Random random;

    private Config(){}

    public static synchronized Random getRandom(){

        if( random == null ){

            synchronized (Config.class) {

                return new Random();
            }
        }
        else
            return new Random();
    }
}
