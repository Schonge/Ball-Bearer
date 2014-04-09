package com.jconnolly.ballbearer.scenes;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;

import com.jconnolly.ballbearer.SceneManager;
import com.jconnolly.ballbearer.SceneManager.SceneType;
import com.jconnolly.ballbearer.resourcemanagers.MenuResourceManager;

/*
 * This class creates the application menu scene
 */
public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener {
	
	//=====================================================
	// VARIABLES
	//=====================================================
	
	private MenuScene menuChild;
	private final int MENU_PLAY = 0;
	private final int MENU_SCORE = 1;
	private final int MENU_HELP = 2;
	
	private Sprite background;

	//=====================================================
	// METHODS
	//=====================================================
	
	@Override
	public void createScene() {
		createMenu();
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.MENU_SCENE;
	}

	// Closes the application
	@Override
	public void onBackPressed() {
		System.exit(0);
	}

	@Override
	public void destroyScene() {
		background.detachSelf();
		background.dispose();
		this.detachSelf();
		this.dispose();
	}
	
	// This method creates the menu items and objects such as the touchable buttons
	public void createMenu() {
		// Background
		this.background = new Sprite(0, 0, MenuResourceManager.getMenuResMan().menuBackTR, vbom);
		attachChild(this.background);
		
		// Menu buttons etc.
		menuChild = new MenuScene(camera);
		menuChild.setPosition(0, 0);
		
		// ScaleMenuItemDecorator provides a scale animation for menu buttons when pressed
		final IMenuItem playItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, MenuResourceManager.getMenuResMan().playBtnTR, vbom), 1.2f, 1);
		final IMenuItem scoreItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_SCORE, MenuResourceManager.getMenuResMan().scoreBtnTR, vbom), 1.2f, 1);
		final IMenuItem helpItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_HELP, MenuResourceManager.getMenuResMan().helpBtnTR, vbom), 1.2f, 1);
		menuChild.addMenuItem(playItem);
		menuChild.addMenuItem(scoreItem);
		menuChild.addMenuItem(helpItem);
		
		menuChild.buildAnimations();
		menuChild.setBackgroundEnabled(false);
		playItem.setPosition(playItem.getX(), playItem.getY());
		scoreItem.setPosition(scoreItem.getX(), scoreItem.getY() + 30);
		helpItem.setPosition(helpItem.getX(), helpItem.getY() + 60);
		
			
		menuChild.setOnMenuItemClickListener(this);
		setChildScene(menuChild);
	}

	// This method switches between scenes based on which button was touched
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
		case MENU_PLAY:
			// Load the Game
			menuActivity.menuToLevelList();
			destroyScene();
			return true;
		case MENU_SCORE:
			return true;
		case MENU_HELP:
			SceneManager.getSceneMan().createHelp();
			return true;
		}
		return false;
	}

}
