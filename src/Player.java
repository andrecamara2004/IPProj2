public class Player {

    private String name;
    private int square;

    // Pre: name != null
    public Player(String name) {
        this.name = name;
        this.square = 1;
        
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
}
