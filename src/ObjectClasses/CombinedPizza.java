package ObjectClasses;

import java.util.ArrayList;
import java.util.Set;

public class CombinedPizza implements IPizza {
    private IPizza pizza1;
    private IPizza pizza2;
    private PizzaBase pizza_base;
    private ArrayList<Crust> crusts = new ArrayList<>();
    
    public CombinedPizza(IPizza pizza1, IPizza pizza2, PizzaBase pizza_base) {
        this.pizza1 = pizza1;
        this.pizza2 = pizza2;
        
        if (pizza_base != pizza1.getBaseInfo() && pizza_base != pizza2.getBaseInfo()) {
            throw new IllegalArgumentException("Комбинированные пиццы должны иметь основу первой или второй пиццы.");
        }
        this.pizza_base = pizza_base;
    }

    public void setPizza1(IPizza pizza) {
        this.pizza1 = pizza;
    }

    public IPizza getPizza1() {
        return pizza1;
    }

    public void setPizza2(IPizza pizza) {
        this.pizza2 = pizza;
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

    public void changeBase(PizzaBase pizza_base) {
        if (pizza_base != pizza1.getBaseInfo() && pizza_base != pizza2.getBaseInfo()) {
            throw new IllegalArgumentException("Комбинированные пиццы должны иметь основу первой или второй пиццы.");
        }
        this.pizza_base = pizza_base;
    }

    @Override
    public PizzaBase getBaseInfo() {
        return pizza_base;
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

        sb.append(getName()).append("\t").append(getPrice()).append("руб.\n");
        sb.append("   Основа: ").append(getBaseInfo().getName()).append("\t").append(getBaseInfo().getPrice()).append("руб.\n");
        for (Ingredient ingredient : uniqueIngredients) {
            int quantity = java.util.Collections.frequency(getIngredientInfo(), ingredient);
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
