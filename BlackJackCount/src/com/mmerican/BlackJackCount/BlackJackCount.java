/* This application creates a stack of cards containing 
 * the number of decks selected by the user. It then
 * find the number of times that there is a BlackJack
 * combination and outputs the percentage. * 
 */

package com.mmerican.BlackJackCount;

import java.util.*;

public class BlackJackCount {
	private ArrayList<Integer> deck = new ArrayList<>();
	private int deckCount;
	
	// constructor
	public BlackJackCount(int deckCount) {
		this.deckCount = deckCount;
		createDeck(deck, deckCount);
	}
	
	// generate deck based on num of decks chosen by user
	private void createDeck(ArrayList<Integer> deck, int numOfDecks) {
		for (int i = 1; i <= numOfDecks; i++) {
			for (int j = 1; j <= 4; j++) { // add values 4 times for the 4 suits
				for (int k = 2; k <= 10; k++) { // add face cards 2 - 10
					deck.add(k);
				}
 				deck.add(11); // add ace
				for (int l = 0; l < 3; l++) { // add J, Q, K
					deck.add(10);
				}
			}
		}
	}
	
	// run through the deck;
	public int dealCards() {
		int numBlackJacks = 0;
		int cardTotal;
		Collections.shuffle(deck);
		System.out.println("got to the loop");
		while (deck.size() > 1) {
			cardTotal = 0;
			for (int i = 0; i < 2; i++) {
				int card1 = deck.get(0);
				deck.remove(0);
				cardTotal += card1;
			}
			if (cardTotal == 21) {
				numBlackJacks++;
			}
		}
		System.out.println("Run through the entire deck");
		return numBlackJacks;
	}
	
	public static void printBlackJackCount(int numOfDecks, int numBlackJacks) {
		System.out.println("The number of BlackJacks dealt in " + numOfDecks +
				" decks is: " + numBlackJacks);
		double blackjackPercent = 100 * (double)numBlackJacks / ((double)numOfDecks * 26);
		System.out.println("The percentage of Black Jacks dealt is: " + blackjackPercent);
	}
}

class runBJC {
	public static void main(String[] args) {
		System.out.println("Welcome to BlackJack Probability Counter");
		System.out.println("Enter the number of decks (1-10)");
		int numOfDecks = new Scanner(System.in).nextInt();
		BlackJackCount count = new BlackJackCount(numOfDecks);
		int numOfBlackJacks = count.dealCards();
		BlackJackCount.printBlackJackCount(numOfDecks, numOfBlackJacks);
	}
}