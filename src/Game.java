/**
 * @author André Câmara
 *         This class is responsible for managing the whole game
 * 
 */

public class Game {

    // constants
    private static final int BIRD_SQUARE_POSITIONS = 9;
    private static final int NOT_FOUND = -1;
    private static final int MAX_POINTS_DICE = 6;
    private static final int MIN_POINTS_DICE = 1;
    

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
    public Game(String colors, Board board) {
        this.players = createPlayersFromColors(colors);
        this.nextPlayerPos = 0;
        this.gameOver = false;
        this.winner = null;
        this.board = board;
        this.skipDeathCliff = false;
    }

    // methods

    /**
     * Creates the players using the String colors
     * 
     * @param colors: the String given that will lead to the name of the players
     * @return an array of Players
     */
    private static Player[] createPlayersFromColors(String colors) {
        int numPlayers = colors.length();
        Player[] players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            String playerName = convertCharAtPosToString(colors, i);
            players[i] = new Player(playerName);
        }

        return players;
    }

    /**
     * Creating an array of char with lenght of 1 index that will lead to a String
     * that will be the name of the player
     * 
     * @param colors: the String given that will lead to the name of the players
     * @return the name of the player
     */
    private static String convertCharAtPosToString(String colors, int i) {
        char[] nameChars = new char[1];
        nameChars[0] = colors.charAt(i);
        String playerName = new String(nameChars);
        return playerName;
    }

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
        int i = findFirstIndexOfPlayer(playerName);

        return players[i].getSquare();
    }

    /**
     * Searches if the player exists
     * 
     * @param playerName: the name of the player
     * @return the first index of the array that equals to playerName
     */
    private int findFirstIndexOfPlayer(String playerName) {
        int i = 0;
        while (i < players.length && !players[i].hasName(playerName)) {
            i++;
        }

        if (i < players.length) {
            return i;
        } else {
            return NOT_FOUND;
        }
    }

    /**
     * Indicates if playerName is valid
     * 
     * @param playerName
     * @return true, if the player exists
     */
    public boolean isValidPlayer(String playerName) {
        int i = findFirstIndexOfPlayer(playerName);

        return i != NOT_FOUND;

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
        return !players[findFirstIndexOfPlayer(playerColor)].hasCharges();
    }

    /**
     * Indicates if the dice is valid
     * 
     * @param pointsDice1: the number of dice1 spots
     * @param pointsDice2: the number of dice2 spots
     * @return true if the number of spots of both dices is valid
     */
    public static boolean isDiceValid(int pointsDice1, int pointsDice2) {
        return isDicePointsValid(pointsDice1) && isDicePointsValid(pointsDice2);
    }

    /**
     * Indicates if the number of spots of the dice is valid
     * 
     * @param pointsDice1: the number of dice spots
     * @return true if the number of spots of the dice is valid
     */
    private static boolean isDicePointsValid(int pointsDice1) {
        return pointsDice1 >= MIN_POINTS_DICE && pointsDice1 <= MAX_POINTS_DICE;
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
        } else {
            updateNextPlayer();
        }

    }

    private void setGameOver(Player playerWinner) {
        gameOver = true;
        winner = playerWinner;

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
                nextPlayer.markAsEliminate();
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

        //TODO melhorar este codigo
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
