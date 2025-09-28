package daa1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random rand = new Random();

        try (FileWriter fw = new FileWriter("results.csv", false)) {
            // CSV header
            fw.write("Algorithm,InputSize,Comparisons,Allocations,MaxDepth,Time(ns)\n");

            // === MergeSort ===
            int[] arr1 = rand.ints(20, 0, 100).toArray();
            Metrics m1 = new Metrics();
            long t1 = System.nanoTime();
            MergeSort.sort(arr1.clone(), m1);
            long elapsed1 = System.nanoTime() - t1;
            System.out.println("MergeSort: " + m1);
            fw.write(String.format("MergeSort,%d,%d,%d,%d,%d\n",
                    arr1.length, m1.comparisons, m1.allocations, m1.maxRecursionDepth, elapsed1));

            // === QuickSort ===
            int[] arr2 = rand.ints(20, 0, 100).toArray();
            Metrics m2 = new Metrics();
            long t2 = System.nanoTime();
            QuickSort.sort(arr2.clone(), m2);
            long elapsed2 = System.nanoTime() - t2;
            System.out.println("QuickSort: " + m2);
            fw.write(String.format("QuickSort,%d,%d,%d,%d,%d\n",
                    arr2.length, m2.comparisons, m2.allocations, m2.maxRecursionDepth, elapsed2));

            // === Deterministic Select ===
            int[] arr3 = rand.ints(15, 0, 50).toArray();
            int k = arr3.length / 2; // median
            Metrics m3 = new Metrics();
            long t3 = System.nanoTime();
            int kth = DeterministicSelect.select(arr3.clone(), k, m3);
            long elapsed3 = System.nanoTime() - t3;
            System.out.println("Deterministic Select (k=" + k + "): " + kth + " | " + m3);
            fw.write(String.format("DeterministicSelect,%d,%d,%d,%d,%d\n",
                    arr3.length, m3.comparisons, m3.allocations, m3.maxRecursionDepth, elapsed3));

            // === Closest Pair of Points ===
            ClosestPair.Point[] pts = {
                    new ClosestPair.Point(2.1, 3.1),
                    new ClosestPair.Point(12.3, 30.7),
                    new ClosestPair.Point(40.5, 50.9),
                    new ClosestPair.Point(5.9, 1.2),
                    new ClosestPair.Point(12.0, 10.3),
                    new ClosestPair.Point(3.0, 4.0)
            };
            Metrics m4 = new Metrics();
            long t4 = System.nanoTime();
            double dist = ClosestPair.findClosest(pts, m4);
            long elapsed4 = System.nanoTime() - t4;
            System.out.println("Closest Pair distance: " + dist + " | " + m4);
            fw.write(String.format("ClosestPair,%d,%d,%d,%d,%d\n",
                    pts.length, m4.comparisons, m4.allocations, m4.maxRecursionDepth, elapsed4));

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n✅ Results saved to results.csv");
    }
}

