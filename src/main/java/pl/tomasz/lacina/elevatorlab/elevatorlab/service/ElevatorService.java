package pl.tomasz.lacina.elevatorlab.elevatorlab.service;

import org.springframework.stereotype.Service;
import pl.tomasz.lacina.elevatorlab.elevatorlab.domain.DormitoryElevator;
import pl.tomasz.lacina.elevatorlab.elevatorlab.domain.Elevator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElevatorService {

    private List<Elevator> elevatorList;

    private Integer maxFloors;

    public Integer getMaxFloors() {
        return Integer.valueOf(maxFloors);
    }

    public List<Elevator> getElevatorList() {
        return  Collections.unmodifiableList(elevatorList);
    }

    public void init(Integer elevatorCount, Integer floorCount){
        this.maxFloors = floorCount;
        elevatorList = new ArrayList<>();

        for(int i = 0 ; i < elevatorCount ; i++) {
            elevatorList.add(new DormitoryElevator(i, maxFloors));
        }

    }


    public void moveElevators(){
        for(Elevator elevator : elevatorList){
            elevator.nextFloor();
            System.out.println(elevator.toString());
        }
        System.out.println("*********************************");
    }

    public Integer callElevator(Integer floor, Integer destinationFloor){
        Elevator closestElevator = getClosestElevator(floor);
        closestElevator.addFloorToQueue(floor,  destinationFloor);

        System.out.println("Elevator " + closestElevator.getId() + " was called to floor " + floor);

        return closestElevator.getId();
    }


    private Elevator getClosestElevator(Integer floor){
        Elevator closest = elevatorList.stream()
                .min(Comparator.comparing(o -> o.distanceFrom(floor)))
                .get();


        return closest;
    }

    public String elevatorsStatus(){
        return elevatorList.stream()
                .map(Elevator::toString)
                .collect(Collectors.joining());
    }



}
