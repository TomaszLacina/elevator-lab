package pl.tomasz.lacina.elevatorlab.elevatorlab.domain;

import com.google.common.collect.Multimap;

import java.util.TreeSet;

public interface  Elevator {
    TreeSet<Integer> getUpwardFloors();

    TreeSet<Integer> getDownwardFloors();

    Multimap<Integer, Integer> getElevatorCalls();

    /**
     * Enumeration for describing elevator's direction.
     */
    enum Direction {
        UP, DOWN, NONE
    }

    /**
     * Tells which direction is the elevator going in.
     *
     * @return Direction Enumeration value describing the direction.
     */
    Direction getDirection();

    /**
     * Get the Id of this elevator.
     *
     * @return primitive integer representing the elevator.
     */
    int getId();

    /**
     * Reports which floor the elevator is at right now.
     *
     * @return int actual floor at the moment.
     */
    Integer currentFloor();

    Integer distanceFrom(Integer floor);

    void nextFloor();

    void addFloorToQueue(Integer floor, Integer moveToFloor);
}
