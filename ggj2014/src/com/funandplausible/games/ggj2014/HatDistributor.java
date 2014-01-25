package com.funandplausible.games.ggj2014;

import java.util.List;

import com.funandplausible.games.ggj2014.drawables.Hat;

public class HatDistributor {
	private float mHatSpawnSize;
	public HatDistributor(ConstantManager cm) {
		mHatSpawnSize = cm.getInt("hat_spawn_size");
	}

	public List<Hat> distributeHats(List<Hat> hats) {
		for (Hat h : hats) {
			float x = GameRoot.services().random().nextFloat()*mHatSpawnSize - mHatSpawnSize/2;
			while (Math.abs(x) < 100) {
				x = GameRoot.services().random().nextFloat()*mHatSpawnSize - mHatSpawnSize/2;
			}
			float y = GameRoot.services().random().nextFloat()*mHatSpawnSize - mHatSpawnSize/2;
			while (Math.abs(y) < 100) {
				y = GameRoot.services().random().nextFloat()*mHatSpawnSize - mHatSpawnSize/2;
			}
			System.out.println(x + " " + y);
			h.setPosition(x, y);
		}

		return hats;
	}
}
