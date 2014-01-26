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
            h.setBound();
            c.receiveHat(h);
        }
        if (fixtureA.getUserData() instanceof Hat && fixtureB.getUserData() instanceof Hat) {
        	Hat h = (Hat) fixtureA.getUserData();
            HatCollector c = (HatCollector) fixtureB.getUserData();
            h.setBound();
            c.receiveHat(h);
        }
    }

    public void checkHatInteractors(Fixture fixtureA, Fixture fixtureB) {
        if (fixtureA.getUserData() instanceof HatInteractor
                && fixtureB.getUserData() instanceof HatInteractor) {
            HatInteractor a = (HatInteractor) fixtureA.getUserData();
            HatInteractor b = (HatInteractor) fixtureB.getUserData();
            
            if (a.isNPC() && b.isNPC()) {
            	return;
            }

            int hatCountA = a.hatCount();
			int hatCountB = b.hatCount();
			if (hatCountA < hatCountB) {
                a.winInteraction(b);
                b.loseInteraction(a);
            } else if (hatCountB < hatCountA) {
                b.winInteraction(a);
                a.loseInteraction(b);
            } else if (hatCountA == hatCountB && a.hatCount() > 0) {
            	GameRoot.services().soundManager().play("grumble");
            	Hat aHat = a.getHats().pop();
            	Hat bHat = b.getHats().pop();
            	a.getHats().push(bHat);
            	b.getHats().push(aHat);
            }
        }
    }

}
