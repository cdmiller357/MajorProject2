package com.example.majorproject2;


/**
 * @author Christopher Miller  Computer Science II 2022 Fall 13W ONL1
 * Major Project
 */

public class GasStation<T> implements Comparable<T>{
    private Point2D point;
    private String brand;
    private boolean is24Hours;

    public GasStation(){
        this.brand = "Data not received";
    }
    public GasStation(Point2D point, String brand, boolean is24Hours) {
        this.point = point;
        this.brand = brand;
        this.is24Hours = is24Hours;
    }
    public GasStation(int x, int y, String brand, boolean is24Hours) {
        this.point = new Point2D(x,y);
        this.brand = brand;
        this.is24Hours = is24Hours;
    }



    @Override
    public int compareTo(T o) {
        return 0;
    }

    public Point2D getPoint() {
        return point;
    }

    public void setPoint(Point2D point) {
        this.point = point;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isIs24Hours() {
        return is24Hours;
    }

    public void setIs24Hours(boolean is24Hours) {
        this.is24Hours = is24Hours;
    }


}
