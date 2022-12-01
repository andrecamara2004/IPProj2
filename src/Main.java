
/**
 * @author André Câmara
 * This is the main class responsible to interact with the user while using
 * the GameSystem class
 */

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    // constants
    private static final String DICE = "dice";
    private static final String STATUS = "status";
    private static final String SQUARE = "square";
    private static final String PLAYER = "player";
    private static final String EXIT = "exit";
    private static final String BOARDS_REPO_FILE_NAME = "boards.txt";

    /**
     * Main program:
     * - Reads the string that will give the names of the players
     * - Reads the amount of squares that the game will have
     * - Reads the amount of cliffs and charges and their places
     * - Creates a new Object of the class GameSystem
     * - Calls the command processor
     * 
     * @param args
     */
    public static void main(String[] args) throws FileNotFoundException{

        Scanner in = new Scanner(System.in);

        String colors = in.nextLine();

        int boardNumber = in.nextInt();
        in.nextLine();

        BoardsRepo boardRepo = new BoardsRepo(BOARDS_REPO_FILE_NAME);
        Board board = boardRepo.getBoardByNumber(boardNumber);

        Game game = new Game(colors, board);

        processCommands(in, game);

        in.close();
    }

    // methods


    /**
     * Creation of the processor of commands
     * 
     * @param in:   the input
     * @param game: the object GameSystem
     */
    private static void processCommands(Scanner in, Game game) {
        String command;
        do {
            command = in.next();
            processCommand(command, in, game);
        } while (!EXIT.equals(command));

    }

    /**
     * Chooses which command to process
     * 
     * @param command
     * @param in
     * @param game
     */
    private static void processCommand(String command, Scanner in, Game game) {
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
            case EXIT:
                processExitCommand(game);
                break;
            default:
                showInvalidCommand(in);
                break;

        }
    }

    /**
     * processes the dice command, so the players can move
     * 
     * @param in
     * @param game
     */
    private static void processDiceCommmand(Scanner in, Game game) {
        int pointsDice1 = in.nextInt();
        int pointsDice2 = in.nextInt();
        in.nextLine();

        if (!Game.isDiceValid(pointsDice1, pointsDice2)) {
            System.out.println("Invalid dice");
        } else if (game.isGameOver()) {
            displayGameIsOver();
        } else {
            game.rollDice(pointsDice1, pointsDice2);
        }

    }

    /**
     * Displays that the game is over
     */
    private static void displayGameIsOver() {
        System.out.println("The game is over");
    }

    /**
     * processes status command, so we can know if the player can roll the dice
     * 
     * @param in
     * @param game
     */
    private static void processStatusCommand(Scanner in, Game game) {
        String playerColor = readPlayerColorToTheEndOfLine(in);
        if (!game.isValidPlayer(playerColor)) {
            displayNonexistentPlayer();
        } else if (game.isGameOver()) {
            displayGameIsOver();
        } else if (game.canRollDice(playerColor)) {
            System.out.printf("%s can roll the dice\n", playerColor);
        } else {
            System.out.printf("%s cannot roll the dice\n", playerColor);
        }
    }

    /**
     * processes the square command, so we can know on which square the player is
     * 
     * @param in
     * @param game
     */
    private static void processSquareCommand(Scanner in, Game game) {
        String playerColor = readPlayerColorToTheEndOfLine(in);
        if (!game.isValidPlayer(playerColor)) {
            displayNonexistentPlayer();
        } else {
            int playerSquare = game.getPlayerSquare(playerColor);
            System.out.printf("%s is on square %d\n", playerColor, playerSquare);
        }
    }

    /**
     * reads everything that is after the command, everything is the name of the
     * player
     * 
     * @param in
     * @return
     */
    private static String readPlayerColorToTheEndOfLine(Scanner in) {

        return in.next() + in.nextLine();
    }

    /**
     * displays nonexistent player
     */
    private static void displayNonexistentPlayer() {
        System.out.println("Nonexistent player");
    }

    /**
     * processes the player command, so we can know the player playing next play
     * 
     * @param in
     * @param game
     */
    private static void processPlayerCommand(Scanner in, Game game) {

        in.nextLine();
        if (game.isGameOver()) {
            displayGameIsOver();
        } else {
            System.out.printf("Next to play: %s\n", game.getNextPlayerName());
        }
    }

    private static void showInvalidCommand(Scanner in) {
        in.nextLine();
        System.out.println("Invalid command");
    }

    /**
     * Command exit, if the game is over, show in console the winner and
     * if the game is not over show on console that there werent any winner
     * 
     * @param game: the object game system
     */
    private static void processExitCommand(Game game) {
        if (game.isGameOver()) {
            System.out.printf("%s won the game!\n", game.getWinnerName());
        } else {
            System.out.println("The game was not over yet...");
        }
    }
}
