package com.aetherwars.card;

public class LevelSpellCard extends SpellCard {
    private int modifierLvl;
    // dilengkapi ya, ini dibuat biar bisa bikin cardList

    LevelSpellCard(int id, String name, String desc, String imagePath, int mana, int modifierLvl) {
        super(id, name, desc, imagePath, mana, -1);
        this.modifierLvl = modifierLvl;
    }

    public int getModifierLvl(){return this.modifierLvl;}

    public void manaNeededAsPlayerLVL (CharacterCard target) {
        if (target.getLevel()%2==0){    // Genap, lgsg bagi
            this.manaNeeded = target.getLevel()/2;
        }
        else {      // Ganjil, hasil bagi (floor) + 1
            this.manaNeeded = target.getLevel()/2 + 1;
        }
    }
    
    @Override
    public void useSpell(CharacterCard target) {
        for (int i = 0; i < Math.abs(this.modifierLvl); i++) {
            if (this.modifierLvl < 0 && target.getLevel() < 10) {
                target.levelDown();
                target.setExp(0);       // Udah ada sih di levelDown()
            } else if (this.modifierLvl > 0 && target.getLevel() > 1) {
                target.levelUp();
                target.setExp(0);
            }
        }
    }

    @Override
    public Card clone() {
        LevelSpellCard cc = new LevelSpellCard(this.id, this.name, this.description, this.imagePath, this.manaNeeded, this.modifierLvl);
        return cc;
    }
}

