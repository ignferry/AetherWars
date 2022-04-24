package com.aetherwars.card;

public class SwapSpellCard extends SpellCard {
    // dilengkapi ya, ini dibuat biar bisa bikin cardList
    public SwapSpellCard(int id, String name, String desc, String imagePath, int duration, int mana) {
        super(id, name, desc, imagePath, mana, duration);
    }

    @Override
    void abstractMethod() {
        // silakan diubah
    }
}
