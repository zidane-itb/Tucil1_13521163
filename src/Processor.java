import java.util.HashSet;

import static java.lang.System.nanoTime;
import static java.lang.System.out;

public class Processor {

    public static HashSet<String> process(Integer[] ln) {
        if (ln.length != 4)
            throw new RuntimeException("Call args error.");

        Character[] ops = {'*', '+', '-', '/'};

        HashSet<Integer> lnSet;
        HashSet<String> history = new HashSet<>();

        int occ = 0;
        long startTime = nanoTime();

        for (int i = 0; i < 4; ++i) {
            lnSet = new HashSet<>();
            lnSet.add(i);

            for (int j = 0; j < 4; ++j) {
                for (int k = 0; k < 4; ++k) {
                    if (lnSet.contains(k))
                        continue;
                    lnSet.add(k);

                    for (int l = 0; l < 4; ++l) {
                        for (int m = 0; m < 4; ++m) {
                            if (lnSet.contains(m))
                                continue;
                            lnSet.add(m);

                            for (int n = 0; n < 4; ++n) {
                                for (int o = 0; o < 4; ++o) {
                                    if (lnSet.contains(o))
                                        continue;

                                    occ += countAll(ln[i], ln[k], ln[m], ln[o], ops[j], ops[l], ops[n], history);

                                }
                            }
                            lnSet.remove(m);
                        }
                    }
                    lnSet.remove(k);
                }
            }
        }

        long finalTime = nanoTime() - startTime;

        out.printf("%d solutions found.\n", occ);

        int pIdx = 0;

        for (String key: history) {
            out.print(key + " | ");
            pIdx += 1;

            if (pIdx % 5 == 0)
                out.println();
        }

        out.printf("\ntime: %d ms\n", finalTime/1000000);

        return history;
    }

    private static int count(int num1, int num2, int num3, int num4, char ops1, char ops2, char ops3,
                             HashSet<String> history) {
        String expr = String.format("((%d %c %d) %c %d) %c %d", num1, ops1, num2, ops2, num3, ops3, num4);

        if (history.contains(expr))
            return 0;

        double value = calculateExpression(calculateExpression(calculateExpression(num1, num2, ops1), num3, ops2), num4, ops3);

        if (value >= 23.999 && value <= 24.001) {
            history.add(expr);
            return 1;
        }

        return 0;
    }

    private static int countSec(int num1, int num2, int num3, int num4, char ops1, char ops2, char ops3,
                                HashSet<String> history) {
        String expr = String.format("(%d %c (%d %c %d)) %c %d", num1, ops1, num2, ops2, num3, ops3, num4);

        if (history.contains(expr))
            return 0;

        double value = calculateExpression(calculateExpression(num1, calculateExpression(num2, num3, ops2), ops1), num4, ops3);

        if (value >= 23.999 && value <= 24.001) {
            history.add(expr);
            return 1;
        }

        return 0;
    }

    private static int countThi(int num1, int num2, int num3, int num4, char ops1, char ops2, char ops3,
                                HashSet<String> history) {
        String expr = String.format("(%d %c %d) %c (%d %c %d)", num1, ops1, num2, ops2, num3, ops3, num4);

        if (history.contains(expr))
            return 0;

        double value = calculateExpression(calculateExpression(num1, num2, ops1), calculateExpression(num3, num4, ops3), ops2);

        if (value >= 23.999 && value <= 24.001) {
            history.add(expr);
            return 1;
        }

        return 0;
    }

    private static int countF(int num1, int num2, int num3, int num4, char ops1, char ops2, char ops3,
                              HashSet<String> history) {
        String expr = String.format("%d %c ((%d %c %d) %c %d)", num1, ops1, num2, ops2, num3, ops3, num4);

        if (history.contains(expr))
            return 0;

        double value = calculateExpression(num1, calculateExpression(calculateExpression(num2, num3, ops2), num4, ops3), ops1);

        if (value >= 23.999 && value <= 24.001) {
            history.add(expr);
            return 1;
        }

        return 0;
    }

    private static int countFi(int num1, int num2, int num3, int num4, char ops1, char ops2, char ops3,
                               HashSet<String> history) {
        String expr = String.format("%d %c (%d %c (%d %c %d))", num1, ops1, num2, ops2, num3, ops3, num4);

        if (history.contains(expr))
            return 0;

        double value = calculateExpression(num1, calculateExpression(num2, calculateExpression(num3, num4, ops3), ops2), ops1);

        if (value >= 23.999 && value <= 24.001) {
            history.add(expr);
            return 1;
        }

        return 0;
    }

    private static int countAll(int num1, int num2, int num3, int num4, char ops1, char ops2, char ops3,
                                HashSet<String> history) {
        return count(num1, num2, num3, num4, ops1, ops2, ops3, history) +
                countSec(num1, num2, num3, num4, ops1, ops2, ops3, history) +
                countThi(num1, num2, num3, num4, ops1, ops2, ops3, history) +
                countF(num1, num2, num3, num4, ops1, ops2, ops3, history) +
                countFi(num1, num2, num3, num4, ops1, ops2, ops3, history);
    }

    private static double calculateExpression(double num1, double num2, char ops) {
        if (ops == '*')
            return num1 * num2;

        if (ops == '+')
            return num1 + num2;

        if (ops == '-')
            return num1 - num2;

        if (ops == '/')
            return num1/num2;

        return 0;
    }


}
