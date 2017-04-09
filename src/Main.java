import java.util.List;

/**
 * Created by elena on 06.04.17.
 */
public class Main {
    public static void main(String [] argc){
        HeiSeilor heiSeilor = new HeiSeilor();
        if (heiSeilor.Resolve()) {
            List<Field> result = heiSeilor.getTreeField();
            System.out.println("Решение нашлось за " + result.size() + " ходов.");
            System.out.println();
            heiSeilor.PrintFieldList(result);
        } else {
            System.out.println("Решение не нашлось.");
        }
    }
}
