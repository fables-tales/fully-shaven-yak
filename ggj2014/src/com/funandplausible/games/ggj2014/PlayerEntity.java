package com.funandplausible.games.ggj2014;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

public class PlayerEntity extends Drawable implements Updateable, HatInteractor {
    private PhysicsSprite mSprite = null;
    private final float mPlayerSpeed;
    private final Stack<Hat> mHats = new Stack<Hat>();
	private AnimationManager mAnimationManager;

    public PlayerEntity() {
        // Define a body for the ball
        Body ballBody;

        // Fixture for the ball
        Fixture ballFixture;
        BodyDef ballBodyDef = new BodyDef();
        ballBodyDef.type = BodyType.DynamicBody;
        ballBodyDef.position.set(0 / GameServices.PIXELS_PER_METER,
                0 / GameServices.PIXELS_PER_METER);
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
        ballFixture.setUserData(this);
        ballBody.setUserData(this);

        Sprite s = GameRoot.services().contentManager().loadSprite("walk_down_0.png");
        s.setBounds(0, 0, 75, 75*1.844f);
        mAnimationManager = new AnimationManager();
        loadAnimation("walk_down",0,1);
        loadAnimation("walk_up",0,1);
        loadAnimation("walk_left",0,1);
        loadAnimation("walk_right",0,1);
        SpriteDrawable sd = new SpriteDrawable(s, 1000);
        mSprite = new PhysicsSprite(sd, ballBody, ballFixture);
        mPlayerSpeed = GameRoot.services().constantManager()
                .getFloat("player_speed");
    }

    private void loadAnimation(String string, int start_frame, int end_frame) {
    	List<Sprite> frames = new ArrayList<Sprite>();
    	for (int i = start_frame; i < end_frame; i++) {
    		Sprite s = GameRoot.services().contentManager().loadSprite(string + "_" + i + ".png");
    		s.setBounds(0, 0, 75, 75*1.844f);
    		frames.add(s);
    	}
    	
    	mAnimationManager.addAnimation(string, frames);
	}

	@Override
    public void update() {
        mSprite.body().setLinearVelocity(inputVector().scl(mPlayerSpeed));
        mSprite.update();
        
        String anim = "walk_down";

        if (inputVector().x < 0) {
        	anim = "walk_left";
        } else if (inputVector().x > 0) {
        	anim = "walk_right";
        }

        if (inputVector().y < 0) {
        	anim = "walk_down";
        } else if (inputVector().y > 0) {
        	anim = "walk_up";
        }
        
        mAnimationManager.startAnimation(anim);
    }

    private float centerY() {
        return position().y;
    }

    private float centerX() {
        return position().x;
    }

    public Vector2 position() {
        return mSprite.position();
    }

    @Override
    public int priority() {
        return mSprite.priority();
    }

    @Override
    public void draw(SpriteBatch sb) {
    	Sprite s = mAnimationManager.nextFrame();
    	s.setPosition(mSprite.position().x, mSprite.position().y);
        s.draw(sb);
    }

    private Vector2 inputVector() {
        return GameRoot.services().inputManager().inputVector();
    }

    public void pushHat(Hat h) {
        if (!mHats.contains(h)) {
            mHats.push(h);
        }
    }

    public List<Hat> popAllHats() {
        List<Hat> build = new ArrayList<Hat>();
        while (!mHats.empty()) {
            build.add(mHats.pop());
        }

        return build;
    }

    @Override
    public int hatCount() {
        return mHats.size();
    }

    @Override
    public void loseInteraction(HatInteractor other) {
        GameRoot.services().scoreBoard().losePoints(10);
    }

    @Override
    public void winInteraction(HatInteractor other) {
        GameRoot.services().scoreBoard().winPoints(10);
        if (other.hatCount() > 0 && mHats.size() < 10) {
            mHats.add(other.getHats().pop());
        }
    }

    @Override
    public Stack<Hat> getHats() {
        return mHats;
    }
}
