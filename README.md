# Elevator System

## Description

This is simple implementation of elevator system. 
We can control elevators by write command into terminal. System supports any range of floors and any number of elevators.

## Usage

To use this program you need java compiler and:
1) Clone this repository:
```
git clone https://github.com/gregori0o/elevators elevators
cd elevators
```
2) Compile and run program:
```
javac src/Main.java
java src/Main int<first floor> int<last floor> int<number of elevators>
```

## Simulation

User can control action of elevators by terminal. There are four command, which you can write:
* step [*n*] - make n steps of simulations, each elevator will move by 0 or 1 floor in one step, 
(if n is not specified, simulation will make one step)
* pickup *num* *change* - call elevator to *num* floor, passenger goes to *num* + *change* floor
* click *num* *floor* - this command simulated clicking *floor* button in *num* elevator
* status - print actual status of elevator systems, for every elevator we get current floor and destination
* stop - terminate simulation

## Algorithm

### Structure

I create this structure of project to simulated real elevator system. In *Building* class are *Floors* and *ElevatorSystem*.
After pickup command system is asked to send elevator to right floor and passenger is added to list on this floor. 
On each floor are two lists, passengers which go up and passengers which go down.
When elevator reaches floor, it takes passenger and gets information about destination of passenger. 
We can simulate random clicking of passenger in elevator too.

### Logic

There are two problems to solve in this system. First, which elevator should be chosen. 
Second, in which order elevator should visit floor.

My system choose elevator in two steps. In first step, system is looking for the nearest elevator, which is stopped or go in the same direction and have this floor in its way.
If there isn't any elevator, which fulfills those requirements, program will calculate distance for every elevator and chose with the smallest value.
Distance is calculated by sum of distance from elevator to destination of this elevator and distance from floor of passenger to the destination.

Elevator use two data structure: tree set and queue. Each elevator is in one of four state stop, down, up or go.
Tree set have order of next stops. The aim of this set is stopping elevator if is passenger, which wait on elevator's way.
Other passengers go to queue. When tree set is empty, passenger is taken from queue. After elevator poped element from queue, 
it brought every passenger, which are in floor, which is on the way of elevator. The idea is to go maximum in one direction and 
take people as much as possible. After this change direction and do the same. If state is down, elevator go down, if up - go up, if stop - is stopped.
If elevator have go state, elevator go to same floor, in which elevator change direction. 
This state allow go to floor and don't take people which wants go in the same way. If we do this, 
there is possible have person, which want going up and person, which want going down.

## Ways to improve

* improve algorithm - add changing of elevator, which is chosen to go for passenger
* use some AI in algorithm
