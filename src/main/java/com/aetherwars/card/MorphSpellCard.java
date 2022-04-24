package com.aetherwars.card;

public class MorphSpellCard extends SpellCard {
    private int targetId;

    // dilengkapi ya, ini dibuat biar bisa bikin cardList
    public MorphSpellCard(int id, String name, String desc, String imagePath, int targetId, int mana) {
        super(id, name, desc, imagePath, mana, -1);
        this.targetId = targetId;
    }

    @Override
    void abstractMethod() {
        // silakan diubah
    }
}
