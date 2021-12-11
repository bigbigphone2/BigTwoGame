
/**
 * it is a subclass of the Hand class,s and are used to model a hand of Pair
 *
 * @author Lee Yat Hei
 */
public class Pair extends Hand {
    /**
     * Constructor for building Pair type hand.
     *
     * @param player a CardGmamePlayer object which specific the current active player
     * @param cards a CardList object which include the Card object which the current player going to place
     *
     */
    public Pair(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }
    /**
     * A method for checking the given card is valid for building a Pair hand
     * @return is it valid or not.
     */
    @Override
    public boolean isValid() {
        if (this.size()!=2){
            return false;
        }
        if (this.getCard(0).rank==this.getCard(1).rank){
            return true;
        }
        return false;
    }
    /**
     * – a method for returning a string specifying the type of this hand.
     *
     * @return the type of this hand-Pair
     */
    @Override
    public String getType() {
        return "Pair";
    }
}
