package com.mygdx.g3il.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.g3il.G3il;

/**
 * Created by toni on 21/06/14.
 */
public class SplashScreen extends G3ilScreen {

    /** the background texture **/
    private final Texture background;
    private final Texture logoLibGDX;
    private final long startTime;
    private static long TIME_SPLASH = 2000;

    public SplashScreen(G3il game) {
        super(game);
        background = new Texture(Gdx.files.internal("texture/background/splash.png"));
        logoLibGDX = new Texture(Gdx.files.internal("texture/logo/libgdx.png"), true);
        startTime = TimeUtils.millis();
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.justTouched() || (TimeUtils.millis()-startTime>TIME_SPLASH)) {
            nextScreen();
        }
    }

    @Override
    public void draw(float delta)
    {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(logoLibGDX, Gdx.graphics.getWidth()-logoLibGDX.getWidth()-50, Gdx.graphics.getHeight()/100*5);
        game.batch.end();
    }

    @Override
    public void nextScreen() {
        game.setScreen(new MainMenuScreen(game));
        dispose();
    }

    @Override
    public void dispose () {
        background.dispose();
        logoLibGDX.dispose();
    }

}