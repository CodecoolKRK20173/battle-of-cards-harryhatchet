package com.codecool.poker.hand;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class HandComparator {
    private List<Hand> hands;
    private List<Hand> bestHands;

    public HandComparator(List<Hand> hands) {
        this.hands = hands;
        this.bestHands = findBestHands();
    }

    public List<Hand> getBestHands() {
        return this.bestHands;
    }

    private List<Hand> findBestHands() {
        List<Hand> bestHands = new ArrayList<>();
        Hand maxHand = hands.get(0);

        for (Hand hand : hands) {
            if (hand.compareTo(maxHand) > 0) {
                bestHands.clear();
                bestHands.add(hand);
                maxHand = hand;
            } else if (hand.compareTo(maxHand) == 0) {
                bestHands.add(hand);
            }
        }

        return bestHands;
    }
}