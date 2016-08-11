package com.example.ilona.ilonarecorder;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by ilona on 2016.08.11..
 */
public class HorusFilterTest {

    private static final int MEMORY_SIZE = 3;
    private FilterInterface filter;

    @Before
    public void setUp() {
        filter = new HorusFilter(MEMORY_SIZE);
    }

    @Test
    public void testWithOneMeasurement() {
        LinkedList<Map<String, Double>> measurements = new LinkedList<Map<String, Double>>();

        Map<String, Double> meas1 = new HashMap<String, Double>();
        meas1.put("AP1", Double.valueOf(-20));
        meas1.put("AP2", Double.valueOf(-40));
        meas1.put("AP3", Double.valueOf(-60));

        measurements.push(meas1);

        Map<String, Double> expected = meas1;
        Map<String, Double> actual = filter.filteringmethod(measurements);

//        System.out.println(expected);
//        System.out.println(actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testWithLessMeasurementsThanMemorySize() {
        LinkedList<Map<String, Double>> measurements = new LinkedList<Map<String, Double>>();

        Map<String, Double> meas1 = new HashMap<String, Double>();
        meas1.put("AP1", Double.valueOf(-20));
        meas1.put("AP2", Double.valueOf(-40));
        meas1.put("AP3", Double.valueOf(-60));

        Map<String, Double> meas2 = new HashMap<String, Double>();
        meas2.put("AP1", Double.valueOf(-21));
        meas2.put("AP2", Double.valueOf(-41));
        meas2.put("AP3", Double.valueOf(-61));

        measurements.push(meas1);
        measurements.push(meas2);

        Map<String, Double> expected = meas2;
        Map<String, Double> actual = filter.filteringmethod(measurements);

//        System.out.println(expected);
//        System.out.println(actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testWhenMeasurementsCountEqualsMemorySize() {
        LinkedList<Map<String, Double>> measurements = new LinkedList<Map<String, Double>>();

        Map<String, Double> meas1 = new HashMap<String, Double>();
        meas1.put("AP1", Double.valueOf(-20));
        meas1.put("AP2", Double.valueOf(-40));
        meas1.put("AP3", Double.valueOf(-60));

        Map<String, Double> meas2 = new HashMap<String, Double>();
        meas2.put("AP1", Double.valueOf(-21));
        meas2.put("AP2", Double.valueOf(-41));
        meas2.put("AP3", Double.valueOf(-61));

        Map<String, Double> meas3 = new HashMap<String, Double>();
        meas3.put("AP1", Double.valueOf(-22));
        meas3.put("AP2", Double.valueOf(-42));
        meas3.put("AP3", Double.valueOf(-62));

        measurements.push(meas1);
        measurements.push(meas2);
        measurements.push(meas3);

        //Calculated and set by us
        Map<String, Double> expected = new HashMap<String, Double>();
        expected.put("AP1", Double.valueOf(-21));
        expected.put("AP2", Double.valueOf(-41));
        expected.put("AP3", Double.valueOf(-61));

        Map<String, Double> actual = filter.filteringmethod(measurements);

//        System.out.println("Expected ==> "+ expected);
//        System.out.println("Actual ====> "+actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testWithMoreMeasurementsThanMemorySize() {
        LinkedList<Map<String, Double>> measurements = new LinkedList<Map<String, Double>>();

        Map<String, Double> meas1 = new HashMap<String, Double>();
        meas1.put("AP1", Double.valueOf(-20));
        meas1.put("AP2", Double.valueOf(-40));
        meas1.put("AP3", Double.valueOf(-60));

        Map<String, Double> meas2 = new HashMap<String, Double>();
        meas2.put("AP1", Double.valueOf(-21));
        meas2.put("AP2", Double.valueOf(-41));
        meas2.put("AP3", Double.valueOf(-61));

        Map<String, Double> meas3 = new HashMap<String, Double>();
        meas3.put("AP1", Double.valueOf(-22));
        meas3.put("AP2", Double.valueOf(-42));
        meas3.put("AP3", Double.valueOf(-62));

        Map<String, Double> meas4 = new HashMap<String, Double>();
        meas4.put("AP1", Double.valueOf(-23));
        meas4.put("AP2", Double.valueOf(-43));
        meas4.put("AP3", Double.valueOf(-63));

        measurements.push(meas1);
        measurements.push(meas2);
        measurements.push(meas3);
        measurements.push(meas4);

        //Calculated and set by us
        Map<String, Double> expected = new HashMap<String, Double>();
        expected.put("AP1", Double.valueOf(-22));
        expected.put("AP2", Double.valueOf(-42));
        expected.put("AP3", Double.valueOf(-62));

        Map<String, Double> actual = filter.filteringmethod(measurements);

//        System.out.println("Expected ==> "+ expected);
//        System.out.println("Actual ====> "+actual);

        assertEquals(expected, actual);
    }

    @Test
    public void testWithMissingAPValues() {
        LinkedList<Map<String, Double>> measurements = new LinkedList<Map<String, Double>>();

        Map<String, Double> meas1 = new HashMap<String, Double>();
//        meas1.put("AP1", Double.valueOf(-20));
//        meas1.put("AP2", Double.valueOf(-40));

        Map<String, Double> meas2 = new HashMap<String, Double>();
//        meas2.put("AP1", Double.valueOf(-21));
//        meas2.put("AP2", Double.valueOf(-41));
        meas2.put("AP3", Double.valueOf(-61));

        Map<String, Double> meas3 = new HashMap<String, Double>();
//        meas3.put("AP1", Double.valueOf(-22));
//        meas3.put("AP2", Double.valueOf(-42));
        meas3.put("AP3", Double.valueOf(-62));

        measurements.push(meas1);
        measurements.push(meas2);
        measurements.push(meas3);

        //Calculated and set by us
        Map<String, Double> expected = new HashMap<String, Double>();
//        expected.put("AP1", Double.valueOf(-21));
//        expected.put("AP2", Double.valueOf(-41));
        expected.put("AP3", Double.valueOf(-61.5));

        Map<String, Double> actual = filter.filteringmethod(measurements);


        assertEquals(expected, actual);
    }
}
