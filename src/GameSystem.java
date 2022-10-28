public class GameSystem {

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

    private Player[] createPlayersFromColors(String colors) {
        Player[] players = new Player[1];
        players[0] = new Player("A");
        // int numPlayers = colors.length();
        // Player[] players = new Player[numPlayers];
        // for (int i = 0; i < numPlayers; i++) {
        //     String color = colors.charAt(i);
        //     players[i] = new Player(color);
        // }
        return players;
    }

    public String getNextPlayerName() {
        return getNextPlayer().getName();
    }

    private Player getNextPlayer() {
        return players[nextPlayerPos];
    }

    public int getPlayerSquare(String playerColor) {
        return players[0].getSquare();
    }

    public boolean isValidPlayer(String playerName) {
        return players[0].hasName(playerName);
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

    }

    // Pre: isGameOver()
    public String getWinnerName() {
        return winner.getName();
    }


}
