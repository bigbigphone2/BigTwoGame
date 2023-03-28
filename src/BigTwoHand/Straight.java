package BigTwoHand;

import PokerGame.*;
/**
 * it is a subclass of the PokerGame.Hand class,s and are used to model a hand of BigTwoHand.Straight
 *
 * @author Lee Yat Hei
 */
public class Straight extends Hand {
    /**
     * Constructor for building BigTwoHand.Straight type hand.
     *
     * @param player a CardGmamePlayer object which specific the current active player
     * @param cards a PokerGame.CardList object which include the PokerGame.Card object which the current player going to place
     *
     */
    public Straight(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }
    /**
     * A method for checking the given card is valid for building a BigTwoHand.Straight hand
     * @return is it valid or not.
     */
    @Override
    public boolean isValid() {
        this.sort();
        int lastRank=-1;
        int currentRank=-1;
        lastRank=this.getCard(0).getRank();
        if (this.size()!=5){
            return false;
        }
        for (int i=1; i<5;i++){
            currentRank=this.getCard(i).getRank();
            if (currentRank==0){
                currentRank=13;
            }
            if (currentRank==1){
                currentRank=14;
            }
            if (currentRank != lastRank+1) {
                return false;
            }
            lastRank=currentRank;
        }
        return true;
    }
    /**
     * – a method for returning a string specifying the type of this hand.
     *
     * @return the type of this hand-BigTwoHand.Straight
     */
    @Override
    public String getType() {
        return "BigTwoHand.Straight";
    }
}
