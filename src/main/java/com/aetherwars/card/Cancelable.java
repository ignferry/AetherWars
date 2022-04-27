package com.aetherwars.card;

abstract public class Cancelable extends SpellCard{

    public Cancelable(int id, String name, String desc, String imagePath, int mana, int duration, String spellType) {
        super(id, name, desc, imagePath, mana, duration, spellType);
    }
    abstract public void cancelSpell(CharacterCard target);
}
