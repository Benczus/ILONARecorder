package com.example.ilona.ilonarecorder;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by bogdandy on 2017.02.10..
 */

public class RSSIServiceTest {
	final private static int QUEUE_WINDOW_SIZE = 5;



	@Test
	public void testQueueToArray() {
		Map<String, Double> filteredWifiRSSI=new HashMap<>();
		Queue<Map<String, Double>> rssiQueue = new ArrayBlockingQueue<>(QUEUE_WINDOW_SIZE);
		filteredWifiRSSI.put("IITAP1", 1.1);
		filteredWifiRSSI.put("IITAP2", 1.2);
		filteredWifiRSSI.put("IITAP3", 1.3);
		filteredWifiRSSI.put("IITAP4", 1.4);
		filteredWifiRSSI.put("IITAP5", 1.5);
		rssiQueue.add(filteredWifiRSSI);

		List<Map<String,Double>> rssiArray = new ArrayList<>();
		Object[] queueValues = rssiQueue.toArray();
		for(int i = 0; i < rssiQueue.size(); i++){
			rssiArray.add((Map<String,Double>)queueValues[i]);
			System.out.println(String.format("%d ---> %s",i,queueValues[i]));
		}
		//System.out.println(rssiArray);

		Map<String,Double> result = new HashMap<>();
		for (int i=0; i<rssiArray.size(); i++){
			result.putAll(rssiArray.get(i));

		}
		System.out.println(result);

		filteredWifiRSSI=new HashMap<>();
		filteredWifiRSSI.put("IITAP1", 2.1);
		filteredWifiRSSI.put("IITAP2", 2.2);
		filteredWifiRSSI.put("IITAP3", 2.3);
		filteredWifiRSSI.put("IITAP4", 2.4);
		filteredWifiRSSI.put("IITAP5", 2.5);
		rssiQueue.add(filteredWifiRSSI);
		filteredWifiRSSI=new HashMap<>();
		filteredWifiRSSI.put("IITAP1", 3.1);
		filteredWifiRSSI.put("IITAP2", 3.2);
		filteredWifiRSSI.put("IITAP3", 3.3);
		filteredWifiRSSI.put("IITAP4", 3.4);
		filteredWifiRSSI.put("IITAP5", 3.5);
		rssiQueue.add(filteredWifiRSSI);
		filteredWifiRSSI=new HashMap<>();
		filteredWifiRSSI.put("IITAP1", 4.1);
		filteredWifiRSSI.put("IITAP2", 4.2);
		filteredWifiRSSI.put("IITAP3", 4.3);
		filteredWifiRSSI.put("IITAP4", 4.4);
		filteredWifiRSSI.put("IITAP5", 4.5);
		rssiQueue.add(filteredWifiRSSI);
		filteredWifiRSSI=new HashMap<>();
		filteredWifiRSSI.put("IITAP1", 5.1);
		filteredWifiRSSI.put("IITAP2", 5.2);
		filteredWifiRSSI.put("IITAP3", 5.3);
		filteredWifiRSSI.put("IITAP4", 5.4);
		filteredWifiRSSI.put("IITAP5", 5.5);
		rssiQueue.add(filteredWifiRSSI);

		rssiArray = new ArrayList<>();
		 queueValues = rssiQueue.toArray();
		for(int i = 0; i < rssiQueue.size(); i++){
				rssiArray.add((Map<String,Double>)queueValues[i]);
				System.out.println(String.format("%d ---> %s",i,queueValues[i]));
		}
		//System.out.println(rssiArray);

		result = new HashMap<>();
		for (int i=0; i<rssiArray.size(); i++){
			result.putAll(rssiArray.get(i));

		}
		System.out.println(result);
		//System.out.println(((HashMap<String,Double>)rssiQueue.toArray()[0]).keySet());
	}
}
