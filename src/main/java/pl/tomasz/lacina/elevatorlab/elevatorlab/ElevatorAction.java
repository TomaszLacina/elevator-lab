package pl.tomasz.lacina.elevatorlab.elevatorlab;

import pl.tomasz.lacina.elevatorlab.elevatorlab.domain.DormitoryElevator;
import pl.tomasz.lacina.elevatorlab.elevatorlab.domain.Elevator;
import pl.tomasz.lacina.elevatorlab.elevatorlab.service.ElevatorCaller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ElevatorAction {


    public static void main(String[] args) {

        List<Elevator> elevatorList = new ArrayList<>();
        int elevatorCount = 3;
        int maxFloor = 10;
        for(int i = 0; i < elevatorCount; i++) {
            elevatorList.add(new DormitoryElevator(i, maxFloor));
        }

        ElevatorCaller elevatorCaller = new ElevatorCaller(elevatorList, maxFloor);

        Integer moves = 500;

        Random random = new Random();
        for(int i = 0 ; i < moves; i++){
            elevatorCaller.callElevator(random.nextInt(maxFloor), random.nextInt(maxFloor));
            elevatorCaller.moveElevators();
            elevatorCaller.printStatus();

        }
    }

}
