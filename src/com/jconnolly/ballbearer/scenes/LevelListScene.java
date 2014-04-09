package com.jconnolly.ballbearer.scenes;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;

import com.jconnolly.ballbearer.LevelListManger.LevelType;
import com.jconnolly.ballbearer.resourcemanagers.LevelListResourceManager;

/*
 * This class is used to display the list of levels available
 * when the play button is touched in the main menu.
 * Levels are selected from this scene.
 */
public class LevelListScene extends BaseLevelScene implements IOnMenuItemClickListener {

	//=====================================================
	// VARIABLES
	//=====================================================
	
	private MenuScene menuChild;
	private final int LEVEL_ONE = 0;
	private final int LEVEL_TWO = 1;
	private final int LEVEL_THREE = 2;
	private final int LEVEL_FOUR = 3;
	
	
	private Sprite background;
	
	//=====================================================
	// METHODS
	//=====================================================

	@Override
	public void createScene() {
		createLevelList();
	}
	
	@Override
	public LevelType getLevelType() {
		return LevelType.LEVEL_LIST_SCENE;
	}

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
	
	// This method creates the selection of levels available
	public void createLevelList() {
		// Background
		this.background = new Sprite(0, 0, LevelListResourceManager.getLevelListResMan().levelListBackTR, vbom);
		attachChild(this.background);
						
		// Menu buttons etc.
		menuChild = new MenuScene(camera);
		menuChild.setPosition(0, 0);
						
		// ScaleMenuItemDecorator provides a scale animation for menu buttons when pressed
		final IMenuItem levelOneItem = new ScaleMenuItemDecorator(new SpriteMenuItem(LEVEL_ONE, LevelListResourceManager.getLevelListResMan().levelOneBtnTR, vbom), 1.2f, 1);
		final IMenuItem levelTwoItem = new ScaleMenuItemDecorator(new SpriteMenuItem(LEVEL_TWO, LevelListResourceManager.getLevelListResMan().levelTwoBtnTR, vbom), 1.2f, 1);
		final IMenuItem levelThreeItem = new ScaleMenuItemDecorator(new SpriteMenuItem(LEVEL_THREE, LevelListResourceManager.getLevelListResMan().levelThreeBtnTR, vbom), 1.2f, 1);
		final IMenuItem levelFourItem = new ScaleMenuItemDecorator(new SpriteMenuItem(LEVEL_FOUR, LevelListResourceManager.getLevelListResMan().levelFourBtnTR, vbom), 1.2f, 1);
		menuChild.addMenuItem(levelOneItem);
		menuChild.addMenuItem(levelTwoItem);
		menuChild.addMenuItem(levelThreeItem);
		menuChild.addMenuItem(levelFourItem);
						
		menuChild.buildAnimations();
		menuChild.setBackgroundEnabled(false);
		// Sets the position of the buttons
		levelOneItem.setPosition(levelOneItem.getX(), levelOneItem.getY());
		levelTwoItem.setPosition(levelTwoItem.getX(), levelTwoItem.getY() + 15);
		levelThreeItem.setPosition(levelThreeItem.getX(), levelThreeItem.getY() + 30);
		levelFourItem.setPosition(levelFourItem.getX(), levelFourItem.getY() + 45);
						
							
		menuChild.setOnMenuItemClickListener(this);
		setChildScene(menuChild);
	}
	
	// This method uses the level selected to change to the selected level activity.
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
		case LEVEL_ONE:
			levelListActivity.listToLevelOne();
			destroyScene();
			return true;
		case LEVEL_TWO:
			levelListActivity.listToLevelTwo();
			destroyScene();
			return true;
		case LEVEL_THREE:
			levelListActivity.listToLevelThree();
			destroyScene();
			return true;
		case LEVEL_FOUR:
			levelListActivity.listToLevelFour();
			destroyScene();
			return true;
		}
		return false;
	}

}
