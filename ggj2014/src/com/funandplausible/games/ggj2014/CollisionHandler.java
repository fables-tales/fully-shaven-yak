package com.funandplausible.games.ggj2014;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.funandplausible.games.ggj2014.drawables.Hat;

public class CollisionHandler {
    public CollisionHandler() {
        GameRoot.services().world().setContactListener(new CollisionContactListener(this));
    }

    public void checkPlayerHat(Fixture fixtureA, Fixture fixtureB) {
        if (fixtureA.getUserData() instanceof PlayerEntity
                && fixtureB.getUserData() instanceof Hat) {
            Hat h = (Hat) fixtureB.getUserData();
            PlayerEntity p = (PlayerEntity) fixtureA.getUserData();
            h.disarm();
            System.out.println(p.hatCount());
            if (p.hatCount() < 10) {
            	p.pushHat(h);
            }
        }
    }

    public void checkHatInteractors(Fixture fixtureA, Fixture fixtureB) {
        if (fixtureA.getUserData() instanceof HatInteractor
                && fixtureB.getUserData() instanceof HatInteractor) {
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
