package com.funandplausible.games.ggj2014;

import java.util.List;
import java.util.Stack;

import com.badlogic.gdx.graphics.Color;
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

    private final float mX;
    private final float mY;
    private final float mVelocityX;
    private PhysicsSprite mSprite;
    private final Stack<Hat> mHats;
    private boolean mDead = false;
    private Sprite mGDXSprite;
    private final HatInteractor mPlayer;

    public EnemyEntity(float initialX, float initialY, float initialVelocityX,
            float bounds, List<Hat> hats, HatInteractor player) {
        mX = initialX;
        mY = initialY;
        mVelocityX = initialVelocityX;
        mHats = new Stack<Hat>();
        mHats.addAll(hats);
        mPlayer = player;
        setupPhysicsSprite();
    }

    private void setupPhysicsSprite() {
        // Define a body for the ball
        Body ballBody;

        // Fixture for the ball
        Fixture ballFixture;
        BodyDef ballBodyDef = new BodyDef();
        ballBodyDef.type = BodyType.DynamicBody;
        ballBodyDef.position.set(mX / GameServices.PIXELS_PER_METER, mY
                / GameServices.PIXELS_PER_METER);
        ballBodyDef.fixedRotation = true;

        // Define a shape for the ball
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(50 / GameServices.PIXELS_PER_METER,
                50 / GameServices.PIXELS_PER_METER);

        // Define a fixture for the ball
        FixtureDef ballFixtureDef = new FixtureDef();
        ballFixtureDef.shape = ps;
        ballFixtureDef.density = 1;

        // Create a ball
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
        mGDXSprite = s;
    }

    @Override
    public void update() {
        int i = 0;
        for (Hat h : mHats) {
            i++;
            h.setPosition(centerX(), centerY() + 50 + i * 15);
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
        mGDXSprite.setColor(playerHatDeltaColor());
        mSprite.draw(sb);
    }

    private Color playerHatDeltaColor() {
        if (hatCount() < mPlayer.hatCount()) {
            return Color.WHITE;
        } else {
            float whiteStop = 0;
            float redStop = 10;

            float delta = hatCount() - mPlayer.hatCount();
            float alongness = delta / (redStop - whiteStop);
            float r = 1.0f;
            float g = 1.0f - alongness;
            float b = 1.0f - alongness;
            return new Color(r, g, b, 1.0f);
        }
    }

    public int playerHatCount() {
        return mPlayer.hatCount();
    }

    @Override
    public int hatCount() {
        return mHats.size();
    }

    @Override
    public void loseInteraction(HatInteractor other) {
        if (mHats.empty()) {
            die();
        }
    }

    private void die() {
        mDead = true;
    }

    public boolean dead() {
        return mDead;
    }

    @Override
    public void winInteraction(HatInteractor other) {
        if (other.hatCount() > 0) {
            mHats.push(other.getHats().pop());
        }
    }

    @Override
    public Stack<Hat> getHats() {
        return mHats;
    }

    public Body body() {
        return mSprite.body();
    }

}
