package com.example.majorproject2;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Christopher Miller  Computer Science II 2022 Fall 13W ONL1
 * Major Project
 */

//Allow updating a rating for a brand without having to update every location.
//As of Assignment 3 this TreeMapFilters class is sort of a BST/HashTable Tools Helper class.
public class TreeMapFilters {
    private static TreeMap<String, Integer> ratingsTreeMap; //String is brand in the GasStation class, Integer is the rating for that brand.
    private static TreeSet<String> is24Hours; //Is a TreeSet of all brands in the GasStation class that are open 24 hours.
    private static AVLTree<KeyValuePair> counties;
    private static HashMapLinear<String, String> counties2;

    public static TreeMap getRatingsTreeMap() {
        return ratingsTreeMap;
    }

    public static TreeSet getIs24Hours() {
        return is24Hours;
    }

    public static AVLTree<KeyValuePair> getCountyDataLocationAVLTree(){
        return counties;
    }

    public static HashMapLinear<String,String> getCountyDataLocationHashTable(){
        return counties2;
    }

    public static void readData() throws IOException {
        ratingsTreeMap = new TreeMap<>();
        //Load  Ratings data  into ArrayList
        ArrayList<String> savedTreeMaps = HelperClass.getTxtFileAsList(new File("data2.txt"));
        while (!savedTreeMaps.isEmpty()) {
            String treeMap = savedTreeMaps.remove(0);
            String strArray[] = treeMap.split(",");
            ratingsTreeMap.put(strArray[0], Integer.parseInt(strArray[1]));
        }

        is24Hours = new TreeSet<>();
        //Load  Hours data  into ArrayList
        ArrayList<String> savedTreeSets = HelperClass.getTxtFileAsList(new File("data3.txt"));
        while (!savedTreeSets.isEmpty()) {
            String treeSet = savedTreeSets.remove(0);
            is24Hours.add(treeSet);
        }

        /* Assignment 3: Add AVLTree requirement. NOTE: This is duplicate functionality to the HashMapLinear HashTable
         *  The Below AVLTree instance and supporting KeyValuePair class and comparator was added to do the following:
         *  -Locates a county data set by searching a list of all counties stored in an AVLTree of key value pairs(of type KeyValuePair).
         *  -The AVLTree has a getAVLTreeNode() method to return the node, in this case a KeyValuePair object, not just
         *  a boolean return that tells you it exists.
         *  -Passes the value returned  to the GasStationList class's readData() method and is used to locate the searched county's data set.
         *
         *  Note: This app is set up as a Desktop app but if this were a web app you would still want this
         *  AVLTree<KeyValuePair> instance to be static.
         *
         *  As of Assignment 3 this TreeMapFilters class is sort of a BST/HashTable Tools Helper class.
         */
        counties = new AVLTree<>(new keyValuePairComparator());
        //Load  counties data  into ArrayList
        ArrayList<String> savedCounties = HelperClass.getTxtFileAsList(new File("data4.txt"));
        while (!savedCounties.isEmpty()) {
            String countyStr = savedCounties.remove(0);
            String[] s = countyStr.split(",");
            counties.insert(new KeyValuePair((s[0]+","+s[1]), s[2]));
        }


        /* Assignment 3: Add HashTable requirement. NOTE: This is duplicate functionality to the AVLTree
         *  The Below HashMapLinear(linear open addressing) instance and supporting KeyValuePair class and comparator was
         *  added to do the following:
         *  -Locates a county data set by searching a list of all counties stored in a HashMap of key value pairs(of type String,String).
         *  -The HashMapLinear has a get(key) method to return the value, in this case a String.
         *  -Passes the value returned to the GasStationList class's readData() method and is used to locate the searched county's data set.
         *
         *  Note: This app is set up as a Desktop app but if this were a web app you would still want this
         *  HashMapLinear<String,String> instance to be static.
         *
         *  As of Assignment 3 this TreeMapFilters class is sort of a BST/HashTable Tools Helper class.
         */
        // 4096 * .75 < 3143 counties in the USA so need 8192 with load factor of .75 for initial capacity.
        counties2 = new HashMapLinear<>(8192);
        //Load  counties data  into ArrayList
        ArrayList<String> savedCounties2 = HelperClass.getTxtFileAsList(new File("data4.txt"));
        while (!savedCounties2.isEmpty()) {
            String countyStr = savedCounties2.remove(0);
            String[] s = countyStr.split(",");
            counties2.put((s[0]+","+s[1]), s[2]);
        }
    }


    protected static class KeyValuePair {
        protected String key;
        protected String value;

        public KeyValuePair(String s1, String s2) {
            this.key = s1;
            this.value = s2;
        }

        //Used for key searches
        public KeyValuePair(String s1) {
            this.key = s1;
        }
    }

    protected static class keyValuePairComparator implements Comparator<KeyValuePair>, Serializable {
        public int compare(KeyValuePair kv1, KeyValuePair kv2){
            if (kv1.key.compareTo(kv2.key) < 0){
                return -1;
            }
            else if (kv1.key.compareTo(kv2.key) > 0) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }
}
