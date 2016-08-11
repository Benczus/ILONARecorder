//package com.example.ilona.ilonarecorder;
//
///**
// * Created by ilona on 2016.08.09..
// */
//public class StaticTimeWindowFilter implements FilterInterface {
//    private final double threshold;
//    private int value;
//    private int memsize;
//    private int[] rssivalues;
//    private int[] results;
//
//    public StaticTimeWindowFilter(int[] rssivalues, int memsize, double threshold) {
//        this.rssivalues = rssivalues;
//        this.threshold = threshold;
//        this.memsize = memsize;
//    }
//
//    public static double mean(int[] m) {
//        double sum = 0;
//        for (int i = 0; i < m.length; i++) {
//            sum += m[i];
//        }
//        return sum / m.length;
//    }
//
//    @Override
//    //takes the last 5 values,
//    //TODO check if it is after the first 5 values.
//
//    public double filteringmethod() {
//        if (Math.abs(rssivalues[0] - rssivalues[1]) > threshold) {
//            for (int i = 0; i <= memsize; i++) {
//                results[i] = rssivalues[memsize + i];
//            }
//            value = (int) mean(results);
//            return value;
//        }
//        return rssivalues[0];
//
//    }
//}
