package Actions;

import java.util.ArrayList;

import ObjectClasses.Pizza;
import ObjectClasses.PizzaBase;
import MainEvent.Input;
import MainEvent.Main;

public class BaseActions {
    private static ArrayList<PizzaBase> pizza_base_list = new ArrayList<>();

    public static ArrayList<PizzaBase> getPizzaBaseList() {
        return pizza_base_list;
    }

    public static void createNewPizzaBase() {
        System.out.println(Main.string_separator);
        System.out.print("Введите название основы: ");
        String name_base = Input.inputString();

        for (PizzaBase pizza_base : pizza_base_list) {
            if (pizza_base.getName().equals(name_base)) {
                System.out.println("Основа с таким названием уже существует!");
                createNewPizzaBase();
                return;
            }
        }
                
        PizzaBase base = pizza_base_list.get(0); /// Классичекая основа
        float max_price = base.getPrice() * 1.2f;
        System.out.print("Введите цену основы (цена основы не должна превышать 20% классической, максимальная цена основы - " + max_price + "руб.): ");
        float price_base = Input.inputFloat();
        PizzaBase pizza_base = new PizzaBase(name_base, price_base, (price_base / base.getPrice()));
               
        if (pizza_base.getPrice() == -1) {
            createNewPizzaBase();
            return;
        }
                
        pizza_base_list.add(pizza_base);
        System.out.println("Основа успешно добавлена!");
        Main.createIngAndBase();
    }

    public static void deletePizzaBase() {
        if (pizza_base_list.isEmpty()) {
            System.out.println("Вы не создали ни одной основы!");
            Main.createIngAndBase();
        }

        System.out.println(Main.string_separator);
        System.out.println("Выберите основу для удаления: ");
                
        int num = 1;

        for (PizzaBase base1 : pizza_base_list) {
            System.out.println(num + ". " + base1.getName() + "\t" + base1.getPrice());
            num++;
        }
                
        System.out.println(num + ". Назад");

        System.out.print("Введите номер: ");
        int choice_del = Input.inputInt();
        if (choice_del == 1) {
            System.out.println("Вы не можете удалить классическую основу!");
            deletePizzaBase();
        }
        else if (choice_del == num)
            Main.createIngAndBase();

        else if ((choice_del > num) || (choice_del <= 0)) {
            Main.errorChoice();
            Main.createIngAndBase();
        }

        ArrayList<Pizza> pizza_list = Actions.PizzaActions.getPizzaList();
        int temp = 0;
        if (pizza_list.size() > 0) {
            for (Pizza pizza : pizza_list) {
                if (pizza.getBaseInfo().getName().equals(pizza_base_list.get(choice_del - 1).getName())) {
                    System.out.println("Эта основа уже используется в пицце " + pizza.getName() + "!");
                    temp++;
                }
            }
        }
        if (temp == 0) {
            pizza_base_list.remove(choice_del - 1);
            System.out.println("Основа успешно удалена!");
        }
        Main.createIngAndBase();
    }

    public static void changePizzaBasePrice() {
        if (pizza_base_list.isEmpty()) {
            System.out.println("Вы не создали ни одной основы!");
            Main.getBaseIngInfo();
        }

        System.out.println(Main.string_separator);
        System.out.println("Выберите основу для изменения цены: ");
        int num = 1;

        for (PizzaBase pizza_base : pizza_base_list) {
            System.out.println(num + ". " + pizza_base.getName() + "\t" + pizza_base.getPrice());
            num++;
        }
        System.out.println(num + ". Назад");
                
        System.out.print("Введите номер: ");
        int choice_base = Input.inputInt();
                
        if (choice_base == 1) {
            System.out.print("Введите новую цену: ");
            float new_price_base = Input.inputFloat();

            for (PizzaBase pizza_base : pizza_base_list) {
                pizza_base.setPrice(new_price_base * pizza_base.getPercentage());
            }

            System.out.println("Цена успешно изменена!");
            Main.getBaseIngInfo();
        }
        else if (choice_base == num) {
            Main.getBaseIngInfo();
        }
        else if ((choice_base > num) || (choice_base <= 0)) {
            Main.errorChoice();
            Main.getBaseIngInfo();
        }

        System.out.println(Main.string_separator);
        PizzaBase base = pizza_base_list.get(0); /// Классичекая основа
        float max_price = base.getPrice() * 1.2f;
        PizzaBase cur_PizzaBase = pizza_base_list.get(choice_base - 1);
        float cur_price = cur_PizzaBase.getPrice();

        System.out.print("Введите цену основы (цена основы не должна превышать 20% классической, максимальная цена основы - " + max_price + "руб.): ");
        float price_base = Input.inputFloat();
        cur_PizzaBase.setPrice(price_base);
               
        if (cur_PizzaBase.getPrice() == -1) {
            cur_PizzaBase.setPrice(cur_price);
            changePizzaBasePrice();
        }
        else {
            System.out.println("Цена успешно изменена!");
            Main.getBaseIngInfo();
        }
    }

    public static void changePizzaBaseName() {
        if (pizza_base_list.isEmpty()) {
            System.out.println("Вы не создали ни одной основы!");
            Main.getBaseIngInfo();
        }

        System.out.println(Main.string_separator);
        System.out.println("Выберите основу для изменения названия: ");
        int num = 1;

        for (PizzaBase pizza_base : pizza_base_list) {
            System.out.println(num + ". " + pizza_base.getName() + "\t" + pizza_base.getPrice());
            num++;
        }
        System.out.println(num + ". Назад");
                
        System.out.print("Введите номер: ");
        int choice_base = Input.inputInt();

        if (choice_base == 1) {
            System.out.println("Вы не можете изменить название классической основы!");
            changePizzaBaseName();
        }
        else if (choice_base == num) {
            Main.getBaseIngInfo();
        }
        else if ((choice_base > num) || (choice_base <= 0)) {
            Main.errorChoice();
            Main.getBaseIngInfo();
        }

        System.out.print("Введите новое название: ");
        String new_name = Input.inputString();

        for (PizzaBase pizza_base : pizza_base_list) {
            if (pizza_base.getName().equals(new_name)) {
                System.out.println("Основа с таким названием уже существует!");
                changePizzaBaseName();
                return;
            }
        }

        pizza_base_list.get(choice_base - 1).setName(new_name);

        System.out.println("Название успешно изменено!");
        Main.getBaseIngInfo();
    }
}
