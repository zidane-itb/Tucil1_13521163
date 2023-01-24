import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import static java.lang.System.out;

public class IoHandler {

    private static HashMap<String, Integer> inputMap;

    public static void start() throws IOException {
        Scanner scanner = new Scanner(System.in);
        HashSet<String> exprs;

        while (true) {
            Integer[] inputs;
            out.println("generate random number? (Y/Any Key)");
            String randC = scanner.nextLine();
            boolean rand = randC.equalsIgnoreCase("y");

            try {
                inputs = getArray(rand);
            } catch (RuntimeException e) {
                out.println("invalid input.");
                continue;
            }

            if (rand)
                out.printf("generated numbers: %d %d %d %d\n", inputs[0], inputs[1], inputs[2], inputs[3]);

            exprs = Processor.process(inputs);

            out.println("simpan ke file? (Y/Any Key) ");
            String input = scanner.next();

            if (input.equalsIgnoreCase("y")) {
                saveToFile(exprs);
            }

            break;
        }

    }

    private static Integer[] getArray(boolean random) {
        if (random) {
            return new Integer[]{MathUtil.getRandom(1, 14), MathUtil.getRandom(1, 14),
                    MathUtil.getRandom(1, 14), MathUtil.getRandom(1, 14)};
        }

        Scanner scanner = new Scanner(System.in);
        int i = 0;
        Integer[] arr = new Integer[4];
        out.println("input numbers (format: O O O O):");

        for (String s: scanner.nextLine().split(" ")) {
            int el = inputMap.getOrDefault(s, -1);

            if (el == -1 || i > 3)
                throw new RuntimeException("");

            arr[i] = el;
            ++i;
        }

        if (i != 4)
            throw new RuntimeException("");

        return arr;
    }

    private static void saveToFile(HashSet<String> exprs) throws IOException {
        String url = "res.txt";

        BufferedWriter out = new BufferedWriter(new FileWriter(url));

        out.write(String.format("%d solutions found.\n", exprs.size()));
        for (String expr : exprs) {
            out.write(expr);
            out.newLine();
        }

        out.close();
    }

    static {
        inputMap = new HashMap<>();
        inputMap.put("A", 1);
        inputMap.put("J", 11);
        inputMap.put("Q", 12);
        inputMap.put("K", 13);
        inputMap.put("2", 2);
        inputMap.put("3", 3);
        inputMap.put("4", 4);
        inputMap.put("5", 5);
        inputMap.put("6", 6);
        inputMap.put("7", 7);
        inputMap.put("8", 8);
        inputMap.put("9", 9);
        inputMap.put("10", 10);
    }

}
