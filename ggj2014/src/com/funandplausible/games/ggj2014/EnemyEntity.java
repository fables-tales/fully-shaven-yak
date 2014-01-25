package com.funandplausible.games.ggj2014;

import java.util.List;
import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.funandplausible.games.ggj2014.drawables.Drawable;
import com.funandplausible.games.ggj2014.drawables.Hat;
import com.funandplausible.games.ggj2014.drawables.SpriteDrawable;

public class EnemyEntity extends Drawable implements Updateable, HatInteractor {
	
	private float mX;
	private float mY;
	private float mVelocityX;
	private PhysicsSprite mSprite;
	private Stack<Hat> mHats;

	public EnemyEntity(float initialX, float initialY, float initialVelocityX,
			float bounds, List<Hat> hats) {
		mX = initialX;
		mY = initialY;
		mVelocityX = initialVelocityX;
		mHats = new Stack<Hat>();
		mHats.addAll(hats);
		setupPhysicsSprite();
	}

	private void setupPhysicsSprite() {
		//Define a body for the ball
		Body ballBody;

		//Fixture for the ball
		Fixture ballFixture;
		BodyDef ballBodyDef = new BodyDef();
		ballBodyDef.type = BodyType.DynamicBody;
		ballBodyDef.position.set(mX/GameServices.PIXELS_PER_METER, mY/GameServices.PIXELS_PER_METER);
		ballBodyDef.fixedRotation = true;

		//Define a shape for the ball
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(50/GameServices.PIXELS_PER_METER, 50/GameServices.PIXELS_PER_METER);

		//Define a fixture for the ball
		FixtureDef ballFixtureDef = new FixtureDef();
		ballFixtureDef.shape = ps;
		ballFixtureDef.density = 1;

		//Create a ball
		ballBody = GameRoot.services().world().createBody(ballBodyDef);
		ballFixture = ballBody.createFixture(ballFixtureDef);
		ballFixture.setSensor(true);
		ballFixture.setUserData(this);
		ballBody.setUserData(this);
		
		Sprite s = GameRoot.services().contentManager().loadSprite("bees.png");
		s.setBounds(0, 0, 75, 75);
		s.setColor(1.0f, 0.0f, 1.0f, 1.0f);
		SpriteDrawable sd = new SpriteDrawable(s, 200);
		mSprite = new PhysicsSprite(sd, ballBody, ballFixture);
	}

	@Override
	public void update() {
		int i = 0;
		for (Hat h : mHats) {
			i++;
			h.setPosition(centerX(), centerY()+50+i*15);
		}
		mSprite.setVelocity(mVelocityX, 0);
		mSprite.update();
	}

	private float centerX() {
		return mSprite.position().x;
	}

	private float centerY() {
		return mSprite.position().y;
	}

	@Override
	public int priority() {
		return mSprite.priority();
	}

	@Override
	public void draw(SpriteBatch sb) {
		mSprite.draw(sb);
	}

	@Override
	public int hatCount() {
		return mHats.size();
	}

	@Override
	public void loseInteraction(HatInteractor other) {
		System.out.println("enemy is sad :(");
	}

	@Override
	public void winInteraction(HatInteractor other) {
		System.out.println("enemy is happy");
	}

	@Override
	public Stack<Hat> getHats() {
		return mHats;
	}

}
