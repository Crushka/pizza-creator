package ObjectClasses;

import java.util.ArrayList;
import java.util.Set;

public class CustomPizza implements IPizza {
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<Crust> crusts = new ArrayList<>();
    private PizzaBase pizza_base;
    private String name;

    public CustomPizza(String name, PizzaBase pizzaBase) {
        this.name = name;
        this.pizza_base = pizzaBase;
    }

    public void setCrust(ArrayList<Crust> crusts) {
        for (Crust crust : crusts) {
            if (crust.isCompatibleWithPizza(this)) {
                this.crusts.add(crust);
                System.out.println("Бортик " + crust.getName() + " добавлен к пицце " + name);
            }
            else {
                System.out.println("Бортик " + crust.getName() + " не является разрешенным для пиццы " + name);
            }
        }
        
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

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArrayList<Ingredient> getIngredientInfo() {
        return ingredients;
    }

    @Override
    public PizzaBase getBaseInfo() {
        return pizza_base;
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
        return price;
    }

    @Override
    public void addCrust(Crust crust) {
        if (!crusts.contains(crust)) {
            crusts.add(crust);
        }
        else {
            System.out.println("Бортик не был добавлен, так как он уже содержится в пицце");
        }
    }

    @Override
    public void delCrust(Crust crust) {
        crusts.remove(crust);
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
        sb.append("   Основа: ").append(this.getBaseInfo().getName()).append("\t")
            .append(this.getBaseInfo().getPrice()).append("руб.\n");
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
