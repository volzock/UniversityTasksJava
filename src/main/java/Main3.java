import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main3 {

    public static int solutions(int a, int b, int c) {
        return 1 + Integer.compare(b * b - 4 * a * c, 0);
    }

    public static int findZip(String s) {
        return s.indexOf("zip", s.indexOf("zip") + 1);
    }

    public static boolean checkPerfect(int number) {
        return IntStream.range(1, number/2 + 1).filter(i -> number % i == 0).sum() == number;
    }

    public static String flipEndChars(String s) {
        if (s.length() == 1) {
            return "Incompatible.";
        }
        if (s.charAt(0) == s.charAt(s.length() - 1)) {
            return "Two's a pair.";
        } else {
            StringBuilder result = new StringBuilder(s);
            result.setCharAt(0, s.charAt(s.length() - 1));
            result.setCharAt(s.length() - 1, s.charAt(0));
            return result.toString();
        }
    }

    public static boolean isValidHexCode(String s) {
        return s.toLowerCase(Locale.ROOT).matches("#[a-f\\d]{6}");
    }

    public static boolean same(int[] arr1, int[] arr2) {
        return IntStream.of(arr1).distinct().count() == IntStream.of(arr2).distinct().count();
    }

    public static boolean isKaprekar(int number) {
        String square = String.valueOf(number * number);
        int sum = (square.length() == 1) ? Integer.parseInt(square) :
                Integer.parseInt(square.substring(square.length()/2)) + Integer.parseInt(square.substring(0, square.length()/2));
        return sum == number;
    }

    public static String longestZero(String s){
        return Stream.of(s.split("1")).max(Comparator.comparingInt(String::length)).orElse("");
    }

    public static int nextPrime(int number) {
        return IntStream.iterate(number, i -> i + 1).filter(num -> {
            for (int i = 2; i < Math.round(Math.sqrt(num)) + 1; ++i) {
                if (num % i == 0) {
                    return false;
                }
            }
            return true;
        }).findFirst().getAsInt();
    }

    public static boolean rightTriangle(int... nums) {
        Arrays.sort(nums);
        return nums[0] * nums[0] + nums[1] * nums[1] == nums[2] * nums[2];
    }

    public static void main(String[] args) {
        // first task
            System.out.println(solutions(1, 0, -1));

        //second task
            System.out.println(findZip("all zip files are compressed"));

        // third task
            System.out.println(checkPerfect(6));

        // fourth task
            System.out.println(flipEndChars("Cat, dog, and mouse."));

        // fifth task
            System.out.println(isValidHexCode("#CD5C5Z"));

        // six task
            System.out.println(same(new int[]{1, 2, 3}, new int[]{1, 2, 3, 3, 3,3 ,3 , 3, 3}));

        // seventh task
            System.out.println(isKaprekar(297));

        //eight task
            System.out.println(longestZero("100100100"));

        // nineth task
            System.out.println(nextPrime(11));

        // tenth task
            System.out.println(rightTriangle(70, 130, 110));
    }
}
