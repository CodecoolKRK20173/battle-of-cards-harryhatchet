package com.codecool.poker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.*;

public class Deck {

    private List<Card> deck;

    public Deck() {
        deck = FXCollections.observableArrayList();
        generateCards();
        shuffle();
    }

    private void generateCards() {
        for (Suit suit: Suit.values()) {
            for (Rank rank: Rank.values()) {
                this.deck.add(new Card(rank, suit, false));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(this.deck);
    }

    public List<Card> drawCards(int n) {
        Random generator = new Random();
        List<Card> drawnCards = FXCollections.observableArrayList();

        int cardIndex = 0;
        for (int i = 0; i < n; i++) {
            cardIndex = generator.nextInt(this.deck.size() - i);
            drawnCards.add(this.deck.get(cardIndex));
            this.deck.remove(cardIndex);
        }

        return drawnCards;
    }
}