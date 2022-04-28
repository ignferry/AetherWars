package com.aetherwars.card;

public class PotionSpellCard extends Cancelable {
    private double attackChangeValue;
    private double hpChangeValue;
    private boolean applyOnSwap = false;
    // dilengkapi ya, ini dibuat biar bisa bikin cardList

    public PotionSpellCard(int id, String name, String desc, String imagePath, int mana, double duration, double attackChangeValue, double hpChangeValue) {
        super(id, name, desc, imagePath, mana, duration, "potion");
        this.attackChangeValue = attackChangeValue;
        this.hpChangeValue = hpChangeValue;
    }

    public double getAttackChangeValue(){ return this.attackChangeValue; }
    public double getHpChangeValue(){ return this.hpChangeValue; }
    public boolean getApplyOnSwap() { return this.applyOnSwap; }

    public void setAttackChangeValue (double newValue) {
        this.attackChangeValue = newValue;
    }
    public void setHpChangeValue (double newValue) {
        this.hpChangeValue = newValue;
    }

    @Override
    public void useSpell(CharacterCard target) {
        double newAtk = Math.max(0, target.getAttack() + this.attackChangeValue);
        double newHp = Math.max(0, target.getHp() + this.hpChangeValue);
        target.changeCurrAtkHp(newAtk, newHp);
        target.addPotionSpell(this);
        if (target.getSwap()) {
            this.applyOnSwap = true;
        }
    }

    public void cancelSpell(CharacterCard target) {
        // -> NOTE : Kalo dicancel bisa jadi Hp = 0, -> MATI
        double newAtk = target.getAttack();
        double newHp = target.getHp();

        // 1.Pemakaian potion tidak dipengaruhi wrap
        if ((!target.getSwap() && !this.applyOnSwap) || (target.getSwap() && this.applyOnSwap)) {
            newAtk = Math.max(0, target.getAttack() - this.attackChangeValue);
            newHp = Math.max(0, target.getHp() - this.hpChangeValue);
        }
        // 2.Pemakaian potion dipengaruhi wrap
        else if ((target.getSwap() && !this.applyOnSwap) || (!target.getSwap() && this.applyOnSwap)) {
            newAtk = Math.max(0, target.getAttack() - this.hpChangeValue);
            newHp = Math.max(0, target.getHp() - this.attackChangeValue);
        }
        target.changeCurrAtkHp(newAtk, newHp);
    }

    @Override
    public Card clone() {
        PotionSpellCard cc = new PotionSpellCard(this.id, this.name, this.description, this.imagePath, this.manaNeeded, this.duration, this.attackChangeValue, this.hpChangeValue );
        return cc;
    }
}