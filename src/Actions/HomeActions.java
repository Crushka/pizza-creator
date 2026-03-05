package Actions;

import java.util.List;

import MainEvent.*;
import ObjectClasses.*;

public class HomeActions {

    public final String string_separator = "--------------";
    DataBase dataBase;
    Input input;

    PizzaActions pizzaActions;
    CrustActions crustActions;
    CombinedPizzaActions combinedPizzaActions;
    OrderActions orderActions;
    IngredientsActions ingredientsActions;
    BaseActions baseActions;

    public HomeActions(DataBase dataBase, Input input) {
        this.dataBase = dataBase;
        this.input = input;

        this.pizzaActions = new PizzaActions(dataBase, input, this);
        this.crustActions = new CrustActions(dataBase, input, this);
        this.combinedPizzaActions = new CombinedPizzaActions(dataBase, input, this);
        this.orderActions = new OrderActions(dataBase, input, this);
        this.ingredientsActions = new IngredientsActions(dataBase, input, this);
        this.baseActions = new BaseActions(dataBase, input, this);
    }

    public void errorChoice() {
        System.out.println("Вы ввели что-то не то!");
    }

    public void home() {
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
        while (true) {
            System.out.print("Введите номер: ");
            int choice = input.inputInt();

            switch (choice) {
                case 1: /// Создать новую пиццу.
                    pizzaActions.createNewPizza();
                    return;
                case 2: /// Добавить/удалить ингредиент к уже созданной пицце.
                    pizzaActions.addORdelPizzaIngredient();
                    return;
                case 3: /// Добавить/удалить бортик уже созданной пицце.
                    pizzaActions.addORdelPizzaCrust();
                    return;
                case 4: /// Вывести информацию о пиццах в системе.
                    pizzaActions.getCustomPizzaListInfo();
                    return;
                case 5: /// Вывести информацию о системных пиццах.
                    pizzaActions.getSystemPizzaList();
                    return;
                case 6: /// Добавление/удаление ингредиентов, основ и бортиков.
                    createThings();
                    return;
                case 7: /// Вывести информацию об ингредиентах и основах.
                    getBaseIngInfo();
                    return;
                case 8: /// Вывести информацию о бортиках
                    crustActions.getCrustInfo();
                    return;
                case 9: /// Перейти к меню взаимодействия с комбинированной пиццей.
                    combinedPizzaActions.startCombinedPizzaActions();
                    return;
                case 10: /// Сделать заказ
                    orderActions.startOrderActions();
                    return;
                default:
                    errorChoice();
                    break;
                }
        }
    }

    public void createThings() {
        System.out.println(string_separator);
        System.out.println("Выберите действие:");
        System.out.println("1. Добавить новый ингредиент");
        System.out.println("2. Добавить новую основу");
        System.out.println("3. Добавить бортик");
        System.out.println("4. Удалить ингредиент");
        System.out.println("5. Удалить основу");
        System.out.println("6. Удалить бортик");
        System.out.println("7. Назад");

        while (true) {
            System.out.print("Введите номер: ");
            int choice = input.inputInt();
            switch (choice) {
                case 1: /// Добавить новый ингредиент
                    ingredientsActions.createNewIngredient();
                    break;
                case 2: /// Добавить новую основу
                    baseActions.createNewPizzaBase();
                    break;
                case 3: /// Добавить бортик
                    crustActions.createNewCrust();
                    break;
                case 4: /// Удалить ингредиент
                    ingredientsActions.deleteIngredient();
                    break;
                case 5: /// Удалить основу
                    baseActions.deletePizzaBase();
                    break;
                case 6: /// Удалить бортик
                    crustActions.deleteCrust();
                    break;
                case 7: /// Назад
                    home();
                    return;
                default:
                    errorChoice();
                    break;
                }
        }
    }

    public void getBaseIngInfo() {
        System.out.println(string_separator);
        System.out.println("Фильтр по названию (оставьте строку пустой для вывода всех элементов): ");
        String filterName = input.inputString();
        List<Ingredient> ingredientsToShow = Filtration.filterIngredientsByName(dataBase.getIngredientsList(), filterName);
        List<PizzaBase> basesToShow = Filtration.filterBasesByName(dataBase.getPizzaBaseList(), filterName);

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

        while (true) {
            System.out.print("Введите номер: ");
            int choice = input.inputInt();

            switch (choice) {
                case 1: /// Изменить цену ингредиента
                    ingredientsActions.changeIngredientPrice();
                    break;
                case 2: /// Изменить цену основы
                    baseActions.changePizzaBasePrice();
                    break;
                case 3: /// Изменить название ингредиента
                    ingredientsActions.changeIngredientName();
                    break;
                case 4: /// Изменить название основы
                    baseActions.changePizzaBaseName();
                    break;
                case 5: /// Назад
                    home();
                    break;
                default:
                    errorChoice();
                    break;
            }
        }
    }
}
