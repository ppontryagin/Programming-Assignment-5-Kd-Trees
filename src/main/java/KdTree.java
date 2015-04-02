import java.util.*;

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

        if (!contains(p)) {
            root = put(root, p, Node.X_ORDER, 0.0, 0.0, 1.0, 1.0);
            ++count;
        }

    }

//    private Node put(Node x, Point2D key, Comparator<Point2D> order, RectHV rect) {
//        if (x == null) {
//            return new Node(key, rect);
//        }
//
//        int cmp = order.compare(key, x.p);
//
//        RectHV subRect;
//
//        if (cmp < 0) {
//            if (order == Node.X_ORDER) {
//                subRect = new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax());
//            } else {
//                subRect = new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());
//            }
//            x.lb = put(x.lb, key, flipOrder(order), subRect);
//
//        } else if (cmp > 0) {
//            if (order == Node.X_ORDER) {
//                subRect = new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
//            } else {
//                subRect = new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax());
//            }
//            x.rt = put(x.rt, key, flipOrder(order), subRect);
//        }
//        return x;
//    }

    private Node put(Node x, Point2D key, Comparator<Point2D> order, double xMin, double yMin, double xMax, double yMax) {
        if (x == null) {
            return new Node(key, xMin, yMin, xMax, yMax);
        }

        int cmp = order.compare(key, x.p);

        if (cmp < 0) {
            if (order == Node.X_ORDER) {
                x.lb = put(x.lb, key, flipOrder(order), x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax());
            } else {
                x.lb = put(x.lb, key, flipOrder(order), x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());
            }

        } else if (cmp > 0) {
            if (order == Node.X_ORDER) {
                x.rt = put(x.rt, key, flipOrder(order), x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());

            } else {
                x.rt = put(x.rt, key, flipOrder(order), x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax());

            }
        }
        return x;
    }

    private Comparator<Point2D> flipOrder(Comparator<Point2D> order) {
        if (order == Node.X_ORDER)
            return Node.Y_ORDER;
        else
            return Node.X_ORDER;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException();

        return get(root, p, Node.X_ORDER);
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

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException();

        List<Point2D> result = new ArrayList<Point2D>();

        getInRange(root, rect, Node.X_ORDER, result);

        return result;
    }

    private void getInRange(Node x, RectHV rect, Comparator<Point2D> order, List<Point2D> result) {
        if (x == null || !x.rect.intersects(rect))
            return;

        if (rect.contains(x.p))
            result.add(x.p);

        getInRange(x.lb, rect, flipOrder(order), result);
        getInRange(x.rt, rect, flipOrder(order), result);
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
        public static final Comparator<Point2D> X_ORDER = new XOrder();
        public static final Comparator<Point2D> Y_ORDER = new YOrder();

        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        private Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }

        private Node(Point2D p, double xMin, double yMin, double xMax, double yMax) {
            this.p = p;
            this.rect = new RectHV(xMin, yMin, xMax, yMax);
        }

        private static class XOrder implements Comparator<Point2D> {
            public int compare(Point2D p, Point2D q) {
                if (p.x() < q.x()) return -1;
                if (p.x() > q.x()) return +1;
                if (p.y() < q.y()) return -1;
                if (p.y() > q.y()) return +1;
                return 0;
            }
        }

        // compare points according to their y-coordinate
        private static class YOrder implements Comparator<Point2D> {
            public int compare(Point2D p, Point2D q) {
                if (p.y() < q.y()) return -1;
                if (p.y() > q.y()) return +1;
                if (p.x() < q.x()) return -1;
                if (p.x() > q.x()) return +1;
                return 0;
            }
        }
    }
}