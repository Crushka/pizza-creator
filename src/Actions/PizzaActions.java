package Actions;

import java.util.ArrayList;
import java.util.List;

import MainEvent.*;
import ObjectClasses.*;
import Order.Order;

public class PizzaActions {

    DataBase dataBase;
    HomeActions homeActions;
    Input input;

    public PizzaActions(DataBase dataBase, Input input, HomeActions homeActions) {
        this.dataBase = dataBase;
        this.input = input;
        this.homeActions = homeActions;
    }

    private boolean isPizzaNameExists(String name) {
        return dataBase.getCustomPizzaList().stream().anyMatch(p -> p.getName().equals(name));
    }

    /// БЛОК ПИЦЦ

    public void createNewPizza() {
        String name;
        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.print("Введите название пиццы: ");
            name = input.inputString();

            if (isPizzaNameExists(name)) {
                System.out.println("Пицца с таким названием уже существует!");
                continue;
            }
            break;
        }

        System.out.println("Выберите основу для пиццы:");
        int num = 1;
        for (PizzaBase pizza_base : dataBase.getPizzaBaseList()) {
            System.out.println(num + ". " + pizza_base.getName() + "\t" + pizza_base.getPrice() + "руб.");
            num++;
        }
        System.out.println(num + ". Назад");
        
        int choice;
        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.print("Введите номер: ");
            choice = input.inputInt();

            if (choice == num)
                homeActions.home();

            else if ((choice > num) || (choice <= 0)) {
                homeActions.errorChoice();
                continue;
            }
            break;
        }

        CustomPizza pizza = new CustomPizza(name, dataBase.getPizzaBaseList().get(choice - 1));

        dataBase.addCustomPizzaToList(pizza);
        System.out.println("Пицца успешно создана!");
        homeActions.home();
        return;
    }

    public void getCustomPizzaListInfo() {
        System.out.println(homeActions.string_separator);

        if (dataBase.getCustomPizzaList().isEmpty()) {
            System.out.println("Вы не создали ни одной пиццы!");
            homeActions.home();
        }

        List<IPizza> toShow = askFilterPizzasByIngredient(new ArrayList<>(dataBase.getCustomPizzaList()));
        if (toShow.isEmpty()) {
            System.out.println("Нет пицц, подходящих под фильтр.");
            homeActions.home();
            return;
        }

        int num = 1;
        for (IPizza pizza : toShow) {
            System.out.println(num + ". " + pizza.toString());
            num++;
        }

        System.out.println(homeActions.string_separator);
        System.out.println("Выберите действие:");
        System.out.println("1. Удалить пиццу");
        System.out.println("2. Изменить название пиццы");
        System.out.println("3. Назад");

        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.print("Введите номер: ");
            int choice = input.inputInt();

            switch (choice) {
                case 1:
                    System.out.print("Введите номер пиццы для удаления: ");
                    int choice2 = input.inputInt();
                    if ((choice2 > num) || (choice2 <= 0)) {
                        homeActions.errorChoice();
                        getCustomPizzaListInfo();
                    }

                    CustomPizza cur_pizza = (CustomPizza) toShow.get(choice2 - 1);
                    boolean isInCombPizza = false;
                    for (CombinedPizza combined_pizza : dataBase.getCombinedPizzaList()) {
                        if ((combined_pizza.getPizza1() == cur_pizza) || (combined_pizza.getPizza2() == cur_pizza)) {
                            isInCombPizza = true;
                            System.out.println("Вы не можете удалить эту пиццу, так как она уже содержится в комбинированной пицце " 
                            + combined_pizza.getName() + "!");
                        }
                    }

                    for (Order order : dataBase.getUncompletedOrdersList()) {
                        order.removePizzaByName(cur_pizza.getName());
                    }

                    if (!isInCombPizza) {
                        dataBase.deleteCustomPizza(cur_pizza);
                        System.out.println("Пицца успешно удалена!");
                    }

                    homeActions.home();
                    break;
                case 2:
                    System.out.println(homeActions.string_separator);
                    System.out.print("Введите номер пиццы для изменения названия: ");
                    int choice3 = input.inputInt();
                    if ((choice3 > num) || (choice3 <= 0)) {
                        homeActions.errorChoice();
                        getCustomPizzaListInfo();
                    }
                    System.out.print("Введите новое название: ");
                    String newName = input.inputString();
                    if (dataBase.getCustomPizzaList().stream().anyMatch(p -> p.getName().equals(newName))) {
                        System.out.println("Пицца с таким названием уже существует!");
                        getCustomPizzaListInfo();
                    }
                    ((CustomPizza) toShow.get(choice3 - 1)).setName(newName);
                    System.out.println("Название успешно изменено!");
                    homeActions.home();
                    break;
                case 3:
                    homeActions.home();
                    break;
                default:
                    homeActions.errorChoice();
                    continue;
            }
            break;
        }
        return;
    }

    /// БЛОК ОСНОВ

    public void changePizzaBase(CustomPizza pizza) {
        System.out.println(homeActions.string_separator);
        System.out.println("Вы не можете удалить основу пиццы! Желаете ли вы её поменять?");
        System.out.println("1. Поменять");
        System.out.println("2. Назад");
        
        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.print("Введите номер: ");
            int choice = input.inputInt();

            switch (choice) {
                case 1:
                    System.out.println(homeActions.string_separator);
                    System.out.println("Выберите новую основу:");

                    int num = 1;
                    for (PizzaBase base : dataBase.getPizzaBaseList()) {
                        System.out.println(num + ". " + base.getName() + "\t" + base.getPrice());
                        num++;
                    }
                    System.out.println(num + ". Назад");

                    while (true) {
                        System.out.print("Введите номер: ");
                        choice = input.inputInt();

                        if (choice == num) {
                            homeActions.home();
                            return;
                        }
                        else if ((choice > num) || (choice <= 0)) {
                            homeActions.errorChoice();
                            continue;
                        }
                        break;
                    }

                    pizza.addBase(dataBase.getPizzaBaseList().get(choice - 1));
                    System.out.println("Основа успешно изменена!");
                    homeActions.home();
                    break;
                case 2:
                    homeActions.home();
                    break;
                default:
                    homeActions.errorChoice();
                    continue;
            }
            break;
        }
        return;
    }

    /// БЛОК ИНГРЕДИЕНТОВ

    public void addORdelPizzaIngredient() {
        System.out.println(homeActions.string_separator);
        if (dataBase.getCustomPizzaList().isEmpty()) {
            System.out.println("Вы не создали ни одной пиццы!");
            homeActions.home();
        }

        System.out.println("Выберите пиццу: ");
        int num = 1;

        for (CustomPizza pizza : dataBase.getCustomPizzaList()) {
            System.out.println(num + ". " + pizza.getName() + "\t" + pizza.getPrice());
            num++;
        }
        System.out.println(num + ". Назад");

        int choice;
        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.print("Введите номер: ");
            choice = input.inputInt();

            if (choice == num) {
                homeActions.home();
                return;
            }
            else if ((choice > num) || (choice <= 0)) {
                homeActions.errorChoice();
                continue;
            }
            break;
        }
                
        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.println("Выберите действие:");
            System.out.println("1. Добавить ингредиент");
            System.out.println("2. Удалить ингредиент");

            System.out.print("Введите номер: ");
            int choice_sec = input.inputInt();
            switch (choice_sec) {
                case 1:
                    System.out.println(homeActions.string_separator);
                    addIngToPizza(dataBase.getCustomPizzaList().get(choice - 1));
                    break;
                case 2:
                    System.out.println(homeActions.string_separator);
                    delIngFromPizza(dataBase.getCustomPizzaList().get(choice - 1));
                    break;
                default:
                    homeActions.errorChoice();
                    continue;
            }
            break;
        }
        return;
    }

    public void addIngToPizza(CustomPizza pizza) {
        System.out.println(homeActions.string_separator);
        System.out.println("Выберите ингредиент для пиццы: ");
        int num = 1;

        for (Ingredient ingredient : dataBase.getIngredientsList()) {
            System.out.println(num + ". " + ingredient.getName() + "\t" + ingredient.getPrice() + "руб.");
            num++;
        }
        System.out.println(num + ". Назад");

        int choice;
        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.print("Введите номер: ");
            choice = input.inputInt();

            if (choice == num) {
                homeActions.home();
                return;
            }
            else if ((choice > num) || (choice <= 0)) {
                homeActions.errorChoice();
                continue;
            }
            break;
        }

        pizza.addIngredient(dataBase.getIngredientsList().get(choice - 1));
        System.out.println("Ингредиент успешно добавлен!");
        
        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.println("1. Добавить ещё");
            System.out.println("2. Назад");
            System.out.print("Введите номер: ");
            choice = input.inputInt();

            switch (choice) {
                case 1:
                    addIngToPizza(pizza);
                    break;
                case 2:
                    homeActions.home();
                    break;
                default:
                    homeActions.errorChoice();
                    continue;
            }
            break;
        }
        return;
    }

    public void delIngFromPizza(CustomPizza pizza) {
        System.out.println(homeActions.string_separator);
        System.out.println("Выберите ингредиент для удаления: ");
        int num = 1;

        for (Ingredient ingredient : pizza.getIngredientInfo()) {
            System.out.println(num + ". " + ingredient.getName() + "\t" + ingredient.getPrice() + "руб.");
            num++;
        }
        System.out.println(num++ + ". Основа: " + pizza.getBaseInfo().getName() + "\t");
        System.out.println(num + ". Назад");
        
        int choice;
        while (true) {
            System.out.print("Введите номер: ");
            choice = input.inputInt();

            if (choice == num) {
                homeActions.home();
                return;
            }
            else if (choice == (num - 1)) {
                changePizzaBase(pizza);
                return;
            }
            else if ((choice > num) || (choice <= 0)) {
                homeActions.errorChoice();
                continue;
            }
            break;
        }

        pizza.deleteIngredient(choice - 1);
        System.out.println("Ингредиент успешно удалён!");

        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.println("1. Удалить ещё");
            System.out.println("2. Назад");
            System.out.print("Введите номер: ");
            choice = input.inputInt();

            switch (choice) {
                case 1:
                    delIngFromPizza(pizza);
                    break;
                case 2:
                    homeActions.home();
                    break;
                default:
                    homeActions.errorChoice();
                    continue;
            }
            break;
        }
        return;
    }

    /// БЛОК БОРТИКОВ

    public void addORdelPizzaCrust() {
        System.out.println(homeActions.string_separator);
        List<IPizza> all_pizzas = dataBase.getAllPizzaList();

        if (all_pizzas.isEmpty()) {
            System.out.println("Вы не создали ни одной пиццы!");
            homeActions.home();
            return;
        }

        if (dataBase.getCrustsList().isEmpty()) {
            System.out.println("Нет бортиков для добавления!");
            homeActions.home();
            return;
        }

        System.out.println("Выберите пиццу: ");
        int num = 1;

        for (IPizza pizza : all_pizzas) {
            System.out.println(num + ". " + pizza.getName() + "\t" + pizza.getPrice());
            num++;
        }
        System.out.println(num + ". Назад");

        int choice;
        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.print("Введите номер: ");
            choice = input.inputInt();

            if (choice == num) {
                homeActions.home();
                return;
            }
            else if ((choice > num) || (choice <= 0)) {
                homeActions.errorChoice();
                continue;
            }
            break;
        }

        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.println("Выберите действие:");
            System.out.println("1. Добавить бортик");
            System.out.println("2. Удалить бортик");

            System.out.print("Введите номер: ");
            int choice_sec = input.inputInt();
            IPizza cur_pizza = all_pizzas.get(choice - 1);
            switch (choice_sec) {
                case 1:
                    addCrustToPizza(cur_pizza);
                    break;
                case 2:
                    delCrustFromPizza(cur_pizza);
                    break;
                default:
                    homeActions.errorChoice();
                    continue;
            }
            break;
        }
        return;
    }

    public void addCrustToPizza(IPizza pizza) {
        System.out.println(homeActions.string_separator);
        System.out.println("Доступные бортики для этой пиццы: ");
        int num = 1;
        ArrayList<Crust> accessible_crusts = new ArrayList<>();
        for (Crust crust : dataBase.getCrustsList()) {
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
            System.out.println(homeActions.string_separator);
            System.out.print("Введите номер: ");
            int choice = input.inputInt();

            if (choice == num) {
                addORdelPizzaCrust();
                return;
            }
            if ((choice > num) || (choice <= 0)) {
                homeActions.errorChoice();
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

    public void delCrustFromPizza(IPizza pizza) {
        System.out.println(homeActions.string_separator);

        if (pizza.getCrustInfo().isEmpty()) {
            System.out.println("У этой пиццы нет бортиков!");
            addORdelPizzaCrust();
            return;
        }

        System.out.println("Выберите бортик для удаления:");

        int num = 1;
        for (Crust crust : pizza.getCrustInfo()) {
            System.out.println(num++ + ". " + crust.getName() + "\t" + crust.getPrice());
        }
        System.out.println(num + ". Назад");

        while (true) {
            System.out.print("Введите номер: ");
            int choice = input.inputInt();

            if (choice == num) {
                addORdelPizzaCrust();
                return;
            }
            if ((choice > num) || (choice <= 0)) {
                homeActions.errorChoice();
                continue;
            }

            Crust cur_crust = pizza.getCrustInfo().get(choice - 1);
            pizza.delCrust(cur_crust);
            System.out.println("Бортик успешно удален!");
            addORdelPizzaCrust();
            return;
        }
    }

    /// БЛОК СИСТЕМНЫХ ПИЦЦ

    private List<IPizza> askFilterPizzasByIngredient(List<IPizza> pizzas) {
        System.out.println("Фильтр по ингредиенту (оставьте строку пустой для вывода всех элементов): ");
        String name = input.inputString();
        return Filtration.filterPizzasByIngredient(pizzas, name);
    }

    public void getSystemPizzaList() {
        System.out.println(homeActions.string_separator);

        List<IPizza> toShow = askFilterPizzasByIngredient(new ArrayList<>(dataBase.getSystemPizzaList()));
        if (toShow.isEmpty()) {
            System.out.println("Нет пицц, подходящих под фильтр.");
            System.out.println("1. Назад");
            System.out.print("Введите номер: ");
            if (input.inputInt() == 1) homeActions.home();
            else homeActions.errorChoice();
            getSystemPizzaList();
            return;
        }

        for (IPizza system_pizza : toShow) {
            System.out.println("- " + system_pizza.toString());
        }
        System.out.println("1. Назад");

        System.out.print("Введите номер: ");
        int choice = input.inputInt();

        switch (choice) {
            case 1:
                homeActions.home();
                break;
            default:
                homeActions.errorChoice();
                getSystemPizzaList();
                break;
        }
        return;
    }

    /// БЛОК КОМБИНИРОВАННЫХ ПИЦЦ В CombinedPizzaActions!!!!
}
