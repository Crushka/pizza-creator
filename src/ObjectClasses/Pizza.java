package ObjectClasses;

import java.util.ArrayList;

public class Pizza {
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private PizzaBase pizza_base;
    private String name;

    public Pizza(String name) {
        this.name = name;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void addBase(PizzaBase pizzaBase) {
        this.pizza_base = pizzaBase;
    }
    
    public void deleteIngredient(int el_num) {
        ingredients.remove(el_num);
    }

    public void deleteIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Ingredient> getIngredientInfo() {
        return ingredients;
    }

    public PizzaBase getBaseInfo() {
        return pizza_base;
    }

    public float getPrice() {
        float price = pizza_base.getPrice();
        for (Ingredient ingredient : ingredients) {
            price += ingredient.getPrice();
        }
        return price;
    }
}
