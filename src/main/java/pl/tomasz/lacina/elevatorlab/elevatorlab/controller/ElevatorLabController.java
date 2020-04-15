package pl.tomasz.lacina.elevatorlab.elevatorlab.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.tomasz.lacina.elevatorlab.elevatorlab.domain.ElevatorCall;
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
    @CrossOrigin(origins = "http://localhost:3000")
    public void callElevator(@RequestBody ElevatorCall elevatorCall) {
        elevatorService.callElevator(elevatorCall.getFloor(), elevatorCall.getDestinationFloor());
    }

    @GetMapping("/elevatorLab")
    @CrossOrigin(origins = "http://localhost:3000")
    public Flux<ServerSentEvent<String>> streamElevators(@RequestParam Integer elevatorCount,
                                                         @RequestParam Integer floorCount) {
        elevatorService.init(elevatorCount, floorCount);

        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> {
                    elevatorService.moveElevators();
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        return ServerSentEvent.<String>builder()
                                .id(String.valueOf(sequence))
                                .event("message")
                                .data(mapper.writeValueAsString(elevatorService.getElevatorList()))
                                .build();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }




    @PostMapping(value = "/randomCalls")
    @CrossOrigin(origins = "http://localhost:3000/")
    public void makeRandomCalls() {
        Random random = new Random();
        taskScheduler.scheduleAtFixedRate(() ->
                callElevator(new ElevatorCall(random.nextInt(elevatorService.getMaxFloors()), random.nextInt(elevatorService.getMaxFloors())))
                , 400);
    }

}
