package com.jconnolly.ballbearer.activities;


import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import com.jconnolly.ballbearer.SceneManager;
import com.jconnolly.ballbearer.resourcemanagers.MenuResourceManager;

import android.content.Intent;
import android.view.KeyEvent;

public class MenuActivity extends BaseGameActivity {
	
	//=====================================================
	// VARIABLES
	//=====================================================

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	
	
	private Camera camera;
	
	//=====================================================
	// METHODS
	//=====================================================
	
	// Creates the game engine otions for the menu
	@Override
	public EngineOptions onCreateEngineOptions() {
		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		// Sets the engin options such as screen orientation and camera
		EngineOptions engOpt = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camera);
		// Enables dithering for the whole engine for better quality images especially when using gradients
		engOpt.getRenderOptions().setDithering(true);
		engOpt.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engOpt.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engOpt;
	}
	
	// Creates the engine using a predefined engine type
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		// Always tries to run at 60 frames per second
		return new LimitedFPSEngine(pEngineOptions, 60);
	}

	// Creates the resources from the Menu Resource Manager
	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		MenuResourceManager.prepareManager(getEngine(), this.camera, getVertexBufferObjectManager(), this);
		MenuResourceManager.getMenuResMan();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	// Created the Splash Scene
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		SceneManager.getSceneMan().createSplashScreen(pOnCreateSceneCallback);
	}

	// Populates the menu scene with sprites, graphics, sounds etc.
	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		// Displays Splash for 5 seconds while loadin the menu resources.
		mEngine.registerUpdateHandler(new TimerHandler(5f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				// Loads menu resources
				MenuResourceManager.getMenuResMan().loadMenuResources();
				// Creates the menu
				SceneManager.getSceneMan().createMenu();
				// Disposes of the splash screen
				SceneManager.getSceneMan().disposeSplash();
			}
		}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(this.isGameLoaded()) {
			System.exit(0);
		}
	}
	
	// If device back button is pressed it returns to previous scene
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			SceneManager.getSceneMan().getCurrentScene().onBackPressed();
		}
		return false;
	}
	
	// Changes activity, using Intents from menu to level list when "Play" is touched
	public void menuToLevelList() {
		MenuResourceManager.getMenuResMan().unloadMenuResources();
		Intent menuIntent = new Intent("com.jconnolly.ballbearer.activities.LEVELLISTACTIVITY");
		startActivity(menuIntent);
	}

}
