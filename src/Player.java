public class Player {

    private String charOfPlayer;
    private int place, numPlaces;

    public Player(String charOfPlayer, int Place, int numPlaces) {
        this.charOfPlayer = charOfPlayer;
        this.place = Place;
        this.numPlaces = numPlaces;

    }

    // Pre : Check if the throw is valid and move places
    public boolean rollDice(int dice1, int dice2) {
        if (dice1 < 1 || dice1 > 6 || dice2 < 1 || dice2 > 6) {
            return false;
        }
        
        place = place + dice1 + dice2;

        return true;
    }

    // Pre : Check if game not over
    public boolean rollDiceWhileNotOver() {
        if(place >= numPlaces) {
            return false;
        } else {
            return true;
        }
    }
}
