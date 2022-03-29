import java.util.*;

public class Elevator {
    private ElevatorState state;
    private TreeSet<Pair> notificationNext;
    private Queue<Pair> notificationQueue;
    private int currentFloor;
    private final Building building;


    //class pair to hold two elements, floor and direction
    private class Pair implements Comparable {
        private final int floor;
        private final int nextDirection; //1 - go up, -1 - go down, 0 - go out of elevator

        public Pair (int firstElement, int secondElement) {
            this.floor = firstElement;
            this.nextDirection = secondElement;
        }

        public int getFloor() {
            return floor;
        }

        public int getNextDirection() {
            return nextDirection;
        }

        @Override
        public int compareTo(Object o) {
            Pair object1 = this;
            Pair object2 = (Pair) o;
            if (object1.getFloor() > object2.getFloor())
                return 1;
            else if (object1.getFloor() < object2.getFloor())
                return -1;
            else if (object1.getNextDirection() == object2.getNextDirection())
                return 0;
            else if (object1.getNextDirection() > object2.getNextDirection())
                return 1;
            return -1;
        }
    }

    public Elevator (int startFloor, Building building) {
        this.building = building;
        state = ElevatorState.STOP;
        notificationNext = new TreeSet<>();
        notificationQueue= new LinkedList<>();
        currentFloor = startFloor;
    }

    private void move() {
        if (state == ElevatorState.UP)
            currentFloor ++;
        else if (state == ElevatorState.DOWN)
            currentFloor --;
        else if (state == ElevatorState.GO) {
            if (notificationQueue.element().getFloor() > currentFloor)
                currentFloor ++;
            else
                currentFloor --;
        }
    }

    public void step() {
        if (state == ElevatorState.STOP) { //elevator is stopped so check queue
            if (!notificationQueue.isEmpty())
                state = ElevatorState.GO;
        }
        move();
        if (state == ElevatorState.GO) { //elevator go to floor of top of queue
            while (currentFloor == notificationQueue.element().getFloor()) { //while, because we may have on queue something from click command
                Pair nextMove = notificationQueue.remove();
                if (nextMove.getNextDirection() == 0) {
                    state = ElevatorState.STOP;
                    if (notificationQueue.isEmpty())
                        break;
                }
                else if (nextMove.getNextDirection() == 1) { //elevator take passenger and go up
                    state = ElevatorState.UP;
                    for (int floor : building.getPassengers(currentFloor, 1)) { //take passengers from floor
                        setNotification(floor, 0);
                    }
                    for (Iterator<Pair> it = notificationQueue.iterator(); it.hasNext(); ) { //take as much as possible from queue
                        Pair element = it.next();
                        if (element.getFloor() >= currentFloor && element.getNextDirection() != -1) {
                            if (element.getFloor() > currentFloor) {
                                setNotification(element.getFloor(), element.getNextDirection());
                            }
                            it.remove();
                        }
                    }
                    break;
                }
                else if (nextMove.getNextDirection() == -1) { //symmetrical situation
                    state = ElevatorState.DOWN;
                    for (int floor : building.getPassengers(currentFloor, -1)) {
                        setNotification(floor, 0);
                    }
                    for (Iterator<Pair> it = notificationQueue.iterator(); it.hasNext(); ) {
                        Pair element = it.next();
                        if (element.getFloor() <= currentFloor && element.getNextDirection() != 1) {
                            if (element.getFloor() < currentFloor) {
                                setNotification(element.getFloor(), element.getNextDirection());
                            }
                            it.remove();
                        }
                    }
                    break;
                }
            }
        }
        else if (state == ElevatorState.UP) { //go up while tree set is not empty and take passenger on the way
            boolean flag = false;
            while (notificationNext.first().getFloor() == currentFloor) {
                if (notificationNext.first().getNextDirection() == 1)
                    flag = true;
                notificationNext.pollFirst();
                if (notificationNext.isEmpty())
                    break;
            }
            if (flag) {
                for (int floor : building.getPassengers(currentFloor, 1)) {
                    setNotification(floor, 0);
                }
            }
            if (notificationNext.isEmpty()) {
                state = ElevatorState.STOP;
            }
        }
        else if (state == ElevatorState.DOWN) {
            boolean flag = false;
            while (notificationNext.last().getFloor() == currentFloor) {
                if (notificationNext.last().getNextDirection() == -1)
                    flag = true;
                notificationNext.pollLast();
                if (notificationNext.isEmpty())
                    break;
            }
            if (flag) {
                for (int floor : building.getPassengers(currentFloor, -1)) {
                    setNotification(floor, 0);
                }
            }
            if (notificationNext.isEmpty()) {
                state = ElevatorState.STOP;
            }
        }
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public ElevatorState getState() {
        return state;
    }

    public int getDestination() {
        if (state == ElevatorState.GO)
            return notificationQueue.element().getFloor();
        else if (state == ElevatorState.UP)
            return notificationNext.last().getFloor();
        else if (state == ElevatorState.DOWN)
            return notificationNext.first().getFloor();
        return currentFloor;
    }

    public void setNotification (int floor, int dir) {
        if (state == ElevatorState.UP && dir != -1 && floor > currentFloor)
            notificationNext.add(new Pair(floor, dir));
        else if (state == ElevatorState.DOWN && dir != 1 && floor < currentFloor)
            notificationNext.add(new Pair(floor, dir));
        else
            notificationQueue.add(new Pair(floor, dir));
    }

    @Override
    public String toString() {
        return "Current floor: " + currentFloor + " go to: " + getDestination() + " state: " + state.toString() +"\n";
    }
}
