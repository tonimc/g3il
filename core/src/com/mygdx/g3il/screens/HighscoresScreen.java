/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.mygdx.g3il.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.g3il.G3il;
import com.mygdx.g3il.Settings;

public class HighscoresScreen extends G3ilScreen {

    private final Texture background;
    private final Texture arrow;

    private static String GAMES = " Partidas";

    Rectangle backBounds;
	Vector3 touchPoint;
	String[] highScores;
	float xOffset = 0;

	public HighscoresScreen(G3il game) {
		super(game);

        background = new Texture(Gdx.files.internal("texture/background/menu.png"));
        arrow = new Texture(Gdx.files.internal("texture/figure/arrow.png"));


		backBounds = new Rectangle(0, 0, arrow.getWidth()+64, arrow.getWidth()+64);
		touchPoint = new Vector3();
		highScores = new String[Settings.MAX_SCORES];
		for (int i = 0; i < Settings.MAX_SCORES; i++) {
			highScores[i] = i + 1 + ". " + Settings.highscores[i];
			xOffset = Math.max(game.font.getBounds(highScores[i]+GAMES).width, xOffset);
		}
		xOffset = camera.position.x - xOffset / 2 + game.font.getSpaceWidth() / 2;
	}

    @Override
    public void update(float delta) {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (backBounds.contains(touchPoint.x, touchPoint.y)) {
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }
    }

    @Override
    public void draw(float delta) {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        game.batch.end();

        game.batch.enableBlending();
        game.batch.begin();

        float y = Gdx.graphics.getHeight()/100*30;
        for (int i = Settings.MAX_SCORES-1; i >= 0; i--) {
            game.font.draw(game.batch, highScores[i]+GAMES, xOffset, y);
            y += game.font.getLineHeight();
        }

        final String text = "Highs Scores";
        y += game.font.getLineHeight()*2;

        game.font.scale(1);
        game.font.draw(game.batch, text, camera.position.x-game.font.getBounds(text).width/2, y);
        game.font.scale(-1);

        game.batch.draw(arrow, 32, 32);
        game.batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        arrow.dispose();
    }

    @Override
    public void nextScreen() {
        game.setScreen(new MainMenuScreen(game));
        dispose();
    }
}
