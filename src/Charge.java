public class Charge {
    
    private int square;
    private int value;


    public Charge(int square, int value) {
        this.square = square;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getSquare() {
        return square;
    }

    public boolean hasSquare(int otherSquare) {
        return square == otherSquare;
    } 

}
