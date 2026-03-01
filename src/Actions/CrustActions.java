package Actions;

import java.util.ArrayList;
import java.util.List;

import MainEvent.*;
import ObjectClasses.*;

public class CrustActions {

    public static void createNewCrust() {
        System.out.println(Main.string_separator);
        System.out.print("Введите название бортика: ");
        String name = Input.inputString();

        System.out.println(Main.string_separator);
        System.out.println("Выберите пиццы, разрешенные для этого бортика через запятую (или введите ALL для выбора всех пицц):");
        int num = 1;
        System.out.println("Пользовательские пиццы:");
        for (CustomPizza pizza : DataBase.getCustomPizzaList()) {
            System.out.println(num + ". " + pizza.getName());
            num++;
        }
        System.out.println("Комбинированные пиццы:");
        for (CombinedPizza pizza : DataBase.getCombinedPizzaList()) {
            System.out.println(num + ". " + pizza.getName());
            num++;
        }
        System.out.println("Системные пиццы:");
        for (SystemPizza pizza : DataBase.getSystemPizzaList()) {
            System.out.println(num + ". " + pizza.getName());
            num++;
        }

        ArrayList<IPizza> pizza_names = new ArrayList<>();

        while (true) {
            System.out.print("Введите номера пицц через запятую (или ALL): ");
            String input = Input.inputString().trim();
            if (input.equalsIgnoreCase("ALL")) {
                pizza_names.clear();
                pizza_names.addAll(DataBase.getCustomPizzaList());
                pizza_names.addAll(DataBase.getCombinedPizzaList());
                pizza_names.addAll(DataBase.getSystemPizzaList());
                break;
            }
            if (input.isEmpty()) {
                System.out.println("Вы должны выбрать хотя бы одну пиццу.");
                continue;
            }
            try {
                ArrayList<IPizza> temp = new ArrayList<>();
                String[] pizzaNumbers = input.split(",");
                for (String numberStr : pizzaNumbers) {
                    int pizzaNumber = Integer.parseInt(numberStr.trim());
                    if (pizzaNumber >= 1 && pizzaNumber < num) {
                        if (pizzaNumber <= DataBase.getCustomPizzaList().size()) {
                            temp.add(DataBase.getCustomPizzaList().get(pizzaNumber - 1));
                        } else if (pizzaNumber <= DataBase.getCustomPizzaList().size() + DataBase.getCombinedPizzaList().size()) {
                            temp.add(DataBase.getCombinedPizzaList().get(pizzaNumber - DataBase.getCustomPizzaList().size() - 1));
                        } else {
                            temp.add(DataBase.getSystemPizzaList().get(pizzaNumber - 
                                DataBase.getCustomPizzaList().size() - DataBase.getCombinedPizzaList().size() - 1));
                        }
                    } else {
                        System.out.println("Номер пиццы " + pizzaNumber + " вне диапазона. Пожалуйста, введите корректные номера.");
                    }
                }
                if (!temp.isEmpty()) {
                    pizza_names = temp;
                    break;
                } else {
                    System.out.println("Не удалось выбрать ни одной пиццы. Попробуйте снова.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Пожалуйста, введите номера пицц через запятую (или ALL).");
            }
        }

        System.out.println(Main.string_separator);
        System.out.println("Выберите ингредиенты для бортика:");
        for (Ingredient ingredient : DataBase.getIngredientsList()) {
            System.out.println(ingredient.getName() + "\t" + ingredient.getPrice());
        }

        ArrayList<Ingredient> crustIngredients = new ArrayList<>();
        while (true) {
            System.out.print("Введите номера ингредиентов через запятую: ");
            String ingredientInput = Input.inputString();
            if (ingredientInput.trim().isEmpty()) {
                System.out.println("Вы не выбрали ни одного ингредиента. Пожалуйста, введите номера ингредиентов через запятую!");
                continue;
            }
            String[] ingredientNumbers = ingredientInput.split(",");
            boolean is_added = false;
            for (String numberStr : ingredientNumbers) {
                try {
                    int ingredientNumber = Integer.parseInt(numberStr.trim());
                    if (ingredientNumber >= 1 && ingredientNumber <= DataBase.getIngredientsList().size()) {
                        is_added = true;
                        crustIngredients.add(DataBase.getIngredientsList().get(ingredientNumber - 1));
                    } else {
                        System.out.println("Номер ингредиента " + ingredientNumber + " вне диапазона. Пожалуйста, введите корректный номер.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Некорректный номер ингредиента: " + numberStr.trim() + ". Пожалуйста, введите корректный номер.");
                }
            }
            if (is_added) {
                System.out.println("Ингредиенты успешно добавлены!");
                break;
            } else {
                System.out.println("Ингредиенты не добавлены! Выберите хотя бы один существующий ингредиент для продолжения");
            }
        }

        Crust crust = new Crust(name, crustIngredients, pizza_names);
        DataBase.getCrustsList().add(crust);

        System.out.println("Бортик успешно создан!");
        Main.createThings();
        return;
    }

    public static void deleteCrust() {
        System.out.println(Main.string_separator);
        System.out.println("Выберите бортик для удаления:");
        int num = 1;
        for (Crust crust : DataBase.getCrustsList())
            System.out.println(num++ + ". " + crust.toString());
        System.out.println(num + ". Назад");

        System.out.print("Введите номер: ");
        int choice = Input.inputInt();

        if (choice == num) {
            Main.createThings();
            return;
        }

        if ((choice < 1) || (choice > num )) {
            Main.errorChoice();
            deleteCrust();
            return;
        }
        
        Crust cur_crust = DataBase.getCrustsList().get(choice - 1);
        ArrayList<IPizza> pizzas_said_no = new ArrayList<>();
        for (IPizza pizza : DataBase.getAllPizzaList()) {
            if (pizza.getCrustInfo().contains(cur_crust)) {
                pizzas_said_no.add(pizza);
            }
        }

        if (!pizzas_said_no.isEmpty()) {
            System.out.println(Main.string_separator);
            for (IPizza pizza : pizzas_said_no)
                System.out.println("Бортик не может быть удален, так как уже содержится в пицце " + pizza.getName() + "!");
            deleteCrust();
            return;
        }
        
        DataBase.getCrustsList().remove(cur_crust);
        System.out.println("Бортик успешно удален!");
        Main.createThings();
        return;
    }

    public static void getCrustInfo() {
        System.out.println(Main.string_separator);
        System.out.println("Фильтр по названию бортика (пусто — показать все): ");
        String filterName = Input.inputString();
        List<Crust> toShow = Filtration.filterCrustsByName(DataBase.getCrustsList(), filterName);
        int num = 1;
        for (Crust crust : toShow) {
            System.out.println(num++ + ". " + crust.toString());
        }

        System.out.println("Выберите действие:");
        System.out.println("1. Поменять название бортика");
        System.out.println("2. Назад");

        while (true) {
            System.out.print("Введите номер: ");
            int choice = Input.inputInt();

            switch (choice) {
                case 1:
                    changeCrustName();
                    break;
                case 2:
                    Main.home();
                    break;
                default:
                    Main.errorChoice();
            }
        }
    }

    public static void changeCrustName() {
        System.out.println(Main.string_separator);
        System.out.println("Выберите бортик:");

        int num = 1;
        for (Crust crust : DataBase.getCrustsList()) {
            System.out.println(num++ + ". " + crust.getName());
        }
        System.out.println(num + ". Назад");

        int choice;
        while (true) {
            System.out.print("Введите номер: ");
            choice = Input.inputInt();

            if (choice == num) {
                getCrustInfo();
                return;
            }

            if ((choice < 1) || (choice > num)) {
                Main.errorChoice();
                continue;
            }

            break;
        }

        Crust cur_crust = DataBase.getCrustsList().get(choice - 1);

        String new_name;
        while (true) {
            System.out.print("Введите новое название: ");
            new_name = Input.inputString();

            if (new_name.isBlank()) {
                System.out.println("Строка не должна быть пустой!");
                continue;
            }
            break;
        }

        cur_crust.setName(new_name);
        System.out.println("Название успешно изменено!");
        getCrustInfo();
        return;
    }
}
