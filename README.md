# Project Title

Mancala

## Description

Provides the user with a fully equiped package that allows two players to play Mancala, using either Kalah or Ayo rules. This package is complete with a visual user interface for input and output. This project contains all of the generally accepted rules of mancala, including bonus turns and captures for Kalah, and a composite of generally accepted Ayo rules.

Pit class:
 - Contains the number of stones in the pit
 - Method to add a stone to the pit
 - Method to get the number of stones in the pit
 - Method to remove all stones from the pit
 - toString method to print the current state of the pit

Store class:
 - Contains the owner of the class
 - Contains the number of stones in the class
 - Method to set the owner
 - Method to get the owner
 - Method to add stones to the store
 - Method to get the number of stones in the store
 - Method to empty the stones from the store
 - toString method to print the current state of the store

Player class:
 - Contains the name of the player
 - Contains the store associated with the player
 - Method to set the name of the player
 - Method to get the name of the player
 - Method to set the store associated to the player
 - Method to get the store associated to the player
 - Method to get the number of stones in the player's store
 - toString method to print the current state of the store

GameRules Abstract Class:
 - Contains the data structure for the game
 - Contains the current player's number
 - Method to get the number of stones in a pit
 - Method to get the data structure
 - Method to check if a side of the board is empty
 - Method to set the current player
 - Method to register two players for the game
 - Method to reset the board
 - Method to check if a move is valid
 - Method to get number of stones in a store
 - Method to remove all stones from a pit
 - Method to add stones to a store
 - Method to get a player's number
 - Method to get the current pit
 - Method to capture stones
 - Method to move all stones from pits to stores
 - toString method to print current state of the game

KalahRules class:
 - Implements the GameRules class
 - Contains the state of bonus turn
 - Method to move stones
 - Method to distribute stones to pits and stores
 - Method to capture stones
 - Method to check if bonus turn
 - Method to set bonus turn

AyoRules class:
 - Implements the GameRules class
 - Method to move stones
 - Method to distribute stones
 - Method to capture stones 

MancalaDataStructure class:
 - Contains all pits and stores
 - Method to add stones to a pit
 - Method to removes stones from a pit
 - Method to add stones to a store
 - Method to get the number of stones in a store
 - Method to get the number of stones in a pit
 - Method to empty stores
 - Method to set up the pits
 - Method to link a store to the board
 - Method to iterate through the board to properly distribute stones
 - Method to get the current iterator position
 - Method to get the next iterator position

MancalaGame class:
 - Contains ArrayList of players
 - Contains the current player
 - Contains the board
 - Method to get the board
 - Method to get the current player
 - Method to get the number of stones in a pit
 - Method to get the Arraylist of players
 - Method to get the number of stones in a player's store
 - Method to get the winner of the game
 - Method to check if the game is over
 - Method to make a move
 - Method to set the board
 - Method to set the current player
 - Method to set the ArrayList of players
 - Method to start a new game
 - toString method to print the current state of the instance

 MancalaUI class:
 - Provides the user with an easy to use interface
 - Performs all input and output for the game
 - Interacts with the MancalaGame class to run the game

## Getting Started

### Dependencies

* Java
* Gradle

### Executing program

* Compile the program using gradle
```
gradle build
```
* Run the program from jar using
```
java -jar build/libs/Mancala.jar
```
* Follow the instructions on the GUI to play the game
## Limitations

The project has been fully completed.

## Author Information

Name: Nathan Probert
<br>
Student ID: 1236772
<br>
Email: nprobert@uoguelph.ca

## Development History

* 1.1 (2023-11-19)
    * Completed all functionality
* 1.0 (2023-11-15)
    * Complete main menu of GUI
* 0.9 (2023-11-14)
    * Completed saving functionality
* 0.8 (2023-11-13)
    * Completed Kalah game rules
    * Completed Ayo game rules
* 0.7 (2023-10-24)
    * Completed test cases for Board class
    * Completed the README
* 0.4 (2023-10-21)
    * Created a revised version of all other classes
* 0.3 (2023-10-20)
    * Created a revised Pit class
* 0.2 (2023-10-15)
    * Created a working version of the game
* 0.1 (2023-10-13)
    * Created file structure

## Acknowledgments

[Java documentation](https://docs.oracle.com/en/java/javase/17/docs/api/index.html)<br>
[Java Swing documentation](https://docs.oracle.com/javase%2F7%2Fdocs%2Fapi%2F%2F/javax/swing/package-summary.html)<br>
Mancala documentation<br>
