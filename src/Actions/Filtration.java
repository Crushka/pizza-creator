package Actions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ObjectClasses.*;
import Order.Order;

public class Filtration {

    /// ПИЦЦЫ

    public static List<IPizza> filterPizzasByIngredient(List<IPizza> pizzas, String ingredient_name) {
        if (ingredient_name == null || ingredient_name.isBlank())
            return new ArrayList<>(pizzas);
        String lower = ingredient_name.trim().toLowerCase();
        return pizzas.stream()
            .filter(p -> p.getIngredientInfo().stream()
                .anyMatch(ing -> ing.getName().toLowerCase().contains(lower)))
            .collect(Collectors.toList());
    }

    /// ЗАКАЗЫ

    public static List<Order> filterOrdersByDate(List<Order> orders, LocalDate date) {
        if (date == null)
            return new ArrayList<>(orders);
        return orders.stream()
            .filter(o -> o.getOrderTime() != null && o.getOrderTime().toLocalDate().equals(date))
            .collect(Collectors.toList());
    }

    /// ИНГРЕДИЕНТЫ

    public static List<Ingredient> filterIngredientsByName(List<Ingredient> ingredients, String name) {
        if (name == null || name.isBlank())
            return new ArrayList<>(ingredients);
        String lower = name.trim().toLowerCase();
        return ingredients.stream()
            .filter(i -> i.getName().toLowerCase().contains(lower))
            .collect(Collectors.toList());
    }

    /// ОСНОВЫ

    public static List<PizzaBase> filterBasesByName(List<PizzaBase> bases, String name) {
        if (name == null || name.isBlank())
            return new ArrayList<>(bases);
        String lower = name.trim().toLowerCase();
        return bases.stream()
            .filter(b -> b.getName().toLowerCase().contains(lower))
            .collect(Collectors.toList());
    }

    /// БОРТИКИ

    public static List<Crust> filterCrustsByName(List<Crust> crusts, String name) {
        if (name == null || name.isBlank())
            return new ArrayList<>(crusts);
        String lower = name.trim().toLowerCase();
        return crusts.stream()
            .filter(c -> c.getName().toLowerCase().contains(lower))
            .collect(Collectors.toList());
    }

    public static List<Crust> filterCrustsByIngredients(List<Crust> crusts, String ingredient_name) {
        if (ingredient_name == null || ingredient_name.isBlank())
            return new ArrayList<>(crusts);
        String lower = ingredient_name.trim().toLowerCase();
        return crusts.stream()
            .filter(c -> c.getIngredients().stream()
                .anyMatch(ing -> ing.getName().toLowerCase().contains(lower)))
            .collect(Collectors.toList());
    }
}
