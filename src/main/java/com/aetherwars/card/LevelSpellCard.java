package com.aetherwars.card;

public class LevelSpellCard extends SpellCard {
    private int modifierLvl;
    // dilengkapi ya, ini dibuat biar bisa bikin cardList

    public LevelSpellCard(int id, String name, String desc, String imagePath, int mana, int modifierLvl) {
        super(id, name, desc, imagePath, mana, -1, "level");
        this.modifierLvl = modifierLvl;
    }

    public int getModifierLvl(){return this.modifierLvl;}

    public int getManaNeededAsPlayerLVL (CharacterCard target) {
        int manacost;
        if (target.getLevel()%2==0){    // Genap, lgsg bagi
            manacost = target.getLevel()/2;
        }
        else {      // Ganjil, hasil bagi (floor) + 1
            manacost = target.getLevel()/2 + 1;
        }
        return manacost;
    }
    
    @Override
    public void useSpell(CharacterCard target) {
        for (int i = 0; i < Math.abs(this.modifierLvl); i++) {
            if (this.modifierLvl < 0 && target.getLevel() > 1) {
                target.levelDown();
                target.setExp(0);       // Udah ada sih di levelDown()
            } else if (this.modifierLvl > 0 && target.getLevel() < 10) {
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

