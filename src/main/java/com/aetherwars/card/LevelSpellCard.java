package com.aetherwars.card;

public class LevelSpellCard extends SpellCard {
    // dilengkapi ya, ini dibuat biar bisa bikin cardList
    LevelSpellCard(int id, String name, String desc, String imagePath) {
        super(id, name, desc, imagePath);
    }

    @Override
    void abstractMethod() {
        // silakan diubah
    }
}
