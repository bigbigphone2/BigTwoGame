package BigTwoHand;

import PokerGame.*;

import java.util.HashMap;
import java.util.Map;

/**
 * it is a subclass of the PokerGame.Hand class,s and are used to model a hand of BigTwoHand.FullHouse
 *
 * @author Lee Yat Hei
 */
public class FullHouse extends Hand {
    /**
     * Constructor for building BigTwoHand.FullHouse type hand.
     *
     * @param player a CardGmamePlayer object which specific the current active player
     * @param cards a PokerGame.CardList object which include the PokerGame.Card object which the current player going to place
     *
     */
    public FullHouse(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }
    /**
     * A method for checking the given card is valid for building a fullHouse hand
     * @return is it valid or not.
     */
    @Override
    public boolean isValid() {
        if (this.size()!=5){
            return false;
        }
        this.sort();
        Map<Integer, Integer> count = new HashMap<>(); //rank, count
        for (int i=0; i<5; i++){
            int rank = this.getCard(i).getRank();
            count.put(rank, count.getOrDefault(rank, 0)+1);
        }
        for (Map.Entry<Integer, Integer> entry: count.entrySet()){
            if (entry.getValue() != 2 || entry.getValue() !=3 )
                return false;
        }
        return true;
    }
    /**
     * – a method for returning a string specifying the type of this hand.
     *
     * @return the type of this hand-BigTwoHand.FullHouse
     */
    @Override
    public String getType() {
        return "BigTwoHand.FullHouse";
    }
}
