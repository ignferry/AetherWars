package com.aetherwars.event;

import com.aetherwars.gui.FieldCardController;

public class ClickFieldCardEvent implements Event {
    private FieldCardController fieldCardController;

    public ClickFieldCardEvent(FieldCardController fieldCardController) {
        this.fieldCardController = fieldCardController;
    }

    public FieldCardController getFieldCardController() {
        return this.fieldCardController;
    }
}
