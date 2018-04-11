package com.codecool.poker;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.lang.StringBuilder;

public class Hand {
    private List<Card> cards;
    private int handPoints;

    public Hand(List<Card> cards) {
        this.cards = cards;
        this.handPoints = 100;
        sortCards();
    }

    public void addCards(List<Card> cards) {
        this.cards.addAll(cards);
    }

    private void replaceCards(Card cardToDelete, Card cardToAdd) {
        this.cards.remove(cardToDelete);
        this.cards.add(cardToAdd);
    }

    public List<Card> getCards() {
        return this.cards;
    }

    private Map<Integer, Integer> calculateCardsOccurrence() {
        Map<Integer, Integer> cardsOccurrence = new HashMap<>();
        Integer count;
        Integer strength;
        for(Card card: cards) {
            strength = card.getRank().getCardStrength();
            if(cardsOccurrence.containsKey(strength)) {
                count = cardsOccurrence.get(strength) + 1;
            } else {
                count = 1;
            }
            cardsOccurrence.put(strength, count);
        }
        return cardsOccurrence;
    }

    private void sortCards() {
        Map<Integer, Integer> cardsOccurrence = calculateCardsOccurrence();
        List<Card> sortedCards = new ArrayList<>();
        Integer count1;
        Integer count2;
        
        for(Card card: cards) {
            count1 = card.getRank().getCardStrength();

            for(int i = 0; i < sortedCards.size(); i++) {
                count2 = sortedCards.get(i).getRank().getCardStrength();

                if(cardsOccurrence.get(count1) > (cardsOccurrence.get(count2))) {
                    sortedCards.add(i, card);
                    break;
                } else if (cardsOccurrence.get(count1) == (cardsOccurrence.get(count2))) {
                    if(count1 > count2) {
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
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Card card : this.cards) {
            output.append(card.toString());
            output.append(" ");
        }
        return output.toString();
    }
}