package com.codecool.poker;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class HandPoints implements Comparable<HandPoints> {
    private Hand hand;
    private String name;
    private int points;

    public HandPoints(Hand hand) {
        this.hand = hand;
        this.points = calculateHandPoints();
    }

    public int getHandPoints() {
        return this.points;
    }

    private List<Integer> getSortedOccurValues() {
        List<Integer> occurValues = new ArrayList<>(hand.getCardsOccurrence().values());
        Collections.sort(occurValues, Collections.reverseOrder());
        return occurValues;
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
        return this.hand.getCardsOccurrence().size() < this.hand.getCards().size();
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
        return new RankIterator(hand);
    }

    public Iterator<String> getSuitIterator() {
        return new SuitIterator(hand);
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
        int cardValue2;

        while(itr.hasNext()) {
            cardValue2 = itr.next();

            if(cardValue1 - cardValue2 != 1) {
                return false;
            }

            cardValue1 = cardValue2;
        }
        return true;
    }

    private boolean isFirstAce() {
        Iterator<Integer> itr = getRankIterator();
        return itr.next().equals(14);
    }

    @Override
    public int compareTo(HandPoints other) {
        return points - other.getHandPoints();
    }
}