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
import org.andengine.entity.sprite.Sprite;
import org.andengine.ui.activity.BaseGameActivity;

import com.jconnolly.ballbearer.resourcemanagers.GameResourceManager;
import com.jconnolly.ballbearer.resourcemanagers.MenuResourceManager;
import com.jconnolly.ballbearer.scenes.BaseScene;
import com.jconnolly.ballbearer.scenes.GameScene;

public class GameActivity extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	
	private Camera camera;
	private Scene loadingScene;
	private BaseScene level;
	private Sprite loading;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engOpt = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
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
		GameResourceManager.prepareManager(getEngine(), this.camera, getVertexBufferObjectManager(), this);
		GameResourceManager.getGameResMan().loadLoadingResources();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		createLoadingScreen();
		pOnCreateSceneCallback.onCreateSceneFinished(this.loadingScene);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		MenuResourceManager.getMenuResMan().unloadMenuResources();
		mEngine.registerUpdateHandler(new TimerHandler(5f, new ITimerCallback() 
	    {
	            public void onTimePassed(final TimerHandler pTimerHandler) 
	            {
	                mEngine.unregisterUpdateHandler(pTimerHandler);
	                GameResourceManager.getGameResMan().loadGameResources();
	                level = new GameScene();
	                level.createScene();
	            }
	    }));
	    pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	private void createLoadingScreen() {
		loadingScene = new Scene();
		loading = new Sprite(0, 0, GameResourceManager.getGameResMan().loadingTR, mEngine.getVertexBufferObjectManager());
		loading.setPosition(240, 200);
		loadingScene.attachChild(loading);
	}

}
