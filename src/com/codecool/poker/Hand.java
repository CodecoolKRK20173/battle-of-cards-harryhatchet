package com.codecool.poker;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.lang.Comparable;

public class Hand implements Comparable<Hand> {
    private List<Card> cards;
    private Map<Integer, Integer> cardsOccurrence;
    private HandPoints handPoints;

    public Hand(List<Card> cards) {
        this.cards = cards;
        this.cardsOccurrence = calculateCardsOccurrence();
        sortCards();
        this.handPoints = new HandPoints(this);
    }

    public Map<Integer, Integer> getCardsOccurrence() {
        return this.cardsOccurrence;
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public int getHandPoints() {
        return this.handPoints.getHandPoints();
    }

    public void replaceCards(List<Card> cardsToDelete, List<Card> cardsToAdd) {
        for (int i = 0; i < cardsToDelete.size(); i++) {
            this.cards.remove(cardsToDelete.get(i));
            this.cards.add(cardsToAdd.get(i));
        }
    }

    public void addCards(List<Card> cardsToAdd) {
        for (int i = 0; i < cardsToAdd.size(); i++) {
            this.cards.add(cardsToAdd.get(i));
        }
    }

    public void discard(int index) {
        this.cards.remove(index);
    }

    private Map<Integer, Integer> calculateCardsOccurrence() {
        Map<Integer, Integer> cardsOccurrence = new HashMap<>();
        Integer count;
        Integer strength;
        for (Card card : cards) {
            strength = card.getRank().getCardStrength();
            if (cardsOccurrence.containsKey(strength)) {
                count = cardsOccurrence.get(strength) + 1;
            } else {
                count = 1;
            }
            cardsOccurrence.put(strength, count);
        }
        return cardsOccurrence;
    }

    private void sortCards() {
        List<Card> sortedCards = new ArrayList<>();
        Integer count1;
        Integer count2;

        for (Card card : cards) {
            count1 = card.getRank().getCardStrength();

            for (int i = 0; i < sortedCards.size(); i++) {
                count2 = sortedCards.get(i).getRank().getCardStrength();

                if (cardsOccurrence.get(count1) > (cardsOccurrence.get(count2))) {
                    sortedCards.add(i, card);
                    break;
                } else if (cardsOccurrence.get(count1) == (cardsOccurrence.get(count2))) {
                    if (count1 > count2) {
                        sortedCards.add(i, card);
                        break;
                    }
                }
            }
            if (!sortedCards.contains(card)) {
                sortedCards.add(card);
            }
        }

        this.cards = sortedCards;
    }

    @Override
    public int compareTo(Hand otherHand) {

    }
    
    private boolean equals(Hand otherHand) {

    }
}