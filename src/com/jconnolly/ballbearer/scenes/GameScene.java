package com.jconnolly.ballbearer.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.jconnolly.ballbearer.GameScreenManager.GameSceneType;
import com.jconnolly.ballbearer.level.LevelManager;
import com.jconnolly.ballbearer.resourcemanagers.GameResourceManager;

public class GameScene extends BaseGameScene implements IAccelerationListener {
	
	private HUD gameHUD;
	
	private PhysicsWorld physicsWorld;
	private LevelManager lvlMan;
	//private LevelGenerator levels;
	//private int[][] maze;
	
	private Sprite background;
	private Sprite ball;
	//private Sprite wall;
	
	private Body ballBody;
	
	@Override
	public void createScene() {
		lvlMan = new LevelManager(gameActivity.getAssets());
		createBackground();
		createHUD();
		createPhysics();
		addPlayer();
		createLevel();
		//createLevel();
	}

	@Override
	public GameSceneType getSceneType() {
		return GameSceneType.LEVEL_SCENE;
	}

	@Override
	public void onBackPressed() {
		System.exit(0);		
	}

	@Override
	public void destroyScene() {
		GameResourceManager.getGameResMan().unloadGameResources();
	}
	
	private void createBackground() {
		background = createSprite(0, 0, GameResourceManager.getGameResMan().levelBackTR, vbom);
		attachChild(background);
	}
	
	private void createHUD() {
		gameHUD = new HUD();
		camera.setHUD(gameHUD);
	}
	
	private void createPhysics() {
		physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, 0), false);
		registerUpdateHandler(physicsWorld);
	}
	
	private void addPlayer() {
		final FixtureDef ballFixtureDef = PhysicsFactory.createFixtureDef(0.5f, 0.5f, 0.75f);
		ball = createSprite(300, 300, GameResourceManager.getGameResMan().ballTR, vbom);
		ballBody = PhysicsFactory.createBoxBody(physicsWorld, ball, BodyType.DynamicBody, ballFixtureDef);
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(ball, ballBody, true, false));
		attachChild(ball);
	}
	
	private void createLevel() {
		lvlMan.loadLevel(1, this, physicsWorld);
	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		ball.setPosition(ball.getX() + pAccelerationData.getX(), ball.getY() + pAccelerationData.getY());
		
	}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {
		final Vector2 gravity = Vector2Pool.obtain(pAccelerationData.getX(), pAccelerationData.getY());
		this.physicsWorld.setGravity(gravity);
		Vector2Pool.recycle(gravity);
	}
	
	//===================================================
	// GETTERS AND SETTERS
	//===================================================

}
