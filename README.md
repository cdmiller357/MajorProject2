# MajorProject2
REVISED PROJECT - CHANGE FROM CLOSEST GAS STATIONS TO TRIP PLANNER.

My Major Project is an app to help people plan a trip picking destinations based around "towns" they stop in. I will loosely try to design classes with MVC design.   

On the model side it will use a GasStation class which will have data fields for a point of x,y coordinates inherited from a Point class. I left the class called GasStation even though starting in part 2 it can be any location not just a gas station. A GasStationList class will contain an ArrayList and related methods. Again, starting in part 2 it can be a list of any location not just gas stations. There will be a GenericQuickSort and companion Comparator classes specific to sorting gas stations on a grid. There will be a TreeMapFilters class that will contain a TreeMap for the ratings filter and a Set for the open 24 Hours filter. Both filters will be brand/name specific not location specific for simplicity.  

The class closest to representing a controller class will be called SortByClosest which will have an algorithm that uses quick sort and a comparator to sort by distance to the current location. 

The view portion of the MVC design will be the View class which will handle displaying output and handle user input. New in part 2(amongst other things) will be the use of two stacks, similar to the forward and back buttons on a web browser, to navigate back and forth to the locations/"towns" added to the trip. In the results pane will be a list of locations near the current location and you can toggle with toggle buttons which spots you want to visit in each city, and it will remember them as you are planning. The Center pane is like the map that sort of encompasses a county/regions of a state.

The above is complete in part 2. In part 3 I may implement a BST/AVL that would point to different maps of different counties/regions of a state. Each node in the BST would be a Map of something like county/region names for the key and a data location pointer for the value that points to a data file/table.

In part 3 I added the ability to load maps of different "counties". I have duplicate solutions for this, one Go button loads using an AVLTree and another Go button loads with a HashTable. The AVLTree could give you iterator functionality if there is a way to incorporate that but HashTable will be faster.
 
I didn't have a way to add a new functionality to the program to use both those structures in separate features unless I was going to rework the program extensively which I didn't have time for. 

Obviously, I didn't load a map for every county in the United States but just linked all those counties to the same data file. Most counties if entered will load the main data file I have been using but "Washington County,Maryland" will load  a different one.
