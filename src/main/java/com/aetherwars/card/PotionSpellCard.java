package com.aetherwars.card;

public class PotionSpellCard extends SpellCard {
    private int attackChangeValue;
    private int hpChangeValue;
    // dilengkapi ya, ini dibuat biar bisa bikin cardList

    public PotionSpellCard(int id, String name, String desc, String imagePath, int mana, int duration, int attackChangeValue, int hpChangeValue) {
        super(id, name, desc, imagePath, mana, duration);
        this.attackChangeValue = attackChangeValue;
        this.hpChangeValue = hpChangeValue;
    }

    @Override
    public void useSpell(CharacterCard target) {
        int newAtk = Math.max(0, target.getAttack() + this.attackChangeValue);
        int newHp = Math.max(0, target.getHp() + this.hpChangeValue);
        target.changeAtkHp(newAtk, newHp);
    }

    public void cancelSpell(CharacterCard target) {
        // -> NOTE : Kalo dicancel bisa jadi Hp = 0, -> MATI
        int newAtk = Math.max(0, target.getAttack() - this.attackChangeValue);
        int newHp = Math.max(0, target.getHp() - this.hpChangeValue);
        target.changeAtkHp(newAtk, newHp);
    }

    @Override
    public Card clone(Card c) {
        PotionSpellCard cc = new PotionSpellCard(c.id, c.name, c.description, c.imagePath, c.manaNeeded, 0, 0, 0 );
        return cc;
    }

}
