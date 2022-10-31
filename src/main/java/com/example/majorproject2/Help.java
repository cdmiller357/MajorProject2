package com.example.majorproject2;
/**
 * @author Christopher Miller  Computer Science II 2022 Fall 13W ONL1
 * Major Project
 */

public class Help {

    //Used for static about() method only, should not be an instance.
    private Help(){}

    public static String about(){
        return "My Major Project is an app to help people find a list of the closest gas stations near them. I will loosely try to design classes with MVC design.    \n" +
                "\n" +
                "On the model side it will use a GasStation class which will have data fields for a point, x,y coordinates, inherited from a Point class. In early development it will also have a brand name and a field to denote if it is open 24 hours or not. A GasStationList class will contain an ArrayList and related methods. There will be a GenericQuickSort and companion Comparator class specific to sorting GasStations on a grid. There will be a TreeMapFilters class that will contain a TreeMap for the ratings filter and a Set for the open 24 Hours filter. Both of the filters will be brand specific not location specific for simplicity.  \n" +
                "\n" +
                "The class closest to representing a controller class will be called SortByClosest which will have the main algorithm which I believe will use an AVL Tree for deriving the list closest stations.  \n" +
                "  \n" +
                "The view portion of the MVC design will be the View class which will handle displaying output and handle user input.   \n" +
                "\n" +
                "This should get me mostly through Part 3 but I also expect to have to expand for later Parts of the project. ";
    }
}
