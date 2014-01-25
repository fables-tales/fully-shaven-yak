package com.funandplausible.games.ggj2014;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BoundaryCreator {
	private World mWorld;
	private int mBoundSize;

	public BoundaryCreator() {
		mWorld = GameRoot.services().world();
		mBoundSize = GameRoot.services().constantManager().getInt("enemy_bounds");
		createLeftVerticalWall();
		createRightVerticalWall();
		createTopHorizontalWall();
		createBottomHorizontalWall();
	}

	private void createBottomHorizontalWall() {
		createWall(0, -mBoundSize, mBoundSize, 40);
	}


	private void createTopHorizontalWall() {
		createWall(0, mBoundSize, mBoundSize, 40);
	}

	private void createRightVerticalWall() {
		createWall(mBoundSize, 0, 40, mBoundSize);
	}

	private void createLeftVerticalWall() {
		createWall(-mBoundSize, 0, 40, mBoundSize);
	}

	private void createWall(float x, float y, float width, float height) {
        // Define a body for the ball
        Body ballBody;

        // Fixture for the ball
        BodyDef ballBodyDef = new BodyDef();
        ballBodyDef.type = BodyType.StaticBody;
        ballBodyDef.position.set(x / GameServices.PIXELS_PER_METER,
                y / GameServices.PIXELS_PER_METER);
        ballBodyDef.fixedRotation = true;

        // Define a shape for the ball
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width / GameServices.PIXELS_PER_METER,
                height / GameServices.PIXELS_PER_METER);

        // Define a fixture for the ball
        FixtureDef ballFixtureDef = new FixtureDef();
        ballFixtureDef.shape = ps;
        ballFixtureDef.density = 1;

        // Create a ball
        ballBody = mWorld.createBody(ballBodyDef);
        ballBody.createFixture(ballFixtureDef);
	}
}
