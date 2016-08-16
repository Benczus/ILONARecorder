package com.example.ilona.ilonarecorder;

import java.util.LinkedList;
import java.util.Map;


interface WiFiRSSIFilteringStrategy {

    Map<String, Double> filteringMethod(LinkedList<Map<String, Double>> linkedList);

}
