package MainEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public static LocalDateTime inputDateTime() {
        LocalDateTime orderTime;
        String dateTimeInput = inputString();
        if (dateTimeInput.isEmpty()) {
            return LocalDateTime.now();
        }
        try {
            orderTime = LocalDateTime.parse(dateTimeInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException e) {
            System.out.println("Неверный формат даты и времени!");
            return inputDateTime();
        }
        return orderTime;
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
