package com.funandplausible.games.ggj2014;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.funandplausible.games.ggj2014.drawables.Drawable;
import com.funandplausible.games.ggj2014.drawables.Hat;
import com.funandplausible.games.ggj2014.drawables.SpriteDrawable;

public class GameRoot implements ApplicationListener {

	private static GameServices sServices;
	private static final int STATE_MAIN = 0x01;

	public static GameServices services() {
		return sServices;
	}

	private List<Drawable> mDrawables;
	private Set<Updateable> mUpdateables; 
	private int mState;
	private Box2DDebugRenderer mDebugRenderer;
	private PlayerEntity mPlayer;

	@Override
	public void create() {		
		GameRoot.sServices = new GameServices();
		mDrawables = new ArrayList<Drawable>();
		mUpdateables = new HashSet<Updateable>();
		mState = STATE_MAIN;

		createPlayer();
		createBackground();
		createHats();
		createListener();

		mDebugRenderer = new Box2DDebugRenderer();
	}

	private void createPlayer() {
		mPlayer = new PlayerEntity();
		mDrawables.add(mPlayer);
		mUpdateables.add(mPlayer);
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
		Vector2 oldPlayerPosition = new Vector2(mPlayer.position());
		services().world().step((float) (60/1000.0), 3, 3);

		for (Updateable u : mUpdateables) {
			u.update();
		}
		
		Vector2 playerPosition = mPlayer.position();
		
		camera().translate(playerPosition.x-oldPlayerPosition.x, playerPosition.y-oldPlayerPosition.y, 0);
		camera().update();
		
	}

	private void clear() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}

	private void drawMain() {
		mainSpriteBatch().setProjectionMatrix(cameraMatrix());
		mainSpriteBatch().begin();
		Collections.sort(mDrawables);
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
	
	private Camera camera() {
		return services().camera();
	}

	private Matrix4 cameraMatrix() {
		return services().camera().combined;
	}

	private void createBackground() {
		SpriteDrawable sd = new SpriteDrawable(services().contentManager().loadSprite("bees.png"), -1000);
		mDrawables.add(sd);
	}

	private void createHats() {
		HatDistributor hd = new HatDistributor();
		HatGenerator hg = new HatGenerator();
		for (Hat h : hd.distributeHats(hg.generateHats(constants().getInt("initial_hats")))) {
			mDrawables.add(h);
			mUpdateables.add(h);
		}
	}

	private ConstantManager constants() {
		return GameRoot.services().constantManager();
	}

	private void createListener() {
		new CollisionHandler();
	}

}
