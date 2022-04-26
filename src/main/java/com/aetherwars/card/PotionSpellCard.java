package com.aetherwars.card;

public class PotionSpellCard extends Cancelable {
    private int attackChangeValue;
    private int hpChangeValue;
    // dilengkapi ya, ini dibuat biar bisa bikin cardList

    public PotionSpellCard(int id, String name, String desc, String imagePath, int mana, int duration, int attackChangeValue, int hpChangeValue) {
        super(id, name, desc, imagePath, mana, duration);
        this.attackChangeValue = attackChangeValue;
        this.hpChangeValue = hpChangeValue;
    }

    public int getAttackChangeValue(){return this.attackChangeValue;}
    public int getHpChangeValue(){return this.hpChangeValue;}

    @Override
    public void useSpell(CharacterCard target) {
        double newAtk = Math.max(0, target.getAttack() + this.attackChangeValue);
        double newHp = Math.max(0, target.getHp() + this.hpChangeValue);
        target.changeAtkHp(newAtk, newHp);
    }

    public void cancelSpell(CharacterCard target) {
        // -> NOTE : Kalo dicancel bisa jadi Hp = 0, -> MATI
        double newAtk = Math.max(0, target.getAttack() - this.attackChangeValue);
        double newHp = Math.max(0, target.getHp() - this.hpChangeValue);
        target.changeAtkHp(newAtk, newHp);
    }

    @Override
    public Card clone() {
        PotionSpellCard cc = new PotionSpellCard(this.id, this.name, this.description, this.imagePath, this.manaNeeded, this.duration, this.attackChangeValue, this.hpChangeValue );
        return cc;
    }
}