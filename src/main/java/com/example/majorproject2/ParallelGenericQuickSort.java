package com.example.majorproject2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

/* Assignment 4: Add Parallel processing
 * Added ParallelGenericQuickSort class which is called when a county data file is read from storage in the
 * GasStationList class. When the readData() method is called in that class it calls sortVertically method which calls
 * the parallelGenericQuickSort in this class.
 */
public class ParallelGenericQuickSort {

    public static <E extends Comparable<E>> void parallelGenericQuickSort(ArrayList<E> list) {
        RecursiveAction mainTask = new SortTask(list, 0, list.size() - 1);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(mainTask);
    }

    public static <E> void parallelGenericQuickSort(ArrayList<E> list, Comparator<? super E> comparator) {
        RecursiveAction mainTask = new SortTask2(list, 0, list.size() - 1,comparator);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(mainTask);
    }

    private static class SortTask<E extends Comparable<E>> extends RecursiveAction {
        private final int THRESHOLD = 500;
        private ArrayList<E> list;
        private int first, last;

        SortTask(ArrayList<E> list, int first, int last) {
            this.list = list;
            this.first = first;
            this.last = last;
        }

        @Override
        protected void compute() {
            if (list.size() < THRESHOLD)
                GenericQuickSort.quickSort(list);
            else {
                if (last > first) {
                    int pivotIndex = GenericQuickSort.partition(list, first, last);
                    // Recursively sort the two parts
                    invokeAll(new SortTask(list, first, pivotIndex - 1), new SortTask(list, pivotIndex + 1, last));
                }
            }
        }
    }

    private static class SortTask2<E> extends RecursiveAction {
        private final int THRESHOLD = 500;
        private ArrayList<E> list;
        private int first, last;
        Comparator<? super E> comparator;

        SortTask2(ArrayList<E> list, int first, int last, Comparator<? super E> comparator) {
            this.list = list;
            this.first = first;
            this.last = last;
            this.comparator = comparator;
        }

        @Override
        protected void compute() {
            if (list.size() < THRESHOLD)
                GenericQuickSort.quickSort(list,comparator);
            else {
                if (last > first) {
                    int pivotIndex = GenericQuickSort.partition(list, first, last,comparator);
                    // Recursively sort the two parts
                    invokeAll(new SortTask2(list, first, pivotIndex - 1, comparator),
                            new SortTask2(list, pivotIndex + 1, last, comparator));
                }
            }
        }
    }
}