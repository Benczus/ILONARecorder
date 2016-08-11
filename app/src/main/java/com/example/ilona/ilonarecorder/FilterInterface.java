package com.example.ilona.ilonarecorder;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by ilona on 2016.08.09..
 */
interface FilterInterface {
    int RSSIlevel = 0;

    Map<String, Double> filteringmethod(LinkedList<Map<String, Double>> linkedList);

}
