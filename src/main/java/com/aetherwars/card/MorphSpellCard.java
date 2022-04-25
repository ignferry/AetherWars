package com.aetherwars.card;

import com.aetherwars.cardlist.CardList;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class MorphSpellCard extends SpellCard {
    private int targetId;
    private CharacterCard newForm;
    // dilengkapi ya, ini dibuat biar bisa bikin cardList
    public MorphSpellCard(int id, String name, String desc, String imagePath, int mana, int targetId) {
        super(id, name, desc, imagePath, mana, -1);
        this.targetId = targetId;
        CardList cl = new CardList();
        File charCSV = new File("..\\..\\..\\resources\\com.aetherwars\\card\\data\\character.csv");
        try{
            cl.loadCharacterCards(charCSV);
        }
        catch (IOException e) {
            ;
        }
        catch (URISyntaxException e) {
            ;
        }
        this.newForm = cl.getCharCards(targetId);
    }

    @Override
    public Card clone(Card c) {
        MorphSpellCard cc = new MorphSpellCard(c.id, c.name, c.description, c.imagePath, c.manaNeeded, 0);
        return cc;
    }

    @Override
    public void useSpell(CharacterCard target) {
        target.changeAll(this.newForm);
    }
}
