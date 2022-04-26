package com.aetherwars.event;

import com.aetherwars.card.CharacterCard;

public class AttrChangedEvent implements Event {
    // Event yang terbentuk saat Character card mengalami perubahan atribut
    // Atribut ATK, HP, EXP, LVL
    private CharacterCard card;

    public AttrChangedEvent(CharacterCard c) {
        this.card = c;
    }

    public CharacterCard getCharacterCard() {
        return this.card;
    }
}
