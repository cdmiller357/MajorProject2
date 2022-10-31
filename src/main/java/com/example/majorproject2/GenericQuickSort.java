package com.example.majorproject2;

import java.util.ArrayList;
import java.util.Comparator;

public class GenericQuickSort {

    //Utility Class, should not be an instance
    private GenericQuickSort(){}

    public static <E extends Comparable<E>> void quickSort(ArrayList<E> list){
        quickSort(list, 0, list.size()-1);
    }

    public static <E> void quickSort(ArrayList<E> list, Comparator<? super E> comparator) {
        quickSort(list, 0, list.size()-1,comparator);
    }

    private static <E extends Comparable<E>> void quickSort(ArrayList<E> list, int first, int last) {
        if (last > first) {
            int pivotIndex = partition(list, first, last);
            quickSort(list, first, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, last);
        }
    }

    private static <E> void quickSort(ArrayList<E> list, int first, int last, Comparator<? super E> comparator) {
        if (last > first) {
            int pivotIndex = partition(list, first, last, comparator);
            quickSort(list, first, pivotIndex - 1, comparator);
            quickSort(list, pivotIndex + 1, last, comparator);
        }
    }

    /** Partition the array list[first..last] */
    private static <E extends Comparable<E>> int partition(ArrayList<E> list, int first, int last) {
        E pivot = list.get(first); // Choose the first element as the pivot
        int low = first + 1; // Index for forward search (1)
        int high = last; // Index for backward search (list.length - 1)

        while (high > low) {
            // Search forward from left
            while (low <= high && list.get(low).compareTo(pivot) <= 0)
                low++;

            // Search backward from right
            while (low <= high && list.get(high).compareTo(pivot) > 0)
                high--;

            // Swap two elements in the list
            if (high > low) {
                E temp = list.get(high);
                list.set(high, list.get(low));
                list.set(low, temp);
            }
        }

        while (high > first && list.get(high).compareTo(pivot) >= 0)
            high--;

        // Swap pivot with list[high]
        if (pivot.compareTo(list.get(high)) > 0) {
            list.set(first, list.get(high));
            list.set(high, pivot);
            return high;
        }
        else {
            return first;
        }
    }


    /** Partition the array list[first..last] */
    private static <E> int partition(ArrayList<E> list, int first, int last, Comparator<? super E> comparator) {
        E pivot = list.get(first); // Choose the first element as the pivot
        int low = first + 1; // Index for forward search (1)
        int high = last; // Index for backward search (list.length - 1)

        while (high > low) {
            // Search forward from left
            while (low <= high && comparator.compare(list.get(low), pivot)<= 0)
                low++;

            // Search backward from right
            while (low <= high && comparator.compare(list.get(high), pivot) > 0)
                high--;

            // Swap two elements in the list
            if (high > low) {
                E temp = list.get(high);
                list.set(high, list.get(low));
                list.set(low, temp);
            }
        }

        while (high > first && comparator.compare(list.get(high), pivot) >= 0)
            high--;

        // Swap pivot with list[high]
        if (comparator.compare(pivot, list.get(high)) > 0) {
            list.set(first, list.get(high));
            list.set(high, pivot);
            return high;
        }
        else {
            return first;
        }
    }
}
