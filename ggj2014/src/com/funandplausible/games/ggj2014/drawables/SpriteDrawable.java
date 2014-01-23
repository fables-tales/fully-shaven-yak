package com.funandplausible.games.ggj2014.drawables;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteDrawable extends Drawable {
	
	private Sprite mSprite;
	
	public SpriteDrawable(Sprite s, int priority) {
		super(priority);
		mSprite = s;
	}

	@Override
	public void draw(SpriteBatch sb) {
		mSprite.draw(sb);
	}

	public void setPosition(float x, float y) {
		mSprite.setPosition(x, y);
	}

}
