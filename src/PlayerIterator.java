public class PlayerIterator {
    private Player[] players; // players vector
        private int numberOfPlayers; // number of players in the vector
        private int nextIndex; // index of the next player to return

        // Constructors
        // @Pre: contacts != null && size >= 0 && size <= contacts.length
        public PlayerIterator(Player[] players, int size) {
            this.players = players;
            this.numberOfPlayers = size;
            nextIndex = 0;
        }

        // Methods
        public boolean hasNext() {
            return nextIndex < numberOfPlayers;
        }

        // @pre: hasNext()
        public Player getNext() {
            return players[nextIndex++];
        }
}
