package poker;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;

public class Blackjack {

	//Deck of Cards - Amount of Players as the Parameter
	static Deck deck;
	
	//Bet Counter
	static double totalBet = 0;
	
	//Keyboard Scanner
	static Scanner kb = new Scanner(System.in);
	
	//Default choice
	static int choice = 0;
	
	public static void main(String[] args) throws InterruptedException {
		//Play Game
		playGame();
		//See if player wants to play again...
		playAgain();	
	}
	
	//Start game
	public static void playGame() throws InterruptedException {
		//Create new deck
		deck = new Deck();
		
		System.out.println("Welcome to Carlos' Casino: Blackjack");
		System.out.println("********************************");
		
		//Take in Initial Bet
		setBet();
		
		//Deal card to the Dealer
		Object dealerFirstCard = deck.getCard("dealer");
		
		System.out.println("Thank you \n********************************\nYour First Card: " + deck.getCard("player"));
		System.out.println("********************************\nDealer First Card: " + dealerFirstCard);
		
		//Runs until player hits blackjack, busts, or quits
		while(getPlayerStatus()) {
			//Set choice for stand, hit, or quits
			setChoice();
			//Player Hits
			if(getChoice() == 1) {
				System.out.println("********************************");
				System.out.println("You drew a " + deck.getCard("player"));
				System.out.println("Current Hand: " + deck.getPlayerHand());
				System.out.println("Current Count: " + deck.getPlayerCount());
				System.out.println("********************************\nDealer has a: " + dealerFirstCard);
			}
			//Player Stands
			else if(getChoice() == 2) {
				System.out.println("********************************");
				System.out.println("Dealer had a " + dealerFirstCard);
				//Create delay for dealer revealing cards
				Thread.sleep(3000);
				do {
					System.out.println("********************************");
					System.out.println("Dealer drew a " + deck.getCard("dealer"));
					//Create delay for dealer revealing cards
					Thread.sleep(3000);
				}while(deck.getDealerCount() <= 17);
				System.out.println("Dealer Hand: " + deck.getDealerHand());
				System.out.println("********************************");
				getHeadToHead();
			}
			//Player Quits
			else if(getChoice() == 3) {
				playAgain();
			}
		}
		playAgain();
	}
	
	//Check whether the player wants to play again
	public static void playAgain() throws InterruptedException {
		//Check for invalid answers
		boolean success = false;
		int choice = 2;
		while (!success) {
			try {
				System.out.println("Play again? \n1] Yes \n2] No");
				choice = kb.nextInt();
				if(choice == 1) {
					playGame();
				}
				else if(choice == 2) {
					System.out.println("THANKS FOR PLAYING!");
					System.exit(0);
				}
				else {
					System.out.println("ERROR: Enter in a valid number");
					playAgain();
				}
				success = true;
			} catch (InputMismatchException e) {
				kb.next();
				System.out.println("ERROR: Enter in a valid number");
			}
		}
	}
	
	//Set the choice for player
	public static void setChoice() {
		//Check for invalid answers
		boolean success = false;
        while (!success) {
            try {
            	System.out.println("********************************");
            	System.out.println("1] Hit \n2] Stand \n3] Quit");
                choice = kb.nextInt();
                if(!(choice == 1 || choice == 2 || choice == 3)) {
                	System.out.println("ERROR: Enter in a valid number");
                	setChoice();
                }
                success = true;
            } catch (InputMismatchException e) {
                kb.next();
                System.out.println("ERROR: Enter in a valid number");
            }
        }
	}
	
	public static int getChoice() {
		return choice;
	}
	
	//Get whether the player is still eligible to play
	public static boolean getPlayerStatus() {
		//Check for blackjack: Player hits a hand value of 21 with only 2 cards
		if(deck.getCardsDealtToPlayer() == 2 && deck.getPlayerCount() == 21) {
			System.out.println("BLACKJACK");
			System.out.println("You won: $" + getWinnings(2.5));
			System.out.println("********************************");
			return false;
		}
		//Check for bust: Player hits a hand value of 22 or more
		else if(deck.getPlayerCount() > 21) {
			System.out.println("BUST");
			System.out.println("You lost: $" + getWinnings(-1));
			System.out.println("********************************");
			return false;
		}
		return true;
	}
	
	//Get the final outcome of player versus dealer
	public static void getHeadToHead() throws InterruptedException {
		//Check for player win: Player has a greater hand value than the dealer or the dealer busts
		if(deck.getPlayerCount() > deck.getDealerCount() || deck.getDealerCount() > 21) {
			System.out.println("WINNER");
			System.out.println("You won: $" + getWinnings(2));
			System.out.println("********************************");
		}
		//Check for dealer win: Dealer has a greater hand value than the player
		else if(deck.getPlayerCount() < deck.getDealerCount()) {
			System.out.println("LOSER");
			System.out.println("You lost: $" + getWinnings(-1));
			System.out.println("********************************");
		}
		//Check for tie: Player and Dealer end up with a tied hand value
		else {
			System.out.println("TIE");
			System.out.println("You got back your original bet: $" + getWinnings(1));
			System.out.println("********************************");
		}
		playAgain();
	}
	
	//Set initial bet
	public static void setBet() {
		//Check for invalid answers
		boolean success = false;
		while (!success) {
			try {
				System.out.println("Please wage an intial bet: ");
				totalBet = kb.nextInt();
				if(totalBet < 0.01) {
					System.out.println("ERROR: Please enter a bet worth more than $0.01");
					setBet();
				}
				success = true;
			} catch (InputMismatchException e) {
				kb.next();
				System.out.println("ERROR: Enter in a valid number");
			}
		}
	}
	
	//Get bet
	public static double getBet() {
		return totalBet;
	}
	
	//Get winnings based on the outcome multiplier
	public static double getWinnings(double outcomeMultiplier) {
		return getBet() * outcomeMultiplier;
	}
}
