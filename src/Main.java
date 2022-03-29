

public class Main {
    public static void main (String[] args) {

        try {
            int firstFloor = Integer.parseInt(args[0]);
            int lastFloor = Integer.parseInt(args[1]);
            int elevatorNum = Integer.parseInt(args[2]);

            if (firstFloor >= lastFloor) {
                System.out.println("First floor must be lower than last floor.");
                System.exit(-1);
            }

            if (elevatorNum <= 0) {
                System.out.println("Number of elevators must be positive.");
                System.exit(-1);
            }
            Building building = new Building(firstFloor, lastFloor, elevatorNum);
            UserInterface UI = new UserInterface(building);
            UI.start();
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            System.out
                    .println("Arguments it isn't correct. The valid execution is: java Main int<first floor> int<last floor> int<number of elevators>.");
        } catch (Exception ex) {
            System.out
                    .println("The valid execution is: java Main int<first floor> int<last floor> int<number of elevators>.");
            System.out.println(ex.toString());
        }
    }
}
