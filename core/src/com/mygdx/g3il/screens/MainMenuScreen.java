/*
 * Copyright 2010 Mario Zechner (contact@badlogicgames.com), Nathan Sweet (admin@esotericsoftware.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.mygdx.g3il.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.g3il.G3il;


public class MainMenuScreen extends G3ilScreen {

	/** the background texture **/
	private final Texture background;
	/** the logo texture **/
	private final Texture logo;

    private Rectangle playBounds;
    private Rectangle highscoresBounds;
    private Rectangle helpBounds;
    private Vector3 touchPoint;

	public MainMenuScreen(G3il game) {
        super(game);
		background = new Texture(Gdx.files.internal("texture/background/menu.png"));
		background.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		logo = new Texture(Gdx.files.internal("texture/logo/libgdx.png"));

        final int width = Gdx.graphics.getWidth()/100*90;
        final int height = Gdx.graphics.getHeight()/100*25;

        playBounds = new Rectangle(camera.position.x-width/2, Gdx.graphics.getHeight()/100*55, width, height);
        highscoresBounds = new Rectangle(camera.position.x-width/2, Gdx.graphics.getHeight()/100*20, width, height);
        //helpBounds = new Rectangle(camera.position.x-width/2, Gdx.graphics.getHeight()/100*30, width, height);

        touchPoint = new Vector3();

        game.font.scale(1);
	}

	@Override
	public void update (float delta) {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (playBounds.contains(touchPoint.x, touchPoint.y)) {
                nextScreen(new GameScreen(game));
                Gdx.app.log("[MAIN SCREEN]", "Touch in play");
                return;
            }
            if (highscoresBounds.contains(touchPoint.x, touchPoint.y)) {
                nextScreen(new HighscoresScreen(game));
                Gdx.app.log("[MAIN SCREEN]", "Touch in Highscores");
                return;
            }
//            if (helpBounds.contains(touchPoint.x, touchPoint.y)) {
//                nextScreen(new HelpScreen(game));
//                return;
//            }
        }
	}

	@Override
	public void draw (float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.rect(playBounds.x, playBounds.y, playBounds.getWidth(), playBounds.getHeight());
        shapeRenderer.rect(highscoresBounds.x, highscoresBounds.y, highscoresBounds.getWidth(), highscoresBounds.getHeight());
        shapeRenderer.end();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.setColor(Color.WHITE);
        game.batch.begin();
        game.batch.disableBlending();
        game.batch.draw(background, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.batch.end();

        game.batch.enableBlending();
        game.batch.begin();
        final Vector2 position = new Vector2();
        drawText("Play", playBounds.getCenter(position));
        drawText("High Scores", highscoresBounds.getCenter(position));
        //drawText("Help", helpBounds.getCenter(position));
        game.batch.end();
	}

    private void drawText(final String text, final Vector2 position) {
        final float width = game.font.getBounds(text).width;
        final float height = game.font.getBounds(text).height;
        game.font.draw(game.batch, text, position.x-width/2, position.y+height/2);
    }

    @Override
    public void nextScreen() {
        nextScreen(new MainMenuScreen(game));
    }

    @Override
	public void dispose () {
        game.font.scale(-1);
		background.dispose();
		logo.dispose();
	}
}
