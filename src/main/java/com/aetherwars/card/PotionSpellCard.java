package com.aetherwars.card;

public class PotionSpellCard extends SpellCard {
    private int attackChangeValue;
    private int hpChangeValue;
    // dilengkapi ya, ini dibuat biar bisa bikin cardList
    public PotionSpellCard(int id, String name, String desc, String imagePath, int attackChangeValue, int hpChangeValue, int mana, int duration) {
        super(id, name, desc, imagePath, mana, duration);
        this.attackChangeValue = attackChangeValue;
        this.hpChangeValue = hpChangeValue;
    }

    @Override
    void abstractMethod() {
        // silakan diubah
    }
}
