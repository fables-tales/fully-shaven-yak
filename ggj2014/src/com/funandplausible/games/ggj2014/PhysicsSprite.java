package com.funandplausible.games.ggj2014;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.funandplausible.games.ggj2014.drawables.Drawable;
import com.funandplausible.games.ggj2014.drawables.SpriteDrawable;

public class PhysicsSprite extends Drawable implements Updateable {

	private SpriteDrawable mDrawable;
	private Body mBody;
	private Fixture mFixture;

	public PhysicsSprite(SpriteDrawable sd, Body body, Fixture fixture) {
		mDrawable = sd;
		mBody = body;
		mFixture = fixture;
	}
	
	public Vector2 position() {
		return mBody.getTransform().getPosition().scl(GameServices.PIXELS_PER_METER);
	}
	
	public Body body() {
		return mBody;
	}
	
	@Override
	public void update() {
		mDrawable.setPosition(
				mBody.getPosition().x*GameServices.PIXELS_PER_METER - mDrawable.getWidth()/2,
				mBody.getPosition().y*GameServices.PIXELS_PER_METER - mDrawable.getHeight()/2);
	}

	@Override
	public void draw(SpriteBatch sb) {
		mDrawable.draw(sb);
	}
	
	@Override
	public int priority() {
		return mDrawable.priority();
	}

	public void setPosition(float x, float y) {
		mBody.setTransform(
				new Vector2(x,y).scl((float) (1.0/GameServices.PIXELS_PER_METER)), 0);
	}

	public void setVelocity(float x, float y) {
		mBody.setLinearVelocity(x/GameServices.PIXELS_PER_METER, y/GameServices.PIXELS_PER_METER);
	}
}
