import java.util.Scanner;

public class UserInterface {
    private Building building;

    public UserInterface (Building building) {
        this.building = building;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Start!");
        while (true) {
            String input = scanner.nextLine();
            if (input.startsWith("status")) {
                System.out.println(building.getStatus());
            }
            else if (input.startsWith("click")) {
                String[] res = input.split(" ");
                building.click(Integer.parseInt(res[1]), Integer.parseInt(res[2]));

            }
            else if (input.startsWith("pickup")) {
                String[] res = input.split(" ");
                building.setPassenger(Integer.parseInt(res[1]), Integer.parseInt(res[2]));
            }
            else if (input.startsWith("step")) {
                int count;
                if (input.equals("step"))
                    count = 1;
                else {
                    String[] res = input.split(" ");
                    count = Integer.parseInt(res[1]);
                }
                building.makeSteps(count);
            }
            else if (input.startsWith("stop")) {
                break;
            }
            else {
                System.out.println("Command is not found - " + input);
            }
        }
        scanner.close();
    }
}
