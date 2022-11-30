
/**
 * @author André Câmara
 *         This class is responsible for giving the object Player a name,
 *         the square that the player is and the amount of charges that he has
 */

public class Player {

    // instance  variables
    private String name;
    private int square;
    private int charges;
    private boolean eliminated;
    private boolean firstMove;

    // constructors

    /**
     * Constructor:
     * 
     * @param name: name of the player
     * @pre: name != null
     */
    public Player(String name) {
        this.name = name;
        this.square = 1;
        this.charges = 0;
        this.eliminated = false;
        this.firstMove = true;

    }

    // methods

    public boolean isFirstMove() {
        return firstMove;
    }

    public void markAsEliminate() {
        eliminated = true;
    }

    public boolean isEliminated() {
        return eliminated;
    }

    /**
     * @return The name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Indicates if Player has a name
     * @return true, if Player has a name
     * @pre: playerName != null
     */
    public boolean hasName(String playerName) {
        return playerName.equals(name);
    }

    /**
     * @return The square of the player
     */
    public int getSquare() {
        return square;
    }

    /**
     * Sets the square of the player to the new square
     * @param newSquare: The new square of the player
     * @pre: newSquare >= 1 
     */
    public void setSquare(int newSquare) {
        square = newSquare;
        firstMove = false;
    }

    /**
     * Adds the charges that needs to be added to the player
     * @param newCharges: The new amount of charges of the player
     * @pre: newCharges >= 0
     */
    public void setCharges(int newCharges) {
        charges = newCharges;
    }

    /**
     * Indicates if Player has charges
     * @return true, if Player has charges
     */
    public boolean hasCharges() {
        return charges != 0;
    }

    /**
     * Pays one charge 
     */
    public void payCharge() {
        charges--;
    }

}
