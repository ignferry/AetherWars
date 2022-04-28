package com.aetherwars.card;

public class SwapSpellCard extends Cancelable {
    // dilengkapi ya, ini dibuat biar bisa bikin cardList
    public SwapSpellCard(int id, String name, String desc, String imagePath, int mana, double duration) {
        super(id, name, desc, imagePath, mana, duration, "swap");
    }

    @Override
    public void useSpell(CharacterCard target) {
        double newAtk = Math.max(0, target.getHp());
        double newHp = Math.max(0, target.getAttack());
        target.changeCurrAtkHp(newAtk, newHp);
        target.addSwapSpell(this);
        target.setSwap(true);
    }

    public void cancelSpell(CharacterCard target) {
        // -> NOTE : Kalo dicancel bisa jadi Hp = 0, -> MATI
        this.useSpell(target);
        target.setSwap(false);
    }

    public void addDuration(double dur) {
        this.duration += dur;
    }

    public Card clone() {
        SwapSpellCard cc = new SwapSpellCard(this.id, this.name, this.description, this.imagePath, this.manaNeeded, this.duration);
        return cc;
    }
}