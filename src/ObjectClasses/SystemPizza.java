package ObjectClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SystemPizza implements IPizza {
    private String name;
    private float markup;
    private PizzaBase pizza_base;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Crust> crusts;

    public SystemPizza(String name, PizzaBase pizza_base, float markup,
        ArrayList<Ingredient> ingredients, ArrayList<Crust> crusts) {

        this.name = name;
        this.markup = markup;
        this.pizza_base = pizza_base;
        this.ingredients = ingredients;

        if (crusts != null) {
            for (Crust crust : crusts) {
                if (!crust.isCompatibleWithPizza(this)) {
                    throw new IllegalArgumentException("Бортик " + crust.getName() + " не совместим с пиццей " + name);
                }
            }
            this.crusts = new ArrayList<>(crusts);
        } else {
            this.crusts = new ArrayList<>();
        }
    }

    public SystemPizza(String name, PizzaBase pizza_base, float markup,
        ArrayList<Ingredient> ingredients) {

        this.name = name;
        this.markup = markup;
        this.pizza_base = pizza_base;
        this.ingredients = ingredients;
        this.crusts = new ArrayList<>();
    }

    public void removeIngredient(String name) {
        ingredients.removeIf(ing -> ing.getName().equals(name));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getPrice() {
        float price = pizza_base.getPrice();
        for (Ingredient ingredient : ingredients) {
            price += ingredient.getPrice();
        }
        for (Crust crust : crusts) {
            price += crust.getPrice();
        }
        price *= markup;
        return price;
    }

    @Override
    public List<Ingredient> getIngredientInfo() {
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

    @Override
    public PizzaBase getBaseInfo() {
        return pizza_base;
    }

    public float getMarkup() {
        return markup;
    }

    public SystemPizza copy() {
        ArrayList<Ingredient> ingredientsCopy = new ArrayList<>(this.ingredients);
        ArrayList<Crust> crustsCopy = new ArrayList<>(this.crusts);
        return new SystemPizza(this.name, this.pizza_base, this.markup, ingredientsCopy, crustsCopy);
    }

    public void ingredientsDouble() {
        if (ingredients.size() == 0) {
            return;
        }
        ArrayList<Ingredient> new_ingredients = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            new_ingredients.add(ingredient);
            new_ingredients.add(ingredient);
        }
        ingredients = new_ingredients;
    }

    @Override
    public void addCrust(Crust crust) {
        if (!crusts.contains(crust)) {
            crusts.add(crust);
        }
    }

    @Override
    public void delCrust(Crust crust) {
        crusts.remove(crust);
    } public void delCrust(int index) {
        crusts.remove(index);
    }

    @Override
    public List<Crust> getCrustInfo() {
        return Collections.unmodifiableList(crusts);
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

        sb.append("   Бортики:").append("\n");
        if (crusts.isEmpty()) {
            sb.append("   У этой пиццы нет бортиков!");
        }
        else {
            for (Crust crust : crusts) {
                sb.append("   ").append(crust.getName()).append("\t").append(crust.getPrice()).append("руб.\n");
            }
        }

        return sb.toString();
    }
}
