package com.example.majorproject2;

import java.io.Serializable;
import java.util.Comparator;

public class GasStationComparatorDistance implements Comparator<GasStation>, Serializable {

    //Sorts from closest to furthest of the current location
    public int compare(GasStation gasStation1, GasStation gasStation2){
        if (gasStation1.getDistanceFromCurrentLocation() < gasStation2.getDistanceFromCurrentLocation()){
            return -1;
        }
        else if (gasStation1.getDistanceFromCurrentLocation() > gasStation2.getDistanceFromCurrentLocation()) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
