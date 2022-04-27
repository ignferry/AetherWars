package com.aetherwars.card;

abstract public class SpellCard extends Card {
    // dilengkapi ya, ini dibuat biar bisa bikin cardList
    protected int duration;
    protected String spellType;

    SpellCard(int id, String name, String desc, String imagePath, int mana, int duration, String spellType) {
        super(id, name, desc, imagePath, mana);
        this.duration = duration;
        this.spellType = spellType;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSpellType() { return this.spellType; }
    abstract public void useSpell(CharacterCard target);
    // abstract public Card clone(Card c); -> extend dari Card
}