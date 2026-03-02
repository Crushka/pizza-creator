package Actions;

import java.util.ArrayList;
import java.util.List;

import MainEvent.*;
import ObjectClasses.*;

public class PizzaActions {

    /// Эти комментарии честно-честно я добавляю, просто строк много и потеряться легко(
    /// БЛОК ПИЦЦ

    public static void createNewPizza() {
        System.out.println(Main.string_separator);
        System.out.print("Введите название пиццы: ");
        String name = Input.inputString();

        if (DataBase.getCustomPizzaList().stream().anyMatch(p -> p.getName().equals(name))) {
            System.out.println("Пицца с таким названием уже существует!");
            createNewPizza();
        }

        System.out.println("Выберите основу для пиццы:");
        int num = 1;
        for (PizzaBase pizza_base : DataBase.getPizzaBaseList()) {
            System.out.println(num + ". " + pizza_base.getName() + "\t" + pizza_base.getPrice() + "руб.");
            num++;
        }
        System.out.println(num + ". Назад");
        
        System.out.print("Введите номер: ");
        int choice = Input.inputInt();

        if (choice == num)
            Main.home();

        else if ((choice > num) || (choice <= 0)) {
            Main.errorChoice();
            createNewPizza();
        }
        CustomPizza pizza = new CustomPizza(name, DataBase.getPizzaBaseList().get(choice - 1));

        DataBase.getCustomPizzaList().add(pizza);
        System.out.println("Пицца успешно создана!");
        System.out.println(Main.string_separator);
        Main.home();
    }

    public static void getCustomPizzaListInfo() {
        System.out.println(Main.string_separator);

        if (DataBase.getCustomPizzaList().isEmpty()) {
            System.out.println("Вы не создали ни одной пиццы!");
            Main.home();
        }

        List<IPizza> toShow = askFilterPizzasByIngredient(new ArrayList<>(DataBase.getCustomPizzaList()));
        if (toShow.isEmpty()) {
            System.out.println("Нет пицц, подходящих под фильтр.");
            Main.home();
            return;
        }

        int num = 1;
        for (IPizza pizza : toShow) {
            System.out.println(num + ". " + pizza.toString());
            num++;
        }

        System.out.println(Main.string_separator);
        System.out.println("Выберите действие:");
        System.out.println("1. Удалить пиццу");
        System.out.println("2. Изменить название пиццы");
        System.out.println("3. Назад");

        System.out.print("Введите номер: ");
        int choice = Input.inputInt();

        switch (choice) {
            case 1:
                System.out.print("Введите номер пиццы для удаления: ");
                int choice2 = Input.inputInt();
                if ((choice2 > num) || (choice2 <= 0)) {
                    Main.errorChoice();
                    getCustomPizzaListInfo();
                }

                CustomPizza cur_pizza = (CustomPizza) toShow.get(choice2 - 1);
                boolean isInCombPizza = false;
                for (CombinedPizza combined_pizza : DataBase.getCombinedPizzaList()) {
                    if ((combined_pizza.getPizza1() == cur_pizza) || (combined_pizza.getPizza2() == cur_pizza)) {
                        isInCombPizza = true;
                        System.out.println("Вы не можете удалить эту пиццу, так как она уже содержится в комбинированной пицце " + combined_pizza.getName() + "!");
                    }
                }

                if (!isInCombPizza) {
                    DataBase.getCustomPizzaList().remove(cur_pizza);
                    System.out.println("Пицца успешно удалена!");
                }

                Main.home();
                break;
            case 2:
                System.out.println(Main.string_separator);
                System.out.print("Введите номер пиццы для изменения названия: ");
                int choice3 = Input.inputInt();
                if ((choice3 > num) || (choice3 <= 0)) {
                    Main.errorChoice();
                    getCustomPizzaListInfo();
                }
                System.out.print("Введите новое название: ");
                String newName = Input.inputString();
                if (DataBase.getCustomPizzaList().stream().anyMatch(p -> p.getName().equals(newName))) {
                    System.out.println("Пицца с таким названием уже существует!");
                    getCustomPizzaListInfo();
                }
                ((CustomPizza) toShow.get(choice3 - 1)).setName(newName);
                System.out.println("Название успешно изменено!");
                Main.home();
                break;
            case 3:
                Main.home();
                break;
            default:
                Main.errorChoice();
                getCustomPizzaListInfo();
                break;
        }
    }

    /// БЛОК ОСНОВ

    public static void changePizzaBase(CustomPizza pizza) {
        System.out.println(Main.string_separator);
        System.out.println("Вы не можете удалить основу пиццы! Желаете ли вы её поменять?");
        System.out.println("1. Поменять");
        System.out.println("2. Назад");
        
        System.out.print("Введите номер: ");
        int choice = Input.inputInt();

        switch (choice) {
            case 1:
                System.out.println(Main.string_separator);
                System.out.println("Выберите новую основу:");

                int num = 1;
                for (PizzaBase base : DataBase.getPizzaBaseList()) {
                    System.out.println(num + ". " + base.getName() + "\t" + base.getPrice());
                    num++;
                }
                System.out.println(num + ". Назад");

                System.out.print("Введите номер: ");
                choice = Input.inputInt();

                if (choice == num)
                    Main.home();
                else if ((choice > num) || (choice <= 0)) {
                    Main.errorChoice();
                    delIngFromPizza(pizza);
                }

                pizza.addBase(DataBase.getPizzaBaseList().get(choice - 1));
                System.out.println("Основа успешно изменена!");
                Main.home();
                break;
            case 2:
                Main.home();
                break;
            default:
                Main.errorChoice();
                delIngFromPizza(pizza);
                break;
        }
    }

    /// БЛОК ИНГРЕДИЕНТОВ

    public static void addORdelPizzaIngredient() {
        System.out.println(Main.string_separator);
        if (DataBase.getCustomPizzaList().isEmpty()) {
            System.out.println("Вы не создали ни одной пиццы!");
            Main.home();
        }

        System.out.println("Выберите пиццу: ");
        int num = 1;

        for (CustomPizza pizza : DataBase.getCustomPizzaList()) {
            System.out.println(num + ". " + pizza.getName() + "\t" + pizza.getPrice());
            num++;
        }
        System.out.println(num + ". Назад");

        System.out.print("Введите номер: ");
        int choice = Input.inputInt();

        if (choice == num)
            Main.home();

        else if ((choice > num) || (choice <= 0)) {
            Main.errorChoice();
            Main.home();
        }
                
        while (true) {
            System.out.println(Main.string_separator);
            System.out.println("Выберите действие:");
            System.out.println("1. Добавить ингредиент");
            System.out.println("2. Удалить ингредиент");

            System.out.print("Введите номер: ");
            int choice_sec = Input.inputInt();
            switch (choice_sec) {
                case 1:
                    System.out.println(Main.string_separator);
                    addIngToPizza(DataBase.getCustomPizzaList().get(choice - 1));
                    break;
                case 2:
                    System.out.println(Main.string_separator);
                    delIngFromPizza(DataBase.getCustomPizzaList().get(choice - 1));
                    break;
                default:
                    Main.errorChoice();
            }
        }
    }

    public static void addIngToPizza(CustomPizza pizza) {
        System.out.println(Main.string_separator);
        System.out.println("Выберите ингредиент для пиццы: ");
        int num = 1;

        for (Ingredient ingredient : DataBase.getIngredientsList()) {
            System.out.println(num + ". " + ingredient.getName() + "\t" + ingredient.getPrice() + "руб.");
            num++;
        }
        System.out.println(num + ". Назад");

        System.out.print("Введите номер: ");
        int choice = Input.inputInt();

        if (choice == num)
            Main.home();
        else if ((choice > num) || (choice <= 0)) {
            Main.errorChoice();
            addIngToPizza(pizza);
        }

        pizza.addIngredient(DataBase.getIngredientsList().get(choice - 1));
        System.out.println("Ингредиент успешно добавлен!");
        
        while (true) {
            System.out.println("1. Добавить ещё");
            System.out.println("2. Назад");
            System.out.print("Введите номер: ");
            choice = Input.inputInt();

            switch (choice) {
                case 1:
                    addIngToPizza(pizza);
                    break;
                case 2:
                    Main.home();
                    break;
                default:
                    Main.errorChoice();
                    break;
            }
        }
    }

    public static void delIngFromPizza(CustomPizza pizza) {
        System.out.println(Main.string_separator);
        System.out.println("Выберите ингредиент для удаления: ");
        int num = 1;

        for (Ingredient ingredient : pizza.getIngredientInfo()) {
            System.out.println(num + ". " + ingredient.getName() + "\t" + ingredient.getPrice() + "руб.");
            num++;
        }
        System.out.println(num++ + ". Основа: " + pizza.getBaseInfo().getName() + "\t");
        System.out.println(num + ". Назад");
        
        System.out.print("Введите номер: ");
        int choice = Input.inputInt();

        if (choice == num)
            Main.home();
        else if (choice == (num - 1)) {
            changePizzaBase(pizza);
        }
        else if ((choice > num) || (choice <= 0)) {
            Main.errorChoice();
            delIngFromPizza(pizza);
        }

        pizza.deleteIngredient(choice - 1);
        System.out.println("Ингредиент успешно удалён!");

        while (true) {
            System.out.println("1. Удалить ещё");
            System.out.println("2. Назад");
            System.out.print("Введите номер: ");
            choice = Input.inputInt();

            switch (choice) {
                case 1:
                    delIngFromPizza(pizza);
                    break;
                case 2:
                    Main.home();
                    break;
                default:
                    Main.errorChoice();
                    break;
            }
        }
    }

    /// БЛОК БОРТИКОВ

    public static void addORdelPizzaCrust() {
        System.out.println(Main.string_separator);
        ArrayList<IPizza> all_pizzas = DataBase.getAllPizzaList();

        if (all_pizzas.isEmpty()) {
            System.out.println("Вы не создали ни одной пиццы!");
            Main.home();
            return;
        }

        if (DataBase.getCrustsList().isEmpty()) {
            System.out.println("Нет бортиков для добавления!");
            Main.home();
            return;
        }

        System.out.println("Выберите пиццу: ");
        int num = 1;

        for (IPizza pizza : all_pizzas) {
            System.out.println(num + ". " + pizza.getName() + "\t" + pizza.getPrice());
            num++;
        }
        System.out.println(num + ". Назад");

        System.out.print("Введите номер: ");
        int choice = Input.inputInt();

        if (choice == num)
            Main.home();

        else if ((choice > num) || (choice <= 0)) {
            Main.errorChoice();
            Main.home();
        }

        while (true) {
            System.out.println(Main.string_separator);
            System.out.println("Выберите действие:");
            System.out.println("1. Добавить бортик");
            System.out.println("2. Удалить бортик");

            System.out.print("Введите номер: ");
            int choice_sec = Input.inputInt();
            IPizza cur_pizza = all_pizzas.get(choice - 1);
            switch (choice_sec) {
                case 1:
                    addCrustToPizza(cur_pizza);
                    break;
                case 2:
                    delCrustFromPizza(cur_pizza);
                    break;
                default:
                    Main.errorChoice();
            }
        }
    }

    public static void addCrustToPizza(IPizza pizza) {
        System.out.println(Main.string_separator);
        System.out.println("Доступные бортики для этой пиццы: ");
        int num = 1;
        ArrayList<Crust> accessible_crusts = new ArrayList<>();
        for (Crust crust : DataBase.getCrustsList()) {
            if (crust.isCompatibleWithPizza(pizza)) {
                System.out.println(num++ + ". " + crust.toString());
                accessible_crusts.add(crust);
            }
        }

        if (accessible_crusts.isEmpty()) {
            System.out.println("Для этой пиццы нельзя добавить бортик!");
            addORdelPizzaCrust();
            return;
        }

        System.out.println(num + ". Назад");

        while (true) {
            System.out.println(Main.string_separator);
            System.out.print("Введите номер: ");
            int choice = Input.inputInt();

            if (choice == num) {
                addORdelPizzaCrust();
                return;
            }
            if ((choice > num) || (choice <= 0)) {
                Main.errorChoice();
                continue;
            }
            
            Crust cur_crust = accessible_crusts.get(choice - 1);

            if (pizza.getCrustInfo().contains(cur_crust)) {
                System.out.println("Эта пицца уже содержит этот бортик!");
                continue;
            }

            pizza.addCrust(cur_crust);
            System.out.println("Бортик успешно добавлен!");
            addORdelPizzaCrust();
            return;
        }
    }

    public static void delCrustFromPizza(IPizza pizza) {
        System.out.println(Main.string_separator);
        ArrayList<Crust> included_crusts = new ArrayList<>();

        if (included_crusts.isEmpty()) {
            System.out.println("У этой пиццы нет бортиков!");
            addORdelPizzaCrust();
            return;
        }

        System.out.println("Выберите бортик для удаления:");

        int num = 1;
        for (Crust crust : included_crusts) {
            System.out.println(num++ + ". " + crust.getName() + "\t" + crust.getPrice());
        }
        System.out.println(num + ". Назад");

        while (true) {
            System.out.print("Введите номер: ");
            int choice = Input.inputInt();

            if (choice == num) {
                addORdelPizzaCrust();
                return;
            }
            if ((choice > num) || (choice <= 0)) {
                Main.errorChoice();
            }

            Crust cur_crust = included_crusts.get(choice - 1);
            pizza.delCrust(cur_crust);
            System.out.println("Бортик успешно удален!");
            addORdelPizzaCrust();
            return;
        }
    }

    /// БЛОК СИСТЕМНЫХ ПИЦЦ

    private static List<IPizza> askFilterPizzasByIngredient(List<IPizza> pizzas) {
        System.out.println("Фильтр по ингредиенту (название или пусто - показать все): ");
        String name = Input.inputString();
        return Filtration.filterPizzasByIngredient(pizzas, name);
    }

    public static void getSystemPizzaList() {
        System.out.println(Main.string_separator);

        List<IPizza> toShow = askFilterPizzasByIngredient(new ArrayList<>(DataBase.getSystemPizzaList()));
        if (toShow.isEmpty()) {
            System.out.println("Нет пицц, подходящих под фильтр.");
            System.out.println("1. Назад");
            System.out.print("Введите номер: ");
            if (Input.inputInt() == 1) Main.home();
            else Main.errorChoice();
            getSystemPizzaList();
            return;
        }

        for (IPizza system_pizza : toShow) {
            System.out.println("- " + system_pizza.toString());
        }
        System.out.println("1. Назад");

        System.out.print("Введите номер: ");
        int choice = Input.inputInt();

        switch (choice) {
            case 1:
                Main.home();
                break;
            default:
                Main.errorChoice();
                getSystemPizzaList();
                break;
        }
        return;
    }

    /// БЛОК КОМБИНИРОВАННЫХ ПИЦЦ В CombinedPizzaActions!!!!
}
