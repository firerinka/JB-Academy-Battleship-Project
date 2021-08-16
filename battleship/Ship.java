package battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ship {
    private String name;
    private int length;
    private boolean isAlive;
    private List<String> coordinates;
    private int aliveSellsCount;

    public Ship(String name, int length) {
        this.name = name;
        this.length = length;
        this.isAlive = true;
        coordinates = new ArrayList<>();
        aliveSellsCount = length;
    }

    public String getName() {
        return this.name;
    }

    public int getLength() {
        return this.length;
    }

    public boolean isAlive() { return this.isAlive; }

    public void addCoordinate(String coordinate) {
        this.coordinates.add(coordinate);
    }

    public void shipCellIsShot() {
        aliveSellsCount--;
        if (aliveSellsCount == 0) {
            isAlive = false;
        }
    }

    public boolean isCoordinateInShip(String coord) {
        return this.coordinates.contains(coord);
    }
}
