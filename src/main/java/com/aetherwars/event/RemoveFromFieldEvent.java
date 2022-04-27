package com.aetherwars.event;

import com.aetherwars.gui.FieldCardController;

public class RemoveFromFieldEvent implements Event{
    private FieldCardController fieldCardController;

    public RemoveFromFieldEvent(FieldCardController fieldCardController) {
        this.fieldCardController = fieldCardController;
    }

    public FieldCardController getFieldCardController() {
        return this.fieldCardController;
    }
}
