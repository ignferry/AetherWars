package com.aetherwars.card;

public class MorphSpellCard extends SpellCard {
    // dilengkapi ya, ini dibuat biar bisa bikin cardList
    MorphSpellCard(int id, String name, String desc, String imagePath) {
        super(id, name, desc, imagePath);
    }

    @Override
    void abstractMethod() {
        // silakan diubah
    }
}
