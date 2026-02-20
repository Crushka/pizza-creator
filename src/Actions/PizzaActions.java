package Actions;

import java.util.ArrayList;

import MainEvent.Input;
import MainEvent.Main;
import ObjectClasses.Ingredient;
import ObjectClasses.Pizza;
import ObjectClasses.PizzaBase;

public class PizzaActions {
    public static ArrayList<Pizza> pizza_list = new ArrayList<>();

    public static ArrayList<Pizza> getPizzaList() {
        return pizza_list;
    }

    public static void createNewPizza() {
        System.out.println(Main.string_separator);
        System.out.print("Введите название пиццы: ");
        String name = Input.inputString();
        Pizza pizza = new Pizza(name);

        System.out.println("Выберите основу для пиццы:");
        int num = 1;
        for (PizzaBase pizza_base : BaseActions.getPizzaBaseList()) {
            System.out.println(num + ". " + pizza_base.getInfo() + "\t" + pizza_base.getPrice() + "руб.");
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

        pizza.addBase(BaseActions.getPizzaBaseList().get(choice - 1));

        pizza_list.add(pizza);
        System.out.println("Пицца успешно создана!");
        System.out.println(Main.string_separator);
        Main.home();
    }

    public static void addIngToPizza(Pizza pizza) {
        System.out.println(Main.string_separator);
        System.out.println("Выберите ингредиент для пиццы: ");
        int num = 1;

        for (Ingredient ingredient : IngredientsActions.getIngredientsList()) {
            System.out.println(num + ". " + ingredient.getInfo() + "\t" + ingredient.getPrice() + "руб.");
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

        pizza.addIngredient(IngredientsActions.getIngredientsList().get(choice - 1));
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

    public static void delIngFromPizza(Pizza pizza) {
        System.out.println(Main.string_separator);
        System.out.println("Выберите ингредиент для удаления: ");
        int num = 1;

        for (Ingredient ingredient : pizza.getIngredientInfo()) {
            System.out.println(num + ". " + ingredient.getInfo() + "\t" + ingredient.getPrice());
            num++;
        }
        System.out.println(num++ + ". Основа: " + pizza.getBaseInfo() + "\t");
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

    public static void changePizzaBase(Pizza pizza) {
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
                for (PizzaBase base : BaseActions.getPizzaBaseList()) {
                    System.out.println(num + ". " + base.getInfo() + "\t" + base.getPrice());
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

                pizza.addBase(BaseActions.getPizzaBaseList().get(choice - 1));
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

    public static void getPizzaListInfo() {
        System.out.println(Main.string_separator);

        if (pizza_list.isEmpty()) {
            System.out.println("Вы не создали ни одной пиццы!");
            Main.home();
        }

        int num = 1;

        for (Pizza pizza : pizza_list) {
            System.out.println(num + ". " + pizza.getName() + "\t" + pizza.getPrice() + "руб.");
            System.out.println("   Основа: " + pizza.getBaseInfo().getInfo() + "\t" + pizza.getBaseInfo().getPrice() + "руб.");
            for (Ingredient ingredient : pizza.getIngredientInfo()) {
                System.out.println("   " + ingredient.getInfo() + "\t" + ingredient.getPrice() + "руб.");
            }
            num++;
        }

        System.out.println(Main.string_separator);
        System.out.println("Выберите действие:");
        System.out.println("1. Удалить пиццу");
        System.out.println("2. Назад");

        System.out.print("Введите номер: ");
        int choice = Input.inputInt();

        switch (choice) {
            case 1:
                System.out.print("Введите номер пиццы для удаления: ");
                int choice2 = Input.inputInt();
                if ((choice2 > num) || (choice2 <= 0)) {
                    Main.errorChoice();
                    getPizzaListInfo();
                }
                pizza_list.remove(choice2 - 1);
                System.out.println("Пицца успешно удалена!");
                Main.home();
                break;
            case 2:
                Main.home();
                break;
            default:
                Main.errorChoice();
                getPizzaListInfo();
                break;
        }
    }

    public static void addORdelPizzaIngredient() {
        if (pizza_list.isEmpty()) {
            System.out.println("Вы не создали ни одной пиццы!");
            Main.home();
        }

        System.out.println(Main.string_separator);
        System.out.println("Выберите пиццу: ");
        int num = 1;

        for (Pizza pizza : pizza_list) {
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
                
        System.out.println(Main.string_separator);
        System.out.println("Выберите действие:");
        System.out.println("1. Добавить ингредиент");
        System.out.println("2. Удалить ингредиент");

        System.out.print("Введите номер: ");
        int choice_sec = Input.inputInt();
        switch (choice_sec) {
            case 1:
                System.out.println(Main.string_separator);
                addIngToPizza(pizza_list.get(choice - 1));
                break;
            case 2:
                System.out.println(Main.string_separator);
                delIngFromPizza(pizza_list.get(choice - 1));
                break;
            default:
                Main.errorChoice();
                Main.home();
                break;
        }
    }
}
