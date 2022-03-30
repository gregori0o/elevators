import java.util.ArrayList;

public class Building {
    private ElevatorSystem elevatorSystem;
    private Floor[] floors;
    private final int firstFloor;
    private final int lastFloor;
    private final int elevatorNum;

    public Building (int firstFloor, int lastFloor, int elevatorNum) {
        this.elevatorNum = elevatorNum;
        this.firstFloor = firstFloor;
        this.lastFloor = lastFloor;
        elevatorSystem = new ElevatorSystem(elevatorNum, firstFloor, this);
        floors = new Floor[lastFloor - firstFloor + 1];
        for (int i = 0; i <= lastFloor - firstFloor; i++) {
            if (i == 0)
                floors[i] = new Floor(elevatorSystem, i + firstFloor, -1);
            else if (i == lastFloor - firstFloor)
                floors[i] = new Floor(elevatorSystem, i + firstFloor, 1);
            else
                floors[i] = new Floor(elevatorSystem, i + firstFloor);
        }
    }

    public String getStatus () {
        String buildingDescription = "Building -> min floor: " + firstFloor + " max floor: " + lastFloor + " with " + elevatorNum + " elevators.\n";
        return buildingDescription + elevatorSystem.toString();
    }

    public void click (int num, int floor) {
        if (num < 0 || num >= elevatorNum) {
            System.out.println("Niepoprawny numer windy!");
            return;
        }
        if (floor < firstFloor || floor > lastFloor) {
            System.out.println("Niepoprawny numer piętra!");
            return;
        }
        elevatorSystem.setFloorInElevator(num, floor - firstFloor);
    }

    public void setPassenger (int start, int change) {
        if (start < firstFloor || start > lastFloor) {
            System.out.println("Niepoprawny numer piętra!");
            return;
        }
        if (start + change < firstFloor || start + change > lastFloor) {
            System.out.println("Niepoprawny numer piętra docelowego!");
            return;
        }
        floors[start - firstFloor].setPassenger(change);
    }

    public void makeSteps (int count) {
        elevatorSystem.makeSteps(count);
    }

    public ArrayList<Integer> getPassengers (int floor, int change) {
        if (change == 1) {
            ArrayList<Integer> res = floors[floor - firstFloor].getGoUpPassengers();
            if (res != null)
                return res;
        }
        else if (change == -1) {
            ArrayList<Integer> res = floors[floor - firstFloor].getGoDownPassengers();
            if (res != null)
                return res;
        }
        return new ArrayList<>();
    }
}
