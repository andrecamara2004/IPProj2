public class Board {
    
    private static final int INITIAL_SQUARE = 1;
    private static final int BIRD_SQUARE_POSITIONS = 9;

    private Cliff[] cliffs;
    private Charge[] charges;
    private int numSquares;

    public Board(int numSquares, Charge[] charges, Cliff[] cliffs) {
        this.numSquares = numSquares;
        this.charges = charges;
        this.cliffs = cliffs;
    }

    public int getChargeValue(int square) {
        int idx = findChargeIndex(square);

        return charges[idx].getValue();
    }

    public int ensureInsideBoard(int square) {
        if (square < INITIAL_SQUARE) {
            square = INITIAL_SQUARE;
        } else if(square > numSquares) {
            square = numSquares;
        }

        return square;
    }

    public int getLastSquare() {
        return numSquares;
    }

    public boolean isLastSquare(int square) {
        return square == numSquares;
    }

    public boolean isBirdSquare(int newSquare) {
        return newSquare % BIRD_SQUARE_POSITIONS == 0 &&
                newSquare > INITIAL_SQUARE &&
                newSquare < numSquares;
    }

    public Cliff getCliff(int newSquare) {
        return cliffs[findCliffIndex(newSquare)];
    }

    public boolean isCliffSquare(int newSquare) {
        int i = findCliffIndex(newSquare);
        return i < cliffs.length;
    }

    public boolean isChargeSquare(int newSquare) {
        int i = findChargeIndex(newSquare);
        return i < charges.length;
    }

    private int findCliffIndex(int newSquare) {
        int i = 0;
        while (i < cliffs.length && !cliffs[i].hasSquare(newSquare)) {
            i++;
        }
        return i;
    }

    private int findChargeIndex(int newSquare) {
        int i = 0;
        while (i < charges.length && !charges[i].hasSquare(newSquare)) {
            i++;
        }
        return i;
    }

    public int getInitialSquare() {
        return INITIAL_SQUARE;
    }
}
