package Actions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import Order.Order;
import MainEvent.*;
import ObjectClasses.*;

public class OrderActions {
    private static ArrayList<Order> order_list = new ArrayList<>();

    public static void createOrder() {
        System.out.println(Main.string_separator);
        System.out.println("Пиццы пользователя:");
        int num = 1;
        for (CustomPizza pizza : PizzaActions.getPizzaList()) {
            System.out.println(num + ". " + pizza.toString());
            num++;
        }

        System.out.println(Main.string_separator);
        System.out.println("Пиццы в системе:");
        num = 1;
        for (SystemPizza pizza : PizzaActions.getSystemPizzaList()) {
            System.out.println(num + ". " + pizza.toString());
            num++;
        }

        System.out.println(Main.string_separator);
        System.out.println("Комбинированные пиццы:");
        num = 1;
        for (CombinedPizza pizza : PizzaActions.getCombinedPizzaList()) {
            System.out.println(num + ". " + pizza.toString());
            num++;
        }
        System.out.println(num + ". Назад");

        System.out.println(Main.string_separator);
        System.out.print("Выберите номера пицц для заказа (через запятую): ");
        String input = Input.inputString();
        String[] pizzaNumbers = input.trim().split(",");

        if (pizzaNumbers[0].equals(String.valueOf(num))) {
            Main.home();
            return;
        }

        ArrayList<IPizza> selectedPizzas = new ArrayList<>();
        for (String pizzaNumStr : pizzaNumbers) {
            try {
                int pizzaNum = Integer.parseInt(pizzaNumStr.trim());
                if (pizzaNum >= 1 && pizzaNum < num) {
                    if (pizzaNum <= PizzaActions.getPizzaList().size()) {
                        selectedPizzas.add(PizzaActions.getPizzaList().get(pizzaNum - 1));
                    } else if (pizzaNum <= PizzaActions.getPizzaList().size() + PizzaActions.getSystemPizzaList().size()) {
                        selectedPizzas.add(PizzaActions.getSystemPizzaList().get(pizzaNum - PizzaActions.getPizzaList().size() - 1));
                    } else {
                        selectedPizzas.add(PizzaActions.getCombinedPizzaList().get(pizzaNum - PizzaActions.getPizzaList().size() - PizzaActions.getSystemPizzaList().size() - 1));
                    }
                } else {
                    System.out.println("Номер пиццы " + pizzaNum + " вне диапазона!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат номера пиццы: " + pizzaNumStr);
            }
        }

        if (selectedPizzas.isEmpty()) {
            System.out.println("Вы не выбрали ни одной пиццы!");
            createOrder();
            return;
        }

        System.out.println(Main.string_separator);
        System.out.print("Введите комментарий к заказу (или оставьте пустым): ");
        String comment = Input.inputString();

        System.out.print("Выберите дату и время заказа (в формате ГГГГ-ММ-ДД ЧЧ:ММ, или оставьте пустым для текущего времени): ");
        LocalDateTime orderTime = Input.inputDateTime();

        Order order = new Order(selectedPizzas, comment, orderTime);
        order_list.add(order);
        System.out.println("Заказ успешно создан!");
    }
}
