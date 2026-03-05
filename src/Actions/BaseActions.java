package Actions;

import java.util.List;

import ObjectClasses.CustomPizza;
import ObjectClasses.PizzaBase;
import MainEvent.DataBase;
import MainEvent.Input;

public class BaseActions {
    
    DataBase dataBase;
    HomeActions homeActions;
    Input input;

    public BaseActions(DataBase dataBase, Input input, HomeActions homeActions) {
        this.dataBase = dataBase;
        this.input = input;
        this.homeActions = homeActions;
    }

    public void createNewPizzaBase() {
        String name_base;
        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.print("Введите название основы: ");
            name_base = input.inputString();

            boolean isExist = false;
            for (PizzaBase pizza_base : dataBase.getPizzaBaseList()) {
                if (pizza_base.getName().equals(name_base)) {
                    System.out.println("Основа с таким названием уже существует!");
                    isExist = true;
                }
                if (isExist) break;
            }
            if (isExist) continue;
            break;
        }
                
        PizzaBase base = dataBase.getPizzaBaseList().get(0);
        float max_price = base.getPrice() * 1.2f;
        float price_base; PizzaBase pizza_base;
        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.print("Введите цену основы (цена основы не должна превышать 20% классической, максимальная цена основы - " + max_price + "руб.): ");
            
            price_base = input.inputFloat();
            pizza_base = new PizzaBase(name_base, price_base, (price_base / base.getPrice()));
                
            if (pizza_base.getPrice() == -1) {
                continue;
            }
            break;
        }
                
        dataBase.addPizzaBaseToList(pizza_base);
        System.out.println("Основа успешно добавлена!");
        homeActions.createThings();
    }

    public void deletePizzaBase() {
        if (dataBase.getPizzaBaseList().isEmpty()) {
            System.out.println("Вы не создали ни одной основы!");
            homeActions.createThings();
            return;
        }

        System.out.println(homeActions.string_separator);
        System.out.println("Выберите основу для удаления: ");
                
        int num = 1;

        for (PizzaBase base1 : dataBase.getPizzaBaseList()) {
            System.out.println(num + ". " + base1.getName() + "\t" + base1.getPrice());
            num++;
        }
                
        System.out.println(num + ". Назад");

        int choice_del;
        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.print("Введите номер: ");
            choice_del = input.inputInt();
            if (choice_del == 1) {
                System.out.println("Вы не можете удалить классическую основу!");
                continue;
            }
            else if (choice_del == num) {
                homeActions.createThings();
                return;
            }
            else if ((choice_del > num) || (choice_del <= 0)) {
                homeActions.errorChoice();
                continue;
            }
            break;
        }

        List<CustomPizza> pizza_list = dataBase.getCustomPizzaList();
        int temp = 0;
        if (pizza_list.size() > 0) {
            for (CustomPizza pizza : pizza_list) {
                if (pizza.getBaseInfo().getName().equals(dataBase.getPizzaBaseList().get(choice_del - 1).getName())) {
                    System.out.println("Эта основа уже используется в пицце " + pizza.getName() + "!");
                    temp++;
                }
            }
        }
        if (temp == 0) {
            PizzaBase del_base = dataBase.getPizzaBaseList().get(choice_del - 1);
            dataBase.deletePizzaBase(del_base);
            System.out.println("Основа успешно удалена!");
        }
        homeActions.createThings();
        return;
    }

    public void changePizzaBasePrice() {
        if (dataBase.getPizzaBaseList().isEmpty()) {
            System.out.println("Вы не создали ни одной основы!");
            homeActions.getBaseIngInfo();
            return;
        }

        System.out.println(homeActions.string_separator);
        System.out.println("Выберите основу для изменения цены: ");
        int num = 1;

        for (PizzaBase pizza_base : dataBase.getPizzaBaseList()) {
            System.out.println(num + ". " + pizza_base.getName() + "\t" + pizza_base.getPrice());
            num++;
        }
        System.out.println(num + ". Назад");
        
        int choice_base;
        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.print("Введите номер: ");
            choice_base = input.inputInt();
                    
            if (choice_base == 1) {
                System.out.print("Введите новую цену: ");
                float new_price_base = input.inputFloat();

                for (PizzaBase pizza_base : dataBase.getPizzaBaseList()) {
                    pizza_base.setPrice(new_price_base * pizza_base.getPercentage());
                }

                System.out.println("Цена успешно изменена!");
                homeActions.getBaseIngInfo();
            }
            else if (choice_base == num) {
                homeActions.getBaseIngInfo();
            }
            else if ((choice_base > num) || (choice_base <= 0)) {
                homeActions.errorChoice();
                continue;
            }

            break;
        }

        System.out.println(homeActions.string_separator);
        PizzaBase base = dataBase.getPizzaBaseList().get(0);
        float max_price = base.getPrice() * 1.2f;
        PizzaBase cur_PizzaBase = dataBase.getPizzaBaseList().get(choice_base - 1);
        float cur_price = cur_PizzaBase.getPrice();

        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.print("Введите цену основы (цена основы не должна превышать 20% классической, максимальная цена основы - " + max_price + "руб.): ");
            float price_base = input.inputFloat();
            cur_PizzaBase.setPrice(price_base);
                
            if (cur_PizzaBase.getPrice() == -1) {
                cur_PizzaBase.setPrice(cur_price);
                continue;
            }
            else {
                System.out.println("Цена успешно изменена!");
                homeActions.getBaseIngInfo();
            }
            break;
        }
        return;
    }

    public void changePizzaBaseName() {
        if (dataBase.getPizzaBaseList().isEmpty()) {
            System.out.println("Вы не создали ни одной основы!");
            homeActions.getBaseIngInfo();
            return;
        }

        System.out.println(homeActions.string_separator);
        System.out.println("Выберите основу для изменения названия: ");
        int num = 1;

        for (PizzaBase pizza_base : dataBase.getPizzaBaseList()) {
            System.out.println(num + ". " + pizza_base.getName() + "\t" + pizza_base.getPrice());
            num++;
        }
        System.out.println(num + ". Назад");
        
        int choice_base;
        while (true) {
            System.out.println(homeActions.string_separator);
            System.out.print("Введите номер: ");
            choice_base = input.inputInt();

            if (choice_base == 1) {
                System.out.println("Вы не можете изменить название классической основы!");
                continue;
            }
            else if (choice_base == num) {
                homeActions.getBaseIngInfo();
                return;
            }
            else if ((choice_base > num) || (choice_base <= 0)) {
                homeActions.errorChoice();
                continue;
            }
            break;
        }
        
        String new_name;
        while (true) {
            System.out.print("Введите новое название: ");
            new_name = input.inputString();

            boolean isExist = false;
            for (PizzaBase pizza_base : dataBase.getPizzaBaseList()) {
                if (pizza_base.getName().equals(new_name)) {
                    System.out.println("Основа с таким названием уже существует!");
                    isExist = true;
                }
                if (isExist) break;
            }
            if (isExist) continue;
            break;
        }

        dataBase.getPizzaBaseList().get(choice_base - 1).setName(new_name);

        System.out.println("Название успешно изменено!");
        homeActions.getBaseIngInfo();
    }
}
