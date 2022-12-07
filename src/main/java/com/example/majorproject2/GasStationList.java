package com.example.majorproject2;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Christopher Miller  Computer Science II 2022 Fall 13W ONL1
 * Major Project
 */

/* GasStationList reads the data and is also a  copy of the all the gas stations in memory, instead of
 * reading from disk multiple times.*/
public class GasStationList {
    private static ArrayList<GasStation> list;


    private GasStationList(){};

    //Part 2 -- Added code for deep copy of the list
    public static ArrayList<GasStation> getListDeepCopy(){
        ArrayList<GasStation> deepCopy = new ArrayList<GasStation>();
        for(GasStation gasStation : list)
            deepCopy.add(gasStation.clone());
        return deepCopy;
    }

    //Sorts by y coordinates top to bottom
    public static void sortVertically(){
        ParallelGenericQuickSort.parallelGenericQuickSort(list,  new GasStationComparator());
    }


    /* Assignment 3: Add AVLTree and HashTable requirement
     *  -Locates a county data set by searching a list of all counties stored in an AVLTree/HashTable.
     *  NOTE: The AVLTree/HashTable provide duplicate functionality but the values returned from both is the same so
     *  the below readData() method is not affected by using one or the other.
     *  -The below readData() method is used to locate the searched county's data set.
     *
     *  Note: If this were a web app you would want this class to be an instance not static but static works fine for a desktop app as
     *  only one county will be used at a time.
     */
     public static void readData(TreeMapFilters.KeyValuePair kv) throws IOException {
        list = new ArrayList<>();
        //Load  GasStation data  into ArrayList
        ArrayList<String> savedGasStations  = HelperClass.getTxtFileAsList(new File(kv.value));
        while (!savedGasStations.isEmpty()){
            String gasStation = savedGasStations.remove(0);
            String strArray[] = gasStation.split(",");
            list.add(new GasStation(
                    Integer.parseInt(strArray[0]),          //x value
                    Integer.parseInt(strArray[1]),          //y value
                    strArray[2]                            //Brand
            ));
        }
        sortVertically();
    }
}
