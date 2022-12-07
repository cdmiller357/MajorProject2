package com.example.majorproject2;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Christopher Miller  Computer Science II 2022 Fall 13W ONL1
 * Major Project
 */

//Utility class with static methods only,
public class SortByClosest {

    //Controller Class(sort of), should not be an instance
    private SortByClosest(){}

    /* Part 2 -- Added the code that sorts locations by closest to current location which uses the generic quick sort
    *  with a GasStationComparatorDistance comparator. Uses conditional logic to limit points returned to a 200X200 pixel box.
    *  Also uses TreeMapFilter class TreeSet and TreeMap in conditional logic to apply the filters provided by the end user.
    *  Returns a deep copy of GasStation locations in a 200X200 pixel box with current location in the middle of the box.
    *
    *  Time complexity is O(n) to get a list of points in fall in the 200x200 box and O(nlogn) average complexity(quick sort)
    *  to sort by distance to current location(O(n^2) worst case).*/
    public static ArrayList<GasStation> sortByClosest(Point2D current) throws IOException {
        ArrayList<GasStation> list = new ArrayList<>();
        list.add(new GasStation(current, "Current Location")); //Special "Current Location" zero index entry on list.
        ArrayList<GasStation> list2 = GasStationList.getListDeepCopy();
        for (int i=0; i < list2.size() && list2.get(i).getPoint().getY() < current.getY()+100; i++){
            if(list2.get(i).getPoint().getY() > current.getY()-100 &&
               list2.get(i).getPoint().getX() > current.getX()-100 &&
               list2.get(i).getPoint().getX() < current.getX()+100){
                    list.add(list2.get(i));
                    list.get(list.size()-1).setDistanceFromCurrentLocation(list.get(list.size()-1).getPoint().distance(current));
            }
        }
        GenericQuickSort.quickSort(list,  new GasStationComparatorDistance());
        return list;
    }


    /* Used when program loads before any control parameters can be gathered from end user,
     * will show all points in data file/table on map at launch.*/
    public static ArrayList<GasStation> sortByClosest() throws IOException {
        return GasStationList.getListDeepCopy();
    }
}
