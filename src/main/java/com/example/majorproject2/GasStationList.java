package com.example.majorproject2;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
        GenericQuickSort.quickSort(list,  new GasStationComparator());
    }

    public static void readData() throws IOException {
        list = new ArrayList<>();
        //Load  GasStation data  into ArrayList
        ArrayList<String> savedGasStations  = HelperClass.getTxtFileAsList(new File("data1.txt"));
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
