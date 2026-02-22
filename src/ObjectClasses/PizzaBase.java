package ObjectClasses;

public class PizzaBase extends PizzaElem {
    private float percentage_from_default;
    private static float classic_base_price;

    public PizzaBase(String name, float price, float percentage_from_default) {
        super.setName(name);
        this.percentage_from_default = percentage_from_default;

        setPrice(price);
    }

    public float getPercentage() {
        return percentage_from_default;
    }

    @Override
    public void setPrice(float price) {
        if (super.getName().equals("Classic")) {
            classic_base_price = price;
            super.setPrice(price);
            return;
        }
        
        if (classic_base_price == 0) {
            classic_base_price = price;
            super.setPrice(price);
        }
        else {
            if (price > (classic_base_price * 1.2f)) {
                System.out.println("Цена основы слишком большая!");
                super.setPrice(-1);
            }
            else {
                super.setPrice(price);;
            }
        }
    }
}
