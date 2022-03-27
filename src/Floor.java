import java.util.ArrayList;

public class Floor {
    private ArrayList<Integer> goUp;
    private ArrayList<Integer> goDown;
    private boolean firstFloor;
    private boolean lastFloor;
    private final int floorNum;
    private ElevatorSystem elevatorSystem;

    public Floor (ElevatorSystem elevatorSystem, int floorNum) {
        this.elevatorSystem = elevatorSystem;
        goUp = new ArrayList<>();
        goDown = new ArrayList<>();
        this.floorNum = floorNum;
        firstFloor = false;
        lastFloor = false;
    }

    public Floor (ElevatorSystem elevatorSystem, int floorNum, int isFirstOrLast) { //isFirstOrLast == 1 -> this is last floor, == -1 -> this is first floor
        this.elevatorSystem = elevatorSystem;
        this.floorNum = floorNum;
        if (isFirstOrLast == 1) {
            lastFloor = true;
            firstFloor = false;
            goDown = new ArrayList<>();
        }
        else if (isFirstOrLast == -1) {
            lastFloor = false;
            firstFloor = true;
            goUp = new ArrayList<>();
        }
        else {
            goUp = new ArrayList<>();
            goDown = new ArrayList<>();
            firstFloor = false;
            lastFloor = false;;
        }
    }

    public void setPassenger(int change) {
        if (change > 0 && !lastFloor) {
            goUp.add(change + floorNum);
            elevatorSystem.notifyUp(floorNum);
        }
        else if (change < 0 && !firstFloor) {
            goDown.add(change + floorNum);
            elevatorSystem.notifyDown(floorNum);
        }
    }

    public ArrayList<Integer> getGoUpPassengers() {
        if (lastFloor || goUp.size() == 0)
            return null;
        ArrayList<Integer> result = (ArrayList<Integer>) goUp.clone();
        goUp.clear();
        return result;
    }

    public ArrayList<Integer> getGoDownPassengers() {
        if (firstFloor || goDown.size() == 0)
            return null;
        ArrayList<Integer> result = (ArrayList<Integer>) goDown.clone();
        goDown.clear();
        return result;
    }
}
