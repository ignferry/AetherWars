package com.aetherwars.card;


public class CharacterCard extends Card {
    private int baseAttack;
    private int baseHp;
    private int mana;
    private int attackUp;
    private int healthUp;
    private int level;
    private int exp = 0;
    private CharacterType type;
    // PrivateKey List<SpellCard> usedSpell; belum ada kelas spellcard

    public CharacterCard(int id, String name, String desc, String imagePath, int baseAtk, int baseHp, int mana, int atkUp, int healthUp,
                         int level, int exp, CharacterType type) {
        super(id, name, desc, imagePath);
        this.baseAttack = baseAtk;
        this.baseHp = baseHp;
        this.mana = mana;
        this.attackUp = atkUp;
        this.healthUp = healthUp;
        this.level = level;
        this.addExp(exp);
        this.type = type;
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
        this.baseAttack += this.attackUp;
        this.baseHp += this.healthUp;
        this.exp -= (this.level * 2 - 1);
        this.level++;
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
