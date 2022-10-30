import java.util.Scanner;

public class Main {

    private static final String DICE = "dice";
    private static final String STATUS = "status";
    private static final String SQUARE = "square";
    private static final String PLAYER = "player";
    private static final String EXIT = "exit";

    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);

        String colors = in.nextLine();

        int numSquares = in.nextInt();
        in.nextLine();

        int numCharges = in.nextInt();
        int[] chargesSquares = createArrayOfSquares(numCharges, in);

        int numCliffs = in.nextInt();
        int[] cliffsSquares = createArrayOfSquares(numCliffs, in);

        GameSystem game = new GameSystem(colors, numSquares, chargesSquares, cliffsSquares);

        processCommands(in, game);

        in.close();
    }

    private static int[] createArrayOfSquares(int numItens, Scanner in) {
        int[] createArrayOfSquares = new int[numItens];
        for (int i = 0; i < numItens; i++) {
            createArrayOfSquares[i] = in.nextInt();
        }
        in.nextLine();
        return createArrayOfSquares;
    }

    private static void processCommands(Scanner in, GameSystem game) {
        String command = in.next();
        while (!EXIT.equals(command)) {
            processCommand(command, in, game);
            command = in.next();
        }

        processExitCommand(game);

    }

    private static void processCommand(String command, Scanner in, GameSystem game) {
        switch (command) {
            case PLAYER:
                processPlayerCommand(in, game);
                break;
            case SQUARE:
                processSquareCommand(in, game);
                break;
            case STATUS:
                processStatusCommand(in, game);
                break;
            case DICE:
                processDiceCommmand(in, game);
                break;
            default:
                showInvalidCommand();
                break;

        }

        in.nextLine();
    }

    private static void processDiceCommmand(Scanner in, GameSystem game) {
        int pointsDice1 = in.nextInt();
        int pointsDice2 = in.nextInt();
        if (!GameSystem.isDiceValid(pointsDice1, pointsDice2)) {
            System.out.println("Invalid dice");
            return;
        }

        if (game.isGameOver()) {
            displayGameIsOver();
            return;
        }

        game.rollDice(pointsDice1, pointsDice2);
    }

    private static void displayGameIsOver() {
        System.out.println("The game is over");
    }

    private static void processStatusCommand(Scanner in, GameSystem game) {
        String playerColor = in.next();
        if (!game.isValidPlayer(playerColor)) {
            displayNonexistentPlayer();
            return;
        }

        if (game.isGameOver()) {
            displayGameIsOver();
            return;
        }

        if (game.canRollDice(playerColor)) {
            System.out.printf("%s can roll the dice\n", playerColor);
        } else {
            System.out.printf("%s cannot roll the dice\n", playerColor);
        }

    }

    private static void processSquareCommand(Scanner in, GameSystem game) {
        String playerColor = in.next();
        if (!game.isValidPlayer(playerColor)) {
            displayNonexistentPlayer();
            return;
        }

        int playerSquare = game.getPlayerSquare(playerColor);
        System.out.printf("%s is on square %d\n", playerColor, playerSquare);
    }

    private static void displayNonexistentPlayer() {
        System.out.println("Nonexistent player");
    }

    private static void processPlayerCommand(Scanner in, GameSystem game) {

        System.out.printf("Next to play: %s\n", game.getNextPlayerName());

    }

    private static void showInvalidCommand() {
        System.out.println("Invalid command");
    }

    private static void processExitCommand(GameSystem game) {
        if (game.isGameOver()) {
            System.out.printf("%s won the game!\n", game.getWinnerName());
        } else {
            System.out.println("The game was not over yet...");
        }
    }
}
