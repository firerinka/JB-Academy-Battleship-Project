package battleship;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private static final char FOG_OF_VAR_CELL = '~';
    private static final char SHIP_CELL = 'O';
    private static final char SHIP_HIT_CELL = 'X';
    private static final char MISSED_CELL = 'M';
    private static final int FIELD_SIZE = 10;

    private final char[][] field = new char[FIELD_SIZE][FIELD_SIZE];


    public Field() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                field[i][j] = FOG_OF_VAR_CELL;
            }
        }
    }

    public void printField() {
        //printing fields legend
        System.out.print("  ");
        for(int i = 1; i <= FIELD_SIZE; i++) {
            System.out.print(i + " ");
        }

        //printing rows
        for (int i = 0; i < FIELD_SIZE; i++) {
            System.out.printf("\n%c ", 'A' + i);
            for (int j = 0; j < FIELD_SIZE; j++) {
                System.out.print(field[i][j] + " ");
            }
        }
        System.out.println();
        System.out.println();
    }

    public void printFieldWithFogOfWar() {
        //printing fields legend
        System.out.print("  ");
        for(int i = 1; i <= FIELD_SIZE; i++) {
            System.out.print(i + " ");
        }

        //printing rows
        for (int i = 0; i < FIELD_SIZE; i++) {
            System.out.printf("\n%c ", 'A' + i);
            for (int j = 0; j < FIELD_SIZE; j++) {
                // print only Xs, Ms and ~
                if (field[i][j] == SHIP_CELL) {
                    System.out.print(FOG_OF_VAR_CELL + " ");
                } else {
                    System.out.print(field[i][j] + " ");
                }
            }
        }
        System.out.println();
        System.out.println();
    }

    public void saveShipPlacement(int[] a, int[] b, Ship ship) {
        sortCoordinates(a, b);

        if (a[0] == b[0]) { // ship is horizontal, iterating by columns
            for (int i = a[1]; i <= b[1]; i++) {
                field[a[0]][i] = SHIP_CELL;
                ship.addCoordinate(Utils.convertIndexesToCoordinate(a[0], i));
            }
        } else if (a[1] == b[1]) { //ship is vertical, iterating by rows
            for (int i = a[0]; i<= b[0]; i++) {
                field[i][a[1]] = SHIP_CELL;
                ship.addCoordinate(Utils.convertIndexesToCoordinate(i, a[1]));
            }
        } else { //ship is diagonal
            int y = a[1];
            for (int i = a[0]; i <= b[0]; i++) {
                field[i][y] = SHIP_CELL;
                ship.addCoordinate(Utils.convertIndexesToCoordinate(i, y));
                y = a[1] < b[1] ? y + 1 : y - 1;//co-directional opposite direction with axis
            }
        }
    }

    public boolean isFieldsRangeAvailable(int[] a, int[] b) throws IllegalArgumentException {
        boolean result = true;
        sortCoordinates(a, b); //if row of a is less then row of b, values are changes there places

        //check if the coordinates are placed on same line
        try {
            if (a[0] != b[0] && a[1] != b[1] && Math.abs(a[0] - b[0]) != Math.abs(a[1] - b[1])) {
                throw new IllegalArgumentException("Error! Wrong ship location! Try again:");
            } else {
                if (a[0] == b[0]) { // ship is horizontal, iterating by colomns
                    for (int i = a[1]; i <= b[1]; i++) {
                        if (!isCoordinateAvailable(a[0], i)) {
                            result = false;
                            break;
                        }
                    }
                } else if (a[1] == b[1]) { //ship is vertical, iterating by rows
                    for (int i = a[0]; i<= b[0]; i++) {
                        if(!isCoordinateAvailable(i, a[1])) {
                            result = false;
                            break;
                        }
                    }
                } else { //ship is diagonal
                    int y = a[1];
                    for (int i = a[0]; i <= b[0]; i++) {
                        if(!isCoordinateAvailable(i, y)) {
                            result = false;
                            break;
                        }
                        y = a[1] < b[1] ? y + 1 : y - 1;//co-directional opposite direction with axis
                    }
                }

                if (!result) {
                    throw new IllegalArgumentException("Error! You placed it too close to another one. Try again:");
                }

                return true;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean shootInCoordinates(int x, int y) {
        boolean result;
        if (field[x][y] == SHIP_CELL || field[x][y] == SHIP_HIT_CELL) {
            field[x][y] = SHIP_HIT_CELL;
            result = true;
        } else {
            field[x][y] = MISSED_CELL;
            result = false;
        }
        return result;
    }

    private boolean isCoordinateAvailable(int x, int y) {
        boolean result = true;
        //creating edges for square around coordinate
        int minRow = x > 0 ? x - 1 : 0;
        int maxRow = x < FIELD_SIZE - 1 ? x + 1 : FIELD_SIZE - 1;
        int minCol = y > 0 ? y - 1 : 0;
        int maxCol = y < FIELD_SIZE - 1 ? y + 1 : FIELD_SIZE - 1;

        //checking if there are already a ship cell in the square
        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                if (field[i][j] == SHIP_CELL) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    private void sortCoordinates(int[] a, int[] b) {
        // swap coordinates in case of:
        // * it's a horizontal ship and first coordinate has bigger colomn
        // * it's a vertical or diagonal ship and first coordinate has lesser row
        if (a[0] > b[0] || a[0] == b[0] && a[1] > b[1]) {
            int c = b[0];
            b[0] = a[0];
            a[0] = c;
            c = b[1];
            b[1] = a[1];
            a[1] = c;
        }
    }
}
