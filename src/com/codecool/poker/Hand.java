package com.codecool.poker;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.lang.StringBuilder;
import java.util.Iterator;
import java.lang.Comparable;
import java.util.Comparator;

public class Hand implements Comparable<Hand>, Comparator<HandPoints>, Iterators {
    private List<Card> cards;
    private Map<Integer, Integer> cardsOccurrence;
    private HandPoints handPoints;

    public Hand(List<Card> cards) {
        this.cards = cards;
        sortCards();
        this.handPoints = new HandPoints(this);
    }

    public Map<Integer, Integer> getCardsOccurrence() {
        return this.cardsOccurrence;
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public HandPoints getHandPoints() {
        return this.handPoints;
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

    public void sortCards() {
        List<Card> sortedCards = new ArrayList<>();
        Integer count1;
        Integer count2;
        cardsOccurrence = calculateCardsOccurrence();

        for (Card card : cards) {
            count1 = card.getRank().getCardStrength();

            for (int i = 0; i < sortedCards.size(); i++) {
                count2 = sortedCards.get(i).getRank().getCardStrength();

                if (isFirstThan(count1, count2)) {
                    sortedCards.add(i, card);
                    break;
                }
            }
            if (!sortedCards.contains(card)) {
                sortedCards.add(card);
            }  
        }
        
        this.cards = sortedCards;
    }

    private boolean isFirstThan(int count1, int count2) {
        if (cardsOccurrence.get(count1) > (cardsOccurrence.get(count2))) {
            return true;
        }
        if (cardsOccurrence.get(count1) == (cardsOccurrence.get(count2))) {
            if (count1 > count2) {
                return true;
            }
        }
        return false;
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
    
    @Override
    public int compareTo(Hand other) {
        List<Card> cards2 = other.getCards();
        int result = compare(handPoints, other.getHandPoints());

        if (result == 0) {
            for (int i = 0; i < cards.size(); i++) {
                result = cards.get(i).compareTo(cards2.get(i));
                if (result != 0) {
                    return result;
                }
            }
        }
        return result;
    }

    @Override
    public int compare(HandPoints hp1, HandPoints hp2) {
        return hp1.getPoints() - hp2.getPoints();
    }

    @Override
    public Iterator<Integer> getRankIterator() {
        return new RankIterator(this);
    }

    
    public Iterator<String> getSuitIterator() {
        return new SuitIterator(this);
    }
}