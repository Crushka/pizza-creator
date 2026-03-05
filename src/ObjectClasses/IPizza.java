package ObjectClasses;

import java.util.List;

public interface IPizza {
    String getName();
    float getPrice();
    List<Ingredient> getIngredientInfo();
    void addCrust(Crust crust);
    void delCrust(Crust crust); void delCrust(int index);
    List<Crust> getCrustInfo();
    PizzaBase getBaseInfo();
    void removeIngredient(String name);
}
