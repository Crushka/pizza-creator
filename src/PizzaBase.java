public class PizzaBase implements IPizzaElems {
    private String type;
    private float price;
    private float percentage_from_default;

    private static float classic_base_price;

    PizzaBase(String type, float price, float percentage_from_default) {
        this.type = type;
        this.percentage_from_default = percentage_from_default;

        setPrice(price);
    }

    public float getPercentage() {
        return percentage_from_default;
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
        if (type.equals("Классическое тесто")) {
            classic_base_price = price;
            this.price = price;
            return;
        }

        if (classic_base_price == 0) {
            classic_base_price = price;
            this.price = price;
        }
        else {
            if (price > (classic_base_price * 1.2f)) {
                System.out.println("Цена основы слишком большая!");
                this.price = -1;
            }
            else {
                this.price = price;
            }
        }
    }
}
