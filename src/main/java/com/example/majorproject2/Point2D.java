/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.majorproject2;
/**
 * @author Christopher Miller  Computer Science II 2022 Fall 13W ONL1
 * Major Project
 */

public class Point2D {
  private double x;
  private double y;
  
  Point2D(){
    this.x = 0;
    this.y = 0;
  }
  Point2D(double x, double y){
    this.x = x;
    this.y = y;
  }
  public void setX(double x) {
    this.x = x;    
  }
  public void setY(double y) {
    this.y = y;    
  }
  public double getX() {
    return x;    
  }
  public double getY() {
    return y;    
  }
  public double distance(Point2D p){
    double xdiffsqrd = Math.pow((this.x - p.getX()), 2); 
    double ydiffsqrd = Math.pow((this.y - p.getY()), 2);
    double dist1 = Math.sqrt(xdiffsqrd + ydiffsqrd);
    return dist1;
  }  
  public double distance(double x, double y){
    double xdiffsqrd = Math.pow((this.x - x), 2); 
    double ydiffsqrd = Math.pow((this.y - y), 2);
    double dist1 = Math.sqrt(xdiffsqrd + ydiffsqrd);
    return dist1;
  }  
  public double distance(Point2D p1, Point2D p2){
    double xdiffsqrd = Math.pow((p1.getX() - p2.getX()), 2); 
    double ydiffsqrd = Math.pow((p1.getY() - p2.getY()), 2);
    double dist1 = Math.sqrt(xdiffsqrd + ydiffsqrd);
    return dist1;
  }   
    public String outCoorinates(){
    return (x + "," + y);
    }
}
