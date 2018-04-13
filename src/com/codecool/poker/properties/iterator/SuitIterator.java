package com.codecool.poker.properties.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import com.codecool.poker.hand.Hand;
import com.codecool.poker.card.Card;

public class SuitIterator implements Iterator<String> {
    private int index;
    private List<Card> cards;
	

	public SuitIterator(Hand hand) {
        this.cards = hand.getCards();
        this.index = 0;
	}

	@Override
	public boolean hasNext() {
		return index < cards.size();
	}

	@Override
	public String next() {
		String color = cards.get(index).getSuit().toString();
		index++;
		return color;
	}
}