package com.example.ilona.ilonarecorder;

import java.util.ArrayList;

/**
 * Created by ilona on 2016.08.11..
 */

//Class to calculate
public class Statistics {
    ArrayList<Double> data;
    int size;

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

