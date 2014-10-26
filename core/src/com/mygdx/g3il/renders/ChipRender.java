package com.mygdx.g3il.renders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.g3il.models.Chip;
import com.mygdx.g3il.models.ChipType;

/**
 * Created by toni on 23/06/14.
 */
public class ChipRender implements Render<Object> {

    private static Texture CHIPE_WHITE = new Texture("texture/figure/white.png");
    private static Texture CHIPE_BLACK = new Texture("texture/figure/black.png");

    private static ChipRender instance;

    public ChipRender() {
    }

    public static ChipRender getInstance() {
        if(instance==null) {
           instance = new ChipRender();
        }
        return instance;
    }

    private Texture getImage(Chip chip) {
        return (chip.getType()==ChipType.WHITE)?CHIPE_WHITE:CHIPE_BLACK;
    }

    private Vector2 getPosition(Chip chip, Vector2 position) {
        Vector2 chipPos = new Vector2();
        chipPos.x = position.x - getImage(chip).getWidth()/2;
        chipPos.y = position.y - getImage(chip).getHeight()/2;
        return chipPos;
    }

    @Override
    public void draw(SpriteBatch batch, Object... params) {
        Chip chip = (Chip) params[0];
        Vector2 position = (Vector2) params[1];
        Vector2 chipPos = getPosition(chip, position);
        batch.draw(getImage(chip), chipPos.x, chipPos.y);
    }
}
