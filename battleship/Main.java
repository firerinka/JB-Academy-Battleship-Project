package battleship;

import java.util.Scanner;

public class Main {
    static Game game;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        game = new Game();
        game.placingShips();
        game.shootingByTurns();
    }
}
