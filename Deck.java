package poker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Deck {
	
	final static String SUITS[] = {"Spades", "Clubs", "Hearts", "Diamonds"};
	final static String VALUES[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
	static HashMap<String, Integer> deckMap = new HashMap<>(); 
	static int totalCardsDealt = 0; 
	static int cardsDealtToPlayer = 0;
	
	static Set<String> playerHand = new HashSet<>();
	static Set<String> dealerHand = new HashSet<>();
	static int playerCount = 0;
	static int dealerCount = 0;
	
	public Deck() {
		createDeck();
		playerCount = 0;
		dealerCount = 0;
		totalCardsDealt = 0;
		cardsDealtToPlayer = 0;
		playerHand = new HashSet<>();
		dealerHand = new HashSet<>();
	}
	
	//Create the deck of cards
	public static void createDeck() {		
		//Iterate through SUITS and VALUES of card to create Hash Map
		for(int suit=0; suit<SUITS.length; suit++) {
			for(int value=0; value<VALUES.length; value++) {
				if(VALUES[value].equals("A")) {
					deckMap.put(VALUES[value] + " of " + SUITS[suit], 1);
				}
				else if(VALUES[value].equals("J") || VALUES[value].equals("Q") || VALUES[value].equals("K")) {
					deckMap.put(VALUES[value] + " of " + SUITS[suit], 10);
				}
				else {
					deckMap.put(VALUES[value] + " of " + SUITS[suit], Integer.parseInt(VALUES[value]));
				}
			}
		}
	}
	
	//Return new random card from deck and remove it
	public Object getCard(String user) {
		//Get Random Card from Deck
		Object[] deckArray = deckMap.keySet().toArray();
		Object card = deckArray[new Random().nextInt(deckArray.length)];
		
		if(user.equals("player")) {
			//Check if the card is an Ace and how to value it
			if(card.toString().startsWith("A ")) {
				if(playerCount >= 11) {
					playerCount+=1;
				}
				else {
					playerCount+=11;
				}
			}
			else {				
				playerCount += deckMap.get(card);
			}
			
			playerHand.add((String)card);
			cardsDealtToPlayer++;
		}
		else if(user.equals("dealer")) {
			//Check if the card is an Ace and how to value it
			if(card.toString().startsWith("A ")) {
				if(dealerCount >= 11) {
					dealerCount+=1;
				}
				else {
					dealerCount+=11;
				}
			}
			else {				
				dealerCount += deckMap.get(card);
			}
			
			dealerHand.add((String)card);
		}
		
		//Update Cards Dealt
		totalCardsDealt++;
		//Remove Card from Deck
		deckMap.remove(card);
		
		return card;
	}
	
	//Return the hand value of player
	public int getPlayerCount() {
		return playerCount;
	}
	
	//Return the hand value of dealer
	public int getDealerCount() {
		return dealerCount;
	}
	
	//Return the hand of player
	public Set<String> getPlayerHand() {
		return playerHand;
	}
	
	//Return the hand of dealer
	public Set<String> getDealerHand() {
		return dealerHand;
	}
	
	//Return the amount of cards dealt to the entire table
	public int getTotalCardsDealt() {
		return totalCardsDealt;
	}
	
	//Return the amount of cards dealt to the player
	public int getCardsDealtToPlayer() {
		return cardsDealtToPlayer;
	}
}
