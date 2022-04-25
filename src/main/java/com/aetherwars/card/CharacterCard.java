package com.aetherwars.card;


import java.util.List;
import java.util.ArrayList;

public class CharacterCard extends Card {
    private int baseAttack;
    private int baseHp;
    private int mana;
    private int attackUp;
    private int healthUp;
    private int level;
    private int exp = 0;
    private CharacterType type;
    private List<SpellCard> usedSpell;

    public CharacterCard(int id, String name, String desc, String imagePath, int baseAtk, int baseHp, int mana, int atkUp, int healthUp,
                         int level, int exp, CharacterType type) {
        super(id, name, desc, imagePath, mana);
        this.baseAttack = baseAtk;
        this.baseHp = baseHp;
        this.attackUp = atkUp;
        this.healthUp = healthUp;
        this.level = level;
        this.addExp(exp);
        this.type = type;
        this.usedSpell = new ArrayList<SpellCard>();
    }

    public String getName() {
        return this.name;
    }

    public String getDesc() {
        return this.description;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public int getHp() {
        return this.baseHp;
    }

    public int getAttack() {
        return this.baseAttack;
    }

    public int getLevel() {
        return this.level;
    }

    public int getExp() {
        return this.exp;
    }

    public void inflictSpellEffects() {

    }

    public void addSpell() {

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

    public void changeAtkHp(int newAtk, int newHp) {
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
        this.mana = other.mana;
        this.attackUp = other.attackUp;
        this.healthUp = other.healthUp;
        this.level = 1;
        this.exp = 0;
        this.type = other.type;
        this.usedSpell = new ArrayList<SpellCard>();
    }

    public Card clone(Card c) {
        return c;
    }

    public void cardInfo() {
        System.out.println("Name: " + this.getName());
        System.out.println("Desc: " + this.getDesc());
        System.out.println("Attack: " + this.getAttack());
        System.out.println("Health: " + this.getHp());
        System.out.println("Exp: " + this.getExp());
        System.out.println("Level: " + this.getLevel());
    }

    public static void main(String[] args) {
        CharacterCard card = new CharacterCard(1,"Agumon", "Ini sebenernya digimon", "google.com", 10, 100, 4, 10, 10, 1,
                6, CharacterType.END);
        card.cardInfo();
    }
}
