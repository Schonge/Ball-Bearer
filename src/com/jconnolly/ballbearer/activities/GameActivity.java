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
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.jconnolly.ballbearer.GameScreenManager;
import com.jconnolly.ballbearer.GameScreenManager.GameSceneType;
import com.jconnolly.ballbearer.resourcemanagers.GameResourceManager;
import com.jconnolly.ballbearer.resourcemanagers.MenuResourceManager;

public class GameActivity extends BaseGameActivity implements SensorEventListener {

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	
	private Camera camera;
	//private Scene level;
	
	private SensorManager sensorMan;
	private PhysicsWorld physicsWorld;
	
	private float tiltSpeedX;
	private float tiltSpeedY;
	
	//private Sprite ball;
	//private Body ballBody;

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
		//GameResourceManager.prepareManager(getEngine(), this.camera, getVertexBufferObjectManager(), this);
		GameResourceManager.getGameResMan();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {		
		sensorMan = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
		sensorMan.registerListener(this, sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
		
		physicsWorld = new PhysicsWorld(new Vector2(0, 0), false);
		GameResourceManager.getGameResMan().engine.registerUpdateHandler(physicsWorld);
		
		MenuResourceManager.getMenuResMan().unloadMenuResources();
		GameScreenManager.getGameScreenMan().createLoadingScene(pOnCreateSceneCallback);
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
	                GameScreenManager.getGameScreenMan().createGameScene();
	                GameScreenManager.getGameScreenMan().setScene(GameSceneType.LEVEL_SCENE);
	                GameScreenManager.getGameScreenMan().disposeLoading();
	            }
	    }));
	    pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		tiltSpeedX = event.values[1];
		tiltSpeedY = event.values[0];
		final Vector2 gravity = Vector2Pool.obtain(tiltSpeedX, tiltSpeedY);
		this.physicsWorld.setGravity(gravity);
		Vector2Pool.recycle(gravity);
	}
	
	//================================================
	// METHODS
	//================================================

}
