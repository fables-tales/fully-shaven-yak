package com.funandplausible.games.ggj2014;

import java.util.List;

import com.funandplausible.games.ggj2014.drawables.Hat;

public class HatDistributor {
	private float mHatSpawnSize;
	public HatDistributor() {
		mHatSpawnSize = GameRoot.services().constantManager().getInt("hat_spawn_size");
	}

	public List<Hat> distributeHats(List<Hat> hats) {
		for (Hat h : hats) {
			float x = GameRoot.services().random().nextFloat()*mHatSpawnSize - mHatSpawnSize/2;
			float y = GameRoot.services().random().nextFloat()*mHatSpawnSize - mHatSpawnSize/2;
			h.setPosition(x, y);
		}

		return hats;
	}
}
