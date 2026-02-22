package Order;

import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalDateTime;
import ObjectClasses.IPizza;

public class Order {
    private UUID order_id;
    private String comment;
    private LocalDateTime order_time;

    private ArrayList<IPizza> pizzas = new ArrayList<>();

    public Order(ArrayList<IPizza> pizzas , String comment) {
        this.order_id = UUID.randomUUID();
        this.pizzas = pizzas;
        this.comment = comment;

        this.order_time = LocalDateTime.now();
    }

    public Order(ArrayList<IPizza> pizzas , String comment, LocalDateTime order_time) {
        this.order_id = UUID.randomUUID();
        this.pizzas = pizzas;
        this.comment = comment;
        this.order_time = order_time;
    }

    public UUID getOrderId() {
        return order_id;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getOrderTime() {
        return order_time;
    }

    public void addPizza(IPizza pizza) {
        pizzas.add(pizza);
    }

    public ArrayList<IPizza> getPizzas() {
        return pizzas;
    }

    public float getPrice() {
        float price = 0;
        for (IPizza pizza : pizzas) {
            price += pizza.getPrice();
        }

        return price;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID заказа: ").append(order_id.toString()).append("\n");
        sb.append("Комментарий: ").append(comment).append("\n");
        sb.append("Время заказа: ").append(order_time.toString()).append("\n");
        sb.append("Пиццы:\n");
        for (IPizza pizza : pizzas) {
            sb.append("- ").append(pizza.getName()).append("\n");
        }
        sb.append("Итоговая стоимость: ").append(getPrice()).append(" руб.\n");
        return sb.toString();
    }
}
