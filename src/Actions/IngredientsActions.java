package Actions;

import java.util.ArrayList;

import ObjectClasses.Ingredient;
import ObjectClasses.Pizza;
import MainEvent.Input;
import MainEvent.Main;

public class IngredientsActions {
    private static ArrayList<Ingredient> ingredients_list = new ArrayList<>();

    public static ArrayList<Ingredient> getIngredientsList() {
        return ingredients_list;
    }

    public static void createNewIngredient() {
        System.out.println(Main.string_separator);
        System.out.print("Введите название ингредиента: ");
        String name = Input.inputString();

        for (Ingredient ingredient : ingredients_list) {
            if (ingredient.getInfo().equals(name)) {
                System.out.println("Ингредиент с таким названием уже существует!");
                createNewIngredient();
                return;
            }
        }

        System.out.print("Введите цену ингредиента: ");
        float price = Input.inputFloat();

        ingredients_list.add(new Ingredient(name, price));
        System.out.println("Ингредиент успешно добавлен!");
        Main.createIngAndBase();
    }

    public static void deleteIngredient() {
        if (ingredients_list.isEmpty()) {
            System.out.println("Вы не создали ни одного ингредиента!");
            Main.createIngAndBase();
            return;
        }

        System.out.println(Main.string_separator);
        System.out.println("Выберите ингредиент для удаления: ");
        int num = 1;

        for (Ingredient ingredient : ingredients_list) {
            System.out.println(num + ". " + ingredient.getInfo() + "\t" + ingredient.getPrice());
            num++;
        }
        System.out.println(num + ". Назад");

        System.out.print("Введите номер: ");
        int choice_del = Input.inputInt();

        if (choice_del == num)
            Main.createIngAndBase();

        else if ((choice_del > num) || (choice_del <= 0)) {
            Main.errorChoice();
            Main.createIngAndBase();
        }

        Ingredient deletedIngredient = ingredients_list.get(choice_del - 1);
        ingredients_list.remove(choice_del - 1);

        for (Pizza pizza : Actions.PizzaActions.getPizzaList()) { /// Удаление ингредиента из пицц, в которых он есть
            java.util.Iterator<Ingredient> it = pizza.getIngredientInfo().iterator();
            while (it.hasNext()) {
                if (it.next().getInfo().equals(deletedIngredient.getInfo())) {
                    it.remove();
                }
            }
        }

        System.out.println("Ингредиент успешно удалён!");
        Main.createIngAndBase();
    }

    public static void changeIngredientPrice() {
        if (ingredients_list.isEmpty()) {
            System.out.println("Вы не создали ни одного ингредиента!");
            Main.getBaseIngInfo();
            return;
        }

        System.out.println(Main.string_separator);
        System.out.println("Выберите ингредиент для изменения цены: ");
        int num = 1;

        for (Ingredient ingredient : ingredients_list) {
            System.out.println(num + ". " + ingredient.getInfo() + "\t" + ingredient.getPrice());
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

        ingredients_list.get(choice_ingredient - 1).setPrice(new_price);
        System.out.println("Цена успешно изменена!");
        Main.getBaseIngInfo();
    }
}
