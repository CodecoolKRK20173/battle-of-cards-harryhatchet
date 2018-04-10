package com.codecool.poker;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class Hand {
    private List<Card> cards;
    private Map<Integer, Integer> cardsOccurrence;
    private int handPoints;

    public Hand(List<Card> cards) {
        this.cards = cards;
        this.cardsOccurrence = calculateCardsOccurrence();
        sortCards();
        // this.handPoints = calculateHandPoints();
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public int getHandPoints() {
        return this.handPoints;
    }

    private List<Integer> getSortedOccurValues() {
        List<Integer> occurValues = (List) cardsOccurrence.values();
        Collections.sort(occurValues, Collections.reverseOrder());
        return occurValues;
    }

    public void replaceCards(List<Card> cardsToDelete, List<Card> cardsToAdd) {
        for(int i = 0; i < cardsToDelete.size(); i++) {
            this.cards.remove(cardsToDelete.get(i));
            this.cards.add(cardsToAdd.get(i));
        }
    }

    public void addCards(List<Card> cardsToAdd) {
        for(int i = 0; i < cardsToAdd.size(); i++) {
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

    // private int calculateHandPoints() {
    //     if (isSeveralTheSameRank()) {
    //         return getRankPoints();
    //     } else if (isSameColor() && isInSequence() && isFirstAce()) {
    //         return 10;
    //     } else if (isSameColor() && isInSequence()) {
    //         return 9;
    //     } else if (isSameColor()) {
    //         return 6;
    //     } else if (isInSequence()) {
    //         return 5;
    //     } else {
    //         return 1;
    //     }
    // }

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

    public Iterator<Integer> getRankIterator() {
        return new RankIterator(this);
    }

    public Iterator<String> getSuitIterator() {
        return new SuitIterator(this);
    }

    private boolean isSameColor() {
        Iterator<String> itr = getSuitIterator();
        String firstCardColor = itr.hasNext() ? itr.next() : null;

        while(itr.hasNext()) {
            if(!itr.next().equals(firstCardColor)) {
                return false;
            }
        }
        return true;
    }

    private boolean isInSequence() {
        Iterator<Integer> itr = getRankIterator();
        int cardValue1 = itr.hasNext() ? itr.next() : 0;
        int cardValue2 = itr.hasNext() ? itr.next() : 0;

        while(itr.hasNext()) {
            cardValue2 = itr.next();

            if(cardValue1 != cardValue2) {
                return false;
            }

            cardValue1 = cardValue2;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Test main");
    }
}