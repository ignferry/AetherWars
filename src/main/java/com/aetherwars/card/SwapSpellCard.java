package com.aetherwars.card;

public class SwapSpellCard extends Cancelable {
    // dilengkapi ya, ini dibuat biar bisa bikin cardList
    public SwapSpellCard(int id, String name, String desc, String imagePath, int mana, int duration) {
        super(id, name, desc, imagePath, mana, duration);
    }

    @Override
    public void useSpell(CharacterCard target) {
        double newAtk = Math.max(0, target.getHp());
        double newHp = Math.max(0, target.getAttack());
        target.changeAtkHp(newAtk, newHp);
    }

    public void cancelSpell(CharacterCard target) {
        // -> NOTE : Kalo dicancel bisa jadi Hp = 0, -> MATI
        this.useSpell(target);
    }

    public void addDuration(int dur) {
        this.duration += dur;
    }

    public Card clone(Card c) {
        SwapSpellCard cc = new SwapSpellCard(c.id, c.name, c.description, c.imagePath, c.manaNeeded, 0);
        return cc;
    }
}