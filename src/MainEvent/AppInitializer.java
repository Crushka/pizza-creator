package MainEvent;

import java.util.ArrayList;

import ObjectClasses.*;

public class AppInitializer {
    private final DataBase dataBase;

    public AppInitializer(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void initialize() {
        createBaseIng();
        createSystemPizza();
    }

    private void createBaseIng() {
        dataBase.addPizzaBaseToList(new PizzaBase("Classic", 80, 1));
        dataBase.addPizzaBaseToList(new PizzaBase("Cheese", 95, 0.95f / dataBase.getPizzaBaseList().get(0).getPercentage()));

        dataBase.addIngredientToList(new Ingredient("Tomatoes", 50));
        dataBase.addIngredientToList(new Ingredient("Mushrooms", 70));
        dataBase.addIngredientToList(new Ingredient("Chicken", 100));
        dataBase.addIngredientToList(new Ingredient("Cheese", 80));
    }

    private void createSystemPizza() {
        ArrayList<Ingredient> ing_for_1 = new ArrayList<>();
        ing_for_1.add(dataBase.getIngredientsList().get(0));
        ing_for_1.add(dataBase.getIngredientsList().get(0));
        ing_for_1.add(dataBase.getIngredientsList().get(1));
        ing_for_1.add(dataBase.getIngredientsList().get(2));
        ing_for_1.add(dataBase.getIngredientsList().get(2));

        dataBase.addSystemPizzaToList(
            new SystemPizza("Pizza Of Your BDay", dataBase.getPizzaBaseList().get(0), 0.7f, ing_for_1)
        );

        ArrayList<Ingredient> ing_for_2 = new ArrayList<>();
        ing_for_2.add(dataBase.getIngredientsList().get(3));
        ing_for_2.add(dataBase.getIngredientsList().get(3));
        ing_for_2.add(dataBase.getIngredientsList().get(3));
        ing_for_2.add(dataBase.getIngredientsList().get(3));
        ing_for_2.add(dataBase.getIngredientsList().get(2));

        dataBase.addSystemPizzaToList(
            new SystemPizza("Cheese Death", dataBase.getPizzaBaseList().get(1), 0.9f, ing_for_2)
        );
    }
}
