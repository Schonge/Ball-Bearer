package com.jconnolly.ballbearer.scenes;

import org.andengine.entity.sprite.Sprite;
import com.jconnolly.ballbearer.SceneManager.SceneType;
import com.jconnolly.ballbearer.resourcemanagers.MenuResourceManager;

public class SplashScene extends BaseScene {
	
	private Sprite splash;

	@Override
	public void createScene() {
		splash = createSprite(0, 0, MenuResourceManager.getMenuResMan().splashTR, vbom);
		splash.setPosition(0, 0);
		attachChild(splash);
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.SPLASH_SCENE;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroyScene() {
		splash.detachSelf();
		splash.dispose();
		this.detachSelf();
		this.dispose();
	}

}
