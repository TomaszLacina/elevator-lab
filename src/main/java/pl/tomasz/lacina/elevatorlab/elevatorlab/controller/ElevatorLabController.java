package pl.tomasz.lacina.elevatorlab.elevatorlab.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.tomasz.lacina.elevatorlab.elevatorlab.service.ElevatorService;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
public class ElevatorLabController {

    @Autowired
    private final ElevatorService elevatorService;

    @Autowired
    private final TaskScheduler taskScheduler;

    @RequestMapping(method = RequestMethod.POST, value = "/callElevator")
    public void callElevator(@RequestParam Integer floor, @RequestParam Integer destinationFloor) {
        elevatorService.callElevator(floor, destinationFloor);
    }

    @GetMapping("/elevatorLab")
    public Flux<ServerSentEvent<String>> streamElevators(@RequestParam Integer elevatorCount,
                                                         @RequestParam Integer floorCount) {
        elevatorService.init(elevatorCount, floorCount);

        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> {
                    elevatorService.moveElevators();

                    return ServerSentEvent.<String>builder()
                            .id(String.valueOf(sequence))
                            .event("periodic-event")
                            .data(elevatorService.elevatorsStatus())
                            .build();
                });
    }




    @PostMapping(value = "/randomCalls")
    public void makeRandomCalls() {
        Random random = new Random();
        taskScheduler.scheduleAtFixedRate(() ->
                callElevator(random.nextInt(elevatorService.getMaxFloors()), random.nextInt(elevatorService.getMaxFloors()))
                , 400);
    }

}
