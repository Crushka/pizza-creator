package Actions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import Order.Order;
import MainEvent.*;
import ObjectClasses.*;

public class OrderActions {
    private static ArrayList<Order> uncompleted_order_list = new ArrayList<>();
    private static ArrayList<Order> completed_order_list = new ArrayList<>();

    public static void startOrderActions() {
        while (true) {
            System.out.println(Main.string_separator);
            System.out.println("1. Создать заказ");
            System.out.println("2. Просмотреть незавершенные заказы");
            System.out.println("3. Просмотреть историю заказов");
            System.out.println("4. Назад");

            System.out.print("Выберите действие: ");
            int choice = Input.inputInt();

            switch (choice) {
                case 1:
                    createOrder();
                    break;
                case 2:
                    viewUncompletedOrders();
                    break;
                case 3:
                    viewCompletedOrders();
                    break;
                case 4:
                    Main.home();
                    return;
                default:
                    Main.errorChoice();
            }
        }
    }

    public static void viewCompletedOrders() {
        if (completed_order_list.isEmpty()) {
            System.out.println("Нет завершенных заказов для отображения.");
            startOrderActions();
            return;
        }

        System.out.println(Main.string_separator);
        System.out.println("Фильтр по дате (дд.мм.гггг или пусто — показать все): ");
        java.time.LocalDate filterDate = Input.inputLocalDate();
        List<Order> toShow = Filtration.filterOrdersByDate(completed_order_list, filterDate);
        if (toShow.isEmpty()) {
            System.out.println("Нет заказов за выбранную дату.");
        } else {
            System.out.println("История заказов:");
            int num = 1;
            for (Order order : toShow) {
                System.out.println(num + ". " + order.toString());
                num++;
            }
        }

        while (true) {
            System.out.println(Main.string_separator);
            System.out.println("Выберите действие:");
            System.out.println("1. Назад");

            System.out.print("Введите номер: ");
            int choice = Input.inputInt();

            switch (choice) {
                case 1:
                    startOrderActions();
                    break;
                default:
                    Main.errorChoice();
            }
        }
    }

    public static void viewUncompletedOrders() {
        if (uncompleted_order_list.isEmpty()) {
            System.out.println("Нет заказов для отображения.");
            startOrderActions();
            return;
        }

        System.out.println(Main.string_separator);
        System.out.println("Список заказов:");
        int num = 1;
        for (Order order : uncompleted_order_list) {
            System.out.println(num + ". " + order.toString());
            num++;
        }

        while (true) {
            System.out.println(Main.string_separator);
            System.out.println("Выберите действие:");
            System.out.println("1. Удалить заказ");
            System.out.println("2. Изменить заказ");
            System.out.println("3. Сделать заказ");
            System.out.println("4. Назад");

            System.out.print("Введите номер: ");
            int choice = Input.inputInt();

            switch (choice) {
                case 1:
                    deleteOrder();
                    break;
                case 2:
                    editUncompletedOrder();
                    break;
                case 3:
                    completeOrder();
                    break;
                case 4:
                    startOrderActions();
                    break;
                default:
                    Main.errorChoice();
            }
        }
    }

    public static void deleteOrder() {
        System.out.print("Введите номер заказа для удаления: ");
        int input = Input.inputInt();

        if (input >= 1 && input <= uncompleted_order_list.size()) {
            uncompleted_order_list.remove(input - 1);
            System.out.println("Заказ удален.");
        } else {
            System.out.println("Неверный номер заказа.");
        }
    }

    public static void createOrder() {
        System.out.println(Main.string_separator);
        System.out.print("Введите комментарий к заказу (или оставьте пустым): ");
        String comment = Input.inputString();

        ArrayList<PizzaWithSize> pizzas = new ArrayList<>();
        Order order = new Order(pizzas, comment);
        addPizzas(order, false);

        uncompleted_order_list.add(order);
        System.out.println("Заказ успешно создан!");
    }

    public static void editUncompletedOrder() {
        while (true) {
            System.out.println(Main.string_separator);   
            System.out.print("Выберите номер заказа для редактирования: ");
            int choice = Input.inputInt();
            if ((choice >= uncompleted_order_list.size()) || (choice < 1)) {
                Main.errorChoice();
                editUncompletedOrder();
                return;
            }

            Order cur_order = uncompleted_order_list.get(choice - 1);
            System.out.println("Выберите действие:");
            System.out.println("1. Изменить комментарий");
            System.out.println("2. Добавить ещё пиццы!");
            System.out.println("3. Редактировать размер пиццы");
            System.out.println("4. Удалить пиццу");
            System.out.println("5. Назад");

            System.out.print("Введите номер: ");
            int order_choice = Input.inputInt();

            switch (order_choice) {
                case 1:
                    changeComment(cur_order);
                    break;
                case 2:
                    addPizzas(cur_order, true);
                    break;
                case 3:
                    editPizzaSize(cur_order);
                    break;
                case 4:
                    deletePizza(cur_order);
                    break;
                case 5:
                    viewUncompletedOrders();
                    break;
                default:
                    Main.errorChoice();
                    break;
            }
        }
    }

    public static void changeComment(Order order) {
        System.out.println(Main.string_separator);
        System.out.print("Введите новый комментарий: ");

        String new_comment = Input.inputString();
        order.setComment(new_comment);
        System.out.println("Комментарий успешно изменён!");
        editUncompletedOrder();
    }

    public static void addPizzas(Order order, boolean from_edit) {
        System.out.println(Main.string_separator);
        System.out.println("Выберите пиццу:");
        
        int global_num = 1;
        System.out.println("Пиццы пользователя:");
        for (CustomPizza pizza : DataBase.getCustomPizzaList()) {
            System.out.println(global_num + ". " + pizza.toString());
            global_num++;
        }

        System.out.println(Main.string_separator);
        System.out.println("Пиццы в системе:");
        for (SystemPizza pizza : DataBase.getSystemPizzaList()) {
            System.out.println(global_num + ". " + pizza.toString());
            global_num++;
        }

        System.out.println(Main.string_separator);
        System.out.println("Комбинированные пиццы:");
        for (CombinedPizza pizza : DataBase.getCombinedPizzaList()) {
            System.out.println(global_num + ". " + pizza.toString());
            global_num++;
        }
        System.out.println(global_num + ". Назад");

        System.out.println(Main.string_separator);
        System.out.print("Выберите номера пицц для заказа (формат: номер-размер, например: 1-0.5, 4-1, 2-2): ");
        String input = Input.inputString();
        String[] pizzaEntries = input.trim().split(",");

        if (pizzaEntries[0].trim().equals(String.valueOf(global_num))) {
            if (!from_edit) Main.home();
            else editUncompletedOrder();
            return;
        }

        ArrayList<PizzaWithSize> selectedPizzas = new ArrayList<>();
        for (String entry : pizzaEntries) {
            entry = entry.trim();
            try {
                String[] parts = entry.split("-");
                if (parts.length != 2) {
                    System.out.println("Неверный формат запроса: " + entry + " (используйте формат: номер-размер)");
                    continue;
                }
                
                int pizzaNum = Integer.parseInt(parts[0].trim());
                double sizeCoefficient = Double.parseDouble(parts[1].trim().replace(",", "."));
                
                if (sizeCoefficient <= 0) {
                    System.out.println("Размер должен быть положительным числом: " + entry);
                    continue;
                }
                
                if (pizzaNum >= 1 && pizzaNum < global_num) {
                    IPizza pizza = null;
                    int customSize = DataBase.getCustomPizzaList().size();
                    int systemSize = DataBase.getSystemPizzaList().size();
                    if (pizzaNum <= customSize) {
                        pizza = DataBase.getCustomPizzaList().get(pizzaNum - 1);
                    } 
                    else if (pizzaNum <= customSize + systemSize) {
                        pizza = DataBase.getSystemPizzaList().get(pizzaNum - customSize - 1);
                    } 
                    else {
                        pizza = DataBase.getCombinedPizzaList().get(pizzaNum - customSize - systemSize - 1);
                    }
                    selectedPizzas.add(new PizzaWithSize(pizza, sizeCoefficient));
                } else {
                    System.out.println("Номер пиццы " + pizzaNum + " вне диапазона!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат запроса: " + entry + " (используйте формат: номер-размер)");
            }
        }

        if (selectedPizzas.isEmpty()) {
            System.out.println("Вы не выбрали ни одной пиццы!");
            if (!from_edit) createOrder();
            else editUncompletedOrder();
            return;
        }

        order.addPizza(selectedPizzas);
    }

    public static void editPizzaSize(Order order) {
        System.out.println(Main.string_separator);
        System.out.println("Выберите пиццу для изменения размера:");
        ArrayList<PizzaWithSize> pizzas = order.getPizzas();

        int num = 1;
        for (PizzaWithSize pizza : pizzas) {
            System.out.println(num++ + ". " + pizza.getPizza().getName() + "\t" + pizza.getSizeCoefficient());
        }
        System.out.println(num + ". Назад");

        while (true) {
            System.out.print("Введите номер: ");
            int pizza_num = Input.inputInt();

            if (pizza_num == num) {
                editUncompletedOrder();
                return;
            }

            if ((pizza_num > 0) && (pizza_num < num)) {
                PizzaWithSize cur_pizza = pizzas.get(pizza_num - 1);
                System.out.print("Введите новый размер для пиццы (0.5 - 1 - 2): ");
                float new_size = Input.inputFloat();

                cur_pizza.setSizeCoefficient(new_size);
                System.out.println("Размер успешно задан!");
                editUncompletedOrder();
                return;
            }
            else {
                Main.errorChoice();
            }
        }
    }

    public static void deletePizza(Order order) {
        System.out.println(Main.string_separator);
        System.out.println("Выберите пиццу для удаления:");
        ArrayList<PizzaWithSize> pizzas = order.getPizzas();

        int num = 1;
        for (PizzaWithSize pizza : pizzas) {
            System.out.println(num++ + ". " + pizza.getPizza().getName() + "\t" + pizza.getSizeCoefficient());
        }
        System.out.println(num + ". Назад");

        while (true) {
            System.out.print("Введите номер: ");
            int pizza_num = Input.inputInt();

            if (pizza_num == num) {
                editUncompletedOrder();
                return;
            }

            if ((pizza_num > 0) && (pizza_num < num)) {
                PizzaWithSize cur_pizza = pizzas.get(pizza_num - 1);
                order.deletePizza(cur_pizza);

                System.out.println("Пицца успешно удалена!");
                editUncompletedOrder();
                return;
            }
            else {
                Main.errorChoice();
            }
        }
    }

    public static void completeOrder() {
        System.out.println(Main.string_separator);
        System.out.print("Выберите номер заказа: ");

        int choice = Input.inputInt();
        if ((choice > uncompleted_order_list.size()) || (choice < 1)) {
            Main.errorChoice();
            completeOrder();
            return;
        }

        Order cur_order = uncompleted_order_list.get(choice - 1);
        
        System.out.print("Введите время заказа (или оставьте пустым для текущего времени), формат ГГГГ-ММ-ДД ЧЧ:ММ: ");
        LocalDateTime inputTime = Input.inputDateTime();

        cur_order.setOrderTime(inputTime);
        completed_order_list.add(cur_order);
        uncompleted_order_list.remove(choice - 1);
        System.out.println("Заказ успешно создан! Ожидайте его в течении 10 минут!");
    }
}
