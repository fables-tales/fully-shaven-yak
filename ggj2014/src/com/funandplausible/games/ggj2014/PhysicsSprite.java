package com.funandplausible.games.ggj2014;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.funandplausible.games.ggj2014.drawables.Drawable;
import com.funandplausible.games.ggj2014.drawables.SpriteDrawable;

public class PhysicsSprite extends Drawable implements Updateable {

	private SpriteDrawable mDrawable;
	private Body mBody;
	private Fixture mFixture;

	public PhysicsSprite(SpriteDrawable sd, Body body, Fixture fixture) {
		super(sd.priority());
		mDrawable = sd;
		mBody = body;
		mFixture = fixture;
	}
	
	@Override
	public void update() {
		mDrawable.setPosition(mBody.getPosition().x*GameServices.PIXELS_PER_METER, mBody.getPosition().y*GameServices.PIXELS_PER_METER);
	}

	@Override
	public void draw(SpriteBatch sb) {
		mDrawable.draw(sb);
	}
	
	@Override
	public int priority() {
		return mDrawable.priority();
	}
}
