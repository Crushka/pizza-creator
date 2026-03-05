package ObjectClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public void removeIngredient(String name) {
        ingredients.removeIf(ing -> ing.getName().equals(name));
    }

    public void removePizzaFromWhiteList(String pizzaName) {
        white_listed_pizzas.removeIf(p -> p.getName().equals(pizzaName));
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

    public List<Ingredient> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void deleteIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    } public void deleteIngredient(int index) {
        ingredients.remove(index);
    }

    public List<IPizza> getWhiteList() {
        return Collections.unmodifiableList(white_listed_pizzas);
    }

    public void addToWhiteList(IPizza pizza) {
        white_listed_pizzas.add(pizza);
    }

    public void deleteFromWhiteList(IPizza pizza) {
        white_listed_pizzas.remove(pizza);
    } public void deleteFromWhiteList(int index) {
        white_listed_pizzas.remove(index);
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
