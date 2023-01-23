import java.util.Random;

public class MathUtil {

    private static final Random random = new Random();

    public static Integer getRandom(int min, int max) {
        return random.nextInt(max - min) + min;
    }

}
