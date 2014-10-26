package com.mygdx.g3il;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.g3il.screens.SplashScreen;

public class G3il extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        // Create a full screen sprite renderer and use the above camera
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/arial.fnt"));
        Settings.load();
        this.setScreen(new SplashScreen(this));
    }

    public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
