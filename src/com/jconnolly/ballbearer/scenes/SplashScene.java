package com.jconnolly.ballbearer.scenes;

import org.andengine.entity.sprite.Sprite;

import com.jconnolly.ballbearer.SceneManager.SceneType;
import com.jconnolly.ballbearer.resourcemanagers.MenuResourceManager;

/*
 * This class creates the splash scene which is the scene that is shown when loading
 * up the application. It can be used to show company logos etc.
 */
public class SplashScene extends BaseScene {

	//=====================================================
	// VARIABLES
	//=====================================================
	
	private Sprite splash;
	
	//=====================================================
	// METHODS
	//=====================================================

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
