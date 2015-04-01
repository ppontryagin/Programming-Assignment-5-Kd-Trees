/**
 * Created by Pavel.Pontryagin on 30.03.2015.
 */
public class StaticClass {

    public static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        public int a;
    }
}


