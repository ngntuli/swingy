package swingy.utils;

public class Formula {

    public static int sizeMap(int level) {
        int size;
        size = ((((level - 1) * 5) + 10) - (level % 2));
        return (size);
    }

    public static int getXP(int level) {
        double xP;
        xP = level * 1000 + Math.pow(((double) level) - 1, 2) * 450;
        return (int) xP;
    }


}
