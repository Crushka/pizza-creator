package Actions;

import java.util.ArrayList;
import java.util.List;

import MainEvent.*;
import ObjectClasses.*;

public class CombinedPizzaActions {
    private static ArrayList<IPizza> system_and_custom_pizzas = new ArrayList<>();

    public static void startCombinedPizzaActions() {
        System.out.println(Main.string_separator);
        System.out.println("Выберите действие: ");
        System.out.println("1. Создать комбинированную пиццу.");
        System.out.println("2. Редактировать/Удалить уже существующую комбинированную пиццу.");
        System.out.println("3. Вывести информацию о комбинированных пиццах.");
        System.out.println("4. Назад");

        System.out.print("Введите номер: ");
        int choice = Input.inputInt();

        switch (choice) {
            case 1:
                createCombinedPizza();
                break;
            case 2:
                changeDelCombinedPizza();
                break;
            case 3:
                getCombinedPizzaListInfo();
                break;
            case 4:
                Main.home();
                break;
            default:
                Main.errorChoice();
                startCombinedPizzaActions();
                break;
        }
    }

    public static void createCombinedPizza() {
        System.out.println(Main.string_separator);
        
        system_and_custom_pizzas.clear();
        system_and_custom_pizzas.addAll(DataBase.getSystemPizzaList());
        system_and_custom_pizzas.addAll(DataBase.getCustomPizzaList());

        int num = 1;
        for (IPizza pizza : system_and_custom_pizzas) {
            System.out.println(num++ + ". " + pizza.toString());
        }
        System.out.println(Main.string_separator);

        /// ПЕРВАЯ ПИЦЦА
        int choice_pizza1;
        while (true) {
            System.out.print("Введите номер для первой пиццы: ");
            choice_pizza1 = Input.inputInt();

            if (choice_pizza1 > system_and_custom_pizzas.size() || choice_pizza1 < 1) {
                Main.errorChoice();
                continue;
            }

            break;
        }

        IPizza pizza1 = system_and_custom_pizzas.get(choice_pizza1 - 1);
        System.out.println(Main.string_separator);

        /// ВТОРАЯ ПИЦЦА
        int choice_pizza2;
        while (true) {
            System.out.print("Введите номер для второй пиццы: ");
            choice_pizza2 = Input.inputInt();

            if (choice_pizza2 > system_and_custom_pizzas.size() || choice_pizza2 < 1) {
                Main.errorChoice();
                continue;
            }

            break;
        }

        IPizza pizza2 = system_and_custom_pizzas.get(choice_pizza2 - 1);
        System.out.println(Main.string_separator);

        if (pizza1 == pizza2) {
            System.out.println("Нельзя выбрать две одинаковые пиццы!");
            createCombinedPizza();
            return;
        }

        /// ВЫБОР ОСНОВЫ ИЗ ДВУХ ПИИЦЦЦИ
        boolean isPizzaEqual = true;
        System.out.println("Выберите основу:");
        System.out.println("1. " + pizza1.getBaseInfo().getName() + "\t" + pizza1.getBaseInfo().getPrice());
        if (pizza1.getBaseInfo() != pizza2.getBaseInfo()) {
            isPizzaEqual = false;
            System.out.println("2. " + pizza2.getBaseInfo().getName() + "\t" + pizza2.getBaseInfo().getPrice());
        }
        
        PizzaBase chosen_base = pizza1.getBaseInfo();;
        while (true) {
            System.out.print("Введите номер: ");
            int choice = Input.inputInt();

            switch (choice) {
                case 1:
                    break;
                case 2:
                    if (isPizzaEqual) {
                        Main.errorChoice();
                        continue;
                    }
                    chosen_base = pizza2.getBaseInfo();
                    break;
                default:
                    Main.errorChoice();
                    continue;
            }
            break;
        }

        CombinedPizza combined_pizza = new CombinedPizza(pizza1, pizza2, chosen_base);
        for (CombinedPizza pizza : DataBase.getCombinedPizzaList()) { /// а есть ли эта пицццаца
            if (((pizza.getPizza1() == combined_pizza.getPizza1() && pizza.getPizza2() == combined_pizza.getPizza2())
                || (pizza.getPizza1() == combined_pizza.getPizza2() && pizza.getPizza2() == combined_pizza.getPizza1()))
                && (pizza.getBaseInfo() == combined_pizza.getBaseInfo())) {

                System.out.println("Такая же комбинированная пицца уже существует!");
                startCombinedPizzaActions();
                return;
            }
        }

        if (!pizza1.getCrustInfo().isEmpty()) {
            combined_pizza.getCrustInfo().addAll(pizza1.getCrustInfo());
        }

        if (!pizza2.getCrustInfo().isEmpty()) {
            combined_pizza.getCrustInfo().addAll(pizza2.getCrustInfo());
        }

        DataBase.getCombinedPizzaList().add(combined_pizza);
        System.out.println("Пицца успешно создана!");
        startCombinedPizzaActions();
        return;
    }

    public static void changeDelCombinedPizza() {
        System.out.println(Main.string_separator);

        if (DataBase.getCombinedPizzaList().isEmpty()) {
            System.out.println("Вы не создали ни одной комбинированной пиццы!");
            startCombinedPizzaActions();
            return;
        }

        System.out.println("Выберите пиццу: ");

        int num = 1;
        for (CombinedPizza pizza : DataBase.getCombinedPizzaList()) {
            System.out.println(num++ + ". " + pizza.getName());
            System.out.println("    Состоит из: " + pizza.getPizza1().getName() + ", " + pizza.getPizza2().getName());
            System.out.println();
        }
        System.out.println(num + ". Назад");

        int choice;
        while (true) {
            System.out.println(Main.string_separator);
            System.out.print("Введите номер: ");
            choice = Input.inputInt();

            if (choice == num) {
                startCombinedPizzaActions();
                return;
            }

            if (choice < 1 || choice > num) {
                Main.errorChoice();
                continue;
            }

            break;
        }

        CombinedPizza cur_pizza = DataBase.getCombinedPizzaList().get(choice - 1);
        
        System.out.println(Main.string_separator);
        System.out.println("Выберите действие: ");
        System.out.println("1. Изменить состав комбинированной пиццы");
        System.out.println("2. Изменить основу комбинированной пиццы");
        System.out.println("3. Добавить/удалить бортики");
        System.out.println("4. Удалить комбинированную пиццу");
        System.out.println("5. Назад");

        while (true) {
            System.out.print("Введите номер: ");
            int blinding_lights = Input.inputInt();

            switch (blinding_lights) {
                case 1:
                    changeCombinedPizza(cur_pizza);
                    break;
                case 2:
                    changeCombinedPizzaBase(cur_pizza);
                    break;
                case 3:
                    addDelCrust(cur_pizza);
                case 4:
                    deleteCombinedPizza(cur_pizza);
                    break;
                case 5:
                    startCombinedPizzaActions();
                    break;
                default:
                    Main.errorChoice();
                    continue;
            }

            break;
        }
        return;
    }

    public static void changeCombinedPizza(CombinedPizza pizza) {
        System.out.println(Main.string_separator);
        System.out.println("Выберите пиццу, которую нужно заменить:");
        System.out.println("1. " + pizza.getPizza1().getName());
        System.out.println("2. " + pizza.getPizza2().getName());

        int pizza_to_change;
        while (true) {
            System.out.print("Введите номер: ");
            pizza_to_change = Input.inputInt();

            if (pizza_to_change > 2 || pizza_to_change < 1) {
                Main.errorChoice();
                continue;
            }
            break;
        }

        System.out.println("Выберите новую пиццу:");

        int num = 1;
        for (IPizza cur_pizza : system_and_custom_pizzas) {
            System.out.println(num++ + ". " + cur_pizza.toString());
        }

        while (true) {
            System.out.println(Main.string_separator);
            System.out.print("Введите номер: ");
            int choice_pizza = Input.inputInt();

            if (choice_pizza > system_and_custom_pizzas.size() || choice_pizza < 1) {
                Main.errorChoice();
                continue;
            }

            IPizza new_pizza = system_and_custom_pizzas.get(choice_pizza - 1);

            switch (pizza_to_change) {
                case 1:
                    if (new_pizza == pizza.getPizza2()) {
                        System.out.println("Вы не можете иметь 2 одинаковые пиццы в комбинированной!");
                        continue;
                    }
                    else {
                        pizza.setPizza1(new_pizza);
                    }
                    break;
                case 2:
                    if (new_pizza == pizza.getPizza1()) {
                        System.out.println("Вы не можете иметь 2 одинаковые пиццы в комбинированной!");
                        continue;
                    }
                    else {
                        pizza.setPizza2(new_pizza);
                    }
                    break;
                default:
                    Main.errorChoice();
                    continue;
            }
            break;
        }
        System.out.println("Вы успешно изменили пиццу " + pizza.getName() + "!");
        startCombinedPizzaActions();
        return;
    }

    public static void changeCombinedPizzaBase(CombinedPizza pizza) {
        System.out.println(Main.string_separator);

        if (pizza.getPizza1().getBaseInfo() == pizza.getPizza2().getBaseInfo()) {
            System.out.println("У этой пиццы нельзя поменять основу! Измените ее состав или создайте новую пиццу.");
            changeDelCombinedPizza();
            return;
        }

        System.out.println(Main.string_separator);
        System.out.println("Выберите новую основу:");
        System.out.println("1. " + pizza.getPizza1().getBaseInfo().getName());
        System.out.println("2. " + pizza.getPizza2().getBaseInfo().getName());

        int base_choice;
        while (true) {
            System.out.println(Main.string_separator);
            System.out.print("Введите номер: ");

            base_choice = Input.inputInt();

            switch (base_choice) {
                case 1:
                    pizza.changeBase(pizza.getPizza1().getBaseInfo());
                    break;
                case 2:
                    pizza.changeBase(pizza.getPizza2().getBaseInfo());
                    break;
                default:
                    Main.errorChoice();
                    continue;
            }

            break;
        }

        System.out.println("Основа успешно изменена!");
        startCombinedPizzaActions();
        return;
    }

    public static void addDelCrust(CombinedPizza pizza) {
        System.out.println(Main.string_separator);
        System.out.println("Бортики в пицце: ");
        boolean isEmpty = false;

        if (pizza.getCrustInfo().isEmpty()) {
            isEmpty = true;
            System.out.println("Вы не добавили ни одного бортика к пицце!");
        }
        else {
            int num = 1;
            for (Crust crust : pizza.getCrustInfo()) {
                System.out.println(num++ + ". " + crust.getName());
            }
        }

        System.out.println(Main.string_separator);
        System.out.println("Выберите действие:");
        System.out.println("1. Добавить бортик");
        System.out.println("2. Удалить бортик.");
        System.out.println("3. Назад");

        while (true) {
            System.out.print("Введите номер: ");
            int choice = Input.inputInt();

            switch (choice) {
                case 1:
                    addCrust(pizza);
                    break;
                case 2:
                    if (isEmpty) {
                        System.out.println("Вы не добавили ни одного бортика к пицце!");
                        addDelCrust(pizza);
                        return;
                    }
                    delCrust(pizza);
                    break;
                case 3:
                    changeDelCombinedPizza();
                    break;
                default:
                    Main.errorChoice();
                    continue;
            }
            break;
        }
        return;
    }

    public static void addCrust(CombinedPizza pizza) {
        System.out.println(Main.string_separator);
        System.out.println("Выберите бортик: ");

        ArrayList<Crust> allowed_crusts = new ArrayList<>();
        boolean is_there_crust = false;
        int num = 1;

        for (Crust crust : DataBase.getCrustsList()) {
            if (crust.getWhiteList().contains(pizza)) {
                is_there_crust = true;
                System.out.println(num++ + ". " + crust.getName() + "\t" + crust.getPrice());
                allowed_crusts.add(crust);
            }
        }

        if (!is_there_crust) {
            System.out.println("Для этой пиццы нельзя добавить бортик! Создайте новый бортик и включите эту пиццу в вайт лист!");
            addDelCrust(pizza);
            return;
        }

        System.out.println(num + ". Назад");

        while (true) {
            System.out.println(Main.string_separator);
            System.out.print("Введите номер: ");
            int crust_choice = Input.inputInt();

            if (crust_choice == num) {
                addDelCrust(pizza);
                return;
            }

            if (crust_choice > num || crust_choice < 1) {
                Main.errorChoice();
                continue;
            }

            pizza.addCrust(allowed_crusts.get(crust_choice - 1));
            break;
        }

        System.out.println("Добавление бортика окончено!");
        addDelCrust(pizza);
        return;
    }

    public static void delCrust(CombinedPizza pizza) {
        System.out.println(Main.string_separator);
        System.out.println("Выберите бортик для удаления:");

        int num = 1;
        for (Crust crust : pizza.getCrustInfo()) {
            System.out.println(num++ + ". " + crust.getName() + "\t" + crust.getPrice());
        }
        System.out.println(num + ". Назад");

        while (true) {
            System.out.print("Введите номер: ");
            int crust_to_delete = Input.inputInt();

            if (crust_to_delete == num) {
                addDelCrust(pizza);
                return;
            }

            if (crust_to_delete > num || crust_to_delete < 1) {
                Main.errorChoice();
                continue;
            }

            pizza.getCrustInfo().remove(crust_to_delete - 1);
            break;
        }
        System.out.println("Бортик успешно удален!");
        addDelCrust(pizza);
        return;
    }

    public static void deleteCombinedPizza(CombinedPizza pizza) {
        System.out.println(Main.string_separator);

        for (Crust crust : DataBase.getCrustsList()) {
            java.util.Iterator<IPizza> it = crust.getWhiteList().iterator();
            while (it.hasNext()) {
                if (it.next().getName().equals(pizza.getName())) {
                    it.remove();
                }
            }
        }

        DataBase.getCombinedPizzaList().remove(pizza);
        System.out.println("Пицца успешно удалена!");
        startCombinedPizzaActions();
        return;
    }

    public static void getCombinedPizzaListInfo() {
        System.out.println(Main.string_separator);

        if (DataBase.getCombinedPizzaList().isEmpty()) {
            System.out.println("Вы не создали ни одной комбинированной пиццы!");
            startCombinedPizzaActions();
            return;
        }

        System.out.println("Фильтр по ингредиенту (оставьте строку пустой для вывода всех элементов): ");
        String filterName = Input.inputString();
        List<IPizza> to_show = Filtration.filterPizzasByIngredient(new ArrayList<>(DataBase.getCombinedPizzaList()), filterName);
        if (to_show.isEmpty()) {
            System.out.println("Нет комбинированных пицц, подходящих под фильтр.");
        } else {
            for (IPizza pizza : to_show) {
                System.out.println("- " + pizza.toString());
            }
        }

        startCombinedPizzaActions();
        return;
    }
}
