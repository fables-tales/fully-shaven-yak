package com.funandplausible.games.ggj2014;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.funandplausible.games.ggj2014.drawables.Hat;

public class CollisionHandler {
    public CollisionHandler() {
        GameRoot.services().world().setContactListener(new CollisionContactListener(this));
    }

    public void checkPlayerHat(Fixture fixtureA, Fixture fixtureB) {
        if (fixtureA.getUserData() instanceof HatCollector
                && fixtureB.getUserData() instanceof Hat) {
            Hat h = (Hat) fixtureB.getUserData();
            HatCollector c = (HatCollector) fixtureA.getUserData();
            h.disarm();
            c.receiveHat(h);
        }
        if (fixtureA.getUserData() instanceof Hat && fixtureB.getUserData() instanceof Hat) {
        	Hat h = (Hat) fixtureA.getUserData();
            HatCollector c = (HatCollector) fixtureB.getUserData();
            h.disarm();
            c.receiveHat(h);
        }
    }

    public void checkHatInteractors(Fixture fixtureA, Fixture fixtureB) {
        if (fixtureA.getUserData() instanceof HatInteractor
                && fixtureB.getUserData() instanceof HatInteractor) {
            HatInteractor a = (HatInteractor) fixtureA.getUserData();
            HatInteractor b = (HatInteractor) fixtureB.getUserData();

            if (a.hatCount() < b.hatCount()) {
                a.winInteraction(b);
                b.loseInteraction(a);
            } else if (b.hatCount() < a.hatCount()) {
                b.winInteraction(a);
                a.loseInteraction(b);
            }
        }
    }

}
