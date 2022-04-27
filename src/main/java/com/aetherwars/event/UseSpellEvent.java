package com.aetherwars.event;

import com.aetherwars.card.SpellCard;
import com.aetherwars.gui.FieldCardController;

public class UseSpellEvent implements Event {
    private SpellCard spellCard;
    private FieldCardController fieldCardController;

    public UseSpellEvent(FieldCardController fieldCardController, SpellCard spellCard) {
        this.fieldCardController = fieldCardController;
        this.spellCard = spellCard;
    }

    public SpellCard getSpellCard() {
        return this.spellCard;
    }

    public FieldCardController getFieldCardController() {
        return this.fieldCardController;
    }
}
