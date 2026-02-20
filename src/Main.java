import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static Scanner scanner = new Scanner(System.in, "UTF-8");
    public static String string_separator = "--------------";

    public static ArrayList<Pizza> pizza_list = new ArrayList<>();
    public static ArrayList<PizzaBase> pizza_base_list = new ArrayList<>();
    public static ArrayList<Ingredient> ingredients_list = new ArrayList<>();

    public static void main(String[] args) {
        createBaseIng();
        home();
        scanner.close();
        return;
    }

    private static void createBaseIng() {
        pizza_base_list.add(new PizzaBase("Классическое тесто", 80, 1));

        ingredients_list.add(new Ingredient("Помидоры", 50));
        ingredients_list.add(new Ingredient("Грибы", 70));
        ingredients_list.add(new Ingredient("Ветчина", 100));
        ingredients_list.add(new Ingredient("Сыр", 80));
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
        int choice = scanner.nextInt();

        switch (choice) {
            case 1: /// Создать новую пиццу.
                createNewPizza();
                break;
            case 2: /// Добавить/удалить ингредиент к уже созданной пицце.
                if (pizza_list.isEmpty()) {
                    System.out.println("Вы не создали ни одной пиццы!");
                    home();
                }

                System.out.println(string_separator);
                System.out.println("Выберите пиццу: ");
                int num = 1;

                for (Pizza pizza : pizza_list) {
                    System.out.println(num + ". " + pizza.getName() + "\t" + pizza.getPrice());
                    num++;
                }
                System.out.println(num + ". Назад");

                System.out.print("Введите номер: ");
                choice = scanner.nextInt();

                if (choice == num)
                    home();

                else if ((choice > num) || (choice <= 0)) {
                    errorChoice();
                    home();
                }
                
                System.out.println(string_separator);
                System.out.println("Выберите действие:");
                System.out.println("1. Добавить ингредиент");
                System.out.println("2. Удалить ингредиент");

                System.out.print("Введите номер: ");
                int choice_sec = scanner.nextInt();
                switch (choice_sec) {
                    case 1:
                        System.out.println(string_separator);
                        addIngToPizza(pizza_list.get(choice - 1));
                        break;
                    case 2:
                        System.out.println(string_separator);
                        delIngFromPizza(pizza_list.get(choice - 1));
                        break;
                    default:
                        errorChoice();
                        home();
                        break;
                }
                break;
            case 3: /// Вывести информацию о пиццах в системе.
                getPizzaListInfo();
                break;
            case 4: /// Добавление/удаление ингредиентов и основ.
                createIngAndBase();
                break;
            case 5: /// Вывести информацию об ингредиентах и основах
                getBaseIngInfo();
                break;
            case 6: /// Сделать заказ
                
                break;
            default:
                errorChoice();
                home();
                break;
        }
    }

    public static void createNewPizza() {
        System.out.println(string_separator);
        System.out.print("Введите название пиццы: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        Pizza pizza = new Pizza(name);

        System.out.println("Выберите основу для пиццы:");
        int num = 1;
        for (PizzaBase pizza_base : pizza_base_list) {
            System.out.println(num + ". " + pizza_base.getInfo() + "\t" + pizza_base.getPrice() + "руб.");
            num++;
        }
        System.out.println(num + ". Назад");
        
        System.out.print("Введите номер: ");
        int choice = scanner.nextInt();

        if (choice == num)
            home();

        else if ((choice > num) || (choice <= 0)) {
            errorChoice();
            createNewPizza();
        }

        pizza.addBase(pizza_base_list.get(choice - 1));

        pizza_list.add(pizza);
        System.out.println("Пицца успешно создана!");
        System.out.println(string_separator);
        home();
    }

    public static void addIngToPizza(Pizza pizza) {
        System.out.println(string_separator);
        System.out.println("Выберите ингредиент для пиццы: ");
        int num = 1;

        for (Ingredient ingredient : ingredients_list) {
            System.out.println(num + ". " + ingredient.getInfo() + "\t" + ingredient.getPrice() + "руб.");
            num++;
        }
        System.out.println(num + ". Назад");

        System.out.print("Введите номер: ");
        int choice = scanner.nextInt();

        if (choice == num)
            home();
        else if ((choice > num) || (choice <= 0)) {
            errorChoice();
            addIngToPizza(pizza);
        }

        pizza.addIngredient(ingredients_list.get(choice - 1));
        System.out.println("Ингредиент успешно добавлен!");
        
        while (true) {
            System.out.println("1. Добавить ещё");
            System.out.println("2. Назад");
            System.out.print("Введите номер: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addIngToPizza(pizza);
                    break;
                case 2:
                    home();
                    break;
                default:
                    errorChoice();
                    break;
            }
        }
    }

    public static void delIngFromPizza(Pizza pizza) {
        System.out.println(string_separator);
        System.out.println("Выберите ингредиент для удаления: ");
        int num = 1;

        for (Ingredient ingredient : pizza.getIngredientInfo()) {
            System.out.println(num + ". " + ingredient.getInfo() + "\t" + ingredient.getPrice());
            num++;
        }
        System.out.println(num++ + ". Основа: " + pizza.getBaseInfo() + "\t");
        System.out.println(num + ". Назад");
        
        System.out.print("Введите номер: ");
        int choice = scanner.nextInt();

        if (choice == num)
            home();
        else if (choice == (num - 1)) {
            changePizzaBase(pizza);
        }
        else if ((choice > num) || (choice <= 0)) {
            errorChoice();
            delIngFromPizza(pizza);
        }

        pizza.deleteIngredient(choice - 1);
        System.out.println("Ингредиент успешно удалён!");

        while (true) {
            System.out.println("1. Удалить ещё");
            System.out.println("2. Назад");
            System.out.print("Введите номер: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    delIngFromPizza(pizza);
                    break;
                case 2:
                    home();
                    break;
                default:
                    errorChoice();
                    break;
            }
        }
    }

    public static void changePizzaBase(Pizza pizza) {
        System.out.println(string_separator);
        System.out.println("Вы не можете удалить основу пиццы! Желаете ли вы её поменять?");
        System.out.println("1. Поменять");
        System.out.println("2. Назад");
        
        System.out.print("Введите номер: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println(string_separator);
                System.out.println("Выберите новую основу:");

                int num = 1;
                for (PizzaBase base : pizza_base_list) {
                    System.out.println(num + ". " + base.getInfo() + "\t" + base.getPrice());
                    num++;
                }
                System.out.println(num + ". Назад");

                System.out.print("Введите номер: ");
                choice = scanner.nextInt();

                if (choice == num)
                    home();
                else if ((choice > num) || (choice <= 0)) {
                    errorChoice();
                    delIngFromPizza(pizza);
                }

                pizza.addBase(pizza_base_list.get(choice - 1));
                System.out.println("Основа успешно изменена!");
                home();
                break;
            case 2:
                home();
                break;
            default:
                errorChoice();
                delIngFromPizza(pizza);
                break;
        }
    }

    public static void getPizzaListInfo() {
        System.out.println(string_separator);

        if (pizza_list.isEmpty()) {
            System.out.println("Вы не создали ни одной пиццы!");
            home();
        }

        int num = 1;

        for (Pizza pizza : pizza_list) {
            System.out.println(num + ". " + pizza.getName() + "\t" + pizza.getPrice() + "руб.");
            num++;
        }

        System.out.println("Выберите действие:");
        System.out.println("1. Удалить пиццу");
        System.out.println("2. Назад");

        System.out.print("Введите номер: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Введите номер пиццы для удаления: ");
                int choice2 = scanner.nextInt();
                if ((choice2 > num) || (choice2 <= 0)) {
                    errorChoice();
                    getPizzaListInfo();
                }
                pizza_list.remove(choice2 - 1);
                System.out.println("Пицца успешно удалена!");
                home();
                break;
            case 2:
                home();
                break;
            default:
                errorChoice();
                getPizzaListInfo();
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

        int choice = scanner.nextInt();
        switch (choice) {
            case 1: /// Добавить новый ингредиент
                System.out.println(string_separator);
                System.out.print("Введите название ингредиента: ");
                scanner.nextLine();
                String name = scanner.nextLine();

                System.out.print("Введите цену ингредиента: ");
                float price = scanner.nextFloat();

                ingredients_list.add(new Ingredient(name, price));
                System.out.println("Ингредиент успешно добавлен!");
                createIngAndBase();
                break;
            case 2: /// Добавить новую основу
                System.out.println(string_separator);
                System.out.print("Введите название основы: ");
                scanner.nextLine();
                String name_base = scanner.nextLine();
                
                PizzaBase base = pizza_base_list.get(0); /// Классичекая основа
                float max_price = base.getPrice() * 1.2f;
                System.out.print("Введите цену основы (цена основы не должна превышать 20% классической, максимальная цена основы - " + max_price + "руб.): ");
                float price_base = scanner.nextFloat();
                PizzaBase pizza_base = new PizzaBase(name_base, price_base, (price_base / base.getPrice()) + 1);
               
                if (pizza_base.getPrice() == -1) {
                    createIngAndBase();
                }
                
                pizza_base_list.add(pizza_base);
                System.out.println("Основа успешно добавлена!");
                createIngAndBase();
                break;
            case 3: /// Удалить ингредиент
                if (ingredients_list.isEmpty()) {
                    System.out.println("Вы не создали ни одного ингредиента!");
                    createIngAndBase();
                }

                System.out.println(string_separator);
                System.out.println("Выберите ингредиент для удаления: ");
                int num = 1;

                for (Ingredient ingredient : ingredients_list) {
                    System.out.println(num + ". " + ingredient.getInfo() + "\t" + ingredient.getPrice());
                    num++;
                }
                System.out.println(num + ". Назад");

                System.out.print("Введите номер: ");
                int choice_del = scanner.nextInt();

                if (choice_del == num)
                    createIngAndBase();

                else if ((choice_del > num) || (choice_del <= 0)) {
                    errorChoice();
                    createIngAndBase();
                }

                ingredients_list.remove(choice_del - 1);
                System.out.println("Ингредиент успешно удалён!");
                createIngAndBase();
                break;
            case 4: /// Удалить основу
                if (pizza_base_list.isEmpty()) {
                    System.out.println("Вы не создали ни одной основы!");
                    createIngAndBase();
                }

                System.out.println(string_separator);
                System.out.println("Выберите основу для удаления: ");
                
                num = 1;

                for (PizzaBase base1 : pizza_base_list) {
                    System.out.println(num + ". " + base1.getInfo() + "\t" + base1.getPrice());
                    num++;
                }
                
                System.out.println(num + ". Назад");

                System.out.print("Введите номер: ");
                choice_del = scanner.nextInt();
                if (choice_del == num)
                    createIngAndBase();

                else if ((choice_del > num) || (choice_del <= 0)) {
                    errorChoice();
                    createIngAndBase();
                }

                pizza_base_list.remove(choice_del - 1);
                System.out.println("Основа успешно удалена!");
                createIngAndBase();
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
        for (Ingredient ingredient : ingredients_list) {
            System.out.println(ingredient.getInfo() + "\t" + ingredient.getPrice());
        }
        System.out.println(string_separator);
        System.out.println("Основы:");
        for (PizzaBase pizza_base : pizza_base_list) {
            System.out.println(pizza_base.getInfo() + "\t" + pizza_base.getPrice());
        }

        System.out.println(string_separator);
        System.out.println("Выберите действие: ");
        System.out.println("1. Изменить цену ингредиента");
        System.out.println("2. Изменить цену основы");
        System.out.println("3. Назад");

        System.out.print("Введите номер: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1: /// Изменить цену ингредиента
                if (ingredients_list.isEmpty()) {
                    System.out.println("Вы не создали ни одного ингредиента!");
                    getBaseIngInfo();
                }

                System.out.println(string_separator);
                System.out.println("Выберите ингредиент для изменения цены: ");
                int num = 1;

                for (Ingredient ingredient : ingredients_list) {
                    System.out.println(num + ". " + ingredient.getInfo() + "\t" + ingredient.getPrice());
                    num++;
                }
                System.out.println(num + ". Назад");
                
                System.out.print("Введите номер: ");
                int choice_ingredient = scanner.nextInt();

                if (choice_ingredient == num) {
                    getBaseIngInfo();
                }
                else if ((choice_ingredient > num) || (choice_ingredient <= 0)) {
                    errorChoice();
                    getBaseIngInfo();
                }

                System.out.print("Введите новую цену: ");
                float new_price = scanner.nextFloat();

                ingredients_list.get(choice_ingredient - 1).setPrice(new_price);
                System.out.println("Цена успешно изменена!");
                getBaseIngInfo();
                break;
            case 2: /// Изменить цену основы
                if (pizza_base_list.isEmpty()) {
                    System.out.println("Вы не создали ни одной основы!");
                    getBaseIngInfo();
                }

                System.out.println(string_separator);
                System.out.println("Выберите основу для изменения цены: ");
                num = 1;

                for (PizzaBase pizza_base : pizza_base_list) {
                    System.out.println(num + ". " + pizza_base.getInfo() + "\t" + pizza_base.getPrice());
                    num++;
                }
                System.out.println(num + ". Назад");
                
                System.out.print("Введите номер: ");
                int choice_base = scanner.nextInt();
                
                if (choice_base == 1) {
                    System.out.print("Введите новую цену: ");
                    float new_price_base = scanner.nextFloat();

                    for (PizzaBase pizza_base : pizza_base_list) {
                        pizza_base.setPrice(new_price_base * pizza_base.getPercentage());
                    }

                    System.out.println("Цена успешно изменена!");
                    getBaseIngInfo();
                }
                else if (choice_base == num) {
                    getBaseIngInfo();
                }
                else if ((choice_base > num) || (choice_base <= 0)) {
                    errorChoice();
                    getBaseIngInfo();
                }

                System.out.println(string_separator);
                PizzaBase base = pizza_base_list.get(0); /// Классичекая основа
                float max_price = base.getPrice() * 1.2f;
                PizzaBase cur_PizzaBase = pizza_base_list.get(choice_base - 1);
                float cur_price = cur_PizzaBase.getPrice();

                System.out.print("Введите цену основы (цена основы не должна превышать 20% классической, максимальная цена основы - " + max_price + "руб.): ");
                float price_base = scanner.nextFloat();
                cur_PizzaBase.setPrice(price_base);
               
                if (cur_PizzaBase.getPrice() == -1) {
                    cur_PizzaBase.setPrice(cur_price);
                    getBaseIngInfo();
                }
                else {
                    System.out.println("Цена успешно изменена!");
                    getBaseIngInfo();
                }
                break;
            case 3: /// Назад
                home();
                break;
            default:
                errorChoice();
                getBaseIngInfo();
                break;
        }
    }
}
