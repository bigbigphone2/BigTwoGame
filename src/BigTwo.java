import java.util.ArrayList;
/**
 * The BigTwo class is used to model a Big Two card game.
 *
 * @author Lee Yat Hei
 *
 */
public class BigTwo {
    /**
     *  an int specifying the number of players.
     */
    private int numOfPlayers;
    /*
     *   a deck of cards
     */
    private Deck deck;
    /**
     * a list of players
     */
    private ArrayList<CardGamePlayer> playerList = new ArrayList<>();
    /**
     * a list of hands played on the table
     */
    private ArrayList<Hand> handsOnTable = new ArrayList<>();
    /**
     * an integer specifying the index of the current player
     */
    private int currentPlayerIdx;
    /**
     * a BigTwoUI object for providing the user interface.
     */
    private BigTwoGUI ui;
    /**
     * an integer specifying how many player have passed this turn
     */
    private int numOfTimesPass=0;
    /**
     * a Big Two card game client
     */
    private BigTwoClient client;
    /**
     * a method for starting a Big Two card game
     *
     * @param args unused
     */
    public static void main(String[] args){
        BigTwo game= new BigTwo();
        //BigTwoDeck deck= new BigTwoDeck();
        //deck.shuffle();
        //game.start(deck);
    }
    /**
     * a constructor for creating a Big Two card game.
     * it create 4 players and add them to the player list; and create a BigTwoUI object for providing the user interface.
     */

    public BigTwo(){
        this.numOfPlayers=4;
        
        for (int j=0;j<this.numOfPlayers;j++){
            CardGamePlayer player = new CardGamePlayer();
            playerList.add(player);
        }
        this.ui = new BigTwoGUI(this);
        ui.disable();


    }

    /**
     *a method for getting the number of players.
     *
     * @return an int specifying the number of players.
     */
    public int getNumOfPlayers(){
        return this.numOfPlayers;
    }

    /**
     * a method for retrieving the deck of cards being used
     *
     * @return a deck of cards
     */
    public Deck getDeck(){return deck;}

    /**
     * a method for retrieving the list of players
     *
     * @return a list of players.
     */
    public ArrayList<CardGamePlayer> getPlayerList(){
        return playerList;
    }

    /**
     * a method for retrieving the list of hands played on the table
     *
     * @return  a list of hands played on the table
     */
    public ArrayList<Hand> getHandsOnTable(){
        return handsOnTable;
    }

    /**
     *a method for retrieving the index of the current player
     *
     * @return  an integer specifying the index of the current player
     */
    public int getCurrentPlayerIdx(){
        return currentPlayerIdx;
    }

    /**
     *a method for starting/restarting the game with a given shuffled deck of cards
     *
     * @param deck a shuffled deck of cards provided static main method
     */
    public void start(Deck deck){
    	handsOnTable= new ArrayList<>();
        for (int i=0;i<4;i++){
            playerList.get(i).removeAllCards();
            for (int j=0;j<13;j++) {
                playerList.get(i).addCard(deck.getCard(j+i*13));
                if (deck.getCard(j+i*13).getRank()==2 && deck.getCard(j+i*13).getSuit() == 0){
                    currentPlayerIdx=i;
                    //ui.setActivePlayer(i);
                }
            }
            playerList.get(i).getCardsInHand().sort();

        }
        //ui.setActivePlayer(client.getPlayerID());
        //ui.promptActivePlayer();


    }

    /**
     *a method for making a move by a player with the specified index using the cards specified by the list of indices
     *
     * @param playerIdx the index of the active player who are going to make the move
     * @param cardIdx the index of the card which the player are going to place
     */
    public void makeMove(int playerIdx, int[] cardIdx){
    	checkMove(playerIdx,cardIdx);


    }

    /**
     *  a method for checking a move made by a player is valid or not
     * @param playerIdx the index of the active player who are going to make the move
     * @param cardIdx the index of the card which the player are going to place
     */
    public void checkMove(int playerIdx, int[] cardIdx){
        Card firstCard = new Card(0,2);
        CardGamePlayer currentPlayer = playerList.get(currentPlayerIdx);
        boolean ValidMove=false;

        Hand lastHandOnTable = (handsOnTable.isEmpty()) ? null : handsOnTable.get(handsOnTable.size() - 1);
        if (cardIdx!=null){
            Hand CurrentHand= composeHand(currentPlayer,currentPlayer.play(cardIdx));
            if (CurrentHand==null){
                ValidMove=false;
            }
            else if (currentPlayer.getCardsInHand().contains(firstCard) && !CurrentHand.contains(firstCard)){
                ValidMove=false;
            }

            else if (lastHandOnTable==null){
                ValidMove=true;
            }
            else if (numOfTimesPass!=3 && CurrentHand.size()!=lastHandOnTable.size()){
                ValidMove=false;
            }
            else if (numOfTimesPass!=3 && !CurrentHand.beats(lastHandOnTable)){
                ValidMove=false;
            }
            else{
                ValidMove=true;
            }
            if (ValidMove){
                numOfTimesPass=0;
                handsOnTable.add(CurrentHand);
                currentPlayer.removeCards(CurrentHand);
                ui.msgArea.append("{"+CurrentHand.getType()+"}"+CurrentHand+"\n");
                currentPlayerIdx = (currentPlayerIdx + 1) % 4;
                //ui.setActivePlayer(currentPlayerIdx);
                ui.promptActivePlayer();

            }
            else{
                ui.msgArea.append("Not a legal move!!!" + "\n");
                ui.promptActivePlayer();
            }
        }
        else{
            if (currentPlayer.getCardsInHand().contains(firstCard)){
                ui.msgArea.append("Not a legal move!!!" + "\n");
                ui.promptActivePlayer();
            }
            else{

                if (numOfTimesPass==3){
                    ui.msgArea.append("Not a legal move!!!" + "\n");
                    ui.promptActivePlayer();
                }
                else{
                    ui.msgArea.append("{Pass}" + "\n");
                    numOfTimesPass++;
                    currentPlayerIdx = (currentPlayerIdx + 1) % 4;
                    //ui.setActivePlayer(currentPlayerIdx);
                    ui.promptActivePlayer();
                }

            }
        }

        if (endOfGame()){
            ui.setActivePlayer(-1);
            ui.repaint();
            ui.BoardcastEndOfGame();
            ui.disable();
        }

    }

    /**
     * a method for checking if the game ends.
     *
     * @return a boolean value specific the game is ended or not.
     */
    public boolean endOfGame(){
        int lastPlayerIdx;
        if (currentPlayerIdx-1==-1){
            lastPlayerIdx=3;
        }else{
            lastPlayerIdx=currentPlayerIdx-1;
        }

        if (playerList.get(lastPlayerIdx).getNumOfCards()==0){
            return true;
        }
        return false;
    }



    /**
     *a method for returning a valid hand from the specified list of cards of the player
     * @param player a CardGmamePlayer object which specific the current active player
     * @param cards a CardList object which include the Card object which the current player going to place
     *
     * @return a Hand object which include the type of hand and the card included.
     */
    public static Hand composeHand(CardGamePlayer player, CardList cards){

        Single single = new Single(player,cards);
        Pair pair = new Pair(player,cards);
        Triple triple = new Triple(player,cards);
        Straight straight = new Straight(player,cards);
        Flush flush = new Flush(player,cards);
        FullHouse fullhouse = new FullHouse(player,cards);
        Quad quad = new Quad(player,cards);
        StraightFlush straightflush = new StraightFlush(player,cards);
        if(straightflush.isValid())
        {
            return straightflush;
        }

        if(quad.isValid())
        {
            return quad;
        }

        if(fullhouse.isValid())
        {
            return fullhouse;
        }

        if(flush.isValid())
        {
            return flush;
        }

        if(straight.isValid())
        {
            return straight;
        }

        if(triple.isValid())
        {
            return triple;
        }

        if(pair.isValid())
        {
            return pair;
        }

        if(single.isValid())
        {
            return single;
        }
        return null;
    }

}
