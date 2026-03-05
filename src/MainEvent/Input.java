package MainEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Input {
    public Scanner scanner = new Scanner(System.in, "Windows-1251");

    public int inputInt() {
        try {
            int num = scanner.nextInt();
            if (num < 0) {
                System.out.println("Ошибка! Введите неотрицательное число *-*!");
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

    public float inputFloat() {
        try {
            float num = scanner.nextFloat();
            if (num < 0) {
                System.out.println("Ошибка! Введите неотрицательное число *-*!");
                return inputInt();
            }
            scanner.nextLine();
            return num;
        } catch (Exception e) {
            System.out.println("Ошибка ввода! Введите число.");
            scanner.nextLine();
            return inputFloat();
        }
    }

    public LocalDateTime inputDateTime() {
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

    public LocalDate inputLocalDate() {
        String s = inputString().trim();
        if (s.isEmpty())
            return null;
        try {
            return LocalDate.parse(s, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (DateTimeParseException e) {
            System.out.println("Неверный формат даты! Используйте дд.мм.гггг (например 16.02.2007).");
            return inputLocalDate();
        }
    }

    public String inputString() {
        String str = scanner.nextLine();
        return str;
    }

    public void onClose() {
        try {
            scanner.close();
        } catch (Exception e) {
            System.out.println("Ошибка при закрытии сканнера!");
        }
    }
}
