import PokerGame.Card;

/**
 * it is a subclass of the PokerGame.Card class and is used to model a card used in a
 * Big Two card game.
 *
 * @author Lee Yat Hei
 */
public class BigTwoCard extends Card{
    /**
     * the rank Value of the BigTwoCard itself
     */
    int rankValue;
    /**
     * the rank Value of the PokerGame.Card given by the compare method
     */
    int cardRankValue;

    /**
     * a constructor for building a card with the specified suit and rank.
     * @param suit the suit value of the BigTwoCard itself
     * @param rank the rank value of the BigTwoCard itself
     */
    public BigTwoCard(int suit, int rank){
        super(suit,rank);
    }

    /**
     * a method for comparing the order of this card with the specified card
     *
     * @param card the card to be compared
     *
     * @return Returns a negative integer, zero, or a positive integer when this card is
     * less than, equal to, or greater than the specified card
     */
    @Override
    public int compareTo(Card card){
        rankValue=this.rank;
        cardRankValue= card.getRank();

        if (rankValue==0){
            rankValue=13;
        }else if(rankValue==1){
            rankValue=14;
        }
        if (cardRankValue==0){
            cardRankValue=13;
        }else if(cardRankValue==1){
            cardRankValue=14;
        }
        if (rankValue > cardRankValue) {
            return 1;
        } else if (rankValue < cardRankValue) {
            return -1;
        } else if (this.getSuit() > card.getSuit()) {
            return 1;
        } else if (this.getSuit() < card.getSuit()) {
            return -1;
        } else {
            return 0;
        }
    }
}
