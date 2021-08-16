package battleship;

import static battleship.Main.scanner;

public class Game {
    Player firstPlayer;
    Player secondPlayer;

    public Game() {
        firstPlayer = new Player();
        secondPlayer = new Player();
    }

    public void placingShips() {
        System.out.println("Player 1, place your ships on the game field");
        creatingShipsForPlayer(firstPlayer);
        Utils.waitingForNextTurn();
        System.out.println("Player 2, place your ships on the game field");
        creatingShipsForPlayer(secondPlayer);
        Utils.waitingForNextTurn();
    }

    public void shootingByTurns() {
        boolean firstPlayerTurn = true;
        do {
            if (firstPlayerTurn) {
                shootingTurn(firstPlayer);
            } else {
                shootingTurn(secondPlayer);
            }
            if (!firstPlayer.isAllShipsSank() && !secondPlayer.isAllShipsSank()) {
                firstPlayerTurn = !firstPlayerTurn;
                Utils.waitingForNextTurn();
            }
        } while (!firstPlayer.isAllShipsSank() && !secondPlayer.isAllShipsSank());
    }

    private void creatingShip(Player player, Ship ship) {
        boolean isShipSaved = false;
        System.out.printf("Enter the coordinates of the %s (%d cells):%n",
                ship.getName(), ship.getLength());
        do {
            try {
                String[] coords = scanner.nextLine().split("\\s+");
                if (coords.length < 2) {
                    throw new IllegalArgumentException("Error! You entered the wrong coordinates! Try again:");
                }

                int[] a = Utils.convertCoordinateToIndexes(coords[0]);
                int[] b = Utils.convertCoordinateToIndexes(coords[1]);

                int length = Math.abs(a[0] - b[0]) != 0 ? Math.abs(a[0] - b[0]) + 1 : Math.abs(a[1] - b[1]) + 1;
                if (length != ship.getLength()) {
                    throw new IllegalArgumentException(String.format("Error! Wrong length of the %s. Try again:", ship.getName()));
                } else if (player.field.isFieldsRangeAvailable(a, b)) {
                    player.field.saveShipPlacement(a, b, ship);
                    player.field.printField();
                    isShipSaved = true;
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!isShipSaved);
    }

    private void creatingShipsForPlayer(Player player) {
        player.field.printField();
        creatingShip(player, player.aircraftCarrier);
        creatingShip(player, player.battleship);
        creatingShip(player, player.submarine);
        creatingShip(player, player.cruiser);
        creatingShip(player, player.destroyer);
    }

    private void shootingTurn(Player player) {
        Player foe = player == firstPlayer ? secondPlayer :  firstPlayer;
        String name = player == firstPlayer ? "Player 1" : "Player 2";

        foe.field.printFieldWithFogOfWar();
        System.out.println("---------------------");
        player.field.printField();

        System.out.println(name + ", it's your turn:");

        try {
            String shotCoords = scanner.nextLine();
            int[] shotIndexes = Utils.convertCoordinateToIndexes(shotCoords);
            String message;
            if (foe.field.shootInCoordinates(shotIndexes[0], shotIndexes[1])) {
                Ship ship = foe.whatShipWasShot(shotCoords);
                ship.shipCellIsShot();
                if (ship.isAlive()) {
                    message = "You hit a ship!";
                } else {
                    if (!foe.isAllShipsSank()) {
                        message = "You sank a ship!";
                    } else {
                        message = "You sank the last ship. You won. Congratulations!";
                    }
                }
            } else {
                message = "You missed!";
            }
            System.out.println(message);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
