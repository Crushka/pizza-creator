package ObjectClasses;

import java.util.ArrayList;

public interface IPizza {
    String getName();
    float getPrice();
    ArrayList<Ingredient> getIngredientInfo();
    void addCrust(Crust crust);
    void delCrust(Crust crust);
    ArrayList<Crust> getCrustInfo();
    PizzaBase getBaseInfo();
}
