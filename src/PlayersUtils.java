public class PlayersUtils {

    public static final int NOT_FOUND = -1;

    /**
     * Searches if the player exists
     * 
     * @param playerName: the name of the player
     * @return the first index of the array that equals to playerName
     */
    public static int findFirstIndexOfPlayer(Player[] players, String playerName) {
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
    
}
