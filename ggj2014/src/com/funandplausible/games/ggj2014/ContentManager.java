package com.funandplausible.games.ggj2014;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ContentManager {

	public Sprite loadSprite(String fileName) {
		Texture t = new Texture(Gdx.files.internal("assets/" + fileName));
		return new Sprite(t);
	}
}
