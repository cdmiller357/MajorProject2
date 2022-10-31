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
import java.util.ArrayList;

public class View extends Application {
    private ArrayList<GasStation> gasStationsArrayList;
    private BorderPane borderPane;

    //Start: Right Pane - Control Buttons
    private VBox getRightPane() throws IOException {
        VBox controls = new VBox();
        Label lbl0 = new Label("Enter your location's x and y coordinates");
        lbl0.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14));
        TextField tf0 = new TextField("X coordinate (250-1000)");
        tf0.setAlignment(Pos.BASELINE_RIGHT);
        tf0.setPrefColumnCount(20);
        TextField tf1 = new TextField("Y coordinate (50-750)");
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
        find.setMaxWidth(400);

        Button about = new Button("About");
        about.setMaxWidth(400);

        controls.setPadding(new Insets(50, 5, 5, 5));
        controls.getChildren().addAll(lbl0, tf0, tf1, lbl1, hBox,lbl2, hBox2, find, about);
        controls.setAlignment(Pos.CENTER_LEFT);
        controls.setSpacing(30);

        tf0.setOnMousePressed(e ->{
            tf0.clear();
        });
        tf1.setOnMousePressed(e ->{
            tf1.clear();
        });

        find.setOnAction  (e -> {
            Point2D currentLocation = new Point2D();
            currentLocation.setX(HelperClass.validatePositiveIntRange(tf0.getText(),250,1000)); //Helper Class returns -1 if input is not valid
            currentLocation.setY(HelperClass.validatePositiveIntRange(tf1.getText(),50,750));

            RadioButton selectedRadioButton = (RadioButton)toggleGroup.getSelectedToggle();
            int ratingFilter = Integer.parseInt(selectedRadioButton.getText());

            RadioButton selectedRadioButton2 = (RadioButton)toggleGroup2.getSelectedToggle();
            boolean is24Hours = Boolean.parseBoolean(selectedRadioButton2.getText());

            if ( currentLocation.getX() == -1 ||  currentLocation.getY() == -1 ) {
                tf0.setText("X coordinate (250-1000)");
                tf1.setText("Y coordinate (50-750)");
                Alert a = new Alert(Alert.AlertType.WARNING);
                String s =  "The first 2 fields can only be Integers that corresponds to a x/y coordinate on the map." +
                            "The x range is 250-1000. " +
                            "The y range is 50-750" ;
                a.setContentText(s);
                a.show();
            }
            else { //Input is valid
                System.out.println(currentLocation.getX() + " " +  currentLocation.getY() + " "  +  ratingFilter + " " +  is24Hours + " DELETE");
                try {
                    GasStationList.readData(); //only needed since DeepCopy is STUB
                    gasStationsArrayList = SortByClosest.sortByClosest(currentLocation, ratingFilter, is24Hours);
                    borderPane.setCenter(getCenterPane(currentLocation));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //Display Help.about() when About button clicked
        about.setOnAction(e -> {
            TextArea ta  = new TextArea(Help.about());
            ta.setWrapText(true);
            Stage aboutStage = new Stage();
            aboutStage.setTitle("About Dashboard for Survey Data Collection");
            aboutStage.setScene(new Scene(ta,850,350));
            aboutStage.show();
        });
        return controls;
    } //End: Right Pane - Control Buttons

    private Pane getCenterPane(Point2D currentLocation) throws IOException {
        Pane pane = new Pane();
        Rectangle rectangle = new Rectangle(225,10,800,800);
        rectangle.setFill(Color.OLIVE);
        pane.getChildren().add(rectangle);
        int num = 1;
        while (!gasStationsArrayList.isEmpty()){
            GasStation gasStation= gasStationsArrayList.remove(0);
            Circle circle = new Circle( gasStation.getPoint().getX(), gasStation.getPoint().getY(), 2);
            Text text = new Text(gasStation.getPoint().getX()-3, gasStation.getPoint().getY()+15, ""+num++);
            pane.getChildren().addAll(circle, text);
        }
        Circle circle = new Circle(currentLocation.getX(),currentLocation.getY(),4);
        circle.setFill(Color.RED);
        Text text = new Text(currentLocation.getX()-3, currentLocation.getY()+15, "Current Location");
        pane.getChildren().addAll(circle, text);

        return pane;
    }

    private VBox getLeftPane() {
        VBox vBox = new VBox();
        Label lbl0 = new Label("Results:");
        lbl0.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 18));
        vBox.setPadding(new Insets(50, 20, 20, 20));
        vBox.getChildren().addAll(lbl0);
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setSpacing(30);
        return vBox;
    }
    private BorderPane getBorderPane() throws IOException {
        BorderPane borderPane = new BorderPane(); //main pane
        borderPane.setCenter(getCenterPane(new Point2D(-100,-100)));
        borderPane.setRight(getRightPane());
        borderPane.setLeft(getLeftPane());
        return borderPane;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        GasStationList.readData();
        gasStationsArrayList = SortByClosest.sortByClosest(); //Mainly for displaying sorted data in part 1
        TreeMapFilters.readData();
        borderPane = getBorderPane();  //main pane
        Scene scene = new Scene(borderPane, 1200, 600);
        primaryStage.setTitle("Gas Stations Near Me");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
