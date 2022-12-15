import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main4 {

    public static String textProcessor(int n, int k, String input) {
        Supplier<Stream<String>> wordsSuplier = () -> Stream.of(input.split(" "));
        ArrayList<Pair<String, Integer>> formatedText = new ArrayList<>(List.of(new Pair<>("", 0)));

        if (wordsSuplier.get().anyMatch(word -> word.length() > k)) {
            throw new IllegalArgumentException("There's words witch length is bigger than k");
        }

        wordsSuplier.get().forEach(word -> {
                    Pair<String, Integer> line = formatedText.get(formatedText.size() - 1);

                    if (word.length() + line.getSecond() > k) {
                        line = new Pair<>(word, word.length());
                        formatedText.add(line);
                    } else {
                        line.setFirst(String.format("%s %s", line.getFirst(), word));
                        line.setSecond(line.getSecond() + word.length());
                    }
                });

        return formatedText.stream().map(Pair::getFirst).collect(Collectors.joining("\n"));
    }

    public static String[] split(String brackets) {
        ArrayList<String> result = new ArrayList<>();
        int n = 0, state = 1;

        for (int i = 1; i < brackets.length(); ++i) {
            state += brackets.charAt(i) == '(' ? 1 : -1;
            if (state == 0) {
                result.add(brackets.substring(n, i + 1));
                n = i + 1;
            }
        }
        result.add(brackets.substring(n));

        return result.toArray(new String[0]);
    }

    public static String toCamelCase(String string) {
        String[] splitedString = string.split("_");
        return splitedString[0] + Stream.of(splitedString)
                .skip(1).map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining());
    }

    public static String toSnakeCase(String string) {
        return string.chars().mapToObj(letter -> String.valueOf((char) letter))
                .map(letter -> letter.toUpperCase(Locale.ROOT).equals(letter) ?
                        String.format("_%s", letter.toLowerCase(Locale.ROOT)) :
                        letter)
                .collect(Collectors.joining());
    }

    public static String overTime(double[] data) {
        return String.format("%1$,.2f",
                ((data[1] > 17 ? 17 : data[1]) - data[0]) * data[2] +
                        (data[1] > 17 ? (data[1] - 17) * data[2] * data[3] : 0));
    }

    public static String BMI(String weight, String height) {
        Function<String, Double> getWeightInKg = s -> {
            String[] words = s.split(" ");
            return Double.parseDouble(words[0]) / (words[1].equals("pounds") ? 2.205 : 1);
        };
        Function<String, Double> getHeightInMetres = s -> {
            String[] words = s.split(" ");
            return Double.parseDouble(words[0]) / (words[1].equals("inches") ? 39.37 : 1);
        };
        Function<Double, String> getComment = a -> {
            if (Double.compare(a, 18.5) < 0) {
                return "Underweight";
            } else if (Double.compare(a, 24.9) < 0) {
                return "Normal weight";
            } else {
                return "Overweight";
            }
        };

        Double value = getWeightInKg.apply(weight) / Math.pow(getHeightInMetres.apply(height), 2);
        return String.format("%s %s", String.format("%1$,.1f", value), getComment.apply(value));
    }

    public static int bugger(int number) {
        return number > 9 ? bugger(String.valueOf(number).chars().map(num -> num - '0').reduce(1, (x, y) -> x * y)) + 1 : 0;
    }

    public static String toStarShorthand(String string) {
        if (string.isEmpty()) {
            return "";
        }

        ArrayList<Pair<Character, Integer>> pairs = new ArrayList<>(List.of(new Pair<>(string.charAt(0), 1)));

        string.chars().skip(1).forEach(character -> {
            Pair<Character, Integer> pair = pairs.get(pairs.size() - 1);

            if (pair.getFirst().equals((char) character)) {
                pair.setSecond(pair.getSecond() + 1);
            } else {
                pair = new Pair<>((char) character, 1);
                pairs.add(pair);
            }
        });

        return pairs.stream()
                .map(
                        pair -> pair.getSecond() == 1 ? pair.getFirst().toString() : String.format("%s*%d", pair.getFirst().toString(), pair.getSecond())
                ).collect(Collectors.joining());
    }

    public static boolean doesRhyme(String firstString, String secondString) {
        Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u', 'y');
        Function<String, Set<Character>> getVowelsFromString = s -> {
            Set<Character> result = s.chars().mapToObj(elem -> (char) elem).collect(Collectors.toSet());
            result.retainAll(vowels);
            return result;
        };


        String[] s1 = firstString.split(" "), s2 = secondString.split(" ");

        return getVowelsFromString.apply(s1[s1.length - 1].toLowerCase(Locale.ROOT))
                .equals(getVowelsFromString.apply(s2[s2.length - 1].toLowerCase(Locale.ROOT)));
    }

    public static boolean trouble(long firstNumber, long secondNumber) {
        String firstString = String.valueOf(firstNumber), secondString = String.valueOf(secondNumber);
        boolean firstNumberCheck = firstString.chars()
                .map(elem -> elem - '0')
                .distinct()
                .filter(number -> firstString.contains(String.valueOf(number).repeat(3))
                        && secondString.contains(String.valueOf(number).repeat(2))).count() > 0,
                secondNumberCheck = firstString.chars()
                        .map(elem -> elem - '0')
                        .distinct()
                        .filter(number -> secondString.contains(String.valueOf(number).repeat(3))
                && firstString.contains(String.valueOf(number).repeat(2))).count() > 0;
        return firstNumberCheck || secondNumberCheck;
    }

    public static int countUniqueBooks(String string, char character) {
        List<Integer> bookSlices = new ArrayList<>();
        Set<Integer> books = new HashSet<>();
        int index = string.indexOf(character);

        while (index != -1) {
            bookSlices.add(index);
            index = string.indexOf(character, index + 1);
        }

        for (int i = 1; i < bookSlices.size(); i += 2) {
            string.substring(bookSlices.get(i - 1), bookSlices.get(i))
                    .chars()
                    .distinct()
                    .forEach(books::add);
        }

        return books.size() - 1;
    }

    public static void main(String[] args) {
        // first task
        System.out.println(textProcessor(10, 7, "hello my name is Bessie and this is my essay"));
        System.out.println();

        // second task
        System.out.println(Arrays.toString(split("()()()")));
        System.out.println(Arrays.toString(split("((()))")));
        System.out.println(Arrays.toString(split("((()))(())()()(()())")));
        System.out.println(Arrays.toString(split("((())())(()(()()))")));
        System.out.println();

        // thirds task
        System.out.println(toCamelCase("hello_edabit"));
        System.out.println(toSnakeCase("helloEdabit"));
        System.out.println(toCamelCase("is_modal_open"));
        System.out.println(toSnakeCase("getColor"));
        System.out.println();

        // fourth task
        System.out.println(overTime(new double[]{9, 17, 30, 1.5}));
        System.out.println(overTime(new double[]{16, 18, 30, 1.8}));
        System.out.println(overTime(new double[]{13.25, 15, 30, 1.5}));
        System.out.println();

        // fith task
        System.out.println(BMI("205 pounds", "73 inches"));
        System.out.println(BMI("55 kilos", "1.65 meters"));
        System.out.println(BMI("154 pounds", "2 meters"));
        System.out.println();

        // sixth task
        System.out.println(bugger(39));
        System.out.println(bugger(999));
        System.out.println(bugger(4));
        System.out.println();

        // seventh task
        System.out.println(toStarShorthand("abbccc"));
        System.out.println(toStarShorthand("77777geff"));
        System.out.println(toStarShorthand("abc"));
        System.out.println(toStarShorthand(""));
        System.out.println();

        // eight task
        System.out.println(doesRhyme("Sam I am!", "Green eggs and ham."));
        System.out.println(doesRhyme("Sam I am!", "Green eggs and HAM."));
        System.out.println(doesRhyme("You are off to the races", "a splendid day."));
        System.out.println(doesRhyme("and frequently do?", "you gotta move."));
        System.out.println();

        // nineth task
        System.out.println(trouble(451999277L, 41177722899L));
        System.out.println(trouble(1222345, 12345));
        System.out.println(trouble(666789, 12345667));
        System.out.println(trouble(33789, 12345337));
        System.out.println();

        // tenth task
        System.out.println(countUniqueBooks("AZYWABBCATTTA", 'A'));
        System.out.println(countUniqueBooks("$AA$BBCATT$C$$B$", '$'));
        System.out.println(countUniqueBooks("ZZABCDEF", 'Z'));
        System.out.println();
    }
}

class Pair<K, V> {
    private K first;
    private V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public Pair() {}

    public K getFirst() {
        return first;
    }

    public void setFirst(K first) {
        this.first = first;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }
}