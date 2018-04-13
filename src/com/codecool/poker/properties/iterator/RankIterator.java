package com.codecool.poker.properties.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import com.codecool.poker.hand.Hand;
import com.codecool.poker.card.Card;

public class RankIterator implements Iterator<Integer> {
    private int index;
    private List<Card> cards;
	

	public RankIterator(Hand hand) {
        this.cards = hand.getCards();
        this.index = 0;
	}

	@Override
	public boolean hasNext() {
		return index < cards.size();
	}

	@Override
	public Integer next() {
		Integer number = cards.get(index).getRank().getCardStrength();
		index++;
		return number;
	}
}