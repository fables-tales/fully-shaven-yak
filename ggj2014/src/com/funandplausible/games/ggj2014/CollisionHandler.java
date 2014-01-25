package com.funandplausible.games.ggj2014;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.funandplausible.games.ggj2014.drawables.Hat;

public class CollisionHandler {
	public CollisionHandler() {
		GameRoot.services().world().setContactListener(new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				Fixture fixtureA = contact.getFixtureA();
				Fixture fixtureB = contact.getFixtureB();
				checkPlayerHat(fixtureA, fixtureB);
				checkHatInteractors(fixtureA, fixtureB);
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
		});
	}

	private void checkPlayerHat(Fixture fixtureA, Fixture fixtureB) {
		if (fixtureA.getUserData() instanceof PlayerEntity && fixtureB.getUserData() instanceof Hat) {
			Hat h = (Hat) fixtureB.getUserData();
			PlayerEntity p = (PlayerEntity) fixtureA.getUserData();
			h.disarm();
			p.pushHat(h);
		}
	}

	private void checkHatInteractors(Fixture fixtureA, Fixture fixtureB) {
		if (fixtureA.getUserData() instanceof HatInteractor && fixtureB.getUserData() instanceof HatInteractor) {
			HatInteractor a = (HatInteractor) fixtureA.getUserData();
			HatInteractor b = (HatInteractor) fixtureB.getUserData();
			
			if (a.hatCount() > b.hatCount()) {
				a.winInteraction(b);
				b.loseInteraction(a);
			} else if (b.hatCount() > a.hatCount()) {
				b.winInteraction(a);
				a.loseInteraction(b);
			}
		}
	}

}
