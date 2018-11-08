package tests;

import java.util.*;

public class ANNUtils {

    public static double generateRandom() {
        Random rand = new Random();
        int sign = 0;

        if (rand.nextInt(2) == 1)
            sign = 1;
        else
            sign = -1;

        return sign * rand.nextInt(4) + rand.nextDouble() * sign;

    }

    public static Map<List<Double>, List<Double>> getXORTrainingSet() {

        HashMap<List<Double>, List<Double>> inputOutputTrainingData = new HashMap<>();
        inputOutputTrainingData.put(Arrays.asList(0d, 0d), Arrays.asList(0d));
        inputOutputTrainingData.put(Arrays.asList(0d, 1d), Arrays.asList(1d));
        inputOutputTrainingData.put(Arrays.asList(1d, 0d), Arrays.asList(1d));
        inputOutputTrainingData.put(Arrays.asList(1d, 1d), Arrays.asList(0d));

        return inputOutputTrainingData;

    }

    public static Map<List<Double>, List<Double>> getANDTrainingSet() {

        HashMap<List<Double>, List<Double>> inputOutputTrainingData = new HashMap<>();
        inputOutputTrainingData.put(Arrays.asList(0d, 0d), Arrays.asList(0d));
        inputOutputTrainingData.put(Arrays.asList(0d, 1d), Arrays.asList(1d));
        inputOutputTrainingData.put(Arrays.asList(1d, 0d), Arrays.asList(1d));
        inputOutputTrainingData.put(Arrays.asList(1d, 1d), Arrays.asList(1d));

        return inputOutputTrainingData;

    }
}
