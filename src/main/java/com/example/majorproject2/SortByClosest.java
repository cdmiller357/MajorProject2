package com.example.majorproject2;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Christopher Miller  Computer Science II 2022 Fall 13W ONL1
 * Major Project
 */

//Utility class with static methods only
public class SortByClosest {
    public static GasStationList list;

    //Controller Class(sort of), should not be an instance
    private SortByClosest(){}

    public static ArrayList<GasStation> sortByClosest(Point2D point, int ratingFilter, boolean is24Hours) throws IOException {
        //STUB in Part 1, just returns a shallow copy of the GasStationList ArrayList.
        return GasStationList.getListDeepCopy();
    }

    //This overloaded method will be deleted later on. Just used to test GUI in Part 1.
    public static ArrayList<GasStation> sortByClosest() throws IOException {
        //STUB in Part 1, just returns a shallow copy of the GasStationList ArrayList.
        return GasStationList.getListDeepCopy();
    }
    private static void filterByRating(int stars){
        //STUB
    }
    private static void filterBy24Hours(boolean is24Hours){
        //STUB
    }
}
