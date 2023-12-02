import java.util.*;

public class Calculator {
    // Функция для преобразования римских чисел в арабские
    public static int romanToInteger(String s) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int result = 0;
        int prevValue = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            int curValue = romanMap.get(s.charAt(i));

            if (curValue < prevValue) {
                result -= curValue;
            } else {
                result += curValue;
            }
            prevValue = curValue;
        }
        return result;
    }

    // Функция для преобразования арабских чисел в римские
    public static String integerToRoman(int num) {
        if (num < 1 || num > 3999) {
            throw new IllegalArgumentException("Число вне диапазона (1,3999)");
        }

        int[] arabic = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder result = new StringBuilder();
        int i = 0;

        while (num > 0) {
            if (num - arabic[i] >= 0) {
                result.append(roman[i]);
                num -= arabic[i];
            } else {
                i++;
            }
        }
        return result.toString();
    }

    // Функция для проверки используемой системы счисления
    public static void checkNumberSystem(String[] input) {
        if ((input[0].matches("\\d+") && !input[2].matches("\\d+")) || (!input[0].matches("\\d+") && input[2].matches("\\d+"))) {
            throw new IllegalArgumentException("Нельзя использовать одновременно разные системы счисления");
        }
    }

    // Функция для выполнения арифметических операций
    public static int calculate(String[] input) {
        int a, b;
        String operator;
        checkNumberSystem(input); // Проверка на использование одновременно разных систем счисления

        try {
            a = Integer.parseInt(input[0]);
            b = Integer.parseInt(input[2]);
            operator = input[1];
        } catch (NumberFormatException e) {
            a = romanToInteger(input[0]);
            b = romanToInteger(input[2]);
            operator = input[1];
        }

        int result;
        switch (operator) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            default:
                throw new IllegalArgumentException("Неверная операция");
        }

        if (a > 0 && a <= 10 && b > 0 && b <= 10) {
            if (input[0].matches("\\d+") && input[2].matches("\\d+")) {
                return result;
            } else {
                if (result <= 0) {
                    throw new IllegalArgumentException("Результат должен быть положительным римским числом");
                }
                return result;
            }
        } else {
            throw new IllegalArgumentException("Введены недопустимые числа");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (например, 3 + 5):");
        String input = scanner.nextLine();
        String[] values = input.split(" ");

        try {
            int result = calculate(values);
            if (values[0].matches("\\d+") && values[2].matches("\\d+")) {
                System.out.println(result);
            } else {
                System.out.println(integerToRoman(result));
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        scanner.close();
    }
}
