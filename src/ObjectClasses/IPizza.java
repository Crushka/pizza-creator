package ObjectClasses;

import java.util.ArrayList;

public interface IPizza {
    String getName();
    float getPrice();
    ArrayList<Ingredient> getIngredientInfo();
    ArrayList<Crust> getCrustInfo();
    PizzaBase getBaseInfo();
}
