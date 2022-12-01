public class Cliff {

    private static final String CRAB = "crab";
    private static final String HELL = "hell";
    private static final String DEATH = "death";
    
    private int square;
    private String type;

    public Cliff(int square, String type) {
        this.square = square;
        this.type = type;
    }

    public int getSquare() {
        return square;
    }

    public boolean isCrab() {
        return type.equals(CRAB);
    }

    public boolean isHell() {
        return type.equals(HELL);
    }

    public boolean isDeath() {
        return type.equals(DEATH);
    }

    public boolean hasSquare(int otherSquare) {
        return square == otherSquare;
    }

    public void eraseCliff() {
        square = -1;
    }
}
