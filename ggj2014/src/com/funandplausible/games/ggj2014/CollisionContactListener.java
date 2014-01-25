package com.funandplausible.games.ggj2014;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

final class CollisionContactListener implements ContactListener {
    /**
     * 
     */
    private final CollisionHandler mCollisionHandler;

    /**
     * @param collisionHandler
     */
    CollisionContactListener(CollisionHandler collisionHandler) {
        mCollisionHandler = collisionHandler;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        mCollisionHandler.checkPlayerHat(fixtureA, fixtureB);
        mCollisionHandler.checkPlayerHat(fixtureB, fixtureA);
        mCollisionHandler.checkHatInteractors(fixtureA, fixtureB);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // TODO Auto-generated method stub

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // TODO Auto-generated method stub

    }

    @Override
    public void endContact(Contact contact) {
        // TODO Auto-generated method stub

    }
}