package com.funandplausible.games.ggj2014;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.funandplausible.games.ggj2014.drawables.Drawable;
import com.funandplausible.games.ggj2014.drawables.Hat;
import com.funandplausible.games.ggj2014.drawables.SpriteDrawable;

public class EnemyEntity extends Drawable implements Updateable, HatInteractor,
        HatCollector {
    private PhysicsSprite mSprite;
    private final Stack<Hat> mHats;
    private boolean mDead = false;
    private final HatInteractor mPlayer;
    private float mTargetX, mTargetY;
    private int mWaitTicks = 0;
    private final float mBound;
    private String mAnim = "walk_right_bad";
    private final AnimationManager mAnimationManager;

    public EnemyEntity(float initialX, float initialY, float bounds,
            List<Hat> hats, HatInteractor player) {
        mHats = new Stack<Hat>();
        mHats.addAll(hats);
        mPlayer = player;
        setupPhysicsSprite(initialX, initialY);
        mTargetX = initialX;
        mTargetY = initialY;
        mBound = bounds;
        mAnimationManager = new AnimationManager();
        loadAnimation("walk_down_bad", 0, 1);
        loadAnimation("walk_up_bad", 0, 1);
        loadAnimation("walk_left_bad", 0, 1);
        loadAnimation("walk_right_bad", 0, 1);

        pickNewTarget();
    }

    private void loadAnimation(String string, int start_frame, int end_frame) {
        List<Sprite> frames = new ArrayList<Sprite>();
        for (int i = start_frame; i < end_frame; i++) {
            Sprite s = GameRoot.services().contentManager()
                    .loadSprite(string + "_" + i + ".png");
            s.setBounds(0, 0, 75 / 1.844f, 75);
            frames.add(s);
        }

        mAnimationManager.addAnimation(string, frames);
    }

    private void setupPhysicsSprite(float x, float y) {
        // Define a body for the ball
        Body ballBody;

        // Fixture for the ball
        Fixture ballFixture;
        BodyDef ballBodyDef = new BodyDef();
        ballBodyDef.type = BodyType.DynamicBody;
        ballBodyDef.position.set(x / GameServices.PIXELS_PER_METER, y
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
    }

    @Override
    public void update() {
        int i = 0;
        for (Hat h : mHats) {
            i++;
            h.setPosition(centerX(), centerY() + 50 + i * 15);
        }

        Vector2 vel = mSprite.body().getLinearVelocity();

        if (vel.x < 0) {
            mAnim = "walk_left_bad";
        } else if (vel.x > 0) {
            mAnim = "walk_right_bad";
        }

        if (vel.y < 0) {
            mAnim = "walk_down_bad";
        } else if (vel.y > 0) {
            mAnim = "walk_up_bad";
        }

        mAnimationManager.startAnimation(mAnim);

        if (mWaitTicks > 0) {
            if (mWaitTicks == 1) {
                pickNewTarget();
            }
            mSprite.setVelocity(0, 0);
            --mWaitTicks;
        } else {
            final float speed = 20.0f;
            float dx = mTargetX - centerX();
            float dy = mTargetY - centerY();
            float size = (float) Math.hypot(dx, dy);
            if (size < 2.0f) {
                mWaitTicks = 90;
            } else {
                mSprite.setVelocity(speed * dx / size, speed * dy / size);
            }
        }
        mSprite.update();
    }

    private void pickNewTarget() {
        Random rng = new Random();
        mTargetX = mBound * (2 * rng.nextFloat() - 1);
        mTargetY = mBound * (2 * rng.nextFloat() - 1);
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
        Sprite sprite = mAnimationManager.nextFrame();
        sprite.setPosition(mSprite.position().x - 75 / (2 * 1.844f),
                mSprite.position().y - 75 / 2);
        sprite.setColor(playerHatDeltaColor());
        sprite.draw(sb);
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
    public boolean isNPC() {
        return true;
    }

    @Override
    public Stack<Hat> getHats() {
        return mHats;
    }

    public Body body() {
        return mSprite.body();
    }

    @Override
    public void receiveHat(Hat hat) {
        mHats.push(hat);
    }

}
