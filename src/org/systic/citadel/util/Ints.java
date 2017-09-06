package org.systic.citadel.util;

public class Ints {

    public static int roundUpToNearest(int base, int round){
        return (base + (round - 1)) / round * round;
    }

}
