package com.funandplausible.games.ggj2014;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class InputManager {

    public Vector2 inputVector() {
        // TODO: investigate the divide-by-zero case here
        return new Vector2(computeXInput(), computeYInput()).nor();
    }

    private float computeYInput() {
        if (goDown()) {
            return -1;
        } else if (goUp()) {
            return 1;
        } else {
            return 0;
        }
    }

	private boolean goUp() {
		return Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP);
	}

	private boolean goDown() {
		return Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN);
	}

    private float computeXInput() {
        if (goLeft()) {
            return -1;
        } else if (goRight()) {
            return 1;
        } else {
            return 0;
        }
    }

	private boolean goRight() {
		return Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT);
	}

	private boolean goLeft() {
		return Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT);
	}
}
