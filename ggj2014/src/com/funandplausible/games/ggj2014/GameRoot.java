package com.funandplausible.games.ggj2014;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.funandplausible.games.ggj2014.drawables.Drawable;
import com.funandplausible.games.ggj2014.drawables.SpriteDrawable;

public class GameRoot implements ApplicationListener {

	private static GameServices sServices;
	private static final int STATE_MAIN = 0x01;

	public static GameServices services() {
		return sServices;
	}

	private SortedSet<Drawable> mDrawables = new TreeSet<Drawable>();
	private Set<Updateable> mUpdateables = new HashSet<Updateable>();
	private int mState = STATE_MAIN;
	private Box2DDebugRenderer mDebugRenderer;

	@Override
	public void create() {		
		GameRoot.sServices = new GameServices();

		createPhysicsWorld();
		mDebugRenderer = new Box2DDebugRenderer();
	}

	@Override
	public void dispose() {
		mainSpriteBatch().dispose();
	}

	@Override
	public void render() {		
		if (mState == STATE_MAIN) {
			updateMain();
			clear();
			drawMain();
		}
	}

	private void updateMain() {
		services().world().step((float) (60/1000.0), 3, 3);

		for (Updateable u : mUpdateables) {
			u.update();
		}
	}

	private void clear() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}

	private void drawMain() {
		mainSpriteBatch().setProjectionMatrix(cameraMatrix());
		mainSpriteBatch().begin();
		for (Drawable d : mDrawables) {
			d.draw(mainSpriteBatch());
		}
		mainSpriteBatch().end();
		if (services().constantManager().getBoolean("debug_physics")) {
			mDebugRenderer.render(
					services().world(),
					services().camera().combined.cpy().scale(
							GameServices.PIXELS_PER_METER,
							GameServices.PIXELS_PER_METER, 1));
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	private SpriteBatch mainSpriteBatch() {
		return services().mainSpriteBatch();
	}

	private Matrix4 cameraMatrix() {
		return services().camera().combined;
	}

	private ContentManager contentManager() {
		return services().contentManager();
	}

	private void createPhysicsWorld() {
		//Define a body for the ball
		Body ballBody;

		//Fixture for the ball
		Fixture ballFixture;
		BodyDef ballBodyDef = new BodyDef();
		ballBodyDef.type = BodyType.DynamicBody;
		ballBodyDef.position.set(100/GameServices.PIXELS_PER_METER, 100/GameServices.PIXELS_PER_METER);

		//Define a shape for the ball
		CircleShape ballShape = new CircleShape();
		ballShape.setRadius(100/GameServices.PIXELS_PER_METER/2);

		//Define a fixture for the ball
		FixtureDef ballFixtureDef = new FixtureDef();
		ballFixtureDef.shape = ballShape;
		ballFixtureDef.density = 1;

		//Create a ball
		ballBody = services().world().createBody(ballBodyDef);
		ballFixture = ballBody.createFixture(ballFixtureDef);
		Sprite s = contentManager().loadSprite("bees.png");
		s.setBounds(0, 0, 100, 100);
		SpriteDrawable sd = new SpriteDrawable(s, 0);
		PhysicsSprite ps = new PhysicsSprite(sd, ballBody, ballFixture);
		mUpdateables.add(ps);
		mDrawables.add(ps);
	}

}
