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

        GameSystem game = new GameSystem(colors, numSquares);
        
        processCommands(in, game);

        in.close();
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
        int pointDice2 = in.nextInt();
        System.out.println("Tou no Dice TODO");
    }

    private static void processStatusCommand(Scanner in, GameSystem game) {
        String playerColor = in.next();
        System.out.printf("%s can roll the dice TODO \n", playerColor); 

    }

    private static void processSquareCommand(Scanner in, GameSystem game) {
        String playerColor = in.next();
        System.out.printf("%s is on square TODO\n", playerColor );
    }

    private static void processPlayerCommand(Scanner in, GameSystem game) {
        System.out.println("Estou no player");
 
    }

    private static void showInvalidCommand() {
        System.out.println("Invalid command");
    }
    

    
    private static void processExitCommand(GameSystem game) {

        System.out.println("to do exit");
    }


    
}
