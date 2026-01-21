
import java.util.Scanner;

public class Deck {
    private static final String[] SUITS = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    private Card[] Deck;
    private int topCardIndex = 0;

    //constructor
    public Deck(String flag) {
        switch (flag) {
            case "Standard":
                Deck = new Card[SUITS.length * RANKS.length];
                int index = 0;
                for (String suit : SUITS) {
                    for (String rank : RANKS) {
                        Deck[index++] = new Card(suit, rank);
                    }
                }
                break;
            default:
                System.out.println("Invalid deck type");
                break;
        }
    }

    public Deck(String flag, int Decks) {
        switch (flag) {
            case "Standard":
                Deck = new Card[SUITS.length * RANKS.length * Decks];
                int index = 0;
                for (int i = 0; i < Decks; i++) {
                    for (String suit : SUITS) {
                        for (String rank : RANKS) {
                            Deck[index++] = new Card(suit, rank);
                        }
                    }
                }
                break;
            default:
                System.out.println("Invalid deck type");
                break;
        }
    }

    public Deck(String flag, Card[] deck){
        switch(flag){
            case "Discard":
                int length = deck.length;
                this.Deck = new Card[length];
                break;
            default:
                System.out.println("Invalid deck type, please use 'Discard' as flag if passing a deck");
                break;
        }

    }


    //getters
    public Card[] getDeck() {
        return Deck;
    }

    //setters
    public void setDeck(Card[] Deck) {
        this.Deck = Deck;
    }

    //methods
    public void shuffle() {
        for (int i = 0; i < Deck.length; i++){
            int randomIndex = (int)(Math.random() * Deck.length);
            Card temp = Deck[i];
            Deck[i] = Deck[randomIndex];
            Deck[randomIndex] = temp;
        }
    }

    public Card drawCard(Boolean faceUp) {
        for (Card card : Deck) {
            if (!card.getIsDrawn()) {
                card.setIsDrawn(true);
                card.setIsFaceUp(faceUp);
                topCardIndex++;
                return card;
            }
        }
        return null; // all cards have been drawn
    }

    public int getTopCardIndex() {
        return topCardIndex;
    }

    public void cutDeck() {
        Scanner scanner = new Scanner(System.in);
        boolean invalidCut = false;
        int position = 0;
        while (!invalidCut) { 
            System.out.println("Enter cut position (0 to " + (Deck.length - 1) + "): ");
            position = scanner.nextInt();
            invalidCut = true;
            if (position < 0 || position >= Deck.length) {
                System.out.println("Invalid cut position");
                invalidCut = false;
            }
        }
        Card[] newDeck = new Card[Deck.length];
        int index = 0;
        for (int i = position; i < Deck.length; i++) {
            newDeck[index++] = Deck[i];
        }
        for (int i = 0; i < position; i++) {
            newDeck[index++] = Deck[i];
        }
        Deck = newDeck;
    }

    public void resetDeck() {
        for (Card card : Deck) {
            card.setIsDrawn(false);
        }
        topCardIndex = 0;
    }   

    public boolean isDeckEmpty() {
        for (Card card : Deck) {
            if (!card.getIsDrawn()) {
                return false;
            }
        }
        return true;
    }

    public void printDeck() {
        for (Card card : Deck) {
            System.out.println(card.getRank() + " of " + card.getSuit() + (card.getIsDrawn() ? " (Drawn)" : "" + (card.getIsFaceUp() ? " (Face Up)" : " (Face Down)")));
        }
    }

}
