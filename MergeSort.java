package daa1;

import java.util.Arrays;

public class MergeSort {
    private static final int CUTOFF = 10; // use insertion sort for small arrays

    public static void sort(int[] arr, Metrics metrics) {
        int[] buffer = new int[arr.length];
        metrics.incAllocations();
        mergeSort(arr, buffer, 0, arr.length - 1, metrics);
    }

    private static void mergeSort(int[] arr, int[] buffer, int left, int right, Metrics metrics) {
        metrics.enterRecursion();
        try {
            if (right - left + 1 <= CUTOFF) {
                insertionSort(arr, left, right, metrics);
                return;
            }

            if (left < right) {
                int mid = (left + right) / 2;
                mergeSort(arr, buffer, left, mid, metrics);
                mergeSort(arr, buffer, mid + 1, right, metrics);
                merge(arr, buffer, left, mid, right, metrics);
            }
        } finally {
            metrics.exitRecursion();
        }
    }

    private static void insertionSort(int[] arr, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                metrics.incComparisons();
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right, Metrics metrics) {
        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            metrics.incComparisons();
            if (arr[i] <= arr[j]) {
                buffer[k++] = arr[i++];
            } else {
                buffer[k++] = arr[j++];
            }
        }
        while (i <= mid) buffer[k++] = arr[i++];
        while (j <= right) buffer[k++] = arr[j++];

        for (i = left; i <= right; i++) arr[i] = buffer[i];
    }
}
