package ObjectClasses;

import java.util.ArrayList;
import java.util.Set;

public class CombinedPizza implements IPizza {
    private IPizza pizza1;
    private IPizza pizza2;
    private PizzaBase pizzaBase;
    
    public CombinedPizza(IPizza pizza1, IPizza pizza2, PizzaBase pizzaBase) {
        this.pizza1 = pizza1;
        this.pizza2 = pizza2;
        
        if (pizzaBase != pizza1.getBaseInfo() && pizzaBase != pizza2.getBaseInfo()) {
            throw new IllegalArgumentException("Combined pizza must have a base that matches one of the component pizzas.");
        }
        this.pizzaBase = pizzaBase;
    }

    public IPizza getPizza1() {
        return pizza1;
    }

    public IPizza getPizza2() {
        return pizza2;
    }
    
    @Override
    public String getName() {
        return pizza1.getName() + " + " + pizza2.getName();
    }
    
    @Override
    public float getPrice() {
        return (pizza1.getPrice() + pizza2.getPrice()) * 0.5f;
    }

    @Override
    public ArrayList<Ingredient> getIngredientInfo() {
        ArrayList<Ingredient> combinedIngredients = new ArrayList<>();
        combinedIngredients.addAll(pizza1.getIngredientInfo());
        combinedIngredients.addAll(pizza2.getIngredientInfo());
        return combinedIngredients;
    }

    @Override
    public PizzaBase getBaseInfo() {
        return pizzaBase;
    }

    @Override
    public ArrayList<Crust> getCrustInfo() {
        ArrayList<Crust> crusts = new ArrayList<>();
        crusts.addAll(pizza1.getCrustInfo());
        crusts.addAll(pizza2.getCrustInfo());
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
