package com.example.majorproject2;


/**
 * @author Christopher Miller  Computer Science II 2022 Fall 13W ONL1
 * Major Project
 */

//NOTE: Starting in Part 2 GasStation class can just be any location not just a gas station
//Part 2: Add data fields distanceFromCurrentLocation, isMarked, numbered. Removed is24Hours data field.
public class GasStation {
    private Point2D point; //location x,y
    private String brand; //name for location/brand name
    private double distanceFromCurrentLocation; //distance from current location returned from Point2D method.
    private boolean isMarked = false; //This is for the toggle buttons in the left pane results.
    private int numbered; //This is for the number next to the point in the center pane "Map"(The big olive square).

    public GasStation(){
        this.brand = "Data not received";
    }

    public GasStation(Point2D point, String brand) {
        this.point = point;
        this.brand = brand;
    }
    public GasStation(int x, int y, String brand) {
        this.point = new Point2D(x,y);
        this.brand = brand;
    }

    //Part 2 -- Added clone() method for deep copy
    public GasStation clone(){
        GasStation gasStation = new GasStation();
        gasStation.point = this.point.clone();
        gasStation.brand = this.brand;
        return gasStation;
    }

    public Point2D getPoint() {
        return point;
    }

    public String getBrand() {
        return brand;
    }

    public double getDistanceFromCurrentLocation() {
        return distanceFromCurrentLocation;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public int getNumbered() {
        return numbered;
    }

    public void setPoint(Point2D point) {
        this.point = point;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setDistanceFromCurrentLocation(double distanceFromCurrentLocation) {
        this.distanceFromCurrentLocation = distanceFromCurrentLocation;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public void setNumbered(int numbered) {
        this.numbered = numbered;
    }
}
