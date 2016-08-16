package com.example.ilona.ilonarecorder;

import java.util.ArrayList;

//Class to calculate
class Statistics {
    private final ArrayList<Double> data;
    private final int size;

    public Statistics(ArrayList<Double> data) {
        this.data = data;
        size = data.size();
    }

    double getMean() {
        double sum = 0.0;
        for (double a : data)
            sum += a;
        return sum / size;
    }

    double getVariance() {
        double mean = getMean();
        double temp = 0;
        for (double a : data)
            temp += (a - mean) * (a - mean);
        return temp / size;
    }

    double getStdDev() {
        return Math.sqrt(getVariance());
    }
}

