package ObjectClasses;

import java.util.ArrayList;
import java.util.Set;

public class SystemPizza implements IPizza {
    private String name;
    private float price;
    private PizzaBase pizza_base;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Crust> crusts;

    public SystemPizza(String name, float price, PizzaBase pizza_base,
        ArrayList<Ingredient> ingredients, ArrayList<Crust> crusts) {

        this.name = name;
        this.price = price;
        this.pizza_base = pizza_base;
        this.ingredients = ingredients;

        if (crusts != null) {
            for (Crust crust : crusts) {
                if (!crust.isCompatibleWithPizza(this)) {
                    throw new IllegalArgumentException("Crust " + crust.getName() + " is not compatible with pizza " + name);
                }
            }
        }
        this.crusts = crusts;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public ArrayList<Ingredient> getIngredientInfo() {
        return ingredients;
    }

    @Override
    public PizzaBase getBaseInfo() {
        return pizza_base;
    }

    public void ingredientsDouble() {
        if (ingredients.size() == 0) {
            return;
        }
        ArrayList<Ingredient> newIngredients = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            newIngredients.add(ingredient);
            newIngredients.add(ingredient);
        }
        ingredients = newIngredients;
    }

    @Override
    public ArrayList<Crust> getCrustInfo() {
        return crusts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Set<Ingredient> uniqueIngredients = new java.util.HashSet<>(getIngredientInfo());

        sb.append(getName()).append("\t").append(this.getPrice()).append("руб.\n");
        sb.append("   Основа: ").append(this.getBaseInfo().getName()).append("\t").append(this.getBaseInfo().getPrice()).append("руб.\n");
        for (Ingredient ingredient : uniqueIngredients) {
            int quantity = java.util.Collections.frequency(this.getIngredientInfo(), ingredient);
            sb.append("   ").append(ingredient.getName()).append("\t").append(quantity).append("x")
            .append("\t").append(ingredient.getPrice()*quantity).append("руб.\n");
        }
        return sb.toString();
    }
}
