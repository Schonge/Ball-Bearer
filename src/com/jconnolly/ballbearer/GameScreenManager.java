package com.jconnolly.ballbearer;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.jconnolly.ballbearer.LevelListManger.LevelType;
import com.jconnolly.ballbearer.resourcemanagers.LevelTwoResourceManager;
import com.jconnolly.ballbearer.scenes.BaseLevelScene;
import com.jconnolly.ballbearer.scenes.LoadingScene;

public class GameScreenManager {
	
	private BaseLevelScene loadingScene;
	private Scene levelOne;
	
	private static final GameScreenManager GAME_SCREEN_MAN = new GameScreenManager();
	
	private Scene currentScene;
	private LevelType currentSceneType;
	private Engine engine = LevelTwoResourceManager.getLvlTwoResMan().engine;
	
	public enum GameSceneType {
		LOADING_SCENE,
		LEVEL_SCENE,
	}
	
	//====================================================
	// METHODS
	//====================================================

	
	public void createLoadingScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		LevelTwoResourceManager.getLvlTwoResMan().loadLoadingResources();
		loadingScene = new LoadingScene();
		currentScene = loadingScene;
		pOnCreateSceneCallback.onCreateSceneFinished(loadingScene);
	}
	
	public void createGameScene() {
		LevelTwoResourceManager.getLvlTwoResMan().loadGameResources();
		LevelTwoResourceManager.getLvlTwoResMan().loadTileManager();
		levelOne = new Scene();
		currentScene = levelOne;
		setScene(levelOne);
	}
	
	public void disposeLoading() {
		LevelTwoResourceManager.getLvlTwoResMan().unloadLoadingResources();
		loadingScene.destroyScene();
		loadingScene = null;
	}
	
	//====================================================
	// GETTERS AND SETTERS
	//====================================================
	
	public void setScene(Scene levelOne2) {
		engine.setScene(levelOne2);
		currentScene = levelOne2;
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
	
	public Scene getCurrentScene() {
		return currentScene;
	}
	
	public LevelType getLevelType() {
		return currentSceneType;
	}
}
