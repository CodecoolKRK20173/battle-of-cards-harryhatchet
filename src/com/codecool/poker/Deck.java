package com.codecool.poker;

import java.util.*;

public class Deck {

    private List<Card> deck = new ArrayList<>();

    public Deck() {
        generateCards();
        shuffle();
    }

    private void generateCards() {
        for (Suit suit: Suit.values()) {
            for (Rank rank: Rank.values()) {
                this.deck.add(new Card(rank, suit));
            }
        }
    }

    private void shuffle() {
        Collections.shuffle(this.deck);
    }

    public List<Card> drawCards(int n) {
        Random generator = new Random();
        List<Card> drawnCards = new ArrayList<>();

        int cardIndex = 0;
        for (int i = 0; i < n; i++) {
            cardIndex = generator.nextInt(this.deck.size() - i);
            drawnCards.add(this.deck.get(cardIndex));
            this.deck.remove(cardIndex);
        }

        return drawnCards;
    }
}