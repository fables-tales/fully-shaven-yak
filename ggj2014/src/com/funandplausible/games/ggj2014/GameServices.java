package com.funandplausible.games.ggj2014;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameServices {
	private Camera mCamera = null;
	private SpriteBatch mMainSpriteBatch = null;
	private ContentManager mContentManager = null;
	private World mWorld = null;
	private ConstantManager mConstantManager = null;
	
	public static final Vector2 GRAVITY_VECTOR = new Vector2(0.0f, 0.1f);
	public static final int PIXELS_PER_METER = 32;

	public GameServices() {
		mCamera = makeCamera();
		mMainSpriteBatch = makeSpriteBatch();
		mContentManager = makeContentManager();
		mConstantManager = makeConstantManager();
		mWorld = makeWorld();
	}
	
	public World world() {
		return mWorld;
	}
	
	public Camera camera() {
		return mCamera;
	}
	
	public SpriteBatch mainSpriteBatch() {
		return mMainSpriteBatch;
	}
	
	public ContentManager contentManager() {
		return mContentManager;
	}
	
	public ConstantManager constantManager() {
		return mConstantManager;
	}
	
	public float screenWidth() {
		return Gdx.graphics.getWidth();
	}
	public float screenHeight() {
		return Gdx.graphics.getHeight();
	}
	
	private Camera makeCamera() {
		return new OrthographicCamera(screenWidth(), screenHeight());
	}
	
	private SpriteBatch makeSpriteBatch() {
		return new SpriteBatch();
	}
	
	private ContentManager makeContentManager() {
		return new ContentManager();
	}
	
	private World makeWorld() {
		return new World(GRAVITY_VECTOR, true);
	}
	
	private ConstantManager makeConstantManager() {
		return new ConstantManager(Gdx.files.internal("constants.txt"));
	}

}