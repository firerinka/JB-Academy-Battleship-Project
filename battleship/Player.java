package battleship;

public class Player {
    Field field;
    Ship aircraftCarrier;
    Ship battleship;
    Ship submarine;
    Ship cruiser;
    Ship destroyer;

    public Player() {
        field = new Field();
        aircraftCarrier = new Ship("Aircraft Carrier", 5);
        battleship = new Ship("Battleship", 4);
        submarine = new Ship("Submarine", 3);
        cruiser = new Ship("Cruiser", 3);
        destroyer = new Ship("Destroyer", 2);
    }

    public boolean isAllShipsSank() {
        return !(aircraftCarrier.isAlive() || battleship.isAlive() || submarine.isAlive() || cruiser.isAlive() || destroyer.isAlive());
    }

    public Ship whatShipWasShot(String coordinate) {
        if (aircraftCarrier.isCoordinateInShip(coordinate)) {
            return aircraftCarrier;
        } else if (battleship.isCoordinateInShip(coordinate)) {
            return battleship;
        } else if (submarine.isCoordinateInShip(coordinate)) {
            return submarine;
        } else if (cruiser.isCoordinateInShip(coordinate)) {
            return cruiser;
        } else {
            return destroyer;
        }
    }
}
