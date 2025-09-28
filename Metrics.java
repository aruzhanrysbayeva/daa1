package daa1;

public class Metrics {
    public long comparisons = 0;
    public long allocations = 0;
    public int recursionDepth = 0;
    public int maxRecursionDepth = 0;

    public void reset() {
        comparisons = 0;
        allocations = 0;
        recursionDepth = 0;
        maxRecursionDepth = 0;
    }

    public void incComparisons() { comparisons++; }
    public void incAllocations() { allocations++; }

    public void enterRecursion() {
        recursionDepth++;
        if (recursionDepth > maxRecursionDepth) {
            maxRecursionDepth = recursionDepth;
        }
    }

    public void exitRecursion() { recursionDepth--; }

    @Override
    public String toString() {
        return "Comparisons=" + comparisons +
                ", Allocations=" + allocations +
                ", MaxDepth=" + maxRecursionDepth;
    }
}
