

/**
 * it is a subclass of the Hand class,s and are used to model a hand of Quad
 *
 * @author Lee Yat Hei
 */
public class Quad extends Hand{
    /**
     * Constructor for building Quad type hand.
     *
     * @param player a CardGmamePlayer object which specific the current active player
     * @param cards a CardList object which include the Card object which the current player going to place
     *
     */
    public Quad(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }
    /**
     * A method for checking the given card is valid for building a Quad hand
     * @return is it valid or not.
     */
    @Override
    public boolean isValid() {
        int currentRank=0;
        int repeatTimes=1;
        if (this.size()!=5){
            return false;
        }
        for (int i=0; i<5;i++){
            if (this.getCard(i).getRank() == currentRank){
                repeatTimes++;
            }
            currentRank=this.getCard(i).getRank();
        }
        if (repeatTimes==4){
            return true;
        }
        return false;
    }
    /**
     * – a method for returning a string specifying the type of this hand.
     *
     * @return the type of this hand-Quad
     */
    @Override
    public String getType() {
        return "Quad";
    }
}
