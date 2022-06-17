/**
 * it is a subclass of the Hand class,s and are used to model a hand of FullHouse
 *
 * @author Lee Yat Hei
 */
public class FullHouse extends Hand{
    /**
     * Constructor for building FullHouse type hand.
     *
     * @param player a CardGmamePlayer object which specific the current active player
     * @param cards a CardList object which include the Card object which the current player going to place
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
        if (this.getCard(0).rank==this.getCard(1).rank && this.getCard(1).rank == this.getCard(2).rank){
            if (this.getCard(3).rank==this.getCard(4).rank){
                return true;
            }
            return false;
        }else if (this.getCard(2).rank==this.getCard(3).rank && this.getCard(3).rank == this.getCard(4).rank){
            if (this.getCard(0).rank==this.getCard(1).rank){
                return true;
            }
            return false;
        }
        return false;
    }
    /**
     * – a method for returning a string specifying the type of this hand.
     *
     * @return the type of this hand-FullHouse
     */
    @Override
    public String getType() {
        return "FullHouse";
    }
}
