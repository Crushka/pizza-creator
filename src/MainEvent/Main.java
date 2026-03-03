package MainEvent;

import java.util.ArrayList;
import java.util.List;

import Actions.*;
import ObjectClasses.*;

public class Main {
    public static final String string_separator = "--------------";

    public static void main(String[] args) {
        createBaseIng();
        createSystemPizza();
        home();
        Input.onClose();
        return;
    }

    private static void createBaseIng() {
        DataBase.getPizzaBaseList().add(new PizzaBase("Classic", 80, 1));
        DataBase.getPizzaBaseList().add(new PizzaBase("Cheese", 95, 095f / DataBase.getPizzaBaseList().get(0).getPercentage()));

        DataBase.getIngredientsList().add(new Ingredient("Tomatoes", 50));
        DataBase.getIngredientsList().add(new Ingredient("Mushrooms", 70));
        DataBase.getIngredientsList().add(new Ingredient("Chicken", 100));
        DataBase.getIngredientsList().add(new Ingredient("Cheese", 80));
    }

    private static void createSystemPizza() {
        ArrayList<Ingredient> ing_for_1 = new ArrayList<>();
        ing_for_1.add(DataBase.getIngredientsList().get(0)); /// Tomatoes
        ing_for_1.add(DataBase.getIngredientsList().get(0)); /// Tomatoes
        ing_for_1.add(DataBase.getIngredientsList().get(1)); /// Mushrooms
        ing_for_1.add(DataBase.getIngredientsList().get(2)); /// Chicken
        ing_for_1.add(DataBase.getIngredientsList().get(2)); /// Chicken

        DataBase.getSystemPizzaList().add(
            new SystemPizza("Pizza Of Your BDay",
            DataBase.getPizzaBaseList().get(0), /// Classic
            0.7f,
            ing_for_1)
        );

        ArrayList<Ingredient> ing_for_2 = new ArrayList<>();
        ing_for_2.add(DataBase.getIngredientsList().get(3)); /// Cheese
        ing_for_2.add(DataBase.getIngredientsList().get(3)); /// Cheese
        ing_for_2.add(DataBase.getIngredientsList().get(3)); /// Cheese
        ing_for_2.add(DataBase.getIngredientsList().get(3)); /// Cheese
        ing_for_2.add(DataBase.getIngredientsList().get(2)); /// Chicken

        DataBase.getSystemPizzaList().add(
            new SystemPizza("Cheese Death",
            DataBase.getPizzaBaseList().get(1), /// Cheese
            0.9f,
            ing_for_2)
        );
    }

    public static void errorChoice() {
        System.out.println("Вы ввели что-то не то!");
    }

    public static void home() {
        System.out.println(string_separator);
        System.out.println("Выберите действие:");
        System.out.println();
        System.out.println("1. Создать новую пиццу.");
        System.out.println();
        System.out.println("2. Добавить/удалить ингредиент уже созданной пицце.");
        System.out.println("3. Добавить/удалить бортик уже созданной пицце.");
        System.out.println();
        System.out.println("4. Вывести информацию о созданных пользователем пиццах в системе.");
        System.out.println("5. Вывести информацию о системных пиццах.");
        System.out.println();
        System.out.println("6. Добавление/удаление ингредиентов, основ и бортиков.");
        System.out.println("7. Вывести информацию об ингредиентах и основах.");
        System.out.println("8. Вывести информацию о бортиках.");
        System.out.println();
        System.out.println("9. Перейти к меню взаимодействия с комбинированной пиццей.");
        System.out.println();
        System.out.println("10. Сделать заказ.");
        System.out.println();
        System.out.print("Введите номер: ");
        int choice = Input.inputInt();

        switch (choice) {
            case 1: /// Создать новую пиццу.
                Actions.PizzaActions.createNewPizza();
                break;
            case 2: /// Добавить/удалить ингредиент к уже созданной пицце.
                Actions.PizzaActions.addORdelPizzaIngredient();
                break;
            case 3: /// Добавить/удалить бортик уже созданной пицце.
                Actions.PizzaActions.addORdelPizzaCrust();
                break;
            case 4: /// Вывести информацию о пиццах в системе.
                Actions.PizzaActions.getCustomPizzaListInfo();
                break;
            case 5: /// Вывести информацию о системных пиццах.
                Actions.PizzaActions.getSystemPizzaList();
                break;
            case 6: /// Добавление/удаление ингредиентов, основ и бортиков.
                createThings();
                break;
            case 7: /// Вывести информацию об ингредиентах и основах.
                getBaseIngInfo();
                break;
            case 8: /// Вывести информацию о бортиках
                Actions.CrustActions.getCrustInfo();
                break;
            case 9: /// Перейти к меню взаимодействия с комбинированной пиццей.
                CombinedPizzaActions.startCombinedPizzaActions();
                break;
            case 10: /// Сделать заказ
                Actions.OrderActions.startOrderActions();
                break;
            default:
                errorChoice();
                home();
                break;
        }
    }

    public static void createThings() {
        System.out.println(string_separator);
        System.out.println("Выберите действие:");
        System.out.println("1. Добавить новый ингредиент");
        System.out.println("2. Добавить новую основу");
        System.out.println("3. Добавить бортик");
        System.out.println("4. Удалить ингредиент");
        System.out.println("5. Удалить основу");
        System.out.println("6. Удалить бортик");
        System.out.println("7. Назад");
        System.out.print("Введите номер: ");
        
        int choice = Input.inputInt();
        switch (choice) {
            case 1: /// Добавить новый ингредиент
                Actions.IngredientsActions.createNewIngredient();
                break;
            case 2: /// Добавить новую основу
                Actions.BaseActions.createNewPizzaBase();
                break;
            case 3: /// Добавить бортик
                Actions.CrustActions.createNewCrust();
                break;
            case 4: /// Удалить ингредиент
                Actions.IngredientsActions.deleteIngredient();
                break;
            case 5: /// Удалить основу
                Actions.BaseActions.deletePizzaBase();
                break;
            case 6: /// Удалить бортик
                Actions.CrustActions.deleteCrust();
                break;
            case 7: /// Назад
                home();
                break;
            default:
                errorChoice();
                createThings();
                break;
        }   
    }

    public static void getBaseIngInfo() {
        System.out.println(string_separator);
        System.out.println("Фильтр по названию (оставьте строку пустой для вывода всех элементов): ");
        String filterName = Input.inputString();
        List<Ingredient> ingredientsToShow = Filtration.filterIngredientsByName(DataBase.getIngredientsList(), filterName);
        List<PizzaBase> basesToShow = Filtration.filterBasesByName(DataBase.getPizzaBaseList(), filterName);

        System.out.println("Ингредиенты:");
        for (Ingredient ingredient : ingredientsToShow) {
            System.out.println(ingredient.getName() + "\t" + ingredient.getPrice());
        }
        System.out.println(string_separator);
        System.out.println("Основы:");
        for (PizzaBase pizza_base : basesToShow) {
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
