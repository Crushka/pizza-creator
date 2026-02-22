package ObjectClasses;

import java.util.ArrayList;

public class Crust {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<String> white_listed_pizzas;

    public Crust(String name, ArrayList<Ingredient> ingredients, ArrayList<String> white_listed_pizzas) {
        this.name = name;
        this.ingredients = ingredients;
        this.white_listed_pizzas = white_listed_pizzas;
    }

    public boolean isCompatibleWithPizza(IPizza pizza) {
        return white_listed_pizzas.contains(pizza.getName());
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        float price = 0;
        for (Ingredient ingredient : ingredients) {
            price += ingredient.getPrice();
        }
        return price;
    }
}
