package pl.tomasz.lacina.elevatorlab.elevatorlab.service;

import pl.tomasz.lacina.elevatorlab.elevatorlab.domain.Elevator;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ElevatorCaller {

    List<Elevator> elevatorList;
    Integer maxFloor;

    public ElevatorCaller(List<Elevator> elevatorList, Integer maxFloor) {
        this.elevatorList = elevatorList;
        this.maxFloor = maxFloor;
    }

    public void printStatus(){
        for (Elevator elevator : elevatorList) {
            System.out.println(elevator.toString());
        }
    }


    public void startSimulation(int elevatorMoves){
        ExecutorService executorService = Executors.newSingleThreadExecutor();


        executorService.submit(() -> {
            int counter = 0;
            while (counter < 100) {
                moveElevators();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                counter ++;
            }
        });

    }

    public void moveElevators() {
        for (Elevator elevator : elevatorList) {
            elevator.nextFloor();
        }
    }


    public Integer callElevator(Integer callFromFloor, Integer moveToFloor){
        Elevator closestElevator = getClosestElevator(callFromFloor);
        closestElevator.addFloorToQueue(callFromFloor,  moveToFloor);

        System.out.println("Elevator " + closestElevator.getId() + " was called to floor " + callFromFloor);

        return closestElevator.getId();
    }


    private Elevator getClosestElevator(Integer floor){
        Elevator closest = elevatorList.stream()
                .min(Comparator.comparing(o -> o.distanceFrom(floor)))
                .get();


        return closest;
    }


}

