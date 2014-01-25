package com.funandplausible.games.ggj2014.drawables;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.funandplausible.games.ggj2014.GameRoot;
import com.funandplausible.games.ggj2014.GameServices;
import com.funandplausible.games.ggj2014.PhysicsSprite;
import com.funandplausible.games.ggj2014.Updateable;

public class Hat extends Drawable implements Updateable {
	
	private PhysicsSprite mSprite;
	private Fixture mFixture;
	
	public Hat(Sprite s) {
		//Define a body for the ball
		Body ballBody;

		//Fixture for the ball
		Fixture ballFixture;
		BodyDef ballBodyDef = new BodyDef();
		ballBodyDef.type = BodyType.StaticBody;
		//Define a shape for the ball
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(5.0f/GameServices.PIXELS_PER_METER, 5.0f/GameServices.PIXELS_PER_METER);

		//Define a fixture for the ball
		FixtureDef ballFixtureDef = new FixtureDef();
		ballFixtureDef.shape = ps;
		ballFixtureDef.density = 1;

		//Create a ball
		ballBody = GameRoot.services().world().createBody(ballBodyDef);
		ballFixture = ballBody.createFixture(ballFixtureDef);
		SpriteDrawable sd = new SpriteDrawable(s, 10);
		mSprite = new PhysicsSprite(sd, ballBody, ballFixture);
		ballFixture.setUserData(this);
		ballBody.setUserData(this);
		ballBody.setTransform(new Vector2(10000, 10000), 0);
		mFixture = ballFixture;
	}
	
	public void disarm() {
		mFixture.setUserData(null);
		mFixture.setSensor(true);
	}

	public void setPosition(float x, float y) {
		mSprite.setPosition(x, y);
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
	public void update() {
		mSprite.update();
	}

	public void rearm() {
		mFixture.setUserData(this);
		mFixture.setSensor(false);
	}

	public Vector2 position() {
		return mSprite.position();
	}

}
