public class Board {
    
    private static final int INITIAL_SQUARE = 1;
    private static final int BIRD_SQUARE_POSITIONS = 9;
    private static final int CHARGES_VALUE = 2;

    private int[] cliffsSquares;
    private int[] chargesSquares;
    private int numSquares;

    public Board(int numSquares, int[] chargesSquares, int[] cliffsSquares) {
        this.numSquares = numSquares;
        this.chargesSquares = chargesSquares;
        this.cliffsSquares = cliffsSquares;
    }

    public int getChargeValue(int square) {
        return CHARGES_VALUE;
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

    public boolean isCliffSquare(int newSquare) {
        return squareExistsInArray(newSquare, cliffsSquares);
    }

    public boolean isChargeSquare(int newSquare) {
        return squareExistsInArray(newSquare, chargesSquares);
    }
    
    private boolean squareExistsInArray(int newSquare, int[] array) {
        int i = 0;
        while (i < array.length && array[i] != newSquare) {
            i++;
        }
        return i < array.length;
    }

}
