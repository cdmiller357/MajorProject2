package com.example.majorproject2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Christopher Miller  Computer Science II 2022 Fall 13W ONL1
 * Major Project
 */

//Allow updating a rating for a brand without having to update every location.
public class TreeMapFilters {
    private static TreeMap<String,Integer> ratingsTreeMap; //String is brand in the GasStation class, Integer is the rating for that brand.
    private static TreeSet<String> is24Hours; //Is a TreeSet of all brands in the GasStation class that are open 24 hours.


    public static TreeMap getRatingsTreeMap() {
        return ratingsTreeMap;
    }

    public static TreeSet getIs24Hours() {
        return is24Hours;
    }

    //
    public static void readData() throws IOException {
        ratingsTreeMap = new TreeMap<>();
        //Load  Ratings data  into ArrayList
        ArrayList<String> savedTreeMaps  = HelperClass.getTxtFileAsList(new File("data2.txt"));
        while (!savedTreeMaps.isEmpty()){
            String treeMap = savedTreeMaps.remove(0);
            String strArray[] = treeMap.split(",");
            ratingsTreeMap.put(strArray[0],Integer.parseInt(strArray[1]));
        }

        is24Hours = new TreeSet<>();
        //Load  Hours data  into ArrayList
        ArrayList<String> savedTreeSets  = HelperClass.getTxtFileAsList(new File("data3.txt"));
        while (!savedTreeSets.isEmpty()){
            String treeSet = savedTreeSets.remove(0);
            is24Hours.add(treeSet);
        }
    }
}
