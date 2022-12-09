import java.io.FileNotFoundException;

public class CupSystem {

    private static final int MAX_POINTS_DICE = 6;
    private static final int MIN_POINTS_DICE = 1;

    private Player[] players;
    private Board board;
    private Game currentGame;
    private int gamesFinished;

    public CupSystem(String colors, int boardNumber, BoardsRepo boardsRepo) throws FileNotFoundException {
        this.players = createPlayersFromColors(colors);
        this.board = boardsRepo.getBoardByNumber(boardNumber);
        this.currentGame = new Game(players, board);
        this.gamesFinished = 0;
    }

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
            players[i] = new Player(playerName, i);
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

    public boolean isCupOver() {
        return gamesFinished == players.length - 1;
    }

    public String getNextPlayerName() {
        return currentGame.getNextPlayerName();
    }

    /**
     * Indicates if playerName is valid
     * 
     * @param playerName
     * @return true, if the player exists
     */
    public boolean isValidPlayer(String playerName) {
        int i = PlayersUtils.findFirstIndexOfPlayer(players, playerName);

        return i != PlayersUtils.NOT_FOUND;

    }

    public int getPlayerSquare(String playerColor) {
        return currentGame.getPlayerSquare(playerColor);
    }

    public boolean isEliminatedPlayer(String playerColor) {
        int idx = PlayersUtils.findFirstIndexOfPlayer(players, playerColor);
        return players[idx].isEliminated();
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

    public void rollDice(int pointsDice1, int pointsDice2) {
        currentGame.rollDice(pointsDice1, pointsDice2);
        if (currentGame.isGameOver()) {
            gamesFinished++;
            if (isCupOver()) {
                currentGame.getWinner().markAsCupWinner();
            } else {
                currentGame = new Game(getNextGamePlayers(), board);
            }
        }
    }

    private Player[] getNextGamePlayers() {
        int numActivePlayers = players.length - gamesFinished;
        Player[] activePlayers = new Player[numActivePlayers];
        int i = 0;
        int j = 0;
        do {
            if (!players[i].isEliminated()) {
                activePlayers[j] = players[i];
                activePlayers[j].resetForNewGame();
                j++;
            }

            i++;
        } while (i < players.length && j < activePlayers.length);

        return activePlayers;
    }

    public boolean canRollDice(String playerColor) {
        return currentGame.canRollDice(playerColor);
    }

    /**
     * 
     * @return
     * @pre: isCupOver()
     */
    public String getWinnerName() {
        return currentGame.getWinnerName();
    }

    public PlayerIterator rankedIterator() {
        Player[] aux = new Player[players.length]; 
        for (int i = 0; i < players.length; i++) { 
            aux[i] = players[i];
        }
        
        sort(aux, players.length); 
        return new PlayerIterator(aux, players.length);
    }

    private void sort(Player[] v, int size) {
        for (int i = 0; i < size - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < size; j++) {
                if (v[j].compareTo(v[minIdx]) < 0)
                    minIdx = j;
            }
            
            Player tmp = v[i];
            v[i] = v[minIdx];
            v[minIdx] = tmp;
        }
    }
}