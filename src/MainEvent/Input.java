package MainEvent;
import java.util.Scanner;

public class Input {
    public static Scanner scanner = new Scanner(System.in, "UTF-8");

    public static int inputInt() {
        try {
            int num = scanner.nextInt();
            if (num <= 0) {
                System.out.println("Ошибка ввода! Введите положительное число.");
                scanner.nextLine();
                return inputInt();
            }
            scanner.nextLine();
            return num;
        } catch (Exception e) {
            System.out.println("Ошибка ввода! Введите число.");
            scanner.nextLine();
            return inputInt();
        }
    }

    public static float inputFloat() {
        try {
            float num = scanner.nextFloat();
            if (num <= 0) {
                System.out.println("Ошибка ввода! Введите положительное число.");
                scanner.nextLine();
                return inputFloat();
            }
            scanner.nextLine();
            return num;
        } catch (Exception e) {
            System.out.println("Ошибка ввода! Введите число.");
            scanner.nextLine();
            return inputFloat();
        }
    }

    public static String inputString() {
        String str = scanner.nextLine();
        if (str.trim().isEmpty()) {
            System.out.println("Ошибка ввода! Введите непустую строку.");
            return inputString();
        }
        return str;
    }

    public static void onClose() {
        scanner.close();
    }
}
