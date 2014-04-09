package com.jconnolly.ballbearer.activities;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.jconnolly.ballbearer.resourcemanagers.LevelFourResourceManager;

/*
 * This class is the activity created for Level Four
 */
public class LevelFourActivity extends BaseGameActivity implements SensorEventListener {
	
	//=================================================
	// CONSTANTS
	//=================================================
	
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	
	//=================================================
	// VARIABLES
	//=================================================
	
	private Camera camera;
	private Scene level;
	
	private SensorManager sensorManager;
    private float tiltSpeedX;
    private float tiltSpeedY;
    
    public boolean levelComplete = false;
	
    private int score = 0;
    private int scoreObjRemaining = 3;
    
    private HUD gameHUD;
	private Text scoreText;
	private PhysicsWorld physicsWorld;
	
	// Sprites
	private Sprite background;
	private Sprite ball;
	
	// Obstacles
	private Sprite block;
	private Sprite block2;
	private Sprite block3;
	private Sprite block4;
	private Sprite block5;
	private Sprite trap;
	private Sprite trap2;
	private Sprite trap3;
	private Sprite trap4;
	
	// Boundary Walls
	private Rectangle bottom;
	private Rectangle top;
	private Rectangle right;
	private Rectangle left;
	
	//=================================================
	// METHODS
	//=================================================

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
		LevelFourResourceManager.prepareManager(getEngine(), this.camera, getVertexBufferObjectManager(), this);
		LevelFourResourceManager.getLvlFourResMan().loadGameResources();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		background = new Sprite(0, 0, LevelFourResourceManager.getLvlFourResMan().levelBackTR, mEngine.getVertexBufferObjectManager());
		level = new Scene();
		level.setBackground(new SpriteBackground(background));
		
		sensorManager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
		
		physicsWorld = new PhysicsWorld(new Vector2(0, 0), false);
		this.level.registerUpdateHandler(physicsWorld);
		createWalls();
		createObstacles();
		createHUD();
		pOnCreateSceneCallback.onCreateSceneFinished(level);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		ball = new Sprite(200, 300, LevelFourResourceManager.getLvlFourResMan().ballTR, mEngine.getVertexBufferObjectManager());
		
		final FixtureDef BALL_FIX = PhysicsFactory.createFixtureDef(0.0f, 0.4f, 0.0f);
		
		Body ballBody = PhysicsFactory.createCircleBody(physicsWorld, ball, BodyType.DynamicBody, BALL_FIX);
		this.level.attachChild(ball);
		
		final Sprite scoreObject = new Sprite(10, 10, LevelFourResourceManager.getLvlFourResMan().finishTR, mEngine.getVertexBufferObjectManager()) {

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if(ball.collidesWith(this)) {
					this.setVisible(false);
					addToScore(10);
					setIgnoreUpdate(true);
					scoreObjRemaining--;
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
			
		};
		
		final Sprite scoreObject2 = new Sprite(500, 200, LevelFourResourceManager.getLvlFourResMan().finishTR, mEngine.getVertexBufferObjectManager()) {

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if(ball.collidesWith(this)) {
					this.setVisible(false);
					addToScore(10);
					setIgnoreUpdate(true);
					scoreObjRemaining--;
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
			
		};
		
		final Sprite scoreObject3 = new Sprite(700, 360, LevelFourResourceManager.getLvlFourResMan().finishTR, mEngine.getVertexBufferObjectManager()) {

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if(ball.collidesWith(this)) {
					this.setVisible(false);
					addToScore(10);
					setIgnoreUpdate(true);
					scoreObjRemaining--;
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
			
		};
		// Disables rendering of sprite when not visible
		scoreObject.setCullingEnabled(true);
		scoreObject2.setCullingEnabled(true);
		scoreObject3.setCullingEnabled(true);
		this.level.attachChild(scoreObject);
		this.level.attachChild(scoreObject2);
		this.level.attachChild(scoreObject3);
		
		this.level.registerUpdateHandler(new IUpdateHandler() {
			
			@Override
			public void reset() {	}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				if(ball.collidesWith(trap) || ball.collidesWith(trap2) || ball.collidesWith(trap3) || ball.collidesWith(trap4)) {
					System.exit(0);
				} else if(scoreObjRemaining == 0) {
					System.exit(0);
				}
				
			}
		});
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(ball, ballBody, true, false));
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
	
	private void createObstacles() {
		FixtureDef BLOCK_FIX = PhysicsFactory.createFixtureDef(0.0f, 0.0f, 0.0f);
		FixtureDef TRAP_FIX = PhysicsFactory.createFixtureDef(0.0f, 0.0f, 0.0f);
		
		// int minX = 10, maxX = 758;
		// int minY = 10, maxY = 438;
		
		block = new Sprite(400, 400, LevelFourResourceManager.getLvlFourResMan().wallTR, mEngine.getVertexBufferObjectManager());
		block2 = new Sprite(700, 400, LevelFourResourceManager.getLvlFourResMan().wallTR, mEngine.getVertexBufferObjectManager());
		block3 = new Sprite(600, 300, LevelFourResourceManager.getLvlFourResMan().wallTR, mEngine.getVertexBufferObjectManager());
		block4 = new Sprite(100, 100, LevelFourResourceManager.getLvlFourResMan().wallTR, mEngine.getVertexBufferObjectManager());
		block5 = new Sprite(300, 300, LevelFourResourceManager.getLvlFourResMan().wallTR, mEngine.getVertexBufferObjectManager());
		
		trap = new Sprite(200, 200, LevelFourResourceManager.getLvlFourResMan().trapHoleTR, mEngine.getVertexBufferObjectManager());
		trap2 = new Sprite(100, 350, LevelFourResourceManager.getLvlFourResMan().trapHoleTR, mEngine.getVertexBufferObjectManager());
		trap3 = new Sprite(510, 240, LevelFourResourceManager.getLvlFourResMan().trapHoleTR, mEngine.getVertexBufferObjectManager());
		trap4 = new Sprite(732, 187, LevelFourResourceManager.getLvlFourResMan().trapHoleTR, mEngine.getVertexBufferObjectManager());
		
		Body blockBody = PhysicsFactory.createBoxBody(physicsWorld, block, BodyType.StaticBody, BLOCK_FIX);
		Body blockBody2 = PhysicsFactory.createBoxBody(physicsWorld, block2, BodyType.StaticBody, BLOCK_FIX);
		Body blockBody3 = PhysicsFactory.createBoxBody(physicsWorld, block3, BodyType.StaticBody, BLOCK_FIX);
		Body blockBody4 = PhysicsFactory.createBoxBody(physicsWorld, block4, BodyType.StaticBody, BLOCK_FIX);
		Body blockBody5 = PhysicsFactory.createBoxBody(physicsWorld, block5, BodyType.StaticBody, BLOCK_FIX);
		Body trapBody = PhysicsFactory.createCircleBody(physicsWorld, trap, BodyType.StaticBody, TRAP_FIX);
		Body trapBody2 = PhysicsFactory.createCircleBody(physicsWorld, trap2, BodyType.StaticBody, TRAP_FIX);
		Body trapBody3 = PhysicsFactory.createCircleBody(physicsWorld, trap3, BodyType.StaticBody, TRAP_FIX);
		Body trapBody4 = PhysicsFactory.createCircleBody(physicsWorld, trap4, BodyType.StaticBody, TRAP_FIX);
		
		level.attachChild(block);
		level.attachChild(block2);
		level.attachChild(block3);
		level.attachChild(block4);
		level.attachChild(block5);
		level.attachChild(trap);
		level.attachChild(trap2);
		level.attachChild(trap3);
		level.attachChild(trap4);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(block, blockBody, false, false));
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(block2, blockBody2, false, false));
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(block3, blockBody3, false, false));
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(block4, blockBody4, false, false));
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(block5, blockBody5, false, false));
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(trap, trapBody, false, false));
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(trap2, trapBody2, false, false));
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(trap3, trapBody3, false, false));
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(trap3, trapBody4, false, false));
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
	
	private void createHUD() {
		gameHUD = new HUD();		
		
		// Score Text
		scoreText = new Text(64, 11, LevelFourResourceManager.getLvlFourResMan().simpFont , "Score: 0123456789",
				new TextOptions(HorizontalAlign.LEFT), mEngine.getVertexBufferObjectManager());
		scoreText.setText("Score: 0");
		gameHUD.attachChild(scoreText);
		
		camera.setHUD(gameHUD);
	}
	
	private void addToScore(int i) {
		score += i;
		scoreText.setText("Score: " + score);
	}

}
