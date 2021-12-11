
/**
 * it is a subclass of the Hand class,s and are used to model a hand of Single
 *
 * @author Lee Yat Hei
 */
public class Single extends Hand {
    /**
     * Constructor for building Single type hand.
     *
     * @param player a CardGmamePlayer object which specific the current active player
     * @param cards a CardList object which include the Card object which the current player going to place
     *
     */
    public Single(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }
    /**
     * A method for checking the given card is valid for building a Single hand
     * @return is it valid or not.
     */
    @Override
    public boolean isValid() {
        if (this.size()!=1){
            return false;
        }
        return true;
    }
    /**
     * – a method for returning a string specifying the type of this hand.
     *
     * @return the type of this hand-Single
     */
    @Override
    public String getType() {
        return "Single";
    }
}
