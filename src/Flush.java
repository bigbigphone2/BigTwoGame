
/**
 * it is a subclass of the Hand class,s and are used to model a hand of Flush
 *
 * @author Lee Yat Hei
 */
public class Flush extends Hand{
    /**
     * Constructor for building Flush type hand.
     *
     * @param player a CardGmamePlayer object which specific the current active player
     * @param cards a CardList object which include the Card object which the current player going to place
     *
     */
    public Flush(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * A method for checking the given card is valid for building a flush hand
     * @return is it valid or not.
     */
    @Override
    public boolean isValid() {
        int lastSuit;
        int currentSuit;
        lastSuit=this.getCard(0).suit;
        if (this.size()!=5){
            return false;
        }
        for (int i=0;i<this.size();i++){
            currentSuit=this.getCard(i).suit;
            if(currentSuit!=lastSuit){
                return false;
            }
            lastSuit=this.getCard(i).suit;
        }
        return true;
    }

    /**
     * – a method for returning a string specifying the type of this hand.
     *
     * @return the type of this hand-flush
     */
    @Override
    public String getType() {
        return "Flush";
    }
}
