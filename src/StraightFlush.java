
/**
 * it is a subclass of the Hand class,s and are used to model a hand of StraightFlush
 *
 * @author Lee Yat Hei
 */
public class StraightFlush extends Hand {
    /**
     * Constructor for building StraightFlush type hand.
     *
     * @param player a CardGmamePlayer object which specific the current active player
     * @param cards a CardList object which include the Card object which the current player going to place
     *
     */
    public StraightFlush(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * A method for checking the given card is valid for building a StraightFlush hand
     * @return is it valid or not.
     */
    @Override
    public boolean isValid() {
        this.sort();
        int lastRank=-1;
        int currentRank=-1;
        int lastSuit;
        int currentSuit;
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
        lastSuit=this.getCard(0).suit;
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
     * @return the type of this hand-StraightFlush
     */
    @Override
    public String getType() {
        return "StraightFlush";
    }
}
