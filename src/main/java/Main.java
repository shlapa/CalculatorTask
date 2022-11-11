import java.util.*;
import java.util.stream.Collectors;

public class Main {

    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);
        private int value;
        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }

    public static String inRoman (int input){
        if ((input <= 0) || (input > 4000)) {
            throw new IllegalArgumentException(input + " is_not_in_range_0-4000");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
        int i = 0;
        String sb = "";
        while ((input > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= input) {
                sb += currentSymbol.name();
                input -= currentSymbol.getValue();
            } else {
                i++;
            }
        }
        return sb;
    }

    public static int inArabic (String input){
        HashMap<Character,Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        int sum = 0;
        for(int i = 0; i < input.length(); i++){
            int a = map.get(input.charAt(i));
            if(i + 1 < input.length()) {
                int b = map.get(input.charAt(i + 1));
                if(a >= b){
                    sum = sum + a;
                } else {
                    sum = sum - a;
                }
            } else
                sum = sum + a;
        } return sum;
    }

    public static int A(int a, String input, int index){
        if (!input.matches("\\d.*"))
            return inArabic(input.substring(0, index-1));
        return a = Integer.parseInt(input.substring(0, index-1));
    }

    public static int B(int b, String input, int index){
        if (!input.matches(".*\\d$"))
            return inArabic(input.substring(index + 2, input.length()));
        return b = Integer.parseInt(input.substring(index + 2, input.length()));
    }

    public static String calc(String input){
        if (input.matches("\\d.*") != input.matches(".*\\d$"))
            throw new IllegalArgumentException(input + " cannot_be_completed_operation");
        boolean flag_numeric = true;
        if (input.matches("\\d.*"))
            flag_numeric = true;
        else if (!input.matches("\\d.*"))
            flag_numeric = false;
        int result = 0, a = 0, b = 0, plusIndex = input.indexOf("+"), minusIndex = input.indexOf("-"), multiplicationIndex = input.indexOf("*"), divisionIndex= input.indexOf("/");
        if (plusIndex != -1) {
            a = A(a, input, plusIndex);
            b = B(b, input, plusIndex);
            if ((a <= 0) || (a > 10) || (b <= 0) || (b > 10))
                throw new IllegalArgumentException(input + " cannot_be_completed_operation");
            result = a + b;
        } else if (minusIndex != -1) {
            a = A(a, input, minusIndex);
            b = B(b, input, minusIndex);
            if ((a <= 0) || (a > 10) || (b <= 0) || (b > 10))
                throw new IllegalArgumentException(input + " cannot_be_completed_operation");
            result = a - b;
        } else if (multiplicationIndex != -1){
            a = A(a, input, multiplicationIndex);
            b = B(b, input, multiplicationIndex);
            if ((a <= 0) || (a > 10) || (b <= 0) || (b > 10))
                throw new IllegalArgumentException(input + " cannot_be_completed_operation");
            result = a * b;
        } else if (divisionIndex != -1){
            a = A(a, input, divisionIndex);
            b = B(b, input, divisionIndex);
            if ((a <= 0) || (a > 10) || (b <= 0) || (b > 10))
                throw new IllegalArgumentException(input + " cannot_be_completed_operation");
            result = a / b;
        } else throw new IllegalArgumentException(input + " cannot_be_completed_operation");
        if (flag_numeric == true)
            System.out.println(result);
        else
            System.out.println(inRoman(result));
        return Integer.toString(1);
    }

    public static void main (String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println(" enter_command:");
        String input = sc.nextLine();
        calc (input);
    }
}
