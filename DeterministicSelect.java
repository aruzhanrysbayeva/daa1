package daa1;

import java.util.Arrays;

public class DeterministicSelect {

    public static int select(int[] arr, int k, Metrics metrics) {
        metrics.enterRecursion();
        try {
            if (arr.length == 1) {
                return arr[0];
            }

            int n = arr.length;
            int numGroups = (n + 4) / 5;
            int[] medians = new int[numGroups];
            metrics.incAllocations();

            for (int i = 0; i < numGroups; i++) {
                int start = i * 5;
                int end = Math.min(start + 5, n);
                int[] group = Arrays.copyOfRange(arr, start, end);
                metrics.incAllocations();
                Arrays.sort(group);
                medians[i] = group[(end - start) / 2];
            }

            int pivot = (numGroups == 1) ? medians[0] : select(medians, numGroups / 2, metrics);

            int[] less = Arrays.stream(arr).filter(x -> { metrics.incComparisons(); return x < pivot; }).toArray();
            int[] equal = Arrays.stream(arr).filter(x -> { metrics.incComparisons(); return x == pivot; }).toArray();
            int[] greater = Arrays.stream(arr).filter(x -> { metrics.incComparisons(); return x > pivot; }).toArray();

            if (k < less.length) {
                return select(less, k, metrics);
            } else if (k < less.length + equal.length) {
                return pivot;
            } else {
                return select(greater, k - less.length - equal.length, metrics);
            }
        } finally {
            metrics.exitRecursion();
        }
    }
}
