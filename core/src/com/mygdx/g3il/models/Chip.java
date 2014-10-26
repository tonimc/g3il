package com.mygdx.g3il.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by toni on 17/06/14.
 */
public class Chip {

    private ChipType type = ChipType.EMPTY;

    public Chip(ChipType type) {
        this.type = type;
    }

    public void setType(ChipType type) {
        this.type = type;
    }

    public ChipType getType() {
        return this.type;
    }

    public boolean equals(Chip obj) {
        return (this.type != ChipType.EMPTY && this.type == obj.getType());
    }

    public boolean isEmpty() {
        return (this.type == ChipType.EMPTY);
    }

    @Override
    public String toString() {
        return "[CHIP] "+getType();
    }
}