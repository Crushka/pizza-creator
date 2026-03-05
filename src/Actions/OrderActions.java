package Actions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import Order.Order;
import MainEvent.*;
import ObjectClasses.*;

public class OrderActions {

    DataBase dataBase;
    HomeActions homeActions;
    Input input;

    public OrderActions(DataBase dataBase, Input input, HomeActions homeActions) {
        this.dataBase = dataBase;
        this.input = input;
        this.homeActions = homeActions;
    }

    public void startOrderActions() {
        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.println("1. Создать заказ");
            System.out.println("2. Просмотреть незавершенные заказы");
            System.out.println("3. Просмотреть историю заказов");
            System.out.println("4. Назад");

            System.out.print("Выберите действие: ");
            int choice = input.inputInt();

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
                    homeActions.home();
                    return;
                default:
                    homeActions.errorChoice();
            }
        }
    }

    public void viewCompletedOrders() {
        if (dataBase.getCompletedOrdersList().isEmpty()) {
            System.out.println("Нет завершенных заказов для отображения.");
            startOrderActions();
            return;
        }

        System.out.println(homeActions.string_separator);
        System.out.println("Показать заказы начиная с даты дд.мм.гггг (пусто - показать все): ");
        java.time.LocalDate filterDate = input.inputLocalDate();
        List<Order> toShow = Filtration.filterOrdersByDate(dataBase.getCompletedOrdersList(), filterDate);
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
            System.out.println(homeActions.string_separator);
            System.out.println("Выберите действие:");
            System.out.println("1. Назад");

            System.out.print("Введите номер: ");
            int choice = input.inputInt();

            switch (choice) {
                case 1:
                    startOrderActions();
                    break;
                default:
                    homeActions.errorChoice();
            }
        }
    }

    public void viewUncompletedOrders() {
        if (dataBase.getUncompletedOrdersList().isEmpty()) {
            System.out.println("Нет заказов для отображения.");
            startOrderActions();
            return;
        }

        System.out.println(homeActions.string_separator);
        System.out.println("Список заказов:");
        int num = 1;
        for (Order order : dataBase.getUncompletedOrdersList()) {
            System.out.println(num + ". " + order.toString());
            num++;
        }

        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.println("Выберите действие:");
            System.out.println("1. Удалить заказ");
            System.out.println("2. Изменить заказ");
            System.out.println("3. Сделать заказ");
            System.out.println("4. Назад");

            System.out.print("Введите номер: ");
            int choice = input.inputInt();

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
                    homeActions.errorChoice();
            }
        }
    }

    public void deleteOrder() {
        System.out.print("Введите номер заказа для удаления: ");
        int inp = input.inputInt();

        if (inp >= 1 && inp <= dataBase.getUncompletedOrdersList().size()) {
            Order del_order = dataBase.getUncompletedOrdersList().get(inp - 1);
            dataBase.deleteUncompletedOrder(del_order);
            System.out.println("Заказ удален.");
        } else {
            System.out.println("Неверный номер заказа.");
        }
    }

    public void createOrder() {
        System.out.println(homeActions.string_separator);
        System.out.print("Введите комментарий к заказу (или оставьте пустым): ");
        String comment = input.inputString();

        ArrayList<PizzaWithSize> pizzas = new ArrayList<>();
        Order order = new Order(pizzas, comment);
        addPizzas(order, false);

        if (!order.getPizzas().isEmpty()) {
            dataBase.addUncompletedOrderToList(order);
            System.out.println("Заказ успешно создан!");
        }
    }

    public void editUncompletedOrder() {
        while (true) {
            System.out.println(homeActions.string_separator);   
            System.out.print("Выберите номер заказа для редактирования: ");
            int choice = input.inputInt();
            if ((choice > dataBase.getUncompletedOrdersList().size()) || (choice < 1)) {
                homeActions.errorChoice();
                editUncompletedOrder();
                return;
            }

            Order cur_order = dataBase.getUncompletedOrdersList().get(choice - 1);
            System.out.println("Выберите действие:");
            System.out.println("1. Изменить комментарий");
            System.out.println("2. Добавить ещё пиццы!");
            System.out.println("3. Редактировать размер пиццы");
            System.out.println("4. Удалить пиццу");
            System.out.println("5. Назад");

            System.out.print("Введите номер: ");
            int order_choice = input.inputInt();

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
                    homeActions.errorChoice();
                    break;
            }
        }
    }

    public void changeComment(Order order) {
        System.out.println(homeActions.string_separator);
        System.out.print("Введите новый комментарий: ");

        String new_comment = input.inputString();
        order.setComment(new_comment);
        System.out.println("Комментарий успешно изменён!");
        editUncompletedOrder();
    }

    public void addPizzas(Order order, boolean from_edit) {
        System.out.println(homeActions.string_separator);
        System.out.println("Выберите пиццу:");
        
        int global_num = 1;
        System.out.println("Пиццы пользователя:");
        for (CustomPizza pizza : dataBase.getCustomPizzaList()) {
            System.out.println(global_num + ". " + pizza.toString());
            global_num++;
        }

        System.out.println(homeActions.string_separator);
        System.out.println("Пиццы в системе:");
        for (SystemPizza pizza : dataBase.getSystemPizzaList()) {
            System.out.println(global_num + ". " + pizza.toString());
            global_num++;
        }

        System.out.println(homeActions.string_separator);
        System.out.println("Комбинированные пиццы:");
        for (CombinedPizza pizza : dataBase.getCombinedPizzaList()) {
            System.out.println(global_num + ". " + pizza.toString());
            global_num++;
        }
        System.out.println(global_num + ". Назад");

        System.out.println(homeActions.string_separator);
        System.out.print("Выберите номера пицц для заказа (формат: номер-размер, например: 1-0.5, 4-1, 2-2): ");
        String inp = input.inputString();
        String[] pizza_entries = inp.trim().split(",");

        if (pizza_entries[0].trim().equals(String.valueOf(global_num))) {
            if (!from_edit) homeActions.home();
            else editUncompletedOrder();
            return;
        }

        ArrayList<PizzaWithSize> selected_pizzas = new ArrayList<>();
        for (String entry : pizza_entries) {
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
                    int customSize = dataBase.getCustomPizzaList().size();
                    int systemSize = dataBase.getSystemPizzaList().size();
                    if (pizzaNum <= customSize) {
                        pizza = dataBase.getCustomPizzaList().get(pizzaNum - 1);
                    } 
                    else if (pizzaNum <= customSize + systemSize) {
                        pizza = dataBase.getSystemPizzaList().get(pizzaNum - customSize - 1);
                    } 
                    else {
                        pizza = dataBase.getCombinedPizzaList().get(pizzaNum - customSize - systemSize - 1);
                    }
                    selected_pizzas.add(new PizzaWithSize(pizza, sizeCoefficient));
                } else {
                    System.out.println("Номер пиццы " + pizzaNum + " вне диапазона!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат запроса: " + entry + " (используйте формат: номер-размер)");
            }
        }

        if (selected_pizzas.isEmpty()) {
            System.out.println("Вы не выбрали ни одной пиццы!");
            if (!from_edit) createOrder();
            else editUncompletedOrder();
            return;
        }

        for (int i = 0; i < selected_pizzas.size(); i++) {
            PizzaWithSize sized_pizza = selected_pizzas.get(i);
            if (sized_pizza.getPizza() instanceof SystemPizza) {
                System.out.println(homeActions.string_separator);
                System.out.println("Желаете ли вы удвоить ингредиенты в выбранной пицце " + sized_pizza.getPizza().getName() + "?");

                while (true) {
                    System.out.print("Введите номер (1 - да / 2 - нет): ");
                    int double_choice = input.inputInt();

                    if (double_choice == 1) {
                        SystemPizza orig = (SystemPizza) sized_pizza.getPizza();
                        SystemPizza cloned = orig.copy();
                        cloned.ingredientsDouble();

                        PizzaWithSize new_sized = new PizzaWithSize(cloned, sized_pizza.getSizeCoefficient());
                        selected_pizzas.set(i, new_sized);

                        System.out.println("Ингредиенты удвоены!");
                        break;
                    }
                    else if (double_choice == 2) {
                        break;
                    }
                    else {
                        homeActions.errorChoice();
                        continue;
                    }
                }
            }
        }

        order.addPizza(selected_pizzas);
    }

    public void editPizzaSize(Order order) {
        System.out.println(homeActions.string_separator);
        System.out.println("Выберите пиццу для изменения размера:");
        ArrayList<PizzaWithSize> pizzas = order.getPizzas();

        int num = 1;
        for (PizzaWithSize pizza : pizzas) {
            System.out.println(num++ + ". " + pizza.getPizza().getName() + "\t" + pizza.getSizeCoefficient());
        }
        System.out.println(num + ". Назад");

        while (true) {
            System.out.print("Введите номер: ");
            int pizza_num = input.inputInt();

            if (pizza_num == num) {
                editUncompletedOrder();
                return;
            }

            if ((pizza_num > 0) && (pizza_num < num)) {
                PizzaWithSize cur_pizza = pizzas.get(pizza_num - 1);
                System.out.print("Введите новый размер для пиццы (0.5 - 1 - 2): ");
                float new_size = input.inputFloat();

                cur_pizza.setSizeCoefficient(new_size);
                System.out.println("Размер успешно задан!");
                editUncompletedOrder();
                return;
            }
            else {
                homeActions.errorChoice();
            }
        }
    }

    public void deletePizza(Order order) {
        System.out.println(homeActions.string_separator);
        System.out.println("Выберите пиццу для удаления:");
        ArrayList<PizzaWithSize> pizzas = order.getPizzas();

        int num = 1;
        for (PizzaWithSize pizza : pizzas) {
            System.out.println(num++ + ". " + pizza.getPizza().getName() + "\t" + pizza.getSizeCoefficient());
        }
        System.out.println(num + ". Назад");

        while (true) {
            System.out.print("Введите номер: ");
            int pizza_num = input.inputInt();

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
                homeActions.errorChoice();
            }
        }
    }

    public void completeOrder() {
        System.out.println(homeActions.string_separator);
        System.out.print("Выберите номер заказа: ");

        int choice = input.inputInt();
        if ((choice > dataBase.getUncompletedOrdersList().size()) || (choice < 1)) {
            homeActions.errorChoice();
            completeOrder();
            return;
        }

        Order cur_order = dataBase.getUncompletedOrdersList().get(choice - 1);

        System.out.print("Введите время заказа (или оставьте пустым для текущего времени), формат ГГГГ-ММ-ДД ЧЧ:ММ: ");
        LocalDateTime inputTime = input.inputDateTime();

        cur_order.setOrderTime(inputTime);
        dataBase.addCompletedOrderToList(cur_order);
        dataBase.deleteUncompletedOrder(cur_order);
        System.out.println("Заказ успешно создан! Ожидайте его в течении 10 минут!");
        startOrderActions();
        return;
    }
}
