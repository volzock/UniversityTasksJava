import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main6 {

    // First task
    public static int stirling(int n, int k) {
        if (k == 0 || n == 0 || k > n) {
            return 0;
        } else if (n == k) {
            return 1;
        } else {
            return k * stirling(n - 1, k) + stirling(n - 1, k - 1);
        }
    }

    public static int bell(int quantity) {
        return IntStream.range(0, quantity).map(k -> stirling(quantity, k)).sum() + 1;
    }

    // Second task

    public static String translateWord(String word) {
        Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u', 'y');
        Function<String, String> getFirstConsonants = w -> {
            StringBuilder result = new StringBuilder();

            for (int i = 0; i < w.length(); ++i) {
                if (vowels.contains(w.charAt(i))) {
                    break;
                }
                result.append(w.charAt(i));
            }

            return result.toString();
        };

        if (word == null || word.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        String firstConsonants = getFirstConsonants.apply(word);

        if (firstConsonants.isEmpty()) {
            result.append(word)
                    .append("yay");
        } else {
            result.append(word.replaceFirst(firstConsonants, ""))
                    .append(firstConsonants)
                    .append("ay");
        }

        return result.toString();
    }

    public static String translateSentence(String sentence) {
        List<String> constants = List.of(",", "!", ".", "?");
        return Arrays.stream(sentence.split("\\s")).map(word -> {
            for (String constant : constants) {
                if (word.endsWith(constant)) {
                    return translateWord(word.substring(0, word.length() - 1)) + constant;
                }
            }
            return translateWord(word);
        }).collect(Collectors.joining(" "));
    }

    // 3

    public static boolean validColor(String color) {
        if (color.matches("^rgb\\(\\d{1,3},\\d{1,3},\\d{1,3}\\)$")) {
            return Arrays.stream(color.substring(4).replace(")", "").split(","))
                    .noneMatch(x -> Integer.parseInt(x) > 255);
        } else if (color.matches("rgba\\(\\d{1,3},\\d{1,3},\\d{1,3},([0-1]|0.\\d+)\\)")) {
            return Arrays.stream(color.substring(5).replace(")", "").split(","))
                    .allMatch(x -> x.contains(".") || Integer.parseInt(x) <= 255);
        }
        return false;
    }

    // 4

    static class StripUrlParamsExecutor {
        private ArrayList<String> excepts;

        public StripUrlParamsExecutor() {
            excepts = new ArrayList<>();
        }

        public StripUrlParamsExecutor addExcept(String word) {
            excepts.add(word);
            return this;
        }

        public String execute(String url) {
            if (url.contains("?")) {
                List<String> result = List.of(url.split("\\?"));
                HashMap<String, String> queryData = new HashMap<>();

                Arrays.stream(result.get(1).split("&"))
                        .forEach(equality -> {
                            String[] kv = equality.split("=");
                            queryData.put(kv[0], kv[1]);
                        });

                excepts.forEach(queryData::remove);

                return new StringBuilder()
                        .append(result.get(0))
                        .append("?")
                        .append(queryData.keySet().stream().map(k -> String.format("%s=%s", k, queryData.get(k))).collect(Collectors.joining("&")))
                        .toString();
            } else {
                return url;
            }
        }
    }

    // 5

    public static List<String> getHashTags(String sentence) {
        return Arrays.stream(sentence.split("\\s|[,!.?]")).filter(w -> !w.isEmpty())
                .distinct()
                .sorted((a, b) -> b.length() - a.length())
                .limit(3)
                .map(word -> String.format("#%s", word.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
    }

    // 6

    public static int ulam(int number) {
        ArrayList<Integer> result = new ArrayList<>(List.of(1, 2));

        while (result.size() < number) {
            TreeSet<Integer> tmp = new TreeSet<>(Integer::compare), removed = new TreeSet<>();

            for (int i = 0; i < result.size(); ++i) {
                for (int j = i + 1; j < result.size(); ++j) {
                    if (i != j) {
                        int tmpRes = result.get(i) + result.get(j);
                        if (tmpRes > result.get(result.size() - 1))
                        if (!tmp.contains(tmpRes)) {
                            tmp.add(tmpRes);
                        } else {
                            removed.add(tmpRes);
                        }
                    }
                }
            }
            tmp.removeAll(removed);
            result.add(tmp.first());
        }

        return result.get(number - 1);
    }

    // 7

    public static String longestNonrepeatingSubstring(String input) {
        assert input != null && !input.isEmpty();

        int resultFrom = -1, resultTo = -1, tmpFrom = 0, tmpTo = 0;
        HashSet<Character> tmpChars = new HashSet<>();

        for (int i = 0; i < input.length(); ++i) {
            if (!tmpChars.contains(input.charAt(i))) {
                tmpChars.add(input.charAt(i));
                tmpTo += 1;
            } else {
                tmpChars.clear();
                if (resultTo - resultFrom < tmpTo - tmpFrom) {
                    resultTo = tmpTo;
                    resultFrom = tmpFrom;
                }
                tmpFrom = tmpTo;
            }
        }

        if (resultTo - resultFrom < tmpTo - tmpFrom) {
            resultTo = tmpTo;
            resultFrom = tmpFrom;
        }

        return input.substring(resultFrom, resultTo);
    }

    // 8
    public static String convertToRoman(int n) {
        assert n < 3999 && n > 0;
        StringBuilder result = new StringBuilder();

        HashMap<Integer, String> constants = new HashMap<>(){{
            put(1, "I");
            put(2, "II");
            put(3, "III");
            put(4, "IV");
            put(5, "V");
            put(6, "VI");
            put(7, "VII");
            put(8, "VIII");
            put(9, "IX");
            put(10, "X");
            put(50, "L");
            put(90, "XC");
            put(100, "C");
            put(500, "D");
            put(900, "CM");
            put(1000, "M");
        }};
        Stack<Integer> constantsKeys = new Stack<>(){{
            push(1);
            push(2);
            push(3);
            push(4);
            push(5);
            push(6);
            push(7);
            push(8);
            push(9);
            push(10);
            push(50);
            push(90);
            push(100);
            push(500);
            push(900);
            push(1000);
        }};

        while (n > 0) {
            int tmp = constantsKeys.pop();
            if (tmp > n) {
                continue;
            }
            if (n < 10) {
                result.append(constants.get(tmp));
                break;
            } else {
                result.append(constants.get(tmp).repeat(n / tmp));
                n %= tmp;
            }
        }

        return result.toString();
    }

    // 9
    public static class FormulaExecutor {

        static class ExpressionTree {
            private double value;
            private String operand;
            private ExpressionTree firstOperand;
            private ExpressionTree secondOperand;

            public ExpressionTree(){
                this.value = Double.NEGATIVE_INFINITY;
            }

            public String getOperand() {
                return operand;
            }

            public void setOperand(String operand) {
                this.operand = operand;
            }

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }

            public ExpressionTree getFirstOperand() {
                return firstOperand;
            }

            public void setFirstOperand(ExpressionTree firstOperand) {
                this.firstOperand = firstOperand;
            }

            public ExpressionTree getSecondOperand() {
                return secondOperand;
            }

            public void setSecondOperand(ExpressionTree secondOperand) {
                this.secondOperand = secondOperand;
            }
        }

        private static double executeOperand(String operand, double a, double b) {
            switch (operand) {
                case "+":
                    return a + b;
                case "-":
                    return a - b;
                case "*":
                    return a * b;
                case "/":
                    return a / b;
                default:
                    throw new IllegalArgumentException("Error operand");
            }
        }

        private static ExpressionTree buildExpressionTree(String expression) {
            ExpressionTree root = new ExpressionTree();
            Stack<Pair<ExpressionTree, String>> expressionStack = new Stack<>(){{push(new Pair<>(root, expression));}};

            while (!expressionStack.isEmpty()) {
                Pair<ExpressionTree, String> pair = expressionStack.pop();
                int indexOfOperand;

                if (pair.getSecond().contains("+")) {
                    indexOfOperand = pair.getSecond().indexOf("+");
                    pair.getFirst().setOperand("+");
                } else if (pair.getSecond().contains("-")) {
                    indexOfOperand = pair.getSecond().indexOf("-");
                    pair.getFirst().setOperand("-");
                } else if (pair.getSecond().contains("*")) {
                    indexOfOperand = pair.getSecond().indexOf("*");
                    pair.getFirst().setOperand("*");
                } else if (pair.getSecond().contains("/")) {
                    indexOfOperand = pair.getSecond().indexOf("/");
                    pair.getFirst().setOperand("/");
                } else {
                    pair.getFirst().setValue(Double.parseDouble(pair.getSecond()));
                    continue;
                }

                pair.getFirst().setFirstOperand(new ExpressionTree());
                pair.getFirst().setSecondOperand(new ExpressionTree());

                expressionStack.push(new Pair<>(pair.getFirst().getFirstOperand(), pair.getSecond().substring(0, indexOfOperand)));
                expressionStack.push(new Pair<>(pair.getFirst().getSecondOperand(), pair.getSecond().substring(indexOfOperand + 1)));
            }

            return root;
        }

        private static double computeExpressionTree(ExpressionTree node) {
            if (Double.compare(node.getValue(), Double.NEGATIVE_INFINITY) != 0) {
                return node.getValue();
            }
            return executeOperand(node.getOperand(), computeExpressionTree(node.getFirstOperand()), computeExpressionTree(node.getSecondOperand()));
        }

        private static double compute(String expression) {
            ExpressionTree node = buildExpressionTree(expression);
            return computeExpressionTree(node);
        }

        public static boolean execute(String formula) {
            return Arrays.stream(formula.replaceAll(" ", "").split("="))
                    .map(FormulaExecutor::compute)
                    .reduce(.0, (a, b) -> b - a) < 1e-6;
        }
    }

    // 10
    public static boolean palindromedescendant(int n) {
        String number = String.valueOf(n);
        StringBuilder tmp = new StringBuilder();

        while (number.length() > 1) {
            if (new StringBuilder(number).reverse().toString().equals(number)) {
                return true;
            }

            if (number.length() % 2 != 0) {
                number += "0";
            }

            for (int i = 0; i < number.length(); i += 2) {
                int sum = Character.getNumericValue(number.charAt(i)) + Character.getNumericValue(number.charAt(i + 1));

                if (sum > 9) {
                    tmp.append(sum / 10);
                    sum %= 10;
                }
                tmp.append(sum);
            }

            number = tmp.toString();
            tmp = new StringBuilder();
        }

        return false;
    }

    public static void main(String[] args) {
        // first task
        System.out.println("------------------");
        System.out.println("First task");
        System.out.println(bell(1));
        System.out.println(bell(2));
        System.out.println(bell(3));

        // second task
        System.out.println("------------------");
        System.out.println("Second task");
        System.out.println(translateWord("hello"));
        System.out.println(translateWord("shello"));
        System.out.println(translateWord("ello"));
        System.out.println(translateWord(""));
        System.out.println(translateSentence("I like to eat honey waffles."));

        // third task
        System.out.println("------------------");
        System.out.println("Third task");
        System.out.println(validColor("rgb(0,0,0)"));
        System.out.println(validColor("rgb(0,,0)"));
        System.out.println(validColor("rgb(255,256,255)"));
        System.out.println(validColor("rgba(0,0,0,0.123456789)"));

        // fourth task
        System.out.println("------------------");
        System.out.println("Fourth task");
        System.out.println(new StripUrlParamsExecutor().execute("https://edabit.com?a=1&b=2&a=2"));
        System.out.println(new StripUrlParamsExecutor().addExcept("b").execute("https://edabit.com?a=1&b=2&a=2"));
        System.out.println(new StripUrlParamsExecutor().execute("https://edabit.com"));

        // fifth task
        System.out.println("------------------");
        System.out.println("Fifth task");
        System.out.println(String.join(" ", getHashTags("How the Avocado Became the Fruit of the Global Trade")));
        System.out.println(String.join(" ", getHashTags("Why You Will Probably Pay More for Your Christmas Tree This Year")));
        System.out.println(String.join(" ", getHashTags("Hey Parents, Surprise, Fruit Juice Is Not Fruit")));
        System.out.println(String.join(" ", getHashTags("Visualizing Science")));

        // sixth task
        System.out.println("------------------");
        System.out.println("Sixth task");
        System.out.println(ulam(4));
        System.out.println(ulam(9));
        System.out.println(ulam(206));

        // seventh task
        System.out.println("------------------");
        System.out.println("Seventh task");
        System.out.println(longestNonrepeatingSubstring("abcabcbb"));
        System.out.println(longestNonrepeatingSubstring("aaaaaaaa"));
        System.out.println(longestNonrepeatingSubstring("abcde"));
        System.out.println(longestNonrepeatingSubstring("abcda"));

        // eigth task
        System.out.println("------------------");
        System.out.println("Eigth task");
        System.out.println(convertToRoman(2));
        System.out.println(convertToRoman(12));
        System.out.println(convertToRoman(16));
        System.out.println(convertToRoman(99));

        // ninth task
        System.out.println("------------------");
        System.out.println("Ninth task");
        System.out.println(FormulaExecutor.execute("6 * 4 = 24"));
        System.out.println(FormulaExecutor.execute("18 / 17 = 2"));
        System.out.println(FormulaExecutor.execute("16 * 10 = 160 = 14 + 120"));

        // tenth task
        System.out.println("------------------");
        System.out.println("Tenth task");
        System.out.println(palindromedescendant(11211230));
        System.out.println(palindromedescendant(13001120));
        System.out.println(palindromedescendant(23336014));
        System.out.println(palindromedescendant(11));
    }
}
