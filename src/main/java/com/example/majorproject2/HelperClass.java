package com.example.majorproject2;
/**
 * @author Christopher Miller  Computer Science II 2022 Fall 13W ONL1
 * Major Project
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import java.util.Scanner;

public class HelperClass {

    //Used for static helper methods  only, should not be an instance.
    private HelperClass(){}


    //Reads text file into an ArrayList
    public static ArrayList getTxtFileAsList(File file) throws IOException {
        ArrayList<String> list  = new ArrayList<>();
        if(file.exists()){
            Scanner fileScan = new Scanner(file);
            while(fileScan.hasNextLine()){
                list.add(fileScan.nextLine());
            }
            fileScan.close();
        }
        return list;
    }


    //Gets file and puts each line into an ArrayList and then writes existing and new data to the file.
    public static void writeLineToTxtFile (File file, String str) throws IOException {
        ArrayList<String> list  = new ArrayList<>();
        list = getTxtFileAsList(file);
        try (PrintWriter output = new PrintWriter(file);) {
            for (String s: list){
                output.println(s);
            }
            output.println(str);
        }
    }


    //Returns int x to int y if valid, -1 if invalid
    public static <E extends Number> int validatePositiveIntRange(String s, int x, int y) {
        Scanner input = new Scanner(s);
        if (input.hasNextInt()){
            int num = input.nextInt();
            if (num >= x && num <= y) {
                if (!input.hasNext()) {
                    return num;
                }
                else {
                    return -1;
                }
            }
            else {
                return -1;
            }
        }
        else {
            return -1;
        }
    }


    //Returns double x to int y if valid, -1 if invalid
    public static double validateDoubleRange(String s, int x, int y) {
        Scanner input = new Scanner(s);
        if (input.hasNextDouble()){
            double num = input.nextDouble();
            if (num >= x && num <= y) {
                if (!input.hasNext()) {
                    return num;
                }
                else {
                    return -1;
                }
            }
            else {
                return -1;
            }
        }
        else {
            return -1;
        }
    }


    //Takes a double and returns it as a String formatted to USD.
    public static String displayCurrency(Double amount) {
        NumberFormat currencyFormatter =  NumberFormat.getCurrencyInstance();
        currencyFormatter.setCurrency(Currency.getInstance(Locale.US));
        return currencyFormatter.format(amount);
    }
}
