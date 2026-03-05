package MainEvent;
import Actions.*;

public class Main {
    public static void main(String[] args) {
        Input input = new Input();
        DataBase dataBase = new DataBase();

        new AppInitializer(dataBase).initialize();

        HomeActions homeActions = new HomeActions(dataBase, input);
        homeActions.home();

        input.onClose();
    }
}
