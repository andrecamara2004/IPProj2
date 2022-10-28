public class GameSystem {

    private int numSquares;
    private Player[] players;
    private int nextPlayerPos;
    //private Player winner;

    public GameSystem(String colors, int numSquares) {
        this.numSquares = numSquares;
        this.players = createPlayersFromColors(colors);
        nextPlayerPos = 0;
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
        return players[nextPlayerPos].getName();
    }
}
