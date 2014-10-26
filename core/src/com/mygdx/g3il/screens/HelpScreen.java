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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.g3il.G3il;

public class HelpScreen extends G3ilScreen {

    private final Texture board;
    private final Texture background;

	public HelpScreen (G3il game) {
		super(game);
        board = new Texture(Gdx.files.internal("texture/figure/board.png"));
        background = new Texture(Gdx.files.internal("texture/background/game.png"));
	}

    @Override
    public void update(float delta) {
        if (Gdx.input.justTouched()) {
            nextScreen();
        }
    }

    @Override
    public void draw(float delta) {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();

        game.batch.enableBlending();
        game.batch.begin();
        game.batch.draw(board, camera.position.x, camera.position.y);
        game.batch.end();
    }

    @Override
    public void nextScreen() {
        game.setScreen(new MainMenuScreen(game));
        dispose();
    }

    @Override
    public void dispose() {
        background.dispose();
        board.dispose();
    }
}
