import java.io.Serializable;
import java.util.ArrayList;
/**
 * The BigTwo class s is a subclass of the CardList class and is used to model a hand of cards
 *
 * @author Lee Yat Hei
 *
 */
public abstract class Hand extends CardList implements Serializable {
    /**
     * the player who plays this hand
     */
    private CardGamePlayer player;

    /**
     * – a constructor for building a hand with the specified player and list of cards
     * @param player a CardGmamePlayer object which specific the current active player
     * @param cards a CardList object which include the Card object which the current player going to place
     *
     */
    public Hand(CardGamePlayer player, CardList cards){
        this.player=player;
        for (int i=0;i<cards.size();i++){
            super.addCard(cards.getCard(i));
        }

    }

    /**
     *  a method for retrieving the player of this hand.
     *
     * @return  the player who plays this hand
     */
    public CardGamePlayer getPlayer(){
        return this.player;
    }

    /**
     * – a method for retrieving the top card of this hand.
     * @return – the top card of this hand.
     */
    public Card getTopCard(){
        this.sort();
        Card TopCard = this.getCard(this.size()-1);
        return TopCard;
    }

    /**
     *  a method for checking if this hand beats a specified hand.
     * @param hand the Hand object going to check beat it or not
     * @return ture for beat it. false for can't beat it
     */
    public boolean beats(Hand hand){
        String typeOfHand = new String();
        ArrayList<String> FiveCardGroups = new ArrayList<>();
        FiveCardGroups.add("StraightFlush");
        FiveCardGroups.add("Quad");
        FiveCardGroups.add("FullHouse");
        FiveCardGroups.add("Flush");
        FiveCardGroups.add("Straight");
        typeOfHand= this.getType();
        if (hand== null){
            return true;
        }
        if (FiveCardGroups.contains(typeOfHand)){
            if (FiveCardGroups.indexOf(this.getType())< FiveCardGroups.indexOf(hand.getType())){
                return true;
            }else if (FiveCardGroups.indexOf(this.getType())== FiveCardGroups.indexOf(hand.getType())){
                if (this.getTopCard().compareTo(hand.getTopCard())==1){
                    return true;
                }else {
                    return false;
                }
            }else{
                return false;
            }
        }else{
            if (this.getTopCard().compareTo(hand.getTopCard())==1){
                return true;
            }else{
                return false;
            }
        }
    }

    /**
     *  a method for checking if this is a valid hand
     * @return unused
     */
    public abstract boolean isValid();

    /**
     * a method for returning a string specifying the type of this hand
     * @return unused
     */
    public abstract String getType();

}
