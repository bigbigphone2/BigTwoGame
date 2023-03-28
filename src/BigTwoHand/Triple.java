package BigTwoHand;

import PokerGame.*;
/**
 * it is a subclass of the PokerGame.Hand class,s and are used to model a hand of BigTwoHand.Triple
 *
 * @author Lee Yat Hei
 */

public class Triple extends Hand {
    /**
     * Constructor for building BigTwoHand.Triple type hand.
     *
     * @param player a CardGmamePlayer object which specific the current active player
     * @param cards a PokerGame.CardList object which include the PokerGame.Card object which the current player going to place
     *
     */
    public Triple(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }
    /**
     * A method for checking the given card is valid for building a BigTwoHand.Triple hand
     * @return is it valid or not.
     */
    @Override
    public boolean isValid() {
        if (this.size()!=3){
            return false;
        }
        if (this.getCard(0).getRank() ==this.getCard(1).getRank() && this.getCard(1).getRank() == this.getCard(2).getRank()){
            return true;
        }
        return false;
    }
    /**
     * – a method for returning a string specifying the type of this hand.
     *
     * @return the type of this hand-BigTwoHand.Triple
     */
    @Override
    public String getType() {
        return "BigTwoHand.Triple";
    }
}
