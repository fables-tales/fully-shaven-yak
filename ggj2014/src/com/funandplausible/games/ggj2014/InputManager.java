package com.funandplausible.games.ggj2014;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class InputManager {

	public Vector2 inputVector() {
		return new Vector2(computeXInput(), computeYInput()).nor();
	}

	private float computeYInput() {
		if (Gdx.input.isKeyPressed(Keys.S)) {
			return -1;
		} else if (Gdx.input.isKeyPressed(Keys.W)) {
			return 1;
		} else {
			return 0;
		}
	}

	private float computeXInput() {
		if (Gdx.input.isKeyPressed(Keys.A)) {
			return -1;
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			return 1;
		} else {
			return 0;
		}
	}
}
