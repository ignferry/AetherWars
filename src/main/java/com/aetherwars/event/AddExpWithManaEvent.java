package com.aetherwars.event;

import com.aetherwars.gui.FieldCardController;

public class AddExpWithManaEvent implements Event {
    private FieldCardController fieldCardController;

    public AddExpWithManaEvent(FieldCardController fieldCardController) {
        this.fieldCardController = fieldCardController;
    }

    public FieldCardController getFieldCardController() {
        return this.fieldCardController;
    }
}
