package com.mygdx.g3il.renders;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by toni on 23/06/14.
 */
public interface Render<T> {
    public void draw(SpriteBatch batch, T... params);
}
