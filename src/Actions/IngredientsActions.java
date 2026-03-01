package Actions;

import ObjectClasses.*;
import MainEvent.*;

public class IngredientsActions {

    public static void createNewIngredient() {
        System.out.println(Main.string_separator);
        System.out.print("Введите название ингредиента: ");
        String name = Input.inputString();

        for (Ingredient ingredient : DataBase.getIngredientsList()) {
            if (ingredient.getName().equals(name)) {
                System.out.println("Ингредиент с таким названием уже существует!");
                createNewIngredient();
                return;
            }
        }

        System.out.print("Введите цену ингредиента: ");
        float price = Input.inputFloat();

        DataBase.getIngredientsList().add(new Ingredient(name, price));
        System.out.println("Ингредиент успешно добавлен!");
        Main.createThings();
    }

    public static void deleteIngredient() {
        if (DataBase.getIngredientsList().isEmpty()) {
            System.out.println("Вы не создали ни одного ингредиента!");
            Main.createThings();
            return;
        }

        System.out.println(Main.string_separator);
        System.out.println("Выберите ингредиент для удаления: ");
        int num = 1;

        for (Ingredient ingredient : DataBase.getIngredientsList()) {
            System.out.println(num + ". " + ingredient.getName() + "\t" + ingredient.getPrice());
            num++;
        }
        System.out.println(num + ". Назад");

        System.out.print("Введите номер: ");
        int choice_del = Input.inputInt();

        if (choice_del == num)
            Main.createThings();

        else if ((choice_del > num) || (choice_del <= 0)) {
            Main.errorChoice();
            Main.createThings();
        }

        Ingredient deletedIngredient = DataBase.getIngredientsList().get(choice_del - 1);
        DataBase.getIngredientsList().remove(choice_del - 1);

        for (IPizza pizza : DataBase.getAllPizzaList()) { /// Удаление ингредиента из пицц, в которых он есть
            java.util.Iterator<Ingredient> it = pizza.getIngredientInfo().iterator();
            while (it.hasNext()) {
                if (it.next().getName().equals(deletedIngredient.getName())) {
                    it.remove();
                }
            }
        }

        for (Crust crust : DataBase.getCrustsList()) { /// Удаление ингредиента из бортиках, в которых он есть
            java.util.Iterator<Ingredient> it = crust.getIngredients().iterator();
            while (it.hasNext()) {
                if (it.next().getName().equals(deletedIngredient.getName())) {
                    it.remove();
                }
            }
        }

        System.out.println("Ингредиент успешно удалён!");
        Main.createThings();
    }

    public static void changeIngredientPrice() {
        if (DataBase.getIngredientsList().isEmpty()) {
            System.out.println("Вы не создали ни одного ингредиента!");
            Main.getBaseIngInfo();
            return;
        }

        System.out.println(Main.string_separator);
        System.out.println("Выберите ингредиент для изменения цены: ");
        int num = 1;

        for (Ingredient ingredient : DataBase.getIngredientsList()) {
            System.out.println(num + ". " + ingredient.getName() + "\t" + ingredient.getPrice());
            num++;
        }
        System.out.println(num + ". Назад");
                
        System.out.print("Введите номер: ");
        int choice_ingredient = Input.inputInt();

        if (choice_ingredient == num) {
            Main.getBaseIngInfo();
        }
        else if ((choice_ingredient > num) || (choice_ingredient <= 0)) {
            Main.errorChoice();
            changeIngredientPrice();
        }

        System.out.print("Введите новую цену: ");
        float new_price = Input.inputFloat();

        DataBase.getIngredientsList().get(choice_ingredient - 1).setPrice(new_price);
        System.out.println("Цена успешно изменена!");
        Main.getBaseIngInfo();
    }

    public static void changeIngredientName() {
        if (DataBase.getIngredientsList().isEmpty()) {
            System.out.println("Вы не создали ни одного ингредиента!");
            Main.getBaseIngInfo();
            return;
        }

        System.out.println(Main.string_separator);
        System.out.println("Выберите ингредиент для изменения названия: ");
        int num = 1;

        for (Ingredient ingredient : DataBase.getIngredientsList()) {
            System.out.println(num + ". " + ingredient.getName() + "\t" + ingredient.getPrice());
            num++;
        }
        System.out.println(num + ". Назад");
                
        System.out.print("Введите номер: ");
        int choice_ingredient = Input.inputInt();

        if (choice_ingredient == num) {
            Main.getBaseIngInfo();
        }
        else if ((choice_ingredient > num) || (choice_ingredient <= 0)) {
            Main.errorChoice();
            changeIngredientName();
        }

        System.out.print("Введите новое название: ");
        String new_name = Input.inputString();

        for (Ingredient ingredient : DataBase.getIngredientsList()) {
            if (ingredient.getName().equals(new_name)) {
                System.out.println("Ингредиент с таким названием уже существует!");
                changeIngredientName();
                return;
            }
        }

        DataBase.getIngredientsList().get(choice_ingredient - 1).setName(new_name);

        System.out.println("Название успешно изменено!");
        Main.getBaseIngInfo();
    }
}
