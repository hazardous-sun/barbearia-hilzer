/**
 * Utils
 */
public class Utils {

    public static int RandomIntN(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}