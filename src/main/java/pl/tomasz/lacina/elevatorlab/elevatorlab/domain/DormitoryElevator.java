package pl.tomasz.lacina.elevatorlab.elevatorlab.domain;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.TreeSet;

public class DormitoryElevator implements Elevator {

    private Integer id;

    private Integer currentFloor;
    private Direction direction;

    private TreeSet<Integer> upwardFloors = new TreeSet<>();
    private TreeSet<Integer> downwardFloors = new TreeSet<>((o1, o2) -> -1 * o1.compareTo(o2));
    private Multimap<Integer, Integer> elevatorCalls = ArrayListMultimap.create();

    public DormitoryElevator(Integer id) {
        this.id = id;
        this.currentFloor = 0;
        this.direction = Direction.UP;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void nextFloor(){
        TreeSet<Integer> floorSet = getFloorSet();

        if(floorSet.isEmpty()){
            switchDirection();
            return;
        }

        currentFloor = floorSet.pollFirst();
        if(elevatorCalls.containsKey(currentFloor)){
            elevatorCalls.removeAll(currentFloor)
                    .forEach(this::addFloorToSet);
        }
    }

    @Override
    public void addFloorToQueue(Integer floor, Integer moveToFloor) {
        addFloorToSet(floor);
        elevatorCalls.put(floor, moveToFloor);
    }

    @Override
    public int currentFloor() {
        return currentFloor;
    }

    @Override
    public Integer distanceFrom(Integer floor) {
        TreeSet<Integer> queuedFloors = getFloorSet();

        if (floor.equals(currentFloor) || (upwardFloors.isEmpty() && downwardFloors.isEmpty()) ) {
            return 0;
        }
        if (getFloorDirection(floor) == getDirection()) {
            return Math.abs(floor - currentFloor);
        } else {
            Integer lastFloorFromQueue = queuedFloors.size() > 0 ? queuedFloors.last() : currentFloor;
            return Math.abs(currentFloor - lastFloorFromQueue) + Math.abs(floor - currentFloor);
        }
    }

    private TreeSet<Integer> getFloorSet() {
        return direction == Direction.UP ? upwardFloors : downwardFloors;
    }

    private void switchDirection() {
        if(direction == Direction.UP){
            direction = Direction.DOWN;
        } else {
            direction = Direction.UP;
        }
    }

    private void addFloorToSet(Integer floor) {
        if(floor > currentFloor) {
            upwardFloors.add(floor);
        } else {
            downwardFloors.add(floor);
        }
    }

    private Direction getFloorDirection(Integer floor) {
        if (floor > currentFloor) {
            return Direction.UP;
        } else {
            return Direction.DOWN;
        }
    }

    @Override
    public String toString() {
        return "DormitoryElevator{" +
                "id=" + id +
                ", currentFloor=" + currentFloor +
                ", direction=" + direction +
                ", upwardFloors=" + upwardFloors +
                ", downwardFloors=" + downwardFloors +
                ", elevatorCalls=" + elevatorCalls +
                '}';
    }
}
