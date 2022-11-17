package com.example.majorproject2;
/**
 * @author Christopher Miller  Computer Science II 2022 Fall 13W ONL1
 * Major Project
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;

public class View extends Application {
    //Can update the border pane from different methods so instance variable
    private BorderPane borderPane;
    //Next 3 items are for stack functionality
    private ArrayList<GasStation> gasStationsArrayList; //Current Location and nearby points  displayed Currently
    private Stack<ArrayList<GasStation>> back = new Stack<>(); //Top of this stack is the first location behind the current location
    private Stack<ArrayList<GasStation>> forward  = new Stack<>(); //Top of this stack is the first map ahead of the current location
    //Can write to these text fields from different methods so instance variables
    private TextField tf0 = new TextField("X coordinate (50-750)");
    private TextField tf1 = new TextField("Y coordinate (50-750)");
    private TextField tf2 = new TextField("Messages appear here.");


    //Start: Right Pane - Control Buttons
    private VBox getRightPane() throws IOException {
        tf2.setEditable(false);
        tf2.setAlignment(Pos.BASELINE_RIGHT);
        tf2.setPrefColumnCount(20);
        Label lbl0 = new Label("Enter x and y coordinates of next \"town\".");
        lbl0.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));
        tf0.setAlignment(Pos.BASELINE_RIGHT);
        tf0.setPrefColumnCount(20);
        tf1.setAlignment(Pos.BASELINE_RIGHT);
        tf1.setPrefColumnCount(20);

        Label lbl1 = new Label("Filter by minimum average Rating");
        lbl1.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));
        RadioButton radioButton1 = new RadioButton("5");
        RadioButton radioButton2 = new RadioButton("4");
        RadioButton radioButton3 = new RadioButton("3");
        RadioButton radioButton4 = new RadioButton("2");
        RadioButton radioButton5 = new RadioButton("1");
        radioButton5.setSelected(true); //returns all
        ToggleGroup toggleGroup  = new ToggleGroup();
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);
        radioButton3.setToggleGroup(toggleGroup);
        radioButton4.setToggleGroup(toggleGroup);
        radioButton5.setToggleGroup(toggleGroup);
        HBox hBox = new HBox(radioButton1, radioButton2, radioButton3, radioButton4, radioButton5);
        hBox.setSpacing(20);
        Label lbl2 = new Label("Filter by Open 24 Hours");
        lbl2.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));
        RadioButton radioButton6 = new RadioButton("true");
        RadioButton radioButton7 = new RadioButton("false");
        radioButton7.setSelected(true); //returns all
        ToggleGroup toggleGroup2 = new ToggleGroup();
        radioButton6.setToggleGroup(toggleGroup2);
        radioButton7.setToggleGroup(toggleGroup2);
        HBox hBox2 = new HBox(radioButton6, radioButton7);
        hBox2.setSpacing(20);

        Button find = new Button("Find");
        find.setMinWidth(200);

        Button about = new Button("About");
        about.setMinWidth(200);
        VBox controls = new VBox();
        controls.setPadding(new Insets(50, 5, 5, 5));
        controls.getChildren().addAll(tf2, lbl0, tf0, tf1, lbl1, hBox,lbl2, hBox2, find, about);
        controls.setAlignment(Pos.CENTER_LEFT);
        controls.setSpacing(30);

        tf0.setOnMousePressed(e ->{ //Clears instructions/current coordinates out of x coordinate text field
            tf0.clear();
        });
        tf1.setOnMousePressed(e ->{ //Clears instructions/current coordinates out of y coordinate text field
            tf1.clear();
        });

        //Find is the main button for seeing locations near the current location.
        find.setOnAction  (e -> {
            Point2D currentLocation = new Point2D();
            currentLocation.setX(HelperClass.validatePositiveIntRange(tf0.getText(),50,750)); //Helper Class returns -1 if input is not valid
            currentLocation.setY(HelperClass.validatePositiveIntRange(tf1.getText(),50,750));

            RadioButton selectedRadioButton = (RadioButton)toggleGroup.getSelectedToggle();
            int ratingFilter = Integer.parseInt(selectedRadioButton.getText());

            RadioButton selectedRadioButton2 = (RadioButton)toggleGroup2.getSelectedToggle();
            boolean is24Hours = Boolean.parseBoolean(selectedRadioButton2.getText());

            if ( currentLocation.getX() == -1 ||  currentLocation.getY() == -1 ) { //Input out of range
                tf0.setText("X coordinate (50-750)");
                tf1.setText("Y coordinate (50-750)");
                Alert a = new Alert(Alert.AlertType.WARNING);
                String s =  "The first 2 fields can only be Integers that corresponds to a x/y coordinate on the map." +
                            "The x range is 50-750. " +
                            "The y range is 50-750" ;
                a.setContentText(s);
                a.show();
            }
            else { // else input is valid
                try {
                    if(gasStationsArrayList.get(0).isMarked()) { //Uses special "Current Location" zero index entry on list.
                        back.push(gasStationsArrayList);
                    }
                    gasStationsArrayList = SortByClosest.sortByClosest(currentLocation, ratingFilter, is24Hours);
                    ScrollPane sp = new ScrollPane(getLeftPane());
                    sp.setMinWidth(300);
                    borderPane.setLeft(sp);
                    borderPane.setCenter(getCenterPane());
                    tf2.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }); //End of code for find.setOnAction

        //Display Help.about() when About button clicked
        about.setOnAction(e -> {
            TextArea ta  = new TextArea(Help.about());
            ta.setWrapText(true);
            Stage aboutStage = new Stage();
            aboutStage.setTitle("About Trip Stops Planner");
            aboutStage.setScene(new Scene(ta,850,425));
            aboutStage.show();
        });
        return controls;
    } //End: Right Pane - Control Buttons


    private Pane getCenterPane()  {
        Pane pane = new Pane();
        Rectangle rectangle = new Rectangle(15,15,765,765);
        rectangle.setFill(Color.OLIVE);
        Point2D currentLocation = gasStationsArrayList.get(0).getPoint(); //Location entered into text fields or special off page data in data file for starting location.
        Circle circle1 = new Circle(currentLocation.getX(),currentLocation.getY(),4);
        circle1.setFill(Color.RED);
        Text text1 = new Text(currentLocation.getX()-3, currentLocation.getY()+15, "Current Location");
        tf0.setText("" + (int)currentLocation.getX());
        tf1.setText("" + (int)currentLocation.getY());
        pane.getChildren().addAll(rectangle, circle1,text1);
        //Loop to put current results on map.
        for(int i =1; i < gasStationsArrayList.size(); i++){
            GasStation gasStation= gasStationsArrayList.get(i);
            Circle circle2 = new Circle( gasStation.getPoint().getX(), gasStation.getPoint().getY(), 2);
            Text text2 = new Text(gasStation.getPoint().getX()-3, gasStation.getPoint().getY()+15, ""+gasStation.getNumbered());
            pane.getChildren().addAll(circle2, text2);
        }
        return pane;
    }


    //Start: Left Pane - Results
    private VBox getLeftPane() {
        VBox vBox = new VBox();
        Label lbl0 = new Label("Results:");
        lbl0.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 18));
        Label lbl1 = new Label("Click a result to add/remove that stop to your trip.");
        lbl1.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 9));
        Label lbl2 = new Label("Use the add/remove to Trip buttons to add each \"town\".");
        lbl2.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 9));
        Button backButton = new Button("Back");
        backButton.setMinWidth(75);
        backButton.setOnAction(e -> {
            if(!back.isEmpty()) {
                if(gasStationsArrayList.get(0).isMarked()) { //Uses special "Current Location" zero index entry on list.
                    forward.push(gasStationsArrayList);
                }
                gasStationsArrayList = back.pop();
                ScrollPane sp = new ScrollPane(getLeftPane());
                sp.setMinWidth(300);
                borderPane.setLeft(sp);
                borderPane.setCenter(getCenterPane());
                tf2.setText("");
            }
            else {
                tf2.setText("No Entry found - Beginning of Trip");
            }
        });
        Button forwardButton = new Button("Forward");
        forwardButton.setMinWidth(75);
        forwardButton.setOnAction(e -> {
            if(!forward.isEmpty()) {
                if(gasStationsArrayList.get(0).isMarked()) { //Uses special "Current Location" zero index entry on list.
                    back.push(gasStationsArrayList);
                }
                gasStationsArrayList = forward.pop();
                ScrollPane sp = new ScrollPane(getLeftPane());
                sp.setMinWidth(300);
                borderPane.setLeft(sp);
                borderPane.setCenter(getCenterPane());
                tf2.setText("");
            }
            else {
                tf2.setText("No Entry found - End of Trip");
            }
        });
        Button addToTrip = new Button("Add To Trip");
        addToTrip.setMinWidth(100);
        addToTrip.setOnAction(e -> {
            gasStationsArrayList.get(0).setMarked(true); //Uses special "Current Location" zero index entry on list.
            tf2.setText(gasStationsArrayList.get(0).getPoint().outCoorinates() +" added to Trip");
        });
        Button removeFromTrip = new Button("Remove From Trip");
        removeFromTrip.setMinWidth(100);
        removeFromTrip.setOnAction(e -> {
            gasStationsArrayList.get(0).setMarked(false);//Uses special "Current Location" zero index entry on list.
            tf2.setText(gasStationsArrayList.get(0).getPoint().outCoorinates() +" removed from Trip");
        });
        HBox hBox = new HBox();
        hBox.getChildren().addAll(backButton, forwardButton);
        hBox.setSpacing(20);
        HBox hBox2 = new HBox(addToTrip, removeFromTrip);
        hBox2.setSpacing(20);
        vBox.setPadding(new Insets(50, 10, 10, 10));
        vBox.getChildren().addAll(lbl0, lbl1, lbl2, hBox, hBox2);
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setSpacing(10);
        vBox.setMaxHeight(800);
        //For loop to add toggle button results.
        for(int i =1; i < gasStationsArrayList.size(); i++){
            int finalI = i;
            GasStation gasStation= gasStationsArrayList.get(i);
            gasStation.setNumbered(i);
            String str24 = (TreeMapFilters.getIs24Hours().contains(gasStation.getBrand()))? "  -  24h" : "";
            int stars = (int)TreeMapFilters.getRatingsTreeMap().get(gasStation.getBrand());
            String rating = (stars > 1)? stars + " stars" : stars + " star";
            ToggleButton tb = new ToggleButton(gasStation.getNumbered() + ":  " + gasStation.getBrand() + "  -  "
                    + rating +  str24 + "  -  " + Math.round(gasStation.getDistanceFromCurrentLocation())+"m");
            tb.setSelected(gasStationsArrayList.get(finalI).isMarked());
            tb.setMinWidth(250);
            tb.setAlignment(Pos.TOP_LEFT);
            tb.setOnAction(e -> {
                if (gasStationsArrayList.get(finalI).isMarked()) {
                    gasStationsArrayList.get(finalI).setMarked(false);
                }
                else {
                    gasStationsArrayList.get(finalI).setMarked(true);
                }
            });
            vBox.getChildren().addAll(tb);
            vBox.setAlignment(Pos.TOP_LEFT);
            vBox.setSpacing(10);
        }
        return vBox;
    } //End: Left Pane - Results


    private BorderPane getBorderPane() throws IOException {
        BorderPane borderPane = new BorderPane(); //main pane
        borderPane.setRight(getRightPane());
        ScrollPane sp = new ScrollPane(getLeftPane());
        sp.setMinWidth(300);
        borderPane.setLeft(sp);
        borderPane.setCenter(getCenterPane());
        return borderPane;
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        GasStationList.readData();
        gasStationsArrayList = SortByClosest.sortByClosest(); //loads all points at launch, just have it that way
        TreeMapFilters.readData();
        borderPane = getBorderPane();  //main pane
        Scene scene = new Scene(borderPane, 1200, 600);
        primaryStage.setTitle("Trip Stops Planner");
        primaryStage.setScene(scene);
        primaryStage.show();
        /* When getCenterPane() gets called it puts the current location in the x and y text fields which is
         * the data in the special 0 index of the gasStationArrayList
         * At program launch you want the instructions though so hence the next two lines:
         */
        tf0.setText("X coordinate (50-750)");
        tf1.setText("Y coordinate (50-750)");
    }


    public static void main(String[] args) {
        launch();
    }
}
