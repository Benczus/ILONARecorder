package com.example.ilona.ilonarecorder;

import java.util.LinkedList;
import java.util.Map;


interface WiFiRSSIFilteringStrategy {
    int RSSIlevel = 0;

    Map<String, Double> filteringmethod(LinkedList<Map<String, Double>> linkedList);

}
