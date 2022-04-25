package com.aetherwars.card;

abstract public class Card {
    protected int id;
    protected String name;
    protected String description;
    protected String imagePath;
    protected int manaNeeded;

    abstract public Card clone(Card c);

    Card(int id, String name, String description, String imagePath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getName() {
        return this.name;
    }

    public int getId(){ return this.id ;}
}
