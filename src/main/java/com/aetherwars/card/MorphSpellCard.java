package com.aetherwars.card;

import com.aetherwars.cardlist.CardList;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class MorphSpellCard extends SpellCard {
    private int targetId;
    // dilengkapi ya, ini dibuat biar bisa bikin cardList
    public MorphSpellCard(int id, String name, String desc, String imagePath, int mana, int targetId) {
        super(id, name, desc, imagePath, mana, -1, "morph");
        this.targetId = targetId;
    }

    public int getTargetId(){return this.targetId;}

    @Override
    public Card clone() {
        MorphSpellCard cc = new MorphSpellCard(this.id, this.name, this.description, this.imagePath, this.manaNeeded, this.targetId);
        return cc;
    }

    @Override
    public void useSpell(CharacterCard target) {
        target.changeAll((CharacterCard) CardList.getById(this.targetId));
    }
}
