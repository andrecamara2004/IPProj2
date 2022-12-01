import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class BoardsRepo {

    private String fileName;

    public BoardsRepo(String fileName) {
        this.fileName = fileName;
    }

    public Board getBoardByNumber(int boardNumber) throws FileNotFoundException {
        FileReader reader = new FileReader(fileName);
        Scanner in = new Scanner(reader);
        
        advanceToBoard(boardNumber, in);

        Board board = createBoard(in);

        in.close();
        return board;
    }

    private Board createBoard(Scanner in) {
        int numSquares = in.nextInt();
        in.nextLine();

        int numCharges = in.nextInt();
        Charge[] charges = createArrayOfCharges(numCharges, in);

        int numCliffs = in.nextInt();
        Cliff[] cliffs = createArrayOfCliffs(numCliffs, in);

        Board board = new Board(numSquares, charges, cliffs);
        return board;
    }

    private static Charge[] createArrayOfCharges(int numCharges, Scanner in) {
        Charge[] charges = new Charge[numCharges];
        for (int i = 0; i < numCharges; i++) {
            charges[i] = new Charge(in.nextInt(), in.nextInt());
            in.nextLine();

        }

        return charges;
    }

    private static Cliff[] createArrayOfCliffs(int numCliffs, Scanner in) {
        Cliff[] cliffs = new Cliff[numCliffs];
        for (int i = 0; i < numCliffs; i++) {
            cliffs[i] = new Cliff(in.nextInt(), in.next());
            in.nextLine();

        }

        return cliffs;
    }

    private void advanceToBoard(int boardNumber, Scanner in) {
        for (int i = 1; i < boardNumber; i++) {
            skipBoard(in);
        }
    }

    private void skipBoard(Scanner in) {
        in.nextLine();

        int numCharges = in.nextInt();
        in.nextLine();
        skipLines(numCharges, in);

        int numCliffs = in.nextInt();
        in.nextLine();
        skipLines(numCliffs, in);

    }

    private void skipLines(int count, Scanner in) {
        for (int i = 0; i < count; i++) {
            in.nextLine();
        }
    }

}
