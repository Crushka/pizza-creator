package ObjectClasses;

public class Ingredient implements IPizzaElems {
    private String type;
    private float price;

    public Ingredient(String type, float price) {
        this.type = type;
        this.price = price;
    }

    @Override
    public String getInfo() {
        return type;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public void setPrice(float price) {
        this.price = price;
    }
}
