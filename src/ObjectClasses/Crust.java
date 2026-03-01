package ObjectClasses;

import java.util.ArrayList;

public class Crust {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<IPizza> white_listed_pizzas;

    public Crust(String name, ArrayList<Ingredient> ingredients, ArrayList<IPizza> white_listed_pizzas) {
        this.name = name;
        this.ingredients = ingredients;
        this.white_listed_pizzas = white_listed_pizzas;
    }

    public boolean isCompatibleWithPizza(IPizza pizza) {
        return white_listed_pizzas.contains(pizza);
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

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<IPizza> getWhiteList() {
        return white_listed_pizzas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName()).append("\t").append(getPrice()).append("\n");

        sb.append("Ингредиенты:").append("\n");
        for (Ingredient ingredient : ingredients)
            sb.append(ingredient.getName()).append("\t").append(ingredient.getPrice()).append("\n");

        sb.append("Совместим с пиццами:").append("\n");
        for (IPizza pizza : white_listed_pizzas)
            sb.append(pizza.getName()).append("\n");

        return sb.toString();
    }
}
