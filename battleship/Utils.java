package battleship;

import static battleship.Main.scanner;

public class Utils {
    public static int[] convertCoordinateToIndexes (String coordinate) throws IllegalArgumentException {
        try {
            int firstIndex = (int) coordinate.charAt(0) - 'A';
            int secondIndex = Integer.parseInt(coordinate.substring(1)) - 1;

            if (firstIndex < 0 || firstIndex > 9 || secondIndex < 0 || secondIndex > 9) {
                throw new IllegalArgumentException("Error! You entered the wrong coordinates! Try again:");
            }

            return new int[]{firstIndex, secondIndex};
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Error! You entered the wrong coordinates! Try again:");
        }
    }

    public static String convertIndexesToCoordinate(int x, int y) {
        return Character.toString('A' + x) + Integer.toString(y + 1);
    }

    public static void waitingForNextTurn() {
        do {
            System.out.println("Press Enter and pass the move to another player");
        } while (!"".equals(scanner.nextLine()));
    }
}
