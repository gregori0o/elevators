import java.util.Arrays;

import static java.lang.Math.abs;

public class ElevatorSystem {
    private Elevator [] elevators;

    public ElevatorSystem (int size, int startFloor, Building building) {
        elevators = new Elevator[size];
        for (int i = 0; i < size; i++)
            elevators[i] = new Elevator(startFloor, building);
    }

    public void notifyUp (int floorNum) {
        Elevator toNotify = null;
        int shortestPath = 0;
        for (Elevator elevator : elevators) {
            if (elevator.getState() == ElevatorState.STOP || (elevator.getState() == ElevatorState.UP && elevator.getCurrentFloor() < floorNum)) {
                int path = abs(elevator.getCurrentFloor() - floorNum);
                if (toNotify == null) {
                    shortestPath = path;
                    toNotify = elevator;
                }
                else if (path < shortestPath) {
                    toNotify = elevator;
                    shortestPath = path;
                }
            }
        }
        if (toNotify != null) {
            toNotify.setNotification(floorNum, 1);
            return;
        }
        for (Elevator elevator : elevators) {
            int path = abs(elevator.getDestination() - floorNum) + abs(elevator.getDestination() - elevator.getCurrentFloor());
            if (toNotify == null) {
                shortestPath = path;
                toNotify = elevator;
            }
            else if (path < shortestPath) {
                toNotify = elevator;
                shortestPath = path;
            }
        }
        if (toNotify != null) {
            toNotify.setNotification(floorNum, 1);
        }
    }

    public void notifyDown (int floorNum) {
        Elevator toNotify = null;
        int shortestPath = 0;
        for (Elevator elevator : elevators) {
            if (elevator.getState() == ElevatorState.STOP || (elevator.getState() == ElevatorState.DOWN && elevator.getCurrentFloor() > floorNum)) {
                int path = abs(elevator.getCurrentFloor() - floorNum);
                if (toNotify == null) {
                    shortestPath = path;
                    toNotify = elevator;
                }
                else if (path < shortestPath) {
                    toNotify = elevator;
                    shortestPath = path;
                }
            }
        }
        if (toNotify != null) {
            toNotify.setNotification(floorNum, -1);
            return;
        }
        for (Elevator elevator : elevators) {
            int path = abs(elevator.getDestination() - floorNum) + abs(elevator.getDestination() - elevator.getCurrentFloor());
            if (toNotify == null) {
                shortestPath = path;
                toNotify = elevator;
            }
            else if (path < shortestPath) {
                toNotify = elevator;
                shortestPath = path;
            }
        }
        if (toNotify != null) {
            toNotify.setNotification(floorNum, -1);
        }
    }

    public void setFloorInElevator (int elevator_num, int floor) {
        if (elevators[elevator_num].getCurrentFloor() < floor)
            elevators[elevator_num].setNotification(floor, 0);
        else {
            elevators[elevator_num].setNotification(floor, 0);
        }
    }

    public void makeSteps (int count) {
        for (int i = 0; i < count; i++) {
            for (Elevator elevator : elevators)
                elevator.step();
        }
    }

    @Override
    public String toString() {
        String response = "Elevator System status\n";
        for (Elevator elevator : elevators) {
            response += elevator.toString();
        }
        return response;
    }
}
