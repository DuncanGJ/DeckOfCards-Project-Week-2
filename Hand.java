public class Hand {
    private Card[] cards;

    //constructor new empty hand
    public Hand(int size) {
        this.cards = new Card[size]; 

    }

    public Hand(Hand hand, int size) {
        this.cards = new Card[size];
        for (int i = 0; i < hand.getSize(); i++) {
            this.cards[i] = hand.getCard(i);
        }
    }


    //methods

    //adds a card to the hand, checks if last element of array is !null and resizes if necessary
    public void addCard(Card card) {
        if (cards[cards.length -1] != null) {
            reSize(cards.length + 1);
            cards[cards.length - 1] = card;
            return;
        }
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] == null) {
                cards[i] = card;
                break;
            }
        }
    }

    public Card getCard(int index) {
        if (index >= 0 && index < cards.length) {
            return cards[index];
        }
        return null;
    }

    public int getSize() {
        return cards.length;
    }

    public void reSize (int newSize) {
        Card[] newCards = new Card[newSize];
        for (int i = 0; i < Math.min(cards.length, newSize); i++) {
            newCards[i] = cards[i];
        }
        cards = newCards;
    }

    public void clear() {
        for (int i = 0; i < cards.length; i++) {
            cards[i] = null;
        }
    }

    public Card[] getCards() {
        return cards;
    }

    public void printHand(Boolean showAll) {
        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            if (card != null) {
                if (showAll == true) {
                    card.printCard();
                } else if (card.getIsFaceUp() == true) {
                    card.printCard();
                } else {
                    System.out.println("Card is face down.");
                }
            }
        }
    }

    public void printHand() {
        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            if (card != null && card.getIsFaceUp() == true) {
                card.printCard();
            }
            else if (card != null && card.getIsFaceUp() == false) {
                System.out.println("Card is face down.");
            }
        }
    }

    public boolean hasAce() {
        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            if (card != null && card.getRank().equals("Ace")) {
                return true;
            }
        }
        return false;
    }

    public int countAces() {
        int aceCount = 0;
        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            if (card != null && card.getRank().equals("Ace")) {
                aceCount++;
            }
        }
        return aceCount;
    }   

    public boolean hasFaceCard(int tenFlag) {
        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            if (card != null) {
                switch(card.getRank()) {
                    case "Jack":
                    case "Queen":
                    case "King":
                        return true;
                    case "10":
                        if (tenFlag == 1) {
                            return true;
                        }
                        break;
                    default:
                        return false;
                }
            }
        }
        throw new IllegalArgumentException("Method hasFaceCard should always return value, this should never happen");
    }


}