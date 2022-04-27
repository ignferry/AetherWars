package com.aetherwars.card;

import java.util.ArrayList;
import java.util.List;

public class CharacterCard extends Card {
    private double baseAttack;
    private double baseHp;
    private int attackUp;
    private int healthUp;
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

    public void setSwap(boolean swapped) { this.swapped = swapped; }

    public CharacterType getType() {
        return this.type;
    }

    public void setHp(double hp) {
        this.baseHp = hp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public boolean isDie(){
        return this.baseHp <= 0;
    }

    public void addSwapSpell(SwapSpellCard swap) {
        if (this.usedSwap.size() == 0) {
            this.usedSwap.add(swap);
        }
        else {
            this.usedSwap.get(0).addDuration(swap.getDuration());
        }
    }

    public void addPotionSpell(PotionSpellCard potion) {
        this.usedPotion.add(potion);
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
            this.exp -= (this.level * 2 - 1);
            this.level++;
        }
    }

    public void levelDown() {
        if (this.level > 1) {
            this.baseAttack = Math.max(0, this.baseAttack - this.attackUp);
            this.baseHp = Math.max(0, this.baseHp - this.healthUp);
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