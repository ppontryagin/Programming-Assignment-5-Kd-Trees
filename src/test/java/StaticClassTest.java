/**
 * Created by Pavel.Pontryagin on 30.03.2015.
 */
public class StaticClassTest {
    public static void main(String[] args) {

        StaticClass.Node node1 = new StaticClass.Node();
        node1.a = 10;


        StaticClass.Node node2 = new StaticClass.Node();
        node2.a = 20;

        System.out.println(node1);
        System.out.println(node2);
    }
}
