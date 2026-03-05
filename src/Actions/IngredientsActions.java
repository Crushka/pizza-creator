package Actions;

import ObjectClasses.*;
import MainEvent.*;

public class IngredientsActions {

    HomeActions homeActions;
    DataBase dataBase;
    Input input;

    public IngredientsActions(DataBase dataBase, Input input, HomeActions homeActions) {
        this.dataBase = dataBase;
        this.input = input;
        this.homeActions = homeActions;
    }

    public void createNewIngredient() {
        System.out.println(homeActions.string_separator);

        String name;
        while (true) {
            System.out.print("Введите название ингредиента: ");
            name = input.inputString();

            boolean isExist = false;
            for (Ingredient ingredient : dataBase.getIngredientsList()) {
                if (ingredient.getName().equals(name)) {
                    System.out.println("Ингредиент с таким названием уже существует!");
                    isExist = true;
                }
                if (isExist) break;
            }
            if (isExist) continue;
            break;
        }

        System.out.print("Введите цену ингредиента: ");
        float price = input.inputFloat();

        dataBase.addIngredientToList(new Ingredient(name, price));
        System.out.println("Ингредиент успешно добавлен!");
        homeActions.createThings();
        return;
    }

    public void deleteIngredient() {
        if (dataBase.getIngredientsList().isEmpty()) {
            System.out.println("Вы не создали ни одного ингредиента!");
            homeActions.createThings();
            return;
        }

        System.out.println(homeActions.string_separator);
        System.out.println("Выберите ингредиент для удаления: ");
        int num = 1;

        for (Ingredient ingredient : dataBase.getIngredientsList()) {
            System.out.println(num + ". " + ingredient.getName() + "\t" + ingredient.getPrice());
            num++;
        }
        System.out.println(num + ". Назад");

        int choice_del;
        while (true) {
            System.out.print("Введите номер: ");
            choice_del = input.inputInt();

            if (choice_del == num)
                homeActions.createThings();

            else if ((choice_del > num) || (choice_del <= 0)) {
                homeActions.errorChoice();
                continue;
            }
            break;
        }

        Ingredient deletedIngredient = dataBase.getIngredientsList().get(choice_del - 1);
        dataBase.deleteIngredient(deletedIngredient);

        for (IPizza pizza : dataBase.getAllPizzaList()) {
            pizza.removeIngredient(deletedIngredient.getName());
        }

        for (Crust crust : dataBase.getCrustsList()) {
            crust.removeIngredient(deletedIngredient.getName());
        }

        System.out.println("Ингредиент успешно удалён!");
        homeActions.createThings();
    }

    public void changeIngredientPrice() {
        if (dataBase.getIngredientsList().isEmpty()) {
            System.out.println("Вы не создали ни одного ингредиента!");
            homeActions.getBaseIngInfo();
            return;
        }

        System.out.println(homeActions.string_separator);
        System.out.println("Выберите ингредиент для изменения цены: ");
        int num = 1;

        for (Ingredient ingredient : dataBase.getIngredientsList()) {
            System.out.println(num + ". " + ingredient.getName() + "\t" + ingredient.getPrice());
            num++;
        }
        System.out.println(num + ". Назад");
        
        int choice_ingredient;
        while (true) {
            System.out.print("Введите номер: ");
            choice_ingredient = input.inputInt();

            if (choice_ingredient == num) {
                homeActions.getBaseIngInfo();
            }
            else if ((choice_ingredient > num) || (choice_ingredient <= 0)) {
                homeActions.errorChoice();
                continue;
            }
            break;
        }

        System.out.print("Введите новую цену: ");
        float new_price = input.inputFloat();

        dataBase.getIngredientsList().get(choice_ingredient - 1).setPrice(new_price);
        System.out.println("Цена успешно изменена!");
        homeActions.getBaseIngInfo();
    }

    public void changeIngredientName() {
        if (dataBase.getIngredientsList().isEmpty()) {
            System.out.println("Вы не создали ни одного ингредиента!");
            homeActions.getBaseIngInfo();
            return;
        }

        System.out.println(homeActions.string_separator);
        System.out.println("Выберите ингредиент для изменения названия: ");
        int num = 1;

        for (Ingredient ingredient : dataBase.getIngredientsList()) {
            System.out.println(num + ". " + ingredient.getName() + "\t" + ingredient.getPrice());
            num++;
        }
        System.out.println(num + ". Назад");
        
        int choice_ingredient;
        while (true) {
            System.out.print("Введите номер: ");
            choice_ingredient = input.inputInt();

            if (choice_ingredient == num) {
                homeActions.getBaseIngInfo();
            }
            else if ((choice_ingredient > num) || (choice_ingredient <= 0)) {
                homeActions.errorChoice();
                continue;
            }
            break;
        }

        String new_name;
        while (true) {
            System.out.print("Введите новое название: ");
            new_name = input.inputString();

            if (new_name.isBlank()) {
                System.out.println("Строка не должна быть пустой!");
                continue;
            }

            boolean isExist = false;
            for (Ingredient ingredient : dataBase.getIngredientsList()) {
                if (ingredient.getName().equals(new_name)) {
                    System.out.println("Ингредиент с таким названием уже существует!");
                    isExist = true;
                    break;
                }
            }
            if (isExist) continue;
            break;
        }

        dataBase.getIngredientsList().get(choice_ingredient - 1).setName(new_name);

        System.out.println("Название успешно изменено!");
        homeActions.getBaseIngInfo();
    }
}
