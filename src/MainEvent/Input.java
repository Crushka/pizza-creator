package MainEvent;
import java.util.Scanner;

public class Input {
    public static Scanner scanner = new Scanner(System.in, "Windows-1251");

    public static int inputInt() {
        try {
            int num = scanner.nextInt();
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
        return str;
    }

    public static void onClose() {
        try {
            scanner.close();
        } catch (Exception e) {
            System.out.println("Ошибка при закрытии сканнера!");
        }
    }
}
