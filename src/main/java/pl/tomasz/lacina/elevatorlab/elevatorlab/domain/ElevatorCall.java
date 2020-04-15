package pl.tomasz.lacina.elevatorlab.elevatorlab.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ElevatorCall {

    private final Integer floor;
    private final Integer destinationFloor;
}
