package com.codecool.poker;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

public class Hand {
    private List<Card> cards;
    private Map<Integer, Integer> cardsOccurrence;
    private int handPoints;

    public Hand(List<Card> cards) {
        this.cards = cards;
        this.cardsOccurrence = calculateCardsOccurrence();
        sortCards();
        this.handPoints = calculateHandPoints();
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public int getHandPoints() {
        return this.handPoints;
    }

    private List<Integer> getSortedOccurValues() {
        return Collections.sort(cardsOccurrence.values(), Collections.reverseOrder()); 
    }

    public void replaceCards(List<Card> cardsToDelete, List<Card> cardsToAdd) {
        for(int i = 0; i < cardsToDelete.size(); i++) {
            this.cards.remove(cardsToDelete.get(i));
            this.cards.add(cardsToAdd.get(i));
        }
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

    private int calculateHandPoints() {
        if (isSeveralTheSameRank()) {
            return getRankPoints();
        } else if (isSameColor() && isInSequence() && isFirstAce()) {
            return 10;
        } else if (isSameColor() && isInSequence()) {
            return 9;
        } else if (isSameColor()) {
            return 6;
        } else if (isInSequence()) {
            return 5;
        } else {
            return 1;
        }
    }

    private boolean isSeveralTheSameRank() {
        return this.cardsOccurrence.size() < this.cards.size();
    }

    private int getRankPoints() {
        List<Integer> occurValues = getSortedOccurValues();
        int maxOccurrence = occurValues.get(0);
        int secondMaxOccurrence = occurValues.get(1);

        if (maxOccurrence == 4) {
            return 8;
        } else if (maxOccurrence == 3) {
            if (secondMaxOccurrence == 2) {
                return 7;
            } else {
                return 4;
            }
        } else if (maxOccurrence == 2) {
            if (secondMaxOccurrence == 2) {
                return 3;
            } 
        }
        return 2;
    }

    public static void main(String[] args) {
        System.out.println("Test main");
    }
}