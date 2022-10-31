package com.example.majorproject2;


import javafx.scene.control.CheckBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Christopher Miller  Computer Science II 2022 Fall 13W ONL1
 * Major Project
 */

public class GasStationList {
    private static ArrayList<GasStation> list;


    private GasStationList(){};

    public static ArrayList<GasStation> getListDeepCopy(){
        //In Part 1 this just returns a shallow copy
        return list;
    }

    public static void sortVertically(){
        GenericQuickSort.quickSort(list,  new GasStationComparator());
    }

    public static void readData() throws IOException {
        list = new ArrayList<>();
        //Load  GasStation data  into ArrayList
        ArrayList<String> savedGasStations  = HelperClass.getTxtFileAsList(new File("data1.txt"));
        savedGasStations.addAll(HelperClass.getTxtFileAsList(new File("data2.txt")));
        while (!savedGasStations.isEmpty()){
            String gasStation = savedGasStations.remove(0);
            String strArray[] = gasStation.split(",");
            list.add(new GasStation(
                    Integer.parseInt(strArray[0]),          //x value
                    Integer.parseInt(strArray[1]),          //y value
                    strArray[2],                            //Brand
                    Boolean.parseBoolean(strArray[3])));    //is24Hours
        }
        sortVertically();
    }
}
