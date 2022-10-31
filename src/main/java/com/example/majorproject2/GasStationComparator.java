package com.example.majorproject2;

import java.io.Serializable;
import java.util.Comparator;

public class GasStationComparator implements Comparator<GasStation>, Serializable {

    //Sorts top to bottom, left to right.
    public int compare(GasStation gasStation1, GasStation gasStation2){
        if (gasStation1.getPoint().getY() == gasStation2.getPoint().getY()){
            if(gasStation1.getPoint().getX() < gasStation2.getPoint().getX()){
                return -1;
            }
            else if (gasStation1.getPoint().getX() > gasStation2.getPoint().getX()) {
                return 1;
            }
            else {
                return 0;
            }
        }
        else if(gasStation1.getPoint().getY() < gasStation2.getPoint().getY()){
            return -1;
        }
        else if (gasStation1.getPoint().getY() > gasStation2.getPoint().getY()) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
