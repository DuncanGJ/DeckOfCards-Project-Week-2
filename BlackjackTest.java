import java.util.Scanner;

public class BlackjackTest {
    public void main(String[] args) {
        
        Boolean exit = false;
        menu(exit);

    }
    public void menu(Boolean exit) {
        Scanner scanner = new Scanner(System.in);
        while (!exit) {
            System.out.println("Menu:");
            System.out.println("1. Play Blackjack");
            System.out.println("2. Instructions");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    playBlackjack(exit);
                    break;
                case 2:
                    System.out.println("Instructions:");
                    System.out.println("Standard blackjack rules");
                    System.out.println("Try to get as close to 21 as possible without going over");
                    System.out.println("Face cards are worth 10, aces are worth 1 or 11");
                    System.out.println("Dealer hits on 16 and stands on 17");
                    lineBreak(3000);
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }   
        }
    }

    // Blackjack game loop
    public void playBlackjack(Boolean exit) {
        //initialize game variables
        Boolean playerNatural;
        Boolean dealerNatural;
        Boolean playerCont; //tracks if player continues to hit or stands
        Boolean handState; //true if hand is over, skips unnecessary steps
        int dealerCount;    
        int playerCount;
        int playerWins = 0;
        int dealerWins = 0;
        int pushes = 0;

        //initialize deck & hands
        System.out.println("How many decks would you like to use (1 is classic, 6 is common for casinos)?");
        
        Scanner scanner = new Scanner(System.in);
        int numDecks = scanner.nextInt();
        Deck deck = new Deck("Standard", numDecks);

        System.out.println("Shuffling the deck...");

        deck.shuffle();
        deck.cutDeck();
        lineBreak();

        Hand playerHand = new Hand(2);
        Hand dealerHand = new Hand(2);

        //game loop
        while (!exit) {
            //resetting variables for new hand
            playerNatural = false;
            dealerNatural = false;
            playerCont = false;
            handState = false;
            dealerCount = 0;    
            playerCount = 0;
            
            //resize hand back to 2 if not first hand
            if (playerWins > 0 || dealerWins > 0 || pushes > 0) {
                playerHand.reSize(2);
                dealerHand.reSize(2);
            }
            
            // Initial deal for hand
            deal(playerHand, dealerHand, deck);

            // Check for naturals on initial deal
            playerNatural = naturalCheck(playerHand);
            dealerNatural = naturalCheck(dealerHand);

            if (playerNatural || dealerNatural) {
                if (playerNatural && dealerNatural) {
                    System.out.println("Both player and dealer have naturals! It's a push.");
                    playerCount = score(playerHand);
                    dealerCount = score(dealerHand);
                    lineBreak();
                    pushes++;
                } else if (playerNatural) {
                    System.out.println("Player has a natural! Player wins!");
                    playerCount = score(playerHand);
                    dealerCount = score(dealerHand);
                    lineBreak();
                    playerWins++;
                } else {
                    System.out.println("Dealer has a natural! Dealer wins!");
                    playerCount = score(playerHand);
                    dealerCount = score(dealerHand);
                    lineBreak();
                    dealerWins++;
                }
                handState = true;
            }

            //Displays hands to player after deal
            if (!handState) {
                dealerCount = score(dealerHand);
                playerCount = score(playerHand);
                System.out.println("your score: " + playerCount);
                System.out.println("Your hand:");
                playerHand.printHand();
                lineBreak();
                System.out.println("Dealer's hand:");
                dealerHand.printHand();
                lineBreak();
            }
            
            if (!handState) { //skips if game already over 
                //player's turn, can hit or stand
                while (!playerCont) {
                    int action;
                    System.out.println("Would you like to (1) Hit or (2) Stand?");
                    
                    try {
                       action  = Integer.valueOf(scanner.next()); 
                    } catch (Exception e) {
                        action = -1;
                    }
                    
                    switch(action) {
                        case 1:
                            System.out.println("Drawing a card...");
                            playerHand.addCard(deck.drawCard(true));
                            drawMsg(playerHand);
                            lineBreak();
                            System.out.println("Your hand:");
                            playerHand.printHand();
                            playerCount = score(playerHand);
                            System.out.println("your score: " + playerCount);
                            lineBreak();
                            if (playerCount > 21) {
                                
                                System.out.println("Player busts! Dealer wins.");
                                lineBreak();
                                handState = true;
                                dealerWins++;
                                playerCont = true;
                            }
                            else if (playerCount == 21) {
                                System.out.println("Blackjack, player has 21!");
                                lineBreak();
                                playerCont = true;
                            }
                            break;
                        case 2:
                            System.out.println("Player stands.");
                            lineBreak();
                            playerCont = true;
                            break;
                        default:
                            System.out.println("Invalid action. Please choose 1 or 2.");
                            break;
                    }
                }
            }
            
            //dealer play
            if (!handState) {
                dealerHand.getCard(1).setIsFaceUp(true); // Reveal dealer's face-down card
                System.out.println("Revealing dealer's hand:");
                dealerHand.printHand();
                dealerCount = score(dealerHand);
                System.out.println("Dealer's score: " + dealerCount);
                lineBreak();
                
                //dealer hits until reaching 17 or higher
                while (dealerCount < 17) {
                    System.out.println("Dealer draws a card...");
                    dealerHand.addCard(deck.drawCard(true));
                    drawMsg(dealerHand);
                    lineBreak();
                    System.out.println("Dealer's hand:");
                    dealerHand.printHand();
                    lineBreak();
                    dealerCount = score(dealerHand);
                    if (dealerCount > 21) {
                        drawMsg(dealerHand);
                        System.out.println("Dealer busts! Player wins.");
                        lineBreak();
                        playerWins++;
                        handState = true;
                    }
                    else if (dealerCount >= 17) {
                        System.out.println("Dealer stands.");
                        lineBreak();
                    }
                    else if (dealerCount == 21) {
                        System.out.println("Blackjack, dealer has 21!");
                        lineBreak();
                    }
                }
            }
            
            System.out.println("Final Scores - Player: " + playerCount + ", Dealer: " + dealerCount);
            
            if (!handState) {
                if (playerCount > dealerCount) {
                    System.out.println("Player wins!");
                    playerWins++;
                } else if (dealerCount > playerCount) {
                    System.out.println("Dealer wins!");
                    dealerWins++;
                } else {
                    System.out.println("It's a push!");
                    pushes++;
                }
            }
            lineBreak();
            playerHand.clear();
            dealerHand.clear();
            System.out.println("Current Standings - Player Wins: " + playerWins + ", Dealer Wins: " + dealerWins + ", Pushes: " + pushes);
            System.out.println("Would you like to play another hand? (1) Yes (2) No");
            int continueChoice = scanner.nextInt();
            if (continueChoice != 1) {
                exit = true;
                System.out.println("Exiting to main menu.");    
            }

        }
            
    }

    // initialize blackjack objects --> 
    public String dealerPlay(Hand dealerHand, Deck deck, int round) {
        String output = "21 | Stand | Bust | Natural";
        switch (round) {
            case 1:
                return output;
            case 2:
                return output;
            default:
                return output;
        }
        
    }

    //helper methods
    
    //deal initial two cards to player and dealer
    public void deal(Hand playerHand, Hand dealerHand, Deck deck) {
        //checks if enough cards in deck, reshuffles if not
        if (deck.getTopCardIndex() + 4 > deck.getDeck().length) {
            System.out.println("Not enough cards in deck. Reshuffling deck.");
            deck.resetDeck();
            deck.shuffle();
            deck.cutDeck();
            lineBreak();
        }
        playerHand.addCard(deck.drawCard(true));
        dealerHand.addCard(deck.drawCard(true));
        playerHand.addCard(deck.drawCard(true));
        dealerHand.addCard(deck.drawCard(false));
    }

    //checks for natural blackjack
    public Boolean naturalCheck(Hand hand) {
        if (hand.getSize() == 2 && hand.hasAce() && hand.hasFaceCard(1)) {
            return true;
        }
       return false;
    }

    //calculates score of hand, accounting for aces as 1 or 11, automatically adjust aces to 1 if bust 
    public int score(Hand hand) {
        int total = 0;
        int aceCount = hand.countAces();
        for (int i = 0; i < hand.getSize(); i++) {
            total += hand.getCard(i).getRankInt();
        }
        
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        return total;
    }

    //msg for that returns most recently drawn card
    public void drawMsg(Hand hand) {
       String string = hand.getCard(hand.getSize()-1).getRank() + " of " + hand.getCard(hand.getSize()-1).getSuit() + " drawn";
       System.out.println(string);
    }

    //condensed line break for readability
    public void lineBreak() {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("");
        try {
            Thread.sleep(1500); // Pause for n milliseconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

        public void lineBreak(int waitTime) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("");
        try {
            Thread.sleep(waitTime); // Pause for n milliseconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}