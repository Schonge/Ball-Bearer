package com.jconnolly.ballbearer;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.jconnolly.ballbearer.resourcemanagers.MenuResourceManager;
import com.jconnolly.ballbearer.scenes.BaseScene;
import com.jconnolly.ballbearer.scenes.HelpScene;
import com.jconnolly.ballbearer.scenes.MainMenuScene;
import com.jconnolly.ballbearer.scenes.SplashScene;

/*
 * This class manages the Menu scenes
 */
public class SceneManager {
	
	//====================================================
	// VARIABLES
	//====================================================
	
	private BaseScene splashScene;
	private BaseScene menuScene;
	private BaseScene scoreScene;
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
		LEVEL_LIST_SCENE,
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
	
	public void createHelp() {
		MenuResourceManager.getMenuResMan().loadMenuResources();
		helpScene = new HelpScene();
		currentScene = helpScene;
		setScene(helpScene);
	}
	
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
	
	// Sets the scene based on which option is selected
	public void setScene(SceneType sceneType) {
		switch(sceneType) {
		case MENU_SCENE:
			setScene(menuScene);
			break;
		case SCORE_SCENE:
			setScene(scoreScene);
			break;
		case HELP_SCENE:
			setScene(helpScene);
		default:
			break;
		}
	}
	
	// Gets instance of SceneManager.java
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
