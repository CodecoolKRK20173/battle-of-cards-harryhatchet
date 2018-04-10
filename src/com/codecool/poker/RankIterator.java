package com.codecool.poker;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

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
		Integer number = Integer.parseInt(cards.get(index).getRank().toString());
		index++;
		return number;
	}
}