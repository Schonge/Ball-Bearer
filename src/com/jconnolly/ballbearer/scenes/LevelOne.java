package com.jconnolly.ballbearer.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.HorizontalAlign;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.jconnolly.ballbearer.LevelListManger.LevelType;
import com.jconnolly.ballbearer.level.LevelManager;
import com.jconnolly.ballbearer.resourcemanagers.GameResourceManager;

public class LevelOne extends BaseLevelScene {
	
	private HUD gameHUD;
	private Text scoreText;
	
	// private int score = 0;
	// private boolean levelComplete;
	
	private PhysicsWorld physicsWorld;
	private LevelManager lvlMan;
	
	private Sprite background;
	private Sprite ball;
	
	private Body ballBody;
	
	@Override
	public void createScene() {
		lvlMan = new LevelManager(levelListActivity.getAssets());
		createBackground();
		createHUD();
		createPhysics();
		addPlayer();
		createLevel();
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
		
		// Score Text
		scoreText = new Text(64, 4, GameResourceManager.getGameResMan().simpFont , "Score: 0123456789",
				new TextOptions(HorizontalAlign.LEFT), vbom);
		scoreText.setText("Score: 0");
		gameHUD.attachChild(scoreText);
		
		camera.setHUD(gameHUD);
	}
	
	private void createPhysics() {
		physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, 0), false);
		registerUpdateHandler(physicsWorld);
	}
	
	/*private void addToScore(int i) {
		score += 1;
		scoreText.setText("Score: " + score);
	}*/
	
	private void createLevel() {
		lvlMan.loadLevel(1, this, physicsWorld);
		// levelComplete = false;
	}
	
	private void addPlayer() {
		final FixtureDef ballFixtureDef = PhysicsFactory.createFixtureDef(0.5f, 0.5f, 0.75f);
		ball = createSprite(736, 416, GameResourceManager.getGameResMan().ballTR, vbom);
		ballBody = PhysicsFactory.createBoxBody(physicsWorld, ball, BodyType.DynamicBody, ballFixtureDef);
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(ball, ballBody, true, false));
		attachChild(ball);
	}

	@Override
	public LevelType getLevelType() {
		return null;
	}
	
	//===================================================
	// GETTERS AND SETTERS
	//===================================================

}
