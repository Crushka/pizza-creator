package ObjectClasses;

public class PizzaWithSize {
    private IPizza pizza;
    private double sizeCoefficient;

    public PizzaWithSize(IPizza pizza, double sizeCoefficient) {
        this.pizza = pizza;
        this.sizeCoefficient = sizeCoefficient;
    }

    public IPizza getPizza() {
        return pizza;
    }

    public double getSizeCoefficient() {
        return sizeCoefficient;
    }

    public void setSizeCoefficient(double sizeCoefficient) {
        this.sizeCoefficient = sizeCoefficient;
    }

    public float getPriceWithSize() {
        return (float) (pizza.getPrice() * sizeCoefficient);
    }

    @Override
    public String toString() {
        return pizza.getName() + " (размер: " + sizeCoefficient + ")";
    }
}
