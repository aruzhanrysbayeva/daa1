package daa1;

import java.util.Arrays;

public class ClosestPair {

    public static class Point {
        double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double findClosest(Point[] pts, Metrics metrics) {
        Point[] ptsSortedX = pts.clone();
        Point[] ptsSortedY = pts.clone();
        Arrays.sort(ptsSortedX, (a, b) -> { metrics.incComparisons(); return Double.compare(a.x, b.x); });
        Arrays.sort(ptsSortedY, (a, b) -> { metrics.incComparisons(); return Double.compare(a.y, b.y); });
        return closest(ptsSortedX, ptsSortedY, metrics);
    }

    private static double closest(Point[] ptsSortedX, Point[] ptsSortedY, Metrics metrics) {
        metrics.enterRecursion();
        try {
            int n = ptsSortedX.length;
            if (n <= 3) return bruteForce(ptsSortedX, metrics);

            int mid = n / 2;
            Point midPoint = ptsSortedX[mid];

            Point[] leftX = Arrays.copyOfRange(ptsSortedX, 0, mid);
            Point[] rightX = Arrays.copyOfRange(ptsSortedX, mid, n);

            Point[] leftY = Arrays.stream(ptsSortedY).filter(p -> p.x <= midPoint.x).toArray(Point[]::new);
            Point[] rightY = Arrays.stream(ptsSortedY).filter(p -> p.x > midPoint.x).toArray(Point[]::new);

            double dl = closest(leftX, leftY, metrics);
            double dr = closest(rightX, rightY, metrics);
            double d = Math.min(dl, dr);

            Point[] strip = Arrays.stream(ptsSortedY)
                    .filter(p -> Math.abs(p.x - midPoint.x) < d)
                    .toArray(Point[]::new);

            return stripClosest(strip, d, metrics);
        } finally {
            metrics.exitRecursion();
        }
    }

    private static double bruteForce(Point[] pts, Metrics metrics) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                metrics.incComparisons();
                double dist = distance(pts[i], pts[j]);
                if (dist < min) min = dist;
            }
        }
        return min;
    }

    private static double stripClosest(Point[] strip, double d, Metrics metrics) {
        double min = d;
        for (int i = 0; i < strip.length; i++) {
            for (int j = i + 1; j < strip.length && (strip[j].y - strip[i].y) < min; j++) {
                metrics.incComparisons();
                double dist = distance(strip[i], strip[j]);
                if (dist < min) min = dist;
            }
        }
        return min;
    }

    private static double distance(Point p1, Point p2) {
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }
}
