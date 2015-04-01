import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class KdTree {

    private Node root = null;
    private int count = 0;

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return (root == null);
    }

    // number of points in the set
    public int size() {
        return count;
    }

    // TODO add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        root = put(root, p, Point2D.X_ORDER, new RectHV(0.0, 0.0, 1.0, 1.0));
    }

    private Node put(Node x, Point2D key, Comparator<Point2D> order, RectHV rect) {
        if (x == null) {
            count++;
            return new Node(key, rect);
        }

        int cmp = order.compare(key, x.p);

        RectHV subRect;

        if (cmp < 0) {
            if (order == Point2D.X_ORDER) {
                subRect = new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax());
            } else {
                subRect = new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());
            }
            x.lb = put(x.lb, key, flipOrder(order), subRect);

        } else if (cmp > 0) {
            if (order == Point2D.X_ORDER) {
                subRect = new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
            } else {
                subRect = new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax());
            }
            x.rt = put(x.rt, key, flipOrder(order), subRect);
        }
        return x;
    }

    private Comparator<Point2D> flipOrder(Comparator<Point2D> order) {
        if (order == Point2D.X_ORDER)
            return Point2D.Y_ORDER;
        else
            return Point2D.X_ORDER;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        return get(root, p, Point2D.X_ORDER);
    }

    // Return true if value associated with key is in the subtree rooted at x;
    // return false if key not present in subtree rooted at x.
    private boolean get(Node x, Point2D key, Comparator<Point2D> order) {
        if (x == null)
            return false;

        int cmp = order.compare(key, x.p);

        if (cmp < 0)
            return get(x.lb, key, flipOrder(order));
        else if (cmp > 0)
            return get(x.rt, key, flipOrder(order));
        else
            return true;
    }


    // draw all points to standard draw
    public void draw() {


        walk(root, "R");
    }

    private void walk(Node r, String color) {
        if (r != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.005);
            r.p.draw();

            StdDraw.setPenRadius(.001);

            if (color.equals("R")) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(r.p.x(), r.rect.ymin(), r.p.x(), r.rect.ymax());
                color = "B";
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(r.rect.xmin(), r.p.y(), r.rect.xmax(), r.p.y());
                color = "R";
            }
//            r.rect.draw();

            walk(r.lb, color);
            walk(r.rt, color);

        }
    }

    // TODO all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();

        Set<Point2D> insidePoints = new TreeSet<Point2D>();

//        for (Point2D p : pointSet) {
//            if (rect.contains(p))
//                insidePoints.add(p);
//        }

        return insidePoints;
    }

    // TODO a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException();



        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        private Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }
}