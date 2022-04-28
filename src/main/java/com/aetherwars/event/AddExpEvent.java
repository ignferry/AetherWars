package com.aetherwars.event;

import com.aetherwars.gui.FieldCardController;

public class AddExpEvent implements Event {
    private FieldCardController fieldCardController;
    private int amount;

    public AddExpEvent(FieldCardController fieldCardController, int amount) {
        this.fieldCardController = fieldCardController;
        this.amount = amount;
    }

    public FieldCardController getFieldCardController() {
        return this.fieldCardController;
    }

    public int getAmount() {
        return this.amount;
    }
}
