package com.aetherwars.card;

abstract public class SpellCard extends Card {
// dilengkapi ya, ini dibuat biar bisa bikin cardList

    SpellCard(String name, String desc, String imagePath) {
        super(name, desc, imagePath);
    }

    abstract void abstractMethod();

    @Override
    Card clone(Card c) {
        // implementasikan
        return null;
    }
}