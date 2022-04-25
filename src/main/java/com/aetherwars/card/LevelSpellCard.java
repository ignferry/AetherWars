package com.aetherwars.card;

public class LevelSpellCard extends SpellCard {
    private int modifierLvl;
    // dilengkapi ya, ini dibuat biar bisa bikin cardList

    LevelSpellCard(int id, String name, String desc, String imagePath, int mana, int modifierLvl) {
        super(id, name, desc, imagePath, mana, -1);
        this.modifierLvl = modifierLvl;
    }

    @Override
    public void useSpell(CharacterCard target) {
        for (int i = 0; i < Math.abs(this.modifierLvl); i++) {
            if (this.modifierLvl < 0) {
                target.levelDown();
            }
            else if (this.modifierLvl > 0) {
                target.levelUp();
            }
        }
    }

    @Override
    public Card clone(Card c) {
        LevelSpellCard cc = new LevelSpellCard(c.id, c.name, c.description, c.imagePath, c.manaNeeded, 0);
        return  cc;
    }
}
