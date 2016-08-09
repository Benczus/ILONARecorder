package com.example.ilona.ilonarecorder;

/**
 * Created by ilona on 2016.08.09..
 */
public class HorusFilter implements FilterInterface {

    private int value;
    private int memsize;
    private int[] rssivalues;
    private int[] results;

    public HorusFilter(int[] rssivalues, int memsize) {
        this.rssivalues = rssivalues;
        this.memsize = memsize;
    }
    //takes the last 5 values,
    //TODO check if it is after the first 5 values.

    public static double mean(int[] m) {
        double sum = 0;
        for (int i = 0; i < m.length; i++) {
            sum += m[i];
        }
        return sum / m.length;
    }

    @Override
    public int filteringmethod() {
        for (int i = 0; i <= memsize; i++) {
            results[i] = rssivalues[memsize + i];
        }
        value = (int) mean(results);
        return value;
    }
}

