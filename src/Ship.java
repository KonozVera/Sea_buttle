import java.util.Scanner;
import java.util.Random;

public class Ship {
    private static final Random random = new Random();
    private final int size;
    private final int quantity;

    public Ship(int size, int quantity) {
        this.size = size;
        this.quantity = quantity;
    }

    public static void placeShips(String[][] pole, Ship ship) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < ship.quantity; i++) {
            while (true) {
                System.out.println("Введите координаты для размещения " + ship.size + "-палубного корабля (например, A1,B1,C1):");
                String input = scanner.nextLine().toUpperCase();
                String[] coordinates = input.split(",");
                if (coordinates.length != ship.size) {
                    System.out.println("Неверный формат ввода. Повторите попытку.");
                    continue;
                }

                int[] positions = new int[coordinates.length * 2];
                boolean validInput = true;

                for (int j = 0; j < coordinates.length; j++) {
                    String coordinate = coordinates[j].trim();
                    if (coordinate.length() < 2 || !Character.isLetter(coordinate.charAt(0)) || !Character.isDigit(coordinate.charAt(1))) {
                        System.out.println("Неверный формат координаты: " + coordinate + ". Повторите попытку.");
                        validInput = false;
                        break;
                    }

                    int column = coordinate.charAt(0) - 'A'; // Преобразуем буквенную координату в индекс столбца
                    int row = Integer.parseInt(coordinate.substring(1)) - 1; // Преобразуем числовую координату в индекс строки
                    positions[j * 2] = row;
                    positions[j * 2 + 1] = column;
                }

                if (!validInput) {
                    continue;
                }

                if (canPlaceShip(pole, ship.size, positions)) {
                    if (areCoordinatesSequential(positions) || (positions[0] == 0 && positions[positions.length - 2] == pole.length - 1)) {
                        placeShip(pole, ship.size, positions);
                        printField(pole);
                        break;
                    } else {
                        System.out.println("Координаты корабля должны идти последовательно. Попробуйте еще раз.");
                    }
                } else {
                    System.out.println("Невозможно разместить корабль по указанным координатам. Попробуйте еще раз.");
                }
            }
        }
    }

    // Метод для проверки, что координаты располагаются последовательно
    private static boolean areCoordinatesSequential(int[] positions) {
        for (int i = 0; i < positions.length - 2; i += 2) {
            if (Math.abs(positions[i] - positions[i + 2]) + Math.abs(positions[i + 1] - positions[i + 3]) != 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean canPlaceShip(String[][] pole, int size, int[] positions) {
        for (int i = 0; i < positions.length / 2; i++) {
            int x = positions[i * 2];
            int y = positions[i * 2 + 1];
            if (x < 0 || x >= pole.length || y < 0 || y >= pole[0].length || !pole[x][y].equals(".")) {
                return false;
            }
        }

        // Проверка на пересечение с другими кораблями
        for (int i = 0; i < positions.length / 2; i++) {
            int x = positions[i * 2];
            int y = positions[i * 2 + 1];
            if (hasAdjacentShip(pole, x, y)) {
                return false;
            }
        }

        return true;
    }

    private static boolean hasAdjacentShip(String[][] pole, int x, int y) {
        // Проверяем наличие корабля в соседних клетках
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < pole.length && j >= 0 && j < pole[0].length && !pole[i][j].equals(".")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void placeShip(String[][] pole, int size, int[] positions) {
        for (int i = 0; i < positions.length / 2; i++) {
            int x = positions[i * 2];
            int y = positions[i * 2 + 1];
            pole[x][y] = "■";
        }
    }

    public static void printField(String[][] pole) {
        System.out.print("   ");
        for (char c = 'A'; c <= 'P'; c++) {
            System.out.print(" " + c + " ");
        }
        System.out.println();

        for (int i = 0; i < pole.length; i++) {
            String rowNumber = (i + 1 < 10) ? " " + (i + 1) : String.valueOf(i + 1);
            System.out.print(rowNumber + " ");
            for (int j = 0; j < pole[i].length; j++) {
                String content = pole[i][j].equals("■") ? "■" : ".";
                System.out.print(" " + content + " ");
            }
            System.out.println();
        }
    }

    public static void autoFillSingleShip(String[][] pole, Ship ship) {

        for (int i = 0; i < ship.quantity; i++) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(pole.length);
                int col = random.nextInt(pole[0].length);
                boolean horizontal = random.nextBoolean();

                if (canPlaceShip(pole, ship.size, row, col, horizontal)) {
                    placeRandomShip(pole, ship.size, row, col, horizontal);
                    placed = true;
                }
            }
        }
    }

    public static boolean canPlaceShip(String[][] pole, int size, int row, int col, boolean horizontal) {
        // Проверка горизонтального размещения
        if (horizontal && col + size <= pole[0].length) {
            for (int c = col; c < col + size; c++) {
                if (!pole[row][c].equals(".") || hasAdjacentShip(pole, row, c)) { //**
                    return false;
                }
            }
            return true;
        }
        // Проверка вертикального размещения
        else if (!horizontal && row + size <= pole.length) {
            for (int r = row; r < row + size; r++) {
                if (!pole[r][col].equals(".") || hasAdjacentShip(pole, r, col)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static void placeRandomShip(String[][] pole, int size, int row, int col, boolean horizontal) {
        if (horizontal) {
            for (int c = col; c < col + size; c++) {
                pole[row][c] = "■";
            }
        } else {
            for (int r = row; r < row + size; r++) {
                pole[r][col] = "■";
            }
        }
    }
}