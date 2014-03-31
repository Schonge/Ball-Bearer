package com.jconnolly.ballbearer.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;

import com.badlogic.gdx.math.Vector2;
import com.jconnolly.ballbearer.LevelGenerator;
import com.jconnolly.ballbearer.SceneManager.SceneType;
import com.jconnolly.ballbearer.resourcemanagers.GameResourceManager;

public class GameScene extends BaseScene {
	
	private HUD gameHUD;
	private PhysicsWorld physicsWorld;
	private LevelGenerator levels;
	private int[][] maze;
	
	private Sprite background;
	private Sprite ball;
	private Sprite wall;
	
	@Override
	public void createScene() {
		createBackground();
		createHUD();
		createPhysics();
		createLevel();
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.MENU_SCENE;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroyScene() {
		// TODO Auto-generated method stub
		
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
	
	private void createLevel() {
		createGameObjects();
		levels = new LevelGenerator();
		maze = levels.createEasyMaze();
		
		for(int i = 0; i < maze.length; i++) {
			for(int j = 0; j < maze[i].length; j++) {
				if(maze[i][j] == 1) {
					ball = createSprite(i, j, GameResourceManager.getGameResMan().ballTR, vbom);
					attachChild(ball);
				} else if(maze[i][j] == 4) {
					wall = createSprite(i, j, GameResourceManager.getGameResMan().wallTR, vbom);
					attachChild(wall);
				}
			}
		}
	}
	
	private void createGameObjects() {
		
		
	}
	
	//===================================================
	// GETTERS AND SETTERS
	//===================================================

}
