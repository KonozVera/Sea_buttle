import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Добро пожаловать в игру 'Морской бой!'. Введите, пожалуйста, ваше имя: ");
        String playerName = scanner.nextLine();

        System.out.println("Дорогой игрок, " + playerName + ", вы присоединились к одиночному режиму игры! Желаем успехов!");

        String[][] pole1 = new String[16][16];
        String[][] pole2 = new String[16][16];

        Ship[] shipMy = new Ship[6];
        Ship[] shipBot = new Ship[6];

        for (int i = 0; i < 6; i++) {
            shipMy[i] = new Ship(i + 1, 6 - i);
            shipBot[i] = new Ship(i + 1, 6 - i);
        }

        for (int i = 0; i < pole1.length; i++) {
            for (int j = 0; j < pole1.length; j++) {
                pole1[i][j] = ".";
                pole2[i][j] = ".";
            }
        }

        System.out.println("Выберите способ размещения кораблей(1 для автоматического заполнения и любой другой символ для второго):");
        System.out.println("1. Автоматическое заполнение");
        System.out.println("2. Ручное размещение (введите любой другой символ)");

        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            autoFillShips(pole1, shipMy);
        } else {
            manualFillShips(pole1, shipMy);
        }

        autoFillShips(pole2, shipBot);
        System.out.println("Ваше поле:                                " + "             \t Поле противника:");
        printFields(pole1, pole2);
        System.out.println("Начинаем атаку на противника!");

    }


    // Метод автоматического заполнения кораблей
    public static void autoFillShips(String[][] pole, Ship[] ships) {
        for (Ship ship : ships) {
            Ship.autoFillSingleShip(pole, ship);
        }
    }

    // Метод ручного размещения кораблей
    public static void manualFillShips(String[][] pole, Ship[] ships) {
        for (Ship ship : ships) {
            Ship.placeShips(pole, ship);
        }
    }

    public static void printFields(String[][] pole1, String[][] pole2) {
        System.out.print("   ");
        for (char c = 'A'; c <= 'P'; c++) {
            System.out.print(" " + c + " ");
        }

        System.out.print("      ");

        System.out.print("   ");
        for (char c = 'A'; c <= 'P'; c++) {
            System.out.print(" " + c + " ");
        }

        System.out.println();

        for (int i = 0; i < pole1.length; i++) {
            String rowNumber = (i + 1 < 10) ? " " + (i + 1) : String.valueOf(i + 1);
            System.out.print(rowNumber + " ");

            for (int j = 0; j < pole1[i].length; j++) {
                String content = pole1[i][j].equals("■") ? "■" : ".";
                System.out.print(" " + content + " ");
            }

            System.out.print("      ");
            System.out.print(rowNumber + " ");

            for (int j = 0; j < pole2[i].length; j++) {
                String content = pole2[i][j].equals("■") ? "■" : ".";
                System.out.print(" " + content + " ");
            }

            System.out.println();
        }
    }
}