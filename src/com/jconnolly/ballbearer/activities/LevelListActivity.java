package com.jconnolly.ballbearer.activities;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.Intent;
import android.view.KeyEvent;

import com.jconnolly.ballbearer.LevelListManger;
import com.jconnolly.ballbearer.resourcemanagers.LevelListResourceManager;

/*
 * This class creates the activity for the list of levels
 */
public class LevelListActivity extends BaseGameActivity {

	//=====================================================
	// VARIABLES
	//=====================================================
	
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	
	
	private Camera camera;
	
	//=====================================================
	// METHODS
	//=====================================================
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engOpt = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);
		// Enables dithering for the whole engine for better quality images especially when using gradients
		engOpt.getRenderOptions().setDithering(true);
		engOpt.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engOpt.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engOpt;
	}
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new LimitedFPSEngine(pEngineOptions, 60);
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		LevelListResourceManager.prepareManager(getEngine(), this.camera, getVertexBufferObjectManager(), this);
		LevelListResourceManager.getLevelListResMan();
		pOnCreateResourcesCallback.onCreateResourcesFinished();		
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		LevelListManger.getLevelListMan().createLevelList(pOnCreateSceneCallback);
		
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(this.isGameLoaded()) {
			System.exit(0);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			LevelListManger.getLevelListMan().getCurrentLevel().onBackPressed();
		}
		return false;
	}
	
	//=====================================================
	// LEVEL CHOICES
	// These methods change the activities from the level list
	// to the level selected
	//=====================================================
	
	public void listToLevelOne() {
		LevelListResourceManager.getLevelListResMan().unloadLevelListResources();
		Intent levelOne = new Intent("com.jconnolly.ballbearer.activities.LEVELONEACTIVITY");
		startActivity(levelOne);
	}
	
	public void listToLevelTwo() {
		LevelListResourceManager.getLevelListResMan().unloadLevelListResources();
		Intent levelTwo = new Intent("com.jconnolly.ballbearer.activities.LEVELTWOACTIVITY");
		startActivity(levelTwo);
	}
	
	public void listToLevelThree() {
		LevelListResourceManager.getLevelListResMan().unloadLevelListResources();
		Intent levelThree = new Intent("com.jconnolly.ballbearer.activities.LEVELTHREEACTIVITY");
		startActivity(levelThree);
	}
	
	public void listToLevelFour() {
		LevelListResourceManager.getLevelListResMan().unloadLevelListResources();
		Intent levelFour = new Intent("com.jconnolly.ballbearer.activities.LEVELFOURACTIVITY");
		startActivity(levelFour);
	}

}
