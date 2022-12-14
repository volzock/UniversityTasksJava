import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main5 {
    public static int[] encrypt(String message) {
        AtomicInteger last = new AtomicInteger();
        return message.chars().map(x -> {
            int tmp = last.get();
            last.set(x);
            return x - tmp;
        }).toArray();
    }

    public static String decrypt(int[] message) {
        AtomicInteger last = new AtomicInteger();
        return Arrays.stream(message)
                .mapToObj(elem -> {
                    int tmp = last.get() + elem;
                    last.set(tmp);
                    return tmp;
                })
                .map(elem -> String.valueOf((char) elem.intValue()))
                .collect(Collectors.joining());
    }

    public static boolean canMove(String chessPiece, String firstPlace, String secondPlace) {
        Function<String, Pair<Integer, Integer>> getCoordinatesByPlace = place -> {
            return new Pair<>(place.charAt(0) - 'A', place.charAt(1) - '1');
        };

        Pair<Integer, Integer> firstCoordinates = getCoordinatesByPlace.apply(firstPlace),
                secondCoordinates = getCoordinatesByPlace.apply(secondPlace),
                resultVector = new Pair<>(secondCoordinates.getFirst() - firstCoordinates.getFirst(),
                        secondCoordinates.getSecond() - firstCoordinates.getSecond());

        switch (chessPiece.toLowerCase(Locale.ROOT)) {
            case "pawn":
                return resultVector.getSecond() == 1 && Math.abs(resultVector.getFirst()) < 2;
            case "knight":
                return Math.abs(resultVector.getFirst()) == 2 && Math.abs(resultVector.getSecond()) == 1;
            case "rook":
                return resultVector.getFirst() == 0 || resultVector.getSecond() == 0;
            case "bishop":
                return Math.abs(resultVector.getFirst()) == Math.abs(resultVector.getSecond());
            case "queen":
                return canMove("rook", firstPlace, secondPlace) || canMove("bishop", firstPlace, secondPlace);
            case "king":
                return Math.abs(resultVector.getFirst()) < 2 && Math.abs(resultVector.getSecond()) < 2;
            default:
                throw new IllegalArgumentException("There's no chess piece with such name");
        }
    }

    public static boolean canComplete(String completable, String message) {
        Stack<Integer> completableChars = new Stack<>();
        new StringBuilder(completable).reverse().chars().forEach(completableChars::push);
        message.chars().forEach(c -> {
            if (c == completableChars.peek()) {
                completableChars.pop();
            }
        });
        return completableChars.empty();
    }

    public static int sumDigProd(int... nums) {
        int num = Arrays.stream(nums).sum();
        while (num > 10) {
            num = String.valueOf(num).chars().map(x -> x - '0').reduce(1, (x, y) -> x * y);
        }
        return num;
    }

    public static List<String> sameVowelGroup(String[] words) {
        Set<Character> vowels = Set.of('a', 'e', 'i', 'o', 'u', 'y');
        Function<String, Set<Character>> getVowelsFromString = s -> {
            Set<Character> result = s.toLowerCase(Locale.ROOT).chars().mapToObj(elem -> (char) elem).collect(Collectors.toSet());
            result.retainAll(vowels);
            return result;
        };

        if (words.length == 0) {
            return new ArrayList<>();
        } else {
            Set<Character> firstWordVowels = getVowelsFromString.apply(words[0]);
            return Arrays.stream(words)
                    .filter(word -> getVowelsFromString.apply(word).equals(firstWordVowels))
                    .collect(Collectors.toList());
        }
    }

    public static boolean validateCard(Long cardNumber) {
        String clearedCardNumber = new StringBuilder(cardNumber.toString().substring(0, cardNumber.toString().length() - 1)).reverse().toString();
        int number = 0, tmp = 0;

        if (clearedCardNumber.length() < 12 || clearedCardNumber.length() > 18) {
            return false;
        }

        for (int i = 0; i < clearedCardNumber.length(); ++i) {
            tmp = clearedCardNumber.charAt(i) - '0';
            if (i % 2 == 1) {
                number += tmp;
            } else {
                tmp *= 2;
                if (tmp >= 10) {
                    number += tmp / 10 + tmp % 10;
                } else {
                    number += tmp;
                }
            }
        }

        return 10 - number % 10 == cardNumber % 10;
    }

    public static String numToEng(int number) {
        HashMap<Integer, String> numToWord = new HashMap<>(){{
            put(0, "zero");
            put(1, "one");
            put(2, "two");
            put(3, "three");
            put(4, "four");
            put(5, "five");
            put(6, "six");
            put(7, "seven");
            put(8, "eight");
            put(9, "nine");
            put(10, "ten");
            put(11, "eleven");
            put(12, "twelve");
            put(13, "thirteen");
            put(14, "fourteen");
            put(15, "fifteen");
            put(16, "sixteen");
            put(17, "seventeen");
            put(18, "eighteen");
            put(19, "nineteen");
            put(20, "twenty");
            put(30, "thirty");
            put(40, "forty");
            put(50, "fifty");
            put(60, "sixty");
            put(70, "seventy");
            put(80, "eighty");
            put(90, "ninety");
            put(100, "one hundred");
            put(200, "two hundreds");
            put(300, "three hundreds");
            put(400, "four hundreds");
            put(500, "five hundreds");
            put(600, "six hundreds");
            put(700, "seven hundreds");
            put(800, "eight hundreds");
            put(900, "nine hundreds");
        }};

        if (numToWord.containsKey(number)) {
            return numToWord.get(number);
        } else {
            if (number > 100) {
                return String.format("%s %s", numToWord.get(number / 100 * 100), numToEng(number % 100));
            } else {
                return String.format("%s %s", numToWord.get(number / 10 * 10), numToEng(number % 10));
            }
        }
    }

    public static String numToRus(int number) {
        HashMap<Integer, String> numToWord = new HashMap<>(){{
            put(0, "????????");
            put(1, "????????");
            put(2, "??????");
            put(3, "??????");
            put(4, "????????????");
            put(5, "????????");
            put(6, "??????????");
            put(7, "????????");
            put(8, "????????????");
            put(9, "????????????");
            put(10, "????????????");
            put(11, "????????????????????");
            put(12, "????????????????????");
            put(13, "????????????????????");
            put(14, "????????????????????????");
            put(15, "????????????????????");
            put(16, "??????????????????????");
            put(17, "????????????????????");
            put(18, "????????????????????????");
            put(19, "??????????????????????");
            put(20, "????????????????");
            put(30, "????????????????");
            put(40, "??????????");
            put(50, "????????????????");
            put(60, "????????????????????");
            put(70, "??????????????????");
            put(80, "??????????????????????");
            put(90, "??????????????????????");
            put(100, "??????");
            put(200, "????????????");
            put(300, "????????????");
            put(400, "??????????????????");
            put(500, "??????????????");
            put(600, "????????????????");
            put(700, "??????????????");
            put(800, "??????????????????");
            put(900, "??????????????????");
        }};

        if (numToWord.containsKey(number)) {
            return numToWord.get(number);
        } else {
            if (number > 100) {
                return String.format("%s %s", numToWord.get(number / 100 * 100), numToRus(number % 100));
            } else {
                return String.format("%s %s", numToWord.get(number / 10 * 10), numToRus(number % 10));
            }
        }
    }

    public static String getSha256Hash(String message) {
        return Hashing.sha256().hashString(message, StandardCharsets.UTF_8).toString();
    }

    public static String correctTitle(String message) {
        List<String> exceptions = List.of("and", "the", "of", "in");
        Function<String, String> clearWord = word -> word.replaceAll("(\\.|\\,)", "");
        return Arrays.stream(message.toLowerCase(Locale.ROOT).split(" "))
                .map(word -> {
                    if (!exceptions.contains(clearWord.apply(word))) {
                        return String.format("%s%s", word.substring(0, 1).toUpperCase(Locale.ROOT), word.substring(1));
                    }
                    return word;
                })
                .collect(Collectors.joining(" "));
    }

    public static String hexLattice(int number) {
        BiFunction<Integer, Integer, String> createRow = (elementsPerRow, maxElementsPerRow) -> {
            StringBuilder row = new StringBuilder();

            row.append(" ".repeat(Math.max(0, maxElementsPerRow - elementsPerRow + 1)));

            for (int j = 0; j < elementsPerRow; ++j) {
                row.append('0');
                row.append(' ');
            }

            row.append(" ".repeat(Math.max(0, maxElementsPerRow - elementsPerRow)));

            row.append('\n');

            return row.toString();
        };

        if (number == 1) {
            return " 0 ";
        } else if ((number - 1) % 18 == 0 || number == 7) {
            StringBuilder result = new StringBuilder();
            StringBuilder roofOfHex = new StringBuilder();
            int maxElementPerRow = 2 * ((number - 1) / 18 + 1) + 1, rowQuantity = (number - 1) / 18 + 1;
            int startElementsPerRow = maxElementPerRow - rowQuantity;

            for (int i = 0; i < rowQuantity; ++i) {
                int currentElementsPerRow = startElementsPerRow + i;
                roofOfHex.append(createRow.apply(currentElementsPerRow, maxElementPerRow));
            }

            result.append(roofOfHex);
            result.append(createRow.apply(maxElementPerRow, maxElementPerRow));
            result.append(roofOfHex.reverse().deleteCharAt(0));

            return result.toString();
        } else {
            return "Invalid";
        }
    }

    public static void main(String[] args) {
        // First task
        System.out.println(Arrays.toString(encrypt("Hello")));
        System.out.println(decrypt(new int[]{ 72, 33, -73, 84, -12, -3, 13, -13, -68}));
        System.out.println(Arrays.toString(encrypt("Sunshine")));

        // Second task
        System.out.println(canMove("Rook", "A8", "H8"));
        System.out.println(canMove("Bishop", "A7", "G1"));
        System.out.println(canMove("Queen", "C4", "D6"));

        // Third task
        System.out.println(canComplete("butl", "beautiful"));
        System.out.println(canComplete("butlz", "beautiful"));
        System.out.println(canComplete("tulb", "beautiful"));
        System.out.println(canComplete("bbutl", "beautiful"));

        // Fourth task
        System.out.println(sumDigProd(16, 28));
        System.out.println(sumDigProd(0));
        System.out.println(sumDigProd(1, 2, 3, 4, 5, 6));

        // Fith task
        System.out.println(sameVowelGroup(new String[]{"toe", "ocelot", "maniac"}));
        System.out.println(sameVowelGroup(new String[]{"many", "carriage", "emit", "apricot", "animal"}));
        System.out.println(sameVowelGroup(new String[]{"hoops", "chuff", "bot", "bottom"}));

        // Sixth task
        System.out.println(validateCard(1234567890123456L));
        System.out.println(validateCard(1234567890123452L));

        // Seventh task
        System.out.println(numToEng(0));
        System.out.println(numToEng(18));
        System.out.println(numToEng(126));
        System.out.println(numToEng(909));
        System.out.println(numToRus(0));
        System.out.println(numToRus(18));
        System.out.println(numToRus(126));
        System.out.println(numToRus(909));

        // Eight task
        System.out.println(getSha256Hash("password123"));
        System.out.println(getSha256Hash("Fluffy@home"));
        System.out.println(getSha256Hash("Hey dude!"));

        // Ninth task
        System.out.println(correctTitle("jOn SnoW, kINg IN thE noRth."));
        System.out.println(correctTitle("sansa stark, lady of winterfell."));
        System.out.println(correctTitle("TYRION LANNISTER, HAND OF THE QUEEN."));

        // Tenth task
        System.out.println(hexLattice(1));
        System.out.println(hexLattice(7));
        System.out.println(hexLattice(19));
        System.out.println(hexLattice(21));
        System.out.println(hexLattice(37));
    }
}