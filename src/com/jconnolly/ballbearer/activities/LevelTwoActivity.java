package com.jconnolly.ballbearer.activities;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.jconnolly.ballbearer.resourcemanagers.GameResourceManager;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class LevelTwoActivity extends BaseGameActivity implements SensorEventListener {

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	
	private Camera camera;
	private Scene level;
	
	private SensorManager sensorManager;
    private float tiltSpeedX;
    private float tiltSpeedY;
	
	//private HUD gameHUD;
	private PhysicsWorld physicsWorld;
	
	private Sprite background;
	private Sprite ball;
	private Sprite finishPoint;
	
	private Rectangle bottom;
	private Rectangle top;
	private Rectangle right;
	private Rectangle left;

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
		GameResourceManager.getGameResMan().loadGameResources();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		background = new Sprite(0, 0, GameResourceManager.getGameResMan().levelBackTR, mEngine.getVertexBufferObjectManager());
		level = new Scene();
		level.setBackground(new SpriteBackground(background));
		
		sensorManager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
		
		physicsWorld = new PhysicsWorld(new Vector2(0, 0), false);
		this.level.registerUpdateHandler(physicsWorld);
		createWalls();
		pOnCreateSceneCallback.onCreateSceneFinished(level);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		ball = new Sprite(200, 300, GameResourceManager.getGameResMan().ballTR, mEngine.getVertexBufferObjectManager());
		finishPoint = new Sprite(10, 10, GameResourceManager.getGameResMan().finishTR, mEngine.getVertexBufferObjectManager());
		
		final FixtureDef BALL_FIX = PhysicsFactory.createFixtureDef(0.0f, 0.4f, 0.0f);
		final FixtureDef FINISH_FIX = PhysicsFactory.createFixtureDef(0.0f, 0.0f, 0.0f);
		
		Body ballBody = PhysicsFactory.createCircleBody(physicsWorld, ball, BodyType.DynamicBody, BALL_FIX);
		Body finishBody = PhysicsFactory.createCircleBody(physicsWorld, finishPoint, BodyType.StaticBody, FINISH_FIX);
		this.level.attachChild(ball);
		this.level.attachChild(finishPoint);
		
		this.level.registerUpdateHandler(new IUpdateHandler() {
			
			@Override
			public void reset() {	}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				if(ball.collidesWith(finishPoint)) {
					level.setBackground(new Background(Color.WHITE));
					level.detachSelf();
					level.dispose();
				}
			}
		});
		
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(ball, ballBody, true, false));
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(finishPoint, finishBody, false, false));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
		
	}
	
	private void createWalls() {
		FixtureDef WALL_FIX = PhysicsFactory.createFixtureDef(0.0f, 0.0f, 0.0f);
		
		bottom = new Rectangle(0, CAMERA_HEIGHT-10, CAMERA_WIDTH, 10,	mEngine.getVertexBufferObjectManager());
		top = new Rectangle(0, 0, CAMERA_WIDTH, 10,	mEngine.getVertexBufferObjectManager());
		right = new Rectangle(CAMERA_WIDTH-10, 0, 10, CAMERA_HEIGHT, mEngine.getVertexBufferObjectManager());
		left = new Rectangle(0, 0, 10, CAMERA_HEIGHT,	mEngine.getVertexBufferObjectManager());
		
		bottom.setColor(Color.BLUE);
		top.setColor(Color.BLUE);
		right.setColor(Color.BLUE);
		left.setColor(Color.BLUE);
		
		PhysicsFactory.createBoxBody(physicsWorld, bottom, BodyType.StaticBody, WALL_FIX);
		PhysicsFactory.createBoxBody(physicsWorld, top, BodyType.StaticBody, WALL_FIX);
		PhysicsFactory.createBoxBody(physicsWorld, right, BodyType.StaticBody, WALL_FIX);
		PhysicsFactory.createBoxBody(physicsWorld, left, BodyType.StaticBody, WALL_FIX);
		level.attachChild(bottom);
		level.attachChild(top);
		level.attachChild(right);
		level.attachChild(left);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int acurracy) {
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

}
