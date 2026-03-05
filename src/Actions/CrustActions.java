package Actions;

import java.util.ArrayList;
import java.util.List;

import MainEvent.*;
import ObjectClasses.*;

public class CrustActions {

    DataBase dataBase;
    HomeActions homeActions;
    Input input;

    public CrustActions(DataBase dataBase, Input input, HomeActions homeActions) {
        this.dataBase = dataBase;
        this.input = input;
        this.homeActions = homeActions;
    }

    public void createNewCrust() {
        System.out.println(homeActions.string_separator);
        System.out.print("Введите название бортика: ");
        String name = input.inputString();

        System.out.println(homeActions.string_separator);
        System.out.println("Выберите пиццы, разрешенные для этого бортика через запятую (или введите ALL для выбора всех пицц):");
        int num = 1;
        System.out.println("Пользовательские пиццы:");
        for (CustomPizza pizza : dataBase.getCustomPizzaList()) {
            System.out.println(num + ". " + pizza.getName());
            num++;
        }
        System.out.println("Комбинированные пиццы:");
        for (CombinedPizza pizza : dataBase.getCombinedPizzaList()) {
            System.out.println(num + ". " + pizza.getName());
            num++;
        }
        System.out.println("Системные пиццы:");
        for (SystemPizza pizza : dataBase.getSystemPizzaList()) {
            System.out.println(num + ". " + pizza.getName());
            num++;
        }

        ArrayList<IPizza> pizza_names = new ArrayList<>();

        while (true) {
            System.out.print("Введите номера пицц через запятую (или ALL): ");
            String inp = input.inputString().trim();
            if (inp.equalsIgnoreCase("ALL")) {
                pizza_names.clear();
                pizza_names.addAll(dataBase.getCustomPizzaList());
                pizza_names.addAll(dataBase.getCombinedPizzaList());
                pizza_names.addAll(dataBase.getSystemPizzaList());
                break;
            }
            if (inp.isEmpty()) {
                System.out.println("Вы должны выбрать хотя бы одну пиццу.");
                continue;
            }
            try {
                ArrayList<IPizza> temp = new ArrayList<>();
                String[] pizzaNumbers = inp.split(",");
                for (String numberStr : pizzaNumbers) {
                    int pizzaNumber = Integer.parseInt(numberStr.trim());
                    if (pizzaNumber >= 1 && pizzaNumber < num) {
                        if (pizzaNumber <= dataBase.getCustomPizzaList().size()) {
                            temp.add(dataBase.getCustomPizzaList().get(pizzaNumber - 1));
                        } else if (pizzaNumber <= dataBase.getCustomPizzaList().size() + dataBase.getCombinedPizzaList().size()) {
                            temp.add(dataBase.getCombinedPizzaList().get(pizzaNumber - dataBase.getCustomPizzaList().size() - 1));
                        } else {
                            temp.add(dataBase.getSystemPizzaList().get(pizzaNumber - 
                                dataBase.getCustomPizzaList().size() - dataBase.getCombinedPizzaList().size() - 1));
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

        System.out.println(homeActions.string_separator);
        System.out.println("Выберите ингредиенты для бортика:");
        int num_ing = 1;
        for (Ingredient ingredient : dataBase.getIngredientsList()) {
            System.out.println(num_ing++ + ". " + ingredient.getName() + "\t" + ingredient.getPrice());
        }

        ArrayList<Ingredient> crustIngredients = new ArrayList<>();
        while (true) {
            System.out.print("Введите номера ингредиентов через запятую: ");
            String ingredientInput = input.inputString();
            if (ingredientInput.trim().isEmpty()) {
                System.out.println("Вы не выбрали ни одного ингредиента. Пожалуйста, введите номера ингредиентов через запятую!");
                continue;
            }
            String[] ingredientNumbers = ingredientInput.split(",");
            boolean is_added = false;
            for (String numberStr : ingredientNumbers) {
                try {
                    int ingredientNumber = Integer.parseInt(numberStr.trim());
                    if (ingredientNumber >= 1 && ingredientNumber <= dataBase.getIngredientsList().size()) {
                        is_added = true;
                        crustIngredients.add(dataBase.getIngredientsList().get(ingredientNumber - 1));
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
        dataBase.addCrustToList(crust);

        System.out.println("Бортик успешно создан!");
        homeActions.createThings();
        return;
    }

    public void deleteCrust() {
        System.out.println(homeActions.string_separator);
        System.out.println("Выберите бортик для удаления:");
        int num = 1;
        for (Crust crust : dataBase.getCrustsList())
            System.out.println(num++ + ". " + crust.toString());
        System.out.println(num + ". Назад");

        System.out.print("Введите номер: ");
        int choice = input.inputInt();

        if (choice == num) {
            homeActions.createThings();
            return;
        }

        if ((choice < 1) || (choice > num )) {
            homeActions.errorChoice();
            deleteCrust();
            return;
        }
        
        Crust cur_crust = dataBase.getCrustsList().get(choice - 1);
        ArrayList<IPizza> pizzas_said_no = new ArrayList<>();
        for (IPizza pizza : dataBase.getAllPizzaList()) {
            if (pizza.getCrustInfo().contains(cur_crust)) {
                pizzas_said_no.add(pizza);
            }
        }

        if (!pizzas_said_no.isEmpty()) {
            System.out.println(homeActions.string_separator);
            for (IPizza pizza : pizzas_said_no)
                System.out.println("Бортик не может быть удален, так как уже содержится в пицце " + pizza.getName() + "!");
            deleteCrust();
            return;
        }
        
        dataBase.deleteCrust(cur_crust);
        System.out.println("Бортик успешно удален!");
        homeActions.createThings();
        return;
    }

    public void getCrustInfo() {
        System.out.println(homeActions.string_separator);
        System.out.println("Выберите фильтр:");
        System.out.println("1. Фильтр по названию бортика");
        System.out.println("2. Фильтр по ингредиенту");

        System.out.println(homeActions.string_separator);
        System.out.println("Введите номер: ");
        int filter_choice = input.inputInt();

        List<Crust> toShow;
        switch (filter_choice) {
            case 1:
                System.out.println(homeActions.string_separator);
                System.out.println("Фильтр по названию бортика (оставьте строку пустой для вывода всех элементов): ");
                String filter_name = input.inputString();
                toShow = Filtration.filterCrustsByName(dataBase.getCrustsList(), filter_name);
                break;
            case 2:
                System.out.println(homeActions.string_separator);
                System.out.println("Фильтр по ингредиенту (оставьте строку пустой для вывода всех элементов): ");
                String filter_ing = input.inputString();
                toShow = Filtration.filterCrustsByIngredients(dataBase.getCrustsList(), filter_ing);
                break;
            default:
                homeActions.errorChoice();
                getCrustInfo();
                return;
        }
        
        int num = 1;
        for (Crust crust : toShow) {
            System.out.println(num++ + ". " + crust.toString());
        }

        System.out.println("Выберите действие:");
        System.out.println("1. Поменять название бортика");
        System.out.println("2. Назад");

        while (true) {
            System.out.print("Введите номер: ");
            int choice = input.inputInt();

            switch (choice) {
                case 1:
                    changeCrustName();
                    break;
                case 2:
                    homeActions.home();
                    break;
                default:
                    homeActions.errorChoice();
            }
        }
    }

    public void changeCrustName() {
        System.out.println(homeActions.string_separator);
        System.out.println("Выберите бортик:");

        int num = 1;
        for (Crust crust : dataBase.getCrustsList()) {
            System.out.println(num++ + ". " + crust.getName());
        }
        System.out.println(num + ". Назад");

        int choice;
        while (true) {
            System.out.print("Введите номер: ");
            choice = input.inputInt();

            if (choice == num) {
                getCrustInfo();
                return;
            }

            if ((choice < 1) || (choice > num)) {
                homeActions.errorChoice();
                continue;
            }

            break;
        }

        Crust cur_crust = dataBase.getCrustsList().get(choice - 1);

        String new_name;
        while (true) {
            System.out.print("Введите новое название: ");
            new_name = input.inputString();

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
