public class Card {
    private String suit;
    private String rank;
    private Boolean isDrawn;
    private Boolean isFaceUp;

    //constructor
    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
        this.isDrawn = false;
        this.isFaceUp = false;
    }

    //getters
    public String getSuit() {
        return suit;       
    }
    public String getRank() {
        return rank;       
    }
    public Boolean getIsDrawn() {
        return isDrawn;
    }
    public Boolean getIsFaceUp() {
        return isFaceUp;
    }
    //setters
    public void setSuit(String suit) {
        this.suit = suit;   
    }
    public void setRank(String rank) {
        this.rank = rank;   
    }   
    public void setIsDrawn(Boolean isDrawn) {
        this.isDrawn = isDrawn;
    }
    public void setIsFaceUp(Boolean isFaceUp) {
        this.isFaceUp = isFaceUp;
    }   

    public void printCard() {
        System.out.println(rank + " of " + suit);
    }

    public int getRankInt() {
        switch (rank) {
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            case "10":
            case "Jack":
            case "Queen":
            case "King":
                return 10;
            case "Ace":
                return 11; // Ace can also be 1, but handled in scoring logic
            default:
                return 0;
        }
    }
}
