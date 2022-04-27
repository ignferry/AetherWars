package com.aetherwars.event;

import com.aetherwars.card.Card;

public class ClickCardEvent implements Event{
    // Event yang terbentuk saat kartu, baik yang di field, hand, atau draw phase mendapat dan kehilangan
    // fokus mouse

    private Card card;
    private boolean isCharCardInField;

    public ClickCardEvent(Card c, boolean isCharCardInField) {
        this.card = c;
        this.isCharCardInField = isCharCardInField;
    }

    public Card getCard() {
        return this.card;
    }

    public boolean isCharCardInField() {
        return this.isCharCardInField;
    }
}
