package MainEvent;

import java.util.ArrayList;

import ObjectClasses.*;

public class DataBase {
    private static ArrayList<CustomPizza> custom_pizza_list = new ArrayList<>();
    private static ArrayList<SystemPizza> system_pizza_list = new ArrayList<>();
    private static ArrayList<CombinedPizza> combined_pizza_list = new ArrayList<>();

    private static ArrayList<Crust> crusts = new ArrayList<>();
    private static ArrayList<Ingredient> ingredients_list = new ArrayList<>();
    private static ArrayList<PizzaBase> pizza_base_list = new ArrayList<>();

    public static ArrayList<CustomPizza> getCustomPizzaList() {
        return custom_pizza_list;
    }

    public static ArrayList<SystemPizza> getSystemPizzaList() {
        return system_pizza_list;
    }

    public static ArrayList<CombinedPizza> getCombinedPizzaList() {
        return combined_pizza_list;
    }

    public static ArrayList<IPizza> getAllPizzaList() {
        ArrayList<IPizza> pizzas = new ArrayList<>();
        pizzas.addAll(custom_pizza_list);
        pizzas.addAll(system_pizza_list);
        pizzas.addAll(combined_pizza_list);

        return pizzas;
    }

    public static ArrayList<Crust> getCrustsList() {
        return crusts;
    }

    public static ArrayList<Ingredient> getIngredientsList() {
        return ingredients_list;
    }

    public static ArrayList<PizzaBase> getPizzaBaseList() {
        return pizza_base_list;
    }
}
