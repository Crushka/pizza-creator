package ObjectClasses;

public abstract class PizzaElem {
    private String name;
    private float price;

    public void setPrice(float price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }
}
