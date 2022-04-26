package com.aetherwars.card;

abstract public class Card {
    protected int id;
    protected String name;
    protected String description;
    protected String imagePath;
    protected int manaNeeded;

    abstract public Card clone();

    Card(int id, String name, String description, String imagePath, int manaNeeded) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.manaNeeded = manaNeeded;
    }

    public String getName() {
        return this.name;
    }

    public int getId(){ return this.id; }

    public String getDescription(){return this.description;}

    public String getImagePath(){return this.imagePath;}

    public int getManaNeeded(){return this.manaNeeded;}
}
