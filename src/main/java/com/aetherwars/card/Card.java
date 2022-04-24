package com.aetherwars.card;

abstract public class Card {
    protected String name;
    protected String description;
    protected String imagePath;

    abstract Card clone(Card c);

    Card(String name, String description, String imagePath) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
    }
}
