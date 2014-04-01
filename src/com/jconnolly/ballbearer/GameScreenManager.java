package com.jconnolly.ballbearer;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.jconnolly.ballbearer.resourcemanagers.GameResourceManager;
import com.jconnolly.ballbearer.scenes.BaseGameScene;
import com.jconnolly.ballbearer.scenes.GameScene;
import com.jconnolly.ballbearer.scenes.LoadingScene;

public class GameScreenManager {
	
	private BaseGameScene loadingScene;
	private BaseGameScene gameScene;
	
	private static final GameScreenManager GAME_SCREEN_MAN = new GameScreenManager();
	
	private BaseGameScene currentScene;
	private GameSceneType currentSceneType = GameSceneType.LOADING_SCENE;
	private Engine engine = GameResourceManager.getGameResMan().engine;
	
	public enum GameSceneType {
		LOADING_SCENE,
		LEVEL_SCENE,
	}
	
	//====================================================
	// METHODS
	//====================================================

	
	public void createLoadingScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		GameResourceManager.getGameResMan().loadLoadingResources();
		loadingScene = new LoadingScene();
		currentScene = loadingScene;
		pOnCreateSceneCallback.onCreateSceneFinished(loadingScene);
	}
	
	public void createGameScene() {
		GameResourceManager.getGameResMan().loadGameResources();
		GameResourceManager.getGameResMan().loadTileManager();
		gameScene = new GameScene();
		currentScene = gameScene;
		setScene(gameScene);
	}
	
	public void disposeLoading() {
		GameResourceManager.getGameResMan().unloadLoadingResources();
		loadingScene.destroyScene();
		loadingScene = null;
	}
	
	//====================================================
	// GETTERS AND SETTERS
	//====================================================
	
	public void setScene(BaseGameScene scene) {
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}
	
	public void setScene(GameSceneType sceneType) {
		switch(sceneType) {
		case LEVEL_SCENE:
			setScene(gameScene);
			break;
		default:
			break;
		}
	}
	
	public static GameScreenManager getGameScreenMan() {
		return GAME_SCREEN_MAN;
	}
	
	public BaseGameScene getCurrentScene() {
		return currentScene;
	}
	
	public GameSceneType getSceneType() {
		return currentSceneType;
	}
}
