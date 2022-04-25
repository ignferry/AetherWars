package com.aetherwars.card;

abstract public class SpellCard extends Card {
// dilengkapi ya, ini dibuat biar bisa bikin cardList
    protected int duration;

    SpellCard(int id, String name, String desc, String imagePath, int mana, int duration) {
        super(id, name, desc, imagePath, mana);
        this.duration = duration;
    }

    abstract public void useSpell(CharacterCard target);
//  abstract public Card clone(Card c);     -> extend dari Card
}