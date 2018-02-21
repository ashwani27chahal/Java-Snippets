package com.ashwani.runningMedians;

//For every element in the array maintain a minHeap and a maxHEap
//minHeap should contain upper half of the elements
//maxHeap should contain lower half of the elements
//if both have the same size then average the max from lower half and min from upper half
//if the size differs by one then just return bigger heaps top element



import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void addNumbers(int number, PriorityQueue<Integer> lowerHalf, PriorityQueue<Integer> upperHalf){
        if (lowerHalf.size() ==0 || number < lowerHalf.peek())
            lowerHalf.add(number);
        else
            upperHalf.add(number);
    }

    //heaps need too be rebalanced such that difference in size in not more than 1
    public static void rebalance(PriorityQueue<Integer> lowerHalf, PriorityQueue<Integer> upperHalf){
        PriorityQueue<Integer> biggerHeap = lowerHalf.size() < upperHalf.size()? upperHalf : lowerHalf;
        PriorityQueue<Integer> smallerHeap = lowerHalf.size() < upperHalf.size()? lowerHalf : upperHalf;

        if(biggerHeap.size() - smallerHeap.size() >= 2){
            smallerHeap.add(biggerHeap.poll());
        }
    }

    public static double getMedian(PriorityQueue<Integer> lowerHalf, PriorityQueue<Integer> upperHalf){
        PriorityQueue<Integer> biggerHeap = lowerHalf.size() < upperHalf.size() ? upperHalf : lowerHalf;
        PriorityQueue<Integer> smallerHeap = lowerHalf.size() < upperHalf.size() ? lowerHalf : upperHalf;
        if (biggerHeap.size() == smallerHeap.size())
            return ((double)biggerHeap.peek() + smallerHeap.peek())/2;
        else
            return biggerHeap.peek();
    }


    public static double[] getMedians(int[] a){

        PriorityQueue<Integer> lowerHalf = new PriorityQueue<Integer>(new Comparator<Integer>(){     //max heap
            public int compare(Integer a, Integer b){
                return b-a;
            }
        });
        PriorityQueue<Integer> upperHalf = new PriorityQueue<Integer>();  //min heap

        double[] medians = new double[a.length];
        for (int i = 0; i <a.length; i++){
                addNumbers(a[i], lowerHalf, upperHalf);
                rebalance(lowerHalf, upperHalf);
                medians[i] = getMedian(lowerHalf, upperHalf);
            }
        return medians;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a = new int[n];
        for(int a_i=0; a_i < n; a_i++){
            a[a_i] = in.nextInt();
        }
        double[] runningMedians = getMedians(a);
        for(int i=0; i < n; i++){
            System.out.println(runningMedians[i]);
        }

    }
}
