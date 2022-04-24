package com.aetherwars.card;

public class PotionSpellCard extends SpellCard {
    // dilengkapi ya, ini dibuat biar bisa bikin cardList
    PotionSpellCard(int id, String name, String desc, String imagePath) {
        super(id, name, desc, imagePath);
    }

    @Override
    void abstractMethod() {
        // silakan diubah
    }
}
