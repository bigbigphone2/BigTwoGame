package BigTwoHand;

import PokerGame.*;
/**
 * it is a subclass of the PokerGame.Hand class,s and are used to model a hand of BigTwoHand.Single
 *
 * @author Lee Yat Hei
 */
public class Single extends Hand {
    /**
     * Constructor for building BigTwoHand.Single type hand.
     *
     * @param player a CardGmamePlayer object which specific the current active player
     * @param cards a PokerGame.CardList object which include the PokerGame.Card object which the current player going to place
     *
     */
    public Single(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }
    /**
     * A method for checking the given card is valid for building a BigTwoHand.Single hand
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
     * @return the type of this hand-BigTwoHand.Single
     */
    @Override
    public String getType() {
        return "BigTwoHand.Single";
    }
}
