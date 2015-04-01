import java.util.Set;
import java.util.TreeSet;

public class PointSET {

    private Set<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);

        for (Point2D p : pointSet)
            StdDraw.point(p.x(), p.y());
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();

        Set<Point2D> insidePoints = new TreeSet<Point2D>();

        for (Point2D p : pointSet) {
            if (rect.contains(p))
                insidePoints.add(p);
        }

        return insidePoints;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        if (pointSet.isEmpty())
            return null;

        Point2D nearest = null;

        double minDistance = Double.POSITIVE_INFINITY;
        double currDistance;


        for (Point2D currPoint : pointSet) {
            currDistance = p.distanceSquaredTo(currPoint);
            if (currDistance < minDistance) {
                minDistance = currDistance;
                nearest = currPoint;
            }
        }

        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}