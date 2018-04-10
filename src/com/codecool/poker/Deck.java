package com.codecool.poker;

import java.util.*;

public class Deck {

    private List<Card> deck = new ArrayList<>();

    public Deck() {
        for (Suit suit: Suit.values()) {
            for (Rank rank: Rank.values()) {
                this.deck.add(new Card(rank, suit));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(this.deck);
    }

    public List<Card> drawCards(int n) {
        Random generator = new Random();
        List<Card> drawnCards = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int cardIndex = i;
            do {
                cardIndex = generator.nextInt(52);
            }
            while (drawnCards.contains(this.deck.get(cardIndex)));
            drawnCards.add(this.deck.get(cardIndex));
        }

        return drawnCards;
    }
}