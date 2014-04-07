package com.jconnolly.ballbearer.scenes;

import org.andengine.entity.sprite.Sprite;

import com.jconnolly.ballbearer.LevelListManger.LevelType;
import com.jconnolly.ballbearer.resourcemanagers.LevelTwoResourceManager;

public class LoadingScene extends BaseLevelScene {
	
	private Sprite loading;

	@Override
	public void createScene() {
		loading = createSprite(0, 0, LevelTwoResourceManager.getLvlTwoResMan().loadingTR, vbom);
		loading.setPosition(240, 200);
		attachChild(loading);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroyScene() {
		loading.detachSelf();
		loading.dispose();
		this.detachSelf();
		this.dispose();
	}

	@Override
	public LevelType getLevelType() {
		return null;
	}

	

}
