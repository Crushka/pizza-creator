package MainEvent;

import Actions.*;
import ObjectClasses.Ingredient;
import ObjectClasses.PizzaBase;

public class Main {
    public static final String string_separator = "--------------";

    public static void main(String[] args) {
        createBaseIng();
        home();
        Input.onClose();
        return;
    }

    private static void createBaseIng() {
        BaseActions.getPizzaBaseList().add(new PizzaBase("Classic", 80, 1));

        IngredientsActions.getIngredientsList().add(new Ingredient("Tomatoes", 50));
        IngredientsActions.getIngredientsList().add(new Ingredient("Mushrooms", 70));
        IngredientsActions.getIngredientsList().add(new Ingredient("Chicken", 100));
        IngredientsActions.getIngredientsList().add(new Ingredient("Cheese", 80));
    }

    public static void errorChoice() {
        System.out.println("Вы ввели что-то не то!");
    }

    public static void home() {
        System.out.println(string_separator);
        System.out.println("Выберите действие:");
        System.out.println("1. Создать новую пиццу.");
        System.out.println("2. Добавить/удалить ингредиент уже созданной пицце.");
        System.out.println("3. Вывести информацию о пиццах в системе.");
        System.out.println("4. Добавление/удаление ингредиентов и основ.");
        System.out.println("5. Вывести информацию об ингредиентах и основах.");
        System.out.println("6. Сделать заказ.");
        System.out.print("Введите номер: ");
        int choice = Input.inputInt();

        switch (choice) {
            case 1: /// Создать новую пиццу.
                Actions.PizzaActions.createNewPizza();
                break;
            case 2: /// Добавить/удалить ингредиент к уже созданной пицце.
                Actions.PizzaActions.addORdelPizzaIngredient();
                break;
            case 3: /// Вывести информацию о пиццах в системе.
                Actions.PizzaActions.getPizzaListInfo();
                break;
            case 4: /// Добавление/удаление ингредиентов и основ.
                createIngAndBase();
                break;
            case 5: /// Вывести информацию об ингредиентах и основах
                getBaseIngInfo();
                break;
            case 6: /// Сделать заказ
                System.out.println("Эта функция пока недоступна!");
                break;
            default:
                errorChoice();
                home();
                break;
        }
    }

    public static void createIngAndBase() {
        System.out.println(string_separator);
        System.out.println("Выберите действие:");
        System.out.println("1. Добавить новый ингредиент");
        System.out.println("2. Добавить новую основу");
        System.out.println("3. Удалить ингредиент");
        System.out.println("4. Удалить основу");
        System.out.println("5. Назад");
        System.out.print("Введите номер: ");
        
        int choice = Input.inputInt();
        switch (choice) {
            case 1: /// Добавить новый ингредиент
                Actions.IngredientsActions.createNewIngredient();
                break;
            case 2: /// Добавить новую основу
                Actions.BaseActions.createNewPizzaBase();
                break;
            case 3: /// Удалить ингредиент
                Actions.IngredientsActions.deleteIngredient();
                break;
            case 4: /// Удалить основу
                Actions.BaseActions.deletePizzaBase();
                break;
            case 5: /// Назад
                home();
                break;
            default:
                errorChoice();
                createIngAndBase();
                break;
        }   
    }

    public static void getBaseIngInfo() {
        System.out.println(string_separator);
        System.out.println("Ингредиенты:");
        for (Ingredient ingredient : IngredientsActions.getIngredientsList()) {
            System.out.println(ingredient.getName() + "\t" + ingredient.getPrice());
        }
        System.out.println(string_separator);
        System.out.println("Основы:");
        for (PizzaBase pizza_base : BaseActions.getPizzaBaseList()) {
            System.out.println(pizza_base.getName() + "\t" + pizza_base.getPrice());
        }

        System.out.println(string_separator);
        System.out.println("Выберите действие: ");
        System.out.println("1. Изменить цену ингредиента");
        System.out.println("2. Изменить цену основы");
        System.out.println("3. Изменить название ингредиента");
        System.out.println("4. Изменить название основы");
        System.out.println("5. Назад");

        System.out.print("Введите номер: ");
        int choice = Input.inputInt();

        switch (choice) {
            case 1: /// Изменить цену ингредиента
                Actions.IngredientsActions.changeIngredientPrice();
                break;
            case 2: /// Изменить цену основы
                Actions.BaseActions.changePizzaBasePrice();
                break;
            case 3: /// Изменить название ингредиента
                Actions.IngredientsActions.changeIngredientName();
                break;
            case 4: /// Изменить название основы
                Actions.BaseActions.changePizzaBaseName();
                break;
            case 5: /// Назад
                home();
                break;
            default:
                errorChoice();
                getBaseIngInfo();
                break;
        }
    }
}
