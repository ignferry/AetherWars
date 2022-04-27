package com.aetherwars.event;

import com.aetherwars.card.CharacterCard;
import com.aetherwars.gui.FieldCardController;

public class AttackEvent implements Event {
    private FieldCardController defender;
    private CharacterCard attacker;

    public AttackEvent (FieldCardController defender, CharacterCard attacker) {
        this.defender = defender;
        this.attacker = attacker;
    }

    public FieldCardController getDefender() {
        return this.defender;
    }

    public CharacterCard getAttacker() {
        return this.attacker;
    }
}
