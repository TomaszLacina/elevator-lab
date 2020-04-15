### Build And Run

As the project is, the Spring app can be started as seen below.

build and run the code with Maven

    mvn package
    mvn spring-boot:run
    
    
## Important changes and TODOs
    * I didn't like orginal Elevator interface, I have changed it a bit
    * Algorithm of finding closes elevator isn't dynamic, however as I call neither it was during my living in  student dormitory time. I always had to wait
    * Implement max capacity of elevator
    * Implement stop experiment functionality, right now it works forever
    * Add tests
      