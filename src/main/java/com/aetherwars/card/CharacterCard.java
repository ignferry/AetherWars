package com.aetherwars.card;

import com.aetherwars.player.Player;

import java.util.ArrayList;
import java.util.List;

public class CharacterCard extends Card {
    private double baseAttack;
    private double baseHp;
    private int attackUp;
    private int healthUp;
    private double currentAttack;
    private double currentHp;
    private int level;
    private int exp = 0;
    private boolean swapped = false;
    private CharacterType type;
    private List<SwapSpellCard> usedSwap;
    private List<PotionSpellCard> usedPotion;

    public CharacterCard(int id, String name, String desc, String imagePath, int manaNeeded, double baseAtk, double baseHp,
                         int atkUp, int healthUp,
                         int level, int exp, CharacterType type) {
        super(id, name, desc, imagePath, manaNeeded);
        this.baseAttack = baseAtk;
        this.baseHp = baseHp;
        this.currentAttack = this.baseAttack;
        this.currentHp = this.baseHp;
        this.attackUp = atkUp;
        this.healthUp = healthUp;
        this.level = level;
        this.addExp(exp);
        this.type = type;
        this.usedPotion = new ArrayList<PotionSpellCard>();
        this.usedSwap = new ArrayList<SwapSpellCard>();
    }

    public String getDesc() {
        return this.description;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public double getHp() {
        return this.baseHp;
    }

    public double getAttack() {
        return this.baseAttack;
    }

    public int getLevel() {
        return this.level;
    }

    public int getExp() {
        return this.exp;
    }

    public boolean getSwap() { return this.swapped; }

    public double getCurrentAttack() {return this.currentAttack;}

    public double getCurrentHp() {return this.currentHp;}

    public void setSwap(boolean swapped) { this.swapped = swapped; }

    public CharacterType getType() {
        return this.type;
    }

    public void setHp(double hp) {
        this.baseHp = hp;
    }

    public void setCurrentHp(double hp) { this.currentHp = hp;}

    public void setCurrentAttack(double attack) {this.currentAttack = attack;}

    public void setExp(int exp) {
        this.exp = exp;
    }

    public boolean isDie(){
        return this.baseHp <= 0;
    }

    public void receiveAttack(CharacterCard attacker){
        double damage = 0;
        if((this.getType() == CharacterType.NETHER && attacker.getType() == CharacterType.OVERWORLD) || (this.getType() == CharacterType.OVERWORLD && attacker.getType() == CharacterType.END) || (this.getType() == CharacterType.END && attacker.getType() == CharacterType.NETHER) ){
            damage = attacker.getAttack() * 0.5;
        }
        else {
            damage = attacker.getAttack() * 2;
        }
        this.attackToPotionSpell(damage);
        this.currentHp = Math.max(0, this.currentHp-damage);
    }

    public void attackToPotionSpell(double damage) {
        double remain;
        for (PotionSpellCard p : this.usedPotion) {
            if (damage > 0) {
                // 1.Pemakaian potion tidak dipengaruhi wrap
                if ((!this.swapped && !p.getApplyOnSwap()) || (this.swapped && p.getApplyOnSwap())) {
                    if (p.getHpChangeValue() > 0) {
                        remain = p.getHpChangeValue() - damage;
                        if (remain > 0) {
                            p.setHpChangeValue(remain);
                            damage = 0;
                        }
                        else {
                            p.setHpChangeValue(0);
                            damage = Math.abs(remain);
                        }
                    }
                }
                // 2.Pemakaian potion dipengaruhi wrap
                else if ((this.swapped && !p.getApplyOnSwap()) || (!this.swapped && p.getApplyOnSwap())) {
                    if (p.getAttackChangeValue() > 0) {
                        remain = p.getAttackChangeValue() - damage;
                        if (remain > 0) {
                            p.setAttackChangeValue(remain);
                            damage = 0;
                        }
                        else {
                            p.setAttackChangeValue(0);
                            damage = Math.abs(remain);
                        }
                    }
                }
            }
            else {
                break;
            }
        }
    }

    public void directAttack(Player p){
        p.reduceHp((int)this.getAttack());
    }

    public void addSwapSpell(SwapSpellCard swap) {
        if (this.usedSwap.size() == 0) {
            this.usedSwap.add(swap);
            swap.useSpell(this);
        }
        else {
            this.usedSwap.get(0).addDuration(swap.getDuration());
        }
    }

    public void addPotionSpell(PotionSpellCard potion) {
        this.usedPotion.add(potion);
        potion.useSpell(this);
    }

    public void reduceDuration() {
        for (SwapSpellCard swap : usedSwap) {
            if (swap.getDuration() == 0) {
                swap.cancelSpell(this);
                usedSwap.remove(swap);
            }
            else {
                swap.setDuration(swap.getDuration() - 1);
            }
        }

        for (PotionSpellCard potion : usedPotion) {
            if (potion.getDuration() == 0) {
                potion.cancelSpell(this);
                usedPotion.remove(potion);
            }
            else {
                potion.setDuration(potion.getDuration()-1);
            }
        }
    }

    public Boolean isExpMax() {
        return this.exp >= (this.level * 2 - 1);
    }

    public void addExp(int exp) {
        this.exp += exp;
        while (this.isExpMax()) {
            this.levelUp();
        }
    }

    public void levelUp() {
        if (this.level < 10) {
            this.baseAttack += this.attackUp;
            this.baseHp += this.healthUp;
            this.currentHp += this.baseHp;
            this.currentAttack += this.baseAttack;
            this.exp -= (this.level * 2 - 1);
            this.level++;
        }
    }

    public void levelDown() {
        if (this.level > 1) {
            this.baseAttack = Math.max(0, this.baseAttack - this.attackUp);
            this.baseHp = Math.max(0, this.baseHp - this.healthUp);
            this.currentHp = Math.min(this.currentHp, this.baseHp);
            this.currentAttack = Math.min(this.currentAttack, this.baseAttack);
            this.exp = 0;
            this.level--;
        }
    }

    public void changeAtkHp(double newAtk, double newHp) {
        this.baseAttack = newAtk;
        this.baseHp = newHp;
    }

    public void changeAll(CharacterCard other) {
        this.id = other.id;
        this.name = other.name;
        this.description = other.description;
        this.imagePath = other.imagePath;
        this.manaNeeded = other.manaNeeded;
        this.changeAtkHp(other.baseAttack, other.baseHp);
        this.attackUp = other.attackUp;
        this.healthUp = other.healthUp;
        this.level = 1;
        this.exp = 0;
        this.type = other.type;
        this.usedPotion = new ArrayList<PotionSpellCard>();
        this.usedSwap = new ArrayList<SwapSpellCard>();
    }

    public Card clone() {
        return new CharacterCard(this.id, this.name, this.description, this.imagePath, this.manaNeeded,
        this.baseAttack, this.baseHp, this.attackUp, this.healthUp, this.level, this.exp, this.type);
    }

    public void cardInfo() {
        System.out.println("Name: " + this.getName());
        System.out.println("Desc: " + this.getDesc());
        System.out.println("Attack: " + this.getAttack());
        System.out.println("Health: " + this.getHp());
        System.out.println("Exp: " + this.getExp());
        System.out.println("Level: " + this.getLevel());
    }
}