package com.aetherwars.card;

abstract class Card {
    protected String name;
    protected String description;
    protected String imagePath;

    abstract Card clone(Card c);
}
