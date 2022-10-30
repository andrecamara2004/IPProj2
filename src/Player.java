public class Player {

    private String name;
    private int square;
    private int charges;

    // Pre: name != null
    public Player(String name) {
        this.name = name;
        this.square = 1;
        this.charges = 0;

    }

    public String getName() {
        return name;
    }

    // Pre: playerName != null
    public boolean hasName(String playerName) {
        return playerName.equals(name);
    }

    public int getSquare() {
        return square;
    }

    public void setSquare(int newSquare) {
        square = newSquare;
    }

    public void setCharges(int newCharges) {
        charges = newCharges;
    }

    public boolean hasCharges() {
        return charges != 0;
    }

    public void payCharge() {
        charges--;
    }


}
