package Order;

import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalDateTime;
import ObjectClasses.PizzaWithSize;

public class Order {
    private UUID order_id;
    private String comment;
    private LocalDateTime order_time;

    private ArrayList<PizzaWithSize> pizzas = new ArrayList<>();

    public Order(ArrayList<PizzaWithSize> pizzas , String comment) {
        this.order_id = UUID.randomUUID();
        this.pizzas = pizzas;
        this.comment = comment;
    }
    public Order(ArrayList<PizzaWithSize> pizzas , String comment, LocalDateTime order_time) {
        this.order_id = UUID.randomUUID();
        this.pizzas = pizzas;
        this.comment = comment;
        this.order_time = order_time;
    }

    public UUID getOrderId() {
        return order_id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setOrderTime(LocalDateTime order_time) {
        this.order_time = order_time;
    }

    public LocalDateTime getOrderTime() {
        return order_time;
    }

    public void addPizza(PizzaWithSize pizza) {
        pizzas.add(pizza);
    }
    public void addPizza(ArrayList<PizzaWithSize> pizzas) {
        this.pizzas.addAll(pizzas);
    }

    public void deletePizza(PizzaWithSize pizza) {
        pizzas.remove(pizza);
    }

    public ArrayList<PizzaWithSize> getPizzas() {
        return pizzas;
    }

    public float getPrice() {
        float price = 0;
        for (PizzaWithSize pizza : pizzas) {
            price += pizza.getPriceWithSize();
        }

        return price;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID заказа: ").append(order_id.toString()).append("\n");
        sb.append("Комментарий: ").append(comment).append("\n");
        if (order_time != null)
            sb.append("Время заказа: ").append(order_time.toString()).append("\n");
        else
            sb.append("Заказ еще не отправлен в систему\n");
        sb.append("Пиццы:\n");
        for (PizzaWithSize pizza : pizzas) {
            sb.append("- ").append(pizza.toString()).append(" - ").append(pizza.getPriceWithSize()).append(" руб.\n");
        }
        sb.append("Итоговая стоимость: ").append(getPrice()).append(" руб.\n");
        return sb.toString();
    }
}
