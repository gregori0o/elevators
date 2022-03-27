

public class Main {
    public static void main (String[] args) {
        Building building = new Building(0, 10, 5);
        UserInterface UI = new UserInterface(building);
        UI.start();
    }
}
