package com.aetherwars.card;

import com.aetherwars.player.Player;

import java.util.ArrayList;
import java.util.List;

public class CharacterCard extends Card {
    // public static PotionSpellCard ppp = new PotionSpellCard(0,"","","",0,0,2,2);  --> Untuk Testing di method main
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

    public String getDescription() {
        return this.description;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public double getHp() {
        return this.currentHp;
    }

    public double getAttack() {
        return this.currentAttack;
    }

    public int getLevel() {
        return this.level;
    }

    public int getExp() {
        return this.exp;
    }

    public boolean getSwap() { return this.swapped; }

    public double getBaseAttack() {return this.baseAttack;}

    public double getBaseHp() {return this.baseHp;}

    public void setSwap(boolean swapped) { this.swapped = swapped; }

    public CharacterType getType() {
        return this.type;
    }

    public void setHp(double hp) {
        this.baseHp = hp;
    }

    public void setBaseHp(double hp) { this.baseHp = hp; }

    public void setBaseAttack(double attack) {this.baseAttack = attack; }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public boolean isDie(){
        return this.currentHp <= 0;
    }

    public void receiveAttack(CharacterCard attacker){
        double damage = attacker.getAttack();
        if((this.getType() == CharacterType.NETHER && attacker.getType() == CharacterType.OVERWORLD) || (this.getType() == CharacterType.OVERWORLD && attacker.getType() == CharacterType.END) || (this.getType() == CharacterType.END && attacker.getType() == CharacterType.NETHER) ){
            damage *= 0.5;
        }
        else if ((attacker.getType() == CharacterType.NETHER && this.getType() == CharacterType.OVERWORLD) || (attacker.getType() == CharacterType.OVERWORLD && this.getType() == CharacterType.END) || (attacker.getType() == CharacterType.END && this.getType() == CharacterType.NETHER)) {
            damage *= 2;
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
            if (swap.getDuration() > 0) {
                swap.setDuration(swap.getDuration() - 0.5);
            }
        }

        for (SwapSpellCard swap : usedSwap) {
            if (swap.getDuration() <= 0) {
                swap.cancelSpell(this);
            }
        }

        usedSwap.removeIf(swapSpellCard -> swapSpellCard.getDuration() <= 0);


        for (PotionSpellCard potion : usedPotion) {
            if (potion.getDuration() > 0) {
                potion.setDuration(potion.getDuration() - 0.5);
            }
        }

        for (PotionSpellCard potion : usedPotion) {
            if (potion.getDuration() <= 0) {
                potion.cancelSpell(this);
            }
        }

        usedPotion.removeIf(potionSpellCard -> potionSpellCard.getDuration() <= 0);
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

    public double[] pureHpAttack() {
        double pureAtk = this.getAttack();
        double pureHp = this.getHp();
        boolean swapped = this.getSwap();
        if (this.usedSwap.size() != 0) {
            pureAtk = this.getHp();
            pureHp = this.getAttack();
            swapped = !this.getSwap();
//            System.out.println("Diswap");
        }
        for (PotionSpellCard ps : this.usedPotion) {
            // 1.Pemakaian potion tidak dipengaruhi wrap
            if ((!swapped && !ps.getApplyOnSwap()) || (swapped && ps.getApplyOnSwap())) {
                pureAtk -= ps.getAttackChangeValue();
                pureHp -= ps.getHpChangeValue();
            }
                // 2.Pemakaian potion dipengaruhi wrap
            else if ((swapped && !ps.getApplyOnSwap()) || (!swapped && ps.getApplyOnSwap())) {
                pureAtk -= ps.getHpChangeValue();
                pureHp -= ps.getAttackChangeValue();
            }
//            System.out.println("AtcChange: " + ps.getAttackChangeValue());
//            System.out.println("HpChange: " + ps.getHpChangeValue());
        }
        double[] ret = {pureHp, pureAtk};
        return ret;
    }

    public void restoreHp() {
        // new currentHp = additionalHp  current baseHp  -> additional dari potionSpell
        double additionalHp = this.currentHp - this.pureHpAttack()[0];
//        System.out.println("Efek potion:" + additionalHp);
        this.currentHp = additionalHp + this.baseHp;
    }

    public void levelUp() {
        if (this.level < 10) {
            // Naikin Attack
            this.currentAttack += this.attackUp;
            this.baseAttack += this.attackUp;
            // Restore Hp -> Naikin Hp
            this.restoreHp();
            this.currentHp += this.healthUp;
            this.baseHp += this.healthUp;
            // Exp Requirement -> Dibebaskan
            this.exp -= (this.level * 2 - 1);
            this.level++;
        }
        else if (this.level == 10) {
            this.restoreHp();
        }
    }

    public void levelDown() {
        if (this.level > 1) {
            // Cari pure attribute, bisa jadi sama dengan baseAtribut lama atau lebih kecil
            double[] pureAttribute = this.pureHpAttack();
            // Ubah attack
            double additionalAttack = this.currentAttack - pureAttribute[1];
            this.baseAttack -= this.attackUp;
            this.currentAttack = Math.min(this.baseAttack, pureAttribute[1]);
            this.currentAttack += additionalAttack;
            // Ubah Hp
            double additionalHp = this.currentHp - pureAttribute[0];
            this.baseHp -= this.healthUp;
            this.currentHp = Math.min(this.baseHp, pureAttribute[0]);
            this.currentHp += additionalHp;

            this.exp = 0;
            this.level--;
        }
    }

    public void changeCurrAtkHp(double newAtk, double newHp) {
        this.currentAttack = newAtk;
        this.currentHp = newHp;
    }

    public void changeAll(CharacterCard other) {
        this.id = other.id;
        this.name = other.name;
        this.description = other.description;
        this.imagePath = other.imagePath;
        this.manaNeeded = other.manaNeeded;
        this.changeCurrAtkHp(other.currentAttack, other.currentHp);
        this.baseAttack = other.baseAttack;
        this.baseHp = other.baseHp;
        this.attackUp = other.attackUp;
        this.healthUp = other.healthUp;
        this.level = 1;
        this.exp = 0;
        this.type = other.type;
        this.usedPotion = new ArrayList<PotionSpellCard>();
        this.usedSwap = new ArrayList<SwapSpellCard>();
        this.swapped = false;
    }

    public Card clone() {
        CharacterCard cc = new CharacterCard(this.id, this.name, this.description, this.imagePath, this.manaNeeded,
                this.baseAttack, this.baseHp, this.attackUp, this.healthUp, this.level, this.exp, this.type);
        cc.currentAttack = this.currentAttack;
        cc.currentHp = this.currentHp;
        cc.swapped = this.swapped;
        for (SwapSpellCard s : this.usedSwap) {
            cc.usedSwap.add(s);
        }
        for (PotionSpellCard s : this.usedPotion) {
            cc.usedPotion.add(s);
        }
        return cc;
    }

    public void cardInfo() {
        System.out.println("Name: " + this.getName());
        System.out.println("Desc: " + this.getDescription());
        System.out.println("Attack: " + this.getAttack());
        System.out.println("Health: " + this.getHp());
        System.out.println("Exp: " + this.getExp());
        System.out.println("Level: " + this.getLevel());
    }

//    public static void main(String[] args) {
//        CharacterCard c1 = new CharacterCard(1,"C1", "Kartu1", "no image", 1,
//                10, 10,4,4,1, 0, CharacterType.NETHER);
//        c1.cardInfo();
//        System.out.println("____________1____________");
//        System.out.println();
//        CharacterCard c2 = (CharacterCard) c1.clone();
//        c2.cardInfo();
//        System.out.println("____________2____________");
//        System.out.println();
//        c2.currentHp = 2;
//        c2.cardInfo();
//        System.out.println("____________3____________");
//        System.out.println();
//        ppp.useSpell(c2);
//        c2.level= 9;
//        c2.cardInfo();
//        System.out.println("____________4____________");
//        System.out.println();
//        c2.levelUp();
//        c2.cardInfo();
//        System.out.println("____________5____________");
//        System.out.println();
//        ppp.cancelSpell(c2);
//        c2.usedPotion.remove(ppp);
//        c2.cardInfo();
//        System.out.println("____________6____________");
//        System.out.println();
//        c2.levelDown();
//        c2.cardInfo();
//    }
}