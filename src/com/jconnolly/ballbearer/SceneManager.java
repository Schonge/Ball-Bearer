package com.jconnolly.ballbearer;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.jconnolly.ballbearer.resourcemanagers.MenuResourceManager;
import com.jconnolly.ballbearer.scenes.BaseScene;
import com.jconnolly.ballbearer.scenes.MainMenuScene;
import com.jconnolly.ballbearer.scenes.SplashScene;

public class SceneManager {
	
	private BaseScene splashScene;
	private BaseScene menuScene;
	private BaseScene scoreScene;
	private BaseScene optionsScene;
	private BaseScene helpScene;
	
	private static final SceneManager SCENE_MAN = new SceneManager();
	
	private BaseScene currentScene;
	private SceneType currentSceneType = SceneType.SPLASH_SCENE;
	private Engine engine = MenuResourceManager.getMenuResMan().engine;
	
	public enum SceneType {
		SPLASH_SCENE,
		MENU_SCENE,
		SCORE_SCENE,
		OPTIONS_SCENE,
		HELP_SCENE,
		LOADING_SCENE,
	}
	
	//====================================================
	// METHODS
	//====================================================
	
	public void createSplashScreen(OnCreateSceneCallback pOnCreateSceneCallback) {
		MenuResourceManager.getMenuResMan().loadSplashResources();
		splashScene = new SplashScene();
		currentScene = splashScene;
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}
	
	public void createMenu() {
		MenuResourceManager.getMenuResMan().loadMenuResources();
		menuScene = new MainMenuScene();
		currentScene = menuScene;
		setScene(menuScene);
	}
	
	/*public void createLoading(OnCreateSceneCallback pOnCreateSceneCallback) {
		GameResourceManager.getGameResMan().loadLoadingResources();
		loadingScene = new LoadingScene();
		setScene(loadingScene);
		disposeMenu();
	}
	
	public void createGameScreen(final Engine mEngine) {
		createLoading(null);
		MenuResourceManager.getMenuResMan().unloadMenuResources();
		mEngine.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				mEngine.registerUpdateHandler(pTimerHandler);
				GameResourceManager.getGameResMan().loadGameResources();
				gameScene = new GameScene();
				setScene(gameScene);
			}
		}));
	}
	
	public void disposeLoading() {
		GameResourceManager.getGameResMan().unloadLoadingResources();
		loadingScene.destroyScene();
		loadingScene = null;
	}*/
	
	public void disposeSplash() {
		MenuResourceManager.getMenuResMan().unloadSplashResources();
		splashScene.destroyScene();
		splashScene = null;
	}
	
	public void disposeMenu() {
		MenuResourceManager.getMenuResMan().unloadMenuResources();
		menuScene.destroyScene();
		menuScene = null;
	}
	
	//====================================================
	// GETTERS AND SETTERS
	//====================================================
	
	public void setScene(BaseScene scene) {
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}
	
	public void setScene(SceneType sceneType) {
		switch(sceneType) {
		case MENU_SCENE:
			setScene(menuScene);
			break;
		case SCORE_SCENE:
			setScene(scoreScene);
		case OPTIONS_SCENE:
			setScene(optionsScene);
			break;
		case HELP_SCENE:
			setScene(helpScene);
		default:
			break;
		}
	}
	
	public static SceneManager getSceneMan() {
		return SCENE_MAN;
	}
	
	public BaseScene getCurrentScene() {
		return currentScene;
	}
	
	public SceneType getSceneType() {
		return currentSceneType;
	}

}
