
/**
 * @author André Câmara
 * This is the main class responsible to interact with the user while using
 * the GameSystem class
 */

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    // constants
    private static final String RANKING = "ranking";
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
    public static void main(String[] args) throws FileNotFoundException {

        Scanner in = new Scanner(System.in);

        String colors = in.nextLine();

        int boardNumber = in.nextInt();
        in.nextLine();

        BoardsRepo boardRepo = new BoardsRepo(BOARDS_REPO_FILE_NAME);

        CupSystem cup = new CupSystem(colors, boardNumber, boardRepo);

        processCommands(in, cup);

        in.close();
    }

    // methods

    /**
     * Creation of the processor of commands
     * 
     * @param in:   the input
     * @param game: the object GameSystem
     */
    private static void processCommands(Scanner in, CupSystem cup) {
        String command;
        do {
            command = in.next();
            processCommand(command, in, cup);
        } while (!EXIT.equals(command));

    }

    /**
     * Chooses which command to process
     * 
     * @param command
     * @param in
     * @param cup
     */
    private static void processCommand(String command, Scanner in, CupSystem cup) {
        switch (command) {
            case PLAYER:
                processPlayerCommand(in, cup);
                break;
            case SQUARE:
                processSquareCommand(in, cup);
                break;
            case STATUS:
                processStatusCommand(in, cup);
                break;
            case DICE:
                processDiceCommmand(in, cup);
                break;
            case RANKING:
                processRankingCommand(cup);
                break;
            case EXIT:
                processExitCommand(cup);
                break;
            default:
                showInvalidCommand(in);
                break;

        }
    }

    private static void processRankingCommand(CupSystem cup) {
        PlayerIterator iterator = cup.rankedIterator();
        while (iterator.hasNext()) {
            Player player = iterator.getNext();
            if (!player.isEliminated()) {
                System.out.printf("%s: %d games won; on square %d.\n",
                        player.getName(), player.getWins(), player.getSquare());
            } else {
                System.out.printf("%s: %d games won; eliminated.\n",
                        player.getName(), player.getWins());

            }
        }
    }

    /**
     * processes the dice command, so the players can move
     * 
     * @param in
     * @param cup
     */
    private static void processDiceCommmand(Scanner in, CupSystem cup) {
        int pointsDice1 = in.nextInt();
        int pointsDice2 = in.nextInt();
        in.nextLine();

        if (!CupSystem.isDiceValid(pointsDice1, pointsDice2)) {
            System.out.println("Invalid dice");
        } else if (cup.isCupOver()) {
            displayCupIsOver();
        } else {
            cup.rollDice(pointsDice1, pointsDice2);
        }

    }

    /**
     * Displays that the cup is over
     */
    private static void displayCupIsOver() {
        System.out.println("The cup is over");
    }

    /**
     * processes status command, so we can know if the player can roll the dice
     * 
     * @param in
     * @param cup
     */
    private static void processStatusCommand(Scanner in, CupSystem cup) {
        String playerColor = readPlayerColorToTheEndOfLine(in);
        if (!cup.isValidPlayer(playerColor)) {
            displayNonexistentPlayer();
        } else if (cup.isCupOver()) {
            displayCupIsOver();
        } else if (cup.isEliminatedPlayer(playerColor)) {
            displayEliminatedPlayer();
        } else if (cup.canRollDice(playerColor)) {
            System.out.printf("%s can roll the dice\n", playerColor);
        } else {
            System.out.printf("%s cannot roll the dice\n", playerColor);
        }
    }

    /**
     * processes the square command, so we can know on which square the player is
     * 
     * @param in
     * @param cup
     */
    private static void processSquareCommand(Scanner in, CupSystem cup) {
        String playerColor = readPlayerColorToTheEndOfLine(in);
        if (!cup.isValidPlayer(playerColor)) {
            displayNonexistentPlayer();
        } else if (cup.isEliminatedPlayer(playerColor)) {
            displayEliminatedPlayer();
        } else {
            int playerSquare = cup.getPlayerSquare(playerColor);
            System.out.printf("%s is on square %d\n", playerColor, playerSquare);
        }
    }

    private static void displayEliminatedPlayer() {
        System.out.println("Eliminated player");
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
     * @param cup
     */
    private static void processPlayerCommand(Scanner in, CupSystem cup) {

        in.nextLine();
        if (cup.isCupOver()) {
            displayCupIsOver();
        } else {
            System.out.printf("Next to play: %s\n", cup.getNextPlayerName());
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
    private static void processExitCommand(CupSystem cup) {
        if (cup.isCupOver()) {
            System.out.printf("%s won the cup!\n", cup.getWinnerName());
        } else {
            System.out.println("The cup was not over yet...");
        }
    }
}
