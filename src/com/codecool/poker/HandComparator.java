package com.codecool.poker;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class HandComparator {
    List<Hand> hands;
    List<Hand> theBestHand;

    public HandComparator(List<Hand> hands) {
        this.hands = hands;
        this.theBestHand = findTheBestHand();
    }

    public List<Hand> getTheBestHand() {
        return this.theBestHand;
    }

    private List<Hand> findTheBestHand() {
        List<Hand> theBestHand = new ArrayList<>();
        Hand maxHand = hands.get(0);

        for(Hand hand: hands) {
            if(hand.getHandPoints() > maxHand.getHandPoints()) {
                theBestHand.clear();
                theBestHand.add(hand);
                maxHand = hand;
            } else if(hand.getHandPoints() == maxHand.getHandPoints()) {
                
                if(hand.compareTo(maxHand) > 0) {
                    theBestHand.clear();
                    theBestHand.add(hand);
                    maxHand = hand;
                } else if(hand.compareTo(maxHand) == 0) {
                    theBestHand.add(hand);
                }
                
            }
        }

        return theBestHand;
    }
}