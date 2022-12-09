/**
 * @author André Câmara
 *         This class is responsible for managing the whole game
 * 
 */

public class Game {

    // constants
    private static final int BIRD_SQUARE_POSITIONS = 9;
    
    // instance variables
    private Player[] players;
    private int nextPlayerPos;
    private boolean gameOver;
    private Player winner;
    private Board board;
    private boolean skipDeathCliff;

    // constructors

    /**
     * Constructor:
     * 
     * @param colors:         the String given that will lead to the name of the
     *                        players
     * @param numSquares:     the ammount of squares that the game has
     * @param chargesSquares: the squares that charges on it
     * @param cliffsSquares:  the squares that has cliffs on it
     * @pre: numSquares >= 10 && numSquares <= 150 && colors != null
     */
    public Game(Player[] players, Board board) {
        this.players = players;
        this.nextPlayerPos = 0;
        this.gameOver = false;
        this.winner = null;
        this.board = board;
        this.skipDeathCliff = false;
    }

    // methods

    /**
     * Gives the name of the player that will be playing next play
     * 
     * @return the name of the player
     */
    public String getNextPlayerName() {
        return getNextPlayer().getName();
    }

    /**
     * Chooses the player that will be playing next play
     * 
     * @return the player
     */
    private Player getNextPlayer() {
        return players[nextPlayerPos];
    }

    /**
     * Gives the square of the player
     * 
     * @param playerName: the name of the player
     * @return the square of the player
     * @pre: isValidPlayer(playerName)
     */
    public int getPlayerSquare(String playerName) {
        int i = PlayersUtils.findFirstIndexOfPlayer(players, playerName);

        return players[i].getSquare();
    }


    /**
     * Indicates if the game is over
     * 
     * @return true, if game is over
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Indicates if player can roll the dice
     * 
     * @param playerColor
     * @return true, if player can roll the dice
     */
    public boolean canRollDice(String playerColor) {
        int idx = PlayersUtils.findFirstIndexOfPlayer(players, playerColor);
        return !players[idx].hasCharges();
    }

    /**
     * player plays depending on the manager of the game
     * 
     * @param pointsDice1
     * @param pointsDice2
     * @pre: isDiceValid(pointsDice1, pointsDice2)
     */
    public void rollDice(int pointsDice1, int pointsDice2) {
        int totalDicePoints = pointsDice1 + pointsDice2;
        Player nextPlayer = getNextPlayer();
        int newSquare = nextPlayer.getSquare() + totalDicePoints;

        if (totalDicePoints == 9 && pointsDice1 % 3 == 0 && nextPlayer.isFirstMove() == true) {
            newSquare = board.getLastSquare();
        } else {
            newSquare = checkNewSquare(totalDicePoints, nextPlayer, newSquare);
        }

        // Check if game ended after the play
        nextPlayer.setSquare(newSquare);
        if (board.isLastSquare(newSquare)) {
            setGameOver(nextPlayer);
            findAndMarkEliminatedPlayer();            
        } else {
            updateNextPlayer();
            if(nextPlayer.isEliminated() && players.length == 2) {
                setGameOver(getNextPlayer());
            }
        }

    }

    public Player getWinner() {
        return winner;
    }

    private void setGameOver(Player playerWinner) {
        gameOver = true;
        winner = playerWinner;
        winner.addWin();

    }

    private void findAndMarkEliminatedPlayer() {
        Player eliminated = players[0];
        for(int i = 1; i < players.length; i++) {
            if(players[i].getSquare() <= eliminated.getSquare() && players[i].getOrder() > eliminated.getOrder()) {
                eliminated = players[i];
            }
        }

        eliminated.markAsEliminated();
    }

    /**
     * Verify new square, checking for charges, cliffs and birds
     * 
     * @param totalDicePoints
     * @param nextPlayer
     * @param newSquare
     * @return the adjusted square
     */
    private int checkNewSquare(int totalDicePoints, Player nextPlayer, int newSquare) {
        // Check if player went into a bird square
        if (board.isBirdSquare(newSquare)) {
            newSquare = newSquare + BIRD_SQUARE_POSITIONS;
            // Check if player went into a cliff square
        } else if (board.isCliffSquare(newSquare)) {
            Cliff cliff = board.getCliff(newSquare);
            if(cliff.isCrab()) {
                newSquare = nextPlayer.getSquare() - totalDicePoints;
            } else if(!skipDeathCliff && cliff.isDeath() ) {
                nextPlayer.markAsEliminated();
                skipDeathCliff = true;
            } else if(cliff.isHell()) {
                newSquare = board.getInitialSquare();
            } else if(cliff.getSquare() == -1){
                newSquare = newSquare + 0;
            }

            // Check if player went into a charge square
        } else if (board.isChargeSquare(newSquare)) {
            nextPlayer.setCharges(board.getChargeValue(newSquare));
        }

        return board.ensureInsideBoard(newSquare);
    }

    // Skip player that has charges to pay
    private void updateNextPlayer() {
        updateNextPlayerPos();

        while (players[nextPlayerPos].hasCharges() || players[nextPlayerPos].isEliminated()) {
            if(players[nextPlayerPos].hasCharges()){
                players[nextPlayerPos].payCharge();
            }
            updateNextPlayerPos();
        }
    }

    // Tells who is the next player playing
    private void updateNextPlayerPos() {
        if (nextPlayerPos == players.length - 1) {
            nextPlayerPos = 0;
        } else {
            nextPlayerPos++;
        }
    }

    /**
     * Gives the name of the player that won the game
     * 
     * @return the name of the player that won the game
     * @pre: isGameOver()
     */
    public String getWinnerName() {
        return winner.getName();
    }
}
