package com.jconnolly.ballbearer;

import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.jconnolly.ballbearer.resourcemanagers.LevelListResourceManager;
import com.jconnolly.ballbearer.resourcemanagers.MenuResourceManager;
import com.jconnolly.ballbearer.scenes.BaseLevelScene;
import com.jconnolly.ballbearer.scenes.LevelListScene;

/*
 * This class manages the scenes for the Level List
 * It only consists of one scene 
 */
public class LevelListManger {

	//====================================================
	// VARIABLES
	//====================================================
	
	private BaseLevelScene levelListMenu;
	
	private static final LevelListManger LEVEL_LIST_MAN = new LevelListManger();
	
	private BaseLevelScene currentLevel;
	private LevelType currentLevelType;
	private Engine engine = LevelListResourceManager.getLevelListResMan().engine;
	
	public enum LevelType {
		LEVEL_LIST_SCENE,
	}
	
	//====================================================
	// METHODS
	//====================================================
	
	// Creates the scene from the resources
	public void createLevelList(OnCreateSceneCallback pOnCreateSceneCallback) {
		LevelListResourceManager.getLevelListResMan().loadLevelListResources();
		levelListMenu = new LevelListScene();
		currentLevel = levelListMenu;
		pOnCreateSceneCallback.onCreateSceneFinished(levelListMenu);
	}
	
	// Disposes of the scene and unloads the resources
	public void disposeLevelList() {
		MenuResourceManager.getMenuResMan().unloadMenuResources();
		levelListMenu.destroyScene();
		levelListMenu = null;
	}
	
	//====================================================
	// GETTERS AND SETTERS
	//====================================================
	
	public void setScene(BaseLevelScene scene) {
		engine.setScene(scene);
		currentLevel = scene;
		currentLevelType = scene.getLevelType();
	}
	
	public void setScene(LevelType levelType) {
		switch(levelType) {
		case LEVEL_LIST_SCENE:
			setScene(levelListMenu);
		default:
			break;
		}
	}
	
	public static LevelListManger getLevelListMan() {
		return LEVEL_LIST_MAN;
	}
	
	public BaseLevelScene getCurrentLevel() {
		return currentLevel;
	}
	
	public LevelType getLevelType() {
		return currentLevelType;
	}
	
}
