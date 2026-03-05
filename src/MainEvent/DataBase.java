package MainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ObjectClasses.*;
import Order.Order;

public class DataBase {
    private ArrayList<CustomPizza> custom_pizza_list = new ArrayList<>();
    private ArrayList<SystemPizza> system_pizza_list = new ArrayList<>();
    private ArrayList<CombinedPizza> combined_pizza_list = new ArrayList<>();

    private ArrayList<Crust> crusts = new ArrayList<>();
    private ArrayList<Ingredient> ingredients_list = new ArrayList<>();
    private ArrayList<PizzaBase> pizza_base_list = new ArrayList<>();

    private ArrayList<Order> uncompleted_orders = new ArrayList<>();
    private ArrayList<Order> completed_orders = new ArrayList<>();

    public void addCustomPizzaToList(CustomPizza pizza) {
        custom_pizza_list.add(pizza);
    }

    public void deleteCustomPizza(CustomPizza pizza) {
        custom_pizza_list.remove(pizza);
    }

    public List<CustomPizza> getCustomPizzaList() {
        return Collections.unmodifiableList(custom_pizza_list);
    }

    public void addSystemPizzaToList(SystemPizza pizza) {
        system_pizza_list.add(pizza);
    }

    public void deleteSystemPizza(SystemPizza pizza) {
        system_pizza_list.remove(pizza);
    }

    public List<SystemPizza> getSystemPizzaList() {
        return Collections.unmodifiableList(system_pizza_list);
    }

    public void addCombinedPizzaToList(CombinedPizza pizza) {
        combined_pizza_list.add(pizza);
    }

    public void deleteCombinedPizza(CombinedPizza pizza) {
        combined_pizza_list.remove(pizza);
    }

    public List<CombinedPizza> getCombinedPizzaList() {
        return Collections.unmodifiableList(combined_pizza_list);
    }

    public List<IPizza> getAllPizzaList() {
        ArrayList<IPizza> pizzas = new ArrayList<>();
        pizzas.addAll(custom_pizza_list);
        pizzas.addAll(system_pizza_list);
        pizzas.addAll(combined_pizza_list);

        return Collections.unmodifiableList(pizzas);
    }

    public void addCrustToList(Crust crust) {
        crusts.add(crust);
    }

    public void deleteCrust(Crust crust) {
        crusts.remove(crust);
    }

    public List<Crust> getCrustsList() {
        return Collections.unmodifiableList(crusts);
    }

    public void addIngredientToList(Ingredient ingredient) {
        ingredients_list.add(ingredient);
    }

    public void deleteIngredient(Ingredient ingredient) {
        ingredients_list.remove(ingredient);
    }

    public List<Ingredient> getIngredientsList() {
        return Collections.unmodifiableList(ingredients_list);
    }

    public void addPizzaBaseToList(PizzaBase pizzaBase) {
        pizza_base_list.add(pizzaBase);
    }

    public void deletePizzaBase(PizzaBase pizzaBase) {
        pizza_base_list.remove(pizzaBase);
    }

    public List<PizzaBase> getPizzaBaseList() {
        return Collections.unmodifiableList(pizza_base_list);
    }

    public void addUncompletedOrderToList(Order order) {
        uncompleted_orders.add(order);
    }

    public void deleteUncompletedOrder(Order order) {
        uncompleted_orders.remove(order);
    }

    public List<Order> getUncompletedOrdersList() {
        return Collections.unmodifiableList(uncompleted_orders);
    }

    public void addCompletedOrderToList(Order order) {
        completed_orders.add(order);
    }

    public List<Order> getCompletedOrdersList() {
        return Collections.unmodifiableList(completed_orders);
    }
}
