package com.jconnolly.ballbearer;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.jconnolly.ballbearer.LevelListManger.LevelType;
import com.jconnolly.ballbearer.resourcemanagers.GameResourceManager;
import com.jconnolly.ballbearer.scenes.BaseLevelScene;
import com.jconnolly.ballbearer.scenes.LevelOne;
import com.jconnolly.ballbearer.scenes.LoadingScene;

public class GameScreenManager {
	
	private BaseLevelScene loadingScene;
	private BaseLevelScene levelOne;
	
	private static final GameScreenManager GAME_SCREEN_MAN = new GameScreenManager();
	
	private BaseLevelScene currentScene;
	private LevelType currentSceneType;
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
		levelOne = new LevelOne();
		currentScene = levelOne;
		setScene(levelOne);
	}
	
	public void disposeLoading() {
		GameResourceManager.getGameResMan().unloadLoadingResources();
		loadingScene.destroyScene();
		loadingScene = null;
	}
	
	//====================================================
	// GETTERS AND SETTERS
	//====================================================
	
	public void setScene(BaseLevelScene scene) {
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getLevelType();
	}
	
	public void setScene(GameSceneType sceneType) {
		switch(sceneType) {
		case LEVEL_SCENE:
			setScene(levelOne);
			break;
		default:
			break;
		}
	}
	
	public static GameScreenManager getGameScreenMan() {
		return GAME_SCREEN_MAN;
	}
	
	public BaseLevelScene getCurrentScene() {
		return currentScene;
	}
	
	public LevelType getLevelType() {
		return currentSceneType;
	}
}
