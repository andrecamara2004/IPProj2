public class GameSystem {

    /**
     *
     */
    private static final int NOT_FOUND = -1;
    private static final int MAX_POINTS_DICE = 6;
    private static final int MIN_POINTS_DICE = 1;

    private int numSquares;
    private Player[] players;
    private int nextPlayerPos;
    private boolean gameOver;
    private Player winner;

    public GameSystem(String colors, int numSquares) {
        this.numSquares = numSquares;
        this.players = createPlayersFromColors(colors);
        this.nextPlayerPos = 0;
        this.gameOver = false;
        this.winner = null;
    }

    private static Player[] createPlayersFromColors(String colors) {
        int numPlayers = colors.length();
        Player[] players = new Player[numPlayers];
        for( int i = 0 ; i < numPlayers ; i++) {
            String playerName = convertCharAtPosToString(colors, i);
            players[i] = new Player(playerName);
        }

        return players;
    }

    private static String convertCharAtPosToString(String colors, int i) {
        char[] nameChars = new char[1];
        nameChars[0] = colors.charAt(i);
        String playerName = new String(nameChars);
        return playerName;
    }

    public String getNextPlayerName() {
        return getNextPlayer().getName();
    }

    private Player getNextPlayer() {
        return players[nextPlayerPos];
    }

    // Pre: isValidPlayer(playerName)
    public int getPlayerSquare(String playerName) {
        int i = findFirstIndexOfPlayer(playerName);
        
        return players[i].getSquare();
    }

    private int findFirstIndexOfPlayer(String playerName) {
        int i = 0;
        while(i < players.length && !players[i].hasName(playerName)) {
            i++;
        }
        
        if(i < players.length) {
            return i;
        } else {
            return NOT_FOUND;
        }
    }
    

    public boolean isValidPlayer(String playerName) {
        int i = findFirstIndexOfPlayer(playerName);
        
        return i != NOT_FOUND;
        
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean canRollDice(String playerColor) {
        return true;
    }

    public static boolean isDiceValid(int pointsDice1, int pointsDice2) {
        return isDicePointsValid(pointsDice1) && isDicePointsValid(pointsDice2);
    }

    private static boolean isDicePointsValid(int pointsDice1) {
        return pointsDice1 >= MIN_POINTS_DICE && pointsDice1 <= MAX_POINTS_DICE;
    }

    // Pre: isDiceValid(pointsDice1, pointsDice2)
    public void rollDice(int pointsDice1, int pointsDice2) {
        int totalDicePoints = pointsDice1 + pointsDice2;
        Player nextPlayer = getNextPlayer();
        int newSquare = nextPlayer.getSquare() + totalDicePoints;

        // Check if game ended after the play
        if(newSquare >= numSquares) {
            newSquare = numSquares;
            gameOver = true;
            winner = nextPlayer;
        }
        
        nextPlayer.setSquare(newSquare);
        updateNextPlayer();


    }

    private void updateNextPlayer() {
        if(nextPlayerPos == players.length - 1)  {
            nextPlayerPos = 0;
        } else {
            nextPlayerPos++;
        }

    }

    // Pre: isGameOver()
    public String getWinnerName() {
        return winner.getName();
    }


}
